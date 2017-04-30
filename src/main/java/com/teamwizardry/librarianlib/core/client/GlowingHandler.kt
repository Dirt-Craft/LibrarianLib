package com.teamwizardry.librarianlib.core.client

import com.teamwizardry.librarianlib.features.base.item.IGlowingItem
import com.teamwizardry.librarianlib.features.config.ConfigPropertyBoolean
import com.teamwizardry.librarianlib.features.config.ConfigPropertyStringArray
import com.teamwizardry.librarianlib.features.methodhandles.MethodHandleHelper
import com.teamwizardry.librarianlib.features.utilities.client.GlUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.RenderItem
import net.minecraft.client.renderer.block.model.IBakedModel
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionUtils
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * @author WireSegal
 * Created at 1:55 PM on 4/29/17.
 */
@SideOnly(Side.CLIENT)
object GlowingHandler {

    val parser = "(\\w+:\\w+)(?:@(-1|\\d+))?((?:,(?:-1|\\d+))+,?)?(?:\\|(false|true))?".toRegex()

    @JvmStatic
    @ConfigPropertyStringArray("librarianlib", "client", "glowing", "Items that should glow. Use modid:item@meta,tintindex1,tintindex2|disableLighting, with -1 being untinted. You can have as many tintindexes as you want.\nIf meta is -1, it'll act as a wildcard. If no tintindices are supplied it'll use any.",
            arrayOf("minecraft:glowstone|false",
                    "minecraft:glowstone_dust",
                    "minecraft:blaze_rod",
                    "minecraft:blaze_powder",
                    "minecraft:sea_lantern|false",
                    "minecraft:prismarine_crystals",
                    "minecraft:end_rod|false",
                    "quark:blaze_lantern|false"))
    private var glowingItems = arrayOf<String>()


    @JvmStatic
    @ConfigPropertyBoolean("librarianlib", "client", "potion_glow", "Whether to use the custom potion glow handler.", true)
    private var potionGlow = false

    @JvmStatic
    @ConfigPropertyBoolean("librarianlib", "client", "enchantmnet_glow", "Whether to make enchantments use the glow handler.", true)
    var enchantmentGlow = false
        private set

    fun init() {
        val names = mutableMapOf<String, MutableMap<String, Pair<List<String>, Boolean?>>>()
        for (i in glowingItems) {
            val match = parser.matchEntire(i)
            if (match != null) {
                val name = match.groupValues[1]
                var meta = match.groupValues[2]
                if (meta.isBlank()) meta = "-1"
                val tintIndices = match.groupValues[3].split(",").filterNot(String::isBlank)
                names.getOrPut(name) { mutableMapOf() }.put(meta, tintIndices to (if (match.groupValues[4].isEmpty()) null else match.groupValues[4] != "false"))
            }
        }

        for ((name, map) in names) {
            val item = ForgeRegistries.ITEMS.getValue(ResourceLocation(name)) ?: continue
            val entries = map.entries.toList()
            val indices = entries.associate { it.key.toInt() to (it.value.first.map(String::toInt) to it.value.second) }
            registerCustomGlowHandler(item, {
                stack, model ->
                val array = intArrayOf(*(indices[stack.itemDamage]?.first?.toTypedArray()?.toIntArray() ?: intArrayOf()),
                                       *(indices[-1]?.first?.toTypedArray()?.toIntArray() ?: intArrayOf()))
                IGlowingItem.Helper.wrapperBake(model, array.isEmpty() || array.contains(-1), *array)
            }, { stack, _ -> indices[stack.itemDamage]?.second ?: indices[-1]?.second ?: true })
        }

        if (potionGlow) {
            registerCustomGlowHandler(Items.POTIONITEM, { stack, model ->
                if (PotionUtils.getEffectsFromStack(stack).isNotEmpty()) IGlowingItem.Helper.wrapperBake(model, false, 0) else null
            }, { _, _ -> true})
            registerCustomGlowHandler(Items.SPLASH_POTION, { stack, model ->
                if (PotionUtils.getEffectsFromStack(stack).isNotEmpty()) IGlowingItem.Helper.wrapperBake(model, false, 0) else null
            }, { _, _ -> true})
            registerCustomGlowHandler(Items.LINGERING_POTION, { stack, model ->
                if (PotionUtils.getEffectsFromStack(stack).isNotEmpty()) IGlowingItem.Helper.wrapperBake(model, false, 0) else null
            }, { _, _ -> true})
            registerCustomGlowHandler(Items.TIPPED_ARROW, { stack, model ->
                if (PotionUtils.getEffectsFromStack(stack).isNotEmpty()) IGlowingItem.Helper.wrapperBake(model, false, 0) else null
            }, { _, _ -> true})
        }
    }

    private val renderModel = MethodHandleHelper.wrapperForMethod(RenderItem::class.java, arrayOf("renderModel", "func_175045_a", "a"), IBakedModel::class.java, ItemStack::class.java)

    private val renderSpecialHandlers = mutableMapOf<Item, IGlowingItem>()

    @JvmStatic
    @JvmOverloads
    fun registerCustomGlowHandler(item: Item,
                        modelTransformer: (ItemStack, IBakedModel) -> IBakedModel?,
                        shouldDisableLighting: ((ItemStack, IBakedModel) -> Boolean) = { _, _ -> false }) {
        renderSpecialHandlers.put(item, object : IGlowingItem {
            override fun transformToGlow(itemStack: ItemStack, model: IBakedModel): IBakedModel? {
                return modelTransformer(itemStack, model)
            }

            override fun shouldDisableLightingForGlow(itemStack: ItemStack, model: IBakedModel): Boolean {
                return shouldDisableLighting(itemStack, model)
            }
        })
    }

    @JvmStatic
    fun glow(stack: ItemStack, model: IBakedModel) {
        val item = stack.item as? IGlowingItem ?: renderSpecialHandlers[stack.item]

        if (item != null) {
            val newModel = item.transformToGlow(stack, model)
            if (newModel != null) GlUtils.withLighting(!item.shouldDisableLightingForGlow(stack, model)) {
                val packed = item.packedGlowCoords(stack, model)
                GlUtils.useLightmap(packed) {
                    renderModel(Minecraft.getMinecraft().renderItem, arrayOf(newModel, stack))
                }
            }
        }
    }
}