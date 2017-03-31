package com.teamwizardry.librarianlib.core.client

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.teamwizardry.librarianlib.core.LibrarianLib
import com.teamwizardry.librarianlib.core.LibrarianLog
import com.teamwizardry.librarianlib.core.common.LibLibConfig
import com.teamwizardry.librarianlib.core.common.OwnershipHandler
import com.teamwizardry.librarianlib.features.base.IExtraVariantHolder
import com.teamwizardry.librarianlib.features.base.IModelGenerator
import com.teamwizardry.librarianlib.features.base.IVariantHolder
import com.teamwizardry.librarianlib.features.base.block.IBlockColorProvider
import com.teamwizardry.librarianlib.features.base.block.IModBlockProvider
import com.teamwizardry.librarianlib.features.base.item.IItemColorProvider
import com.teamwizardry.librarianlib.features.base.item.IModItemProvider
import com.teamwizardry.librarianlib.features.base.item.ISpecialModelProvider
import com.teamwizardry.librarianlib.features.helpers.VariantHelper
import com.teamwizardry.librarianlib.features.kotlin.serialize
import com.teamwizardry.librarianlib.features.kotlin.times
import com.teamwizardry.librarianlib.features.methodhandles.MethodHandleHelper
import com.teamwizardry.librarianlib.features.utilities.JsonGenerationUtils
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ItemMeshDefinition
import net.minecraft.client.renderer.block.model.ModelBakery
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.RegistryDelegate
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.io.File
import java.util.*

/**
 * @author WireSegal
 * Created at 2:12 PM on 3/20/16.
 */
object ModelHandler {

    fun serialize(el: JsonElement)
            = if (LibLibConfig.prettyJsonSerialization) el.serialize() else el.toString() + "\n"

    // Easy access
    private val debug = LibrarianLib.DEV_ENVIRONMENT
    private var modName = ""
    val namePad: String
        get() = " " * modName.length

    var generatedFiles = mutableListOf<String>()

    private val variantCache = HashMap<String, MutableList<IVariantHolder>>()

    /**
     * This is Mod name -> (Variant name -> MRL), specifically for ItemMeshDefinitions.
     */
    @SideOnly(Side.CLIENT)
    lateinit var resourceLocations: HashMap<String, HashMap<String, ResourceLocation>>

    var gennedResources = false
        private set

    /**
     * Use this method to inject your item into the list to be loaded at the end of preinit and colorized at the end of init.
     */
    @JvmStatic
    fun registerVariantHolder(holder: IVariantHolder) {
        val name = Loader.instance().activeModContainer()?.modId ?: return
        variantCache.getOrPut(name) { mutableListOf() }.add(holder)
    }

    @SideOnly(Side.CLIENT)
    private fun addToCachedLocations(name: String, mrl: ModelResourceLocation) {
        if (!gennedResources) {
            resourceLocations = hashMapOf()
            gennedResources = true
        }
        resourceLocations.getOrPut(modName) { hashMapOf() }.put(name, mrl)
    }

    @SideOnly(Side.CLIENT)
    fun preInit() {
        MinecraftForge.EVENT_BUS.register(this)

        for ((modid, holders) in variantCache) {
            modName = modid
            log("$modName | Registering models")
            for (holder in holders.sortedBy { (255 - it.variants.size).toChar() + if (it is IModBlockProvider) "b" else "I" + if (it is IModItemProvider) it.providedItem.registryName.resourcePath else "" }) {
                registerModels(holder)
            }
        }

        if (generatedFiles.isNotEmpty() && debug) {
            val home = System.getProperty("user.home")
            generatedFiles = generatedFiles.map { if (it.startsWith(home) && home.isNotBlank()) (if (home.endsWith("/")) "~/" else "~") + it.substring(home.length) else it }.toMutableList()
            val starBegin = "**** THIS IS NOT AN ERROR ****"
            val starPad = (generatedFiles.fold(64) { max, it -> Math.max(max, it.length) } - starBegin.length + 3) * "*"

            print("\n$starBegin$starPad\n")
            print("* ${if (generatedFiles.size == 1) "One file was" else "${generatedFiles.size} files were"} generated by the model loader system.\n")
            print("* Restart the client to lock in the changes.\n")
            print("* Generated files:\n")
            for (file in generatedFiles)
                print("** $file\n")
            print("$starBegin$starPad\n\n")
            FMLCommonHandler.instance().handleExit(0)
        }
    }

    @SideOnly(Side.CLIENT)
    fun init() {
        val itemColors = Minecraft.getMinecraft().itemColors
        val blockColors = Minecraft.getMinecraft().blockColors
        for ((modid, holders) in variantCache) {
            modName = modid
            var flag = false
            for (holder in holders) {
                if (holder is IItemColorProvider && holder is IModItemProvider) {
                    val color = holder.itemColorFunction
                    if (color != null) {
                        if (!flag) {
                            log("$modName | Registering colors")
                            flag = true
                        }
                        log("$namePad | Registering item color for ${holder.providedItem.registryName.resourcePath}")
                        itemColors.registerItemColorHandler(IItemColor(color), holder.providedItem)
                    }
                }

                if (holder is IModBlockProvider && holder is IBlockColorProvider) {
                    val color = holder.blockColorFunction
                    if (color != null) {
                        if (!flag) {
                            log("$modName | Registering colors")
                            flag = true
                        }
                        log("$namePad | Registering block color for ${holder.providedBlock.registryName.resourcePath}")
                        blockColors.registerBlockColorHandler(IBlockColor(color), holder.providedBlock)
                    }
                }

            }
        }
    }

    @SideOnly(Side.CLIENT)
    fun registerModels(holder: IVariantHolder) {
        if (holder is IExtraVariantHolder)
            registerModels(holder, holder.extraVariants, true)

        if (holder is IModItemProvider) {
            val meshDef = holder.meshDefinition
            if (meshDef != null) {
                ModelLoader.setCustomMeshDefinition(holder.providedItem, ItemMeshDefinition(meshDef))
                return
            }
        }

        registerModels(holder, holder.variants, false)

    }

    @SideOnly(Side.CLIENT)
    fun registerModels(holder: IVariantHolder, variants: Array<out String>, extra: Boolean) {
        if (holder is IModBlockProvider && !extra) {
            val mapper = holder.stateMapper
            if (mapper != null)
                ModelLoader.setCustomStateMapper(holder.providedBlock, mapper)

            if (shouldGenerateAnyJson()) generateBlockJson(holder, mapper)
        }

        if (holder is IModItemProvider) {
            val item = holder.providedItem
            for ((index, variantName) in variants.withIndex()) {
                val variant = VariantHelper.toSnakeCase(variantName)

                if (index == 0) {
                    var print = "${namePad} | Registering "

                    if (variant != item.registryName.resourcePath || variants.size != 1 || extra)
                        print += "${if (extra) "extra " else ""}variant${if (variants.size == 1) "" else "s"} of "

                    print += if (item is IModBlockProvider) "block" else "item"
                    print += " ${item.registryName.resourcePath}"
                    log(print)
                }

                if (holder is ISpecialModelProvider && holder.getSpecialModel(index) != null) {
                    log("$namePad |  Variant #${index + 1}: $variant - SPECIAL")
                    continue
                }

                if ((variant != item.registryName.resourcePath || variants.size != 1))
                    log("$namePad |  Variant #${index + 1}: $variant")

                if (shouldGenItemJson(holder)) generateItemJson(holder, variant)

                val model = ModelResourceLocation(ResourceLocation(modName, variant).toString(), "inventory")
                if (!extra) {
                    ModelLoader.setCustomModelResourceLocation(item, index, model)
                    addToCachedLocations(getKey(item, index), model)
                } else {
                    ModelBakery.registerItemVariants(item, model)
                    addToCachedLocations(variantName, model)
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun onModelBake(e: ModelBakeEvent) {
        val customModelGetter = MethodHandleHelper.wrapperForStaticGetter(ModelLoader::class.java, "customModels")
        @Suppress("UNCHECKED_CAST")
        val customModels = customModelGetter.invoke() as MutableMap<Pair<RegistryDelegate<Item>, Int>, ModelResourceLocation>

        for ((modid, holders) in variantCache) {
            modName = modid
            var hasRegisteredAny = false
            for (holder in holders) if (holder is ISpecialModelProvider) {
                val item = holder.providedItem
                var flag = false
                for ((index, variant) in holder.variants.withIndex()) {
                    val model = holder.getSpecialModel(index)
                    if (model != null) {
                        if (!hasRegisteredAny) {
                            log("$modName | Registering special models")
                            hasRegisteredAny = true
                        }
                        if (!flag) {
                            var print = "${namePad} | Applying special model rules for "
                            print += if (item is IModBlockProvider) "block " else "item "
                            print += item.registryName.resourcePath
                            log(print)
                            flag = true
                        }
                        val mrl = ModelResourceLocation(ResourceLocation(modName, variant).toString(), "inventory")
                        log("$namePad | Special model for variant $index - $variant applied")
                        e.modelRegistry.putObject(mrl, model)
                        customModels.put(item.delegate to index, mrl)
                        addToCachedLocations(variant, mrl)
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private fun shouldGenItemJson(provider: IVariantHolder): Boolean {
        if (!shouldGenerateAnyJson()) return false

        if (provider !is IModBlockProvider && provider !is IModItemProvider) return false

        val entry = (provider as? IModBlockProvider)?.providedBlock ?: (provider as IModItemProvider).providedItem

        val statePath = JsonGenerationUtils.getPathForBaseBlockstate(entry)

        val file = File(statePath)
        if (!file.exists()) return true

        val json: JsonElement
        try {
            json = JsonParser().parse(file.reader())
        } catch (t: Throwable) {
            return true
        }

        var isForge = false
        if (json.isJsonObject && json.asJsonObject.has("forge_marker")) {
            val marker = json.asJsonObject["forge_marker"]
            if (marker.isJsonPrimitive && marker.asJsonPrimitive.isNumber)
                isForge = marker.asInt != 0
        }
        if (isForge)
            log("$namePad | Assuming forge override for ${entry.getRegistryName().resourcePath} item model")
        return !isForge
    }

    @SideOnly(Side.CLIENT)
    fun generateItemJson(holder: IModItemProvider, variant: String) {
        if (holder is IModelGenerator && holder.generateMissingItem(variant)) return

        generateItemJson(holder) {
            mapOf(JsonGenerationUtils.getPathForItemModel(holder.providedItem, variant)
                    to JsonGenerationUtils.generateBaseItemModel(holder.providedItem, variant))
        }
    }

    @SideOnly(Side.CLIENT)
    inline fun generateItemJson(holder: IModItemProvider, modelFiles: () -> Map<String, JsonElement>) {
        val files = modelFiles()
        for ((path, model) in files) {
            val file = File(path)
            file.parentFile.mkdirs()
            if (file.createNewFile()) {
                file.writeText(serialize(model))
                log("$namePad | Creating file for variant of ${holder.providedItem.registryName.resourcePath}")
                generatedFiles.add(path)
            }
        }
    }

    @SideOnly(Side.CLIENT)
    fun shouldGenerateAnyJson() = debug && LibLibConfig.generateJson && modName in OwnershipHandler.DEV_OWNED

    @SideOnly(Side.CLIENT)
    fun generateBlockJson(holder: IModBlockProvider, mapper: ((block: Block) -> Map<IBlockState, ModelResourceLocation>)?) {
        if (holder is IModelGenerator && holder.generateMissingBlockstate(mapper)) return

        generateBlockJson(holder, {
            JsonGenerationUtils.generateBaseBlockStates(holder.providedBlock, mapper)
        }, {
            mapOf(JsonGenerationUtils.getPathForBlockModel(holder.providedBlock)
                    to JsonGenerationUtils.generateBaseBlockModel(holder.providedBlock))
        })
    }

    @SideOnly(Side.CLIENT)
    inline fun generateBlockJson(holder: IModBlockProvider,
                                 blockstateFiles: () -> Map<String, JsonElement>,
                                 modelFiles: () -> Map<String, JsonElement>) {
        val files = blockstateFiles()
        var flag = false
        for ((path, model) in files) {
            val stateFile = File(path)
            stateFile.parentFile.mkdirs()
            if (stateFile.createNewFile()) {
                stateFile.writeText(serialize(model))
                ModelHandler.log("${namePad} | Creating a file for blockstate of ${holder.providedBlock.registryName.resourcePath}")
                ModelHandler.generatedFiles.add(path)
                flag = true
            }
        }
        if (flag) {
            val models = modelFiles()
            for ((path, model) in models) {
                val modelFile = File(path)
                modelFile.parentFile.mkdirs()
                if (modelFile.createNewFile()) {
                    modelFile.writeText(serialize(model))
                    ModelHandler.log("${ModelHandler.namePad} | Creating file for block ${holder.providedBlock.registryName.resourcePath}")
                    ModelHandler.generatedFiles.add(path)
                }
            }
        }
    }

    @JvmStatic
    fun getKey(item: Item, meta: Int): String {
        return "i_" + item.registryName + "@" + meta
    }

    fun log(text: String) {
        if (debug) LibrarianLog.info(text)
    }
}