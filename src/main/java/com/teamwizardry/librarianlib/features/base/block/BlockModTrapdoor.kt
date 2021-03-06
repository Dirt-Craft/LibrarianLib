package com.teamwizardry.librarianlib.features.base.block

import com.teamwizardry.librarianlib.core.client.ModelHandler
import com.teamwizardry.librarianlib.features.base.IModelGenerator
import com.teamwizardry.librarianlib.features.base.ModCreativeTab
import com.teamwizardry.librarianlib.features.base.item.IModItemProvider
import com.teamwizardry.librarianlib.features.helpers.VariantHelper
import com.teamwizardry.librarianlib.features.helpers.currentModId
import com.teamwizardry.librarianlib.features.kotlin.extract
import com.teamwizardry.librarianlib.features.kotlin.jsonObject
import com.teamwizardry.librarianlib.features.kotlin.key
import com.teamwizardry.librarianlib.features.utilities.generateBlockStates
import com.teamwizardry.librarianlib.features.utilities.getPathForBlockModel
import com.teamwizardry.librarianlib.features.utilities.getPathForItemModel
import net.minecraft.block.Block
import net.minecraft.block.BlockTrapDoor
import net.minecraft.block.material.MapColor
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.Entity
import net.minecraft.item.ItemBlock
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Explosion
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * The default implementation for an IModBlock.
 */
@Suppress("LeakingThis")
open class BlockModTrapdoor(name: String, val parent: IBlockState) : BlockTrapDoor(parent.material), IModBlock, IModelGenerator {

    private val parentName = parent.block.key

    override val variants = VariantHelper.beginSetupBlock(name, arrayOf())

    override val bareName: String = VariantHelper.toSnakeCase(name)
    val modId = currentModId

    override val itemForm: ItemBlock? by lazy { createItemForm() }

    init {
        VariantHelper.finishSetupBlock(this, bareName, itemForm, this::creativeTab)
    }

    override fun setTranslationKey(name: String): Block {
        super.setTranslationKey(name)
        VariantHelper.setTranslationKeyForBlock(this, modId, name, itemForm)
        return this
    }

    /**
     * Override this to have a custom ItemBlock implementation.
     */
    open fun createItemForm(): ItemBlock? {
        return ItemModBlock(this)
    }

    /**
     * Override this to have a custom creative tab. Leave blank to have a default tab (or none if no default tab is set).
     */
    override val creativeTab: ModCreativeTab?
        get() = ModCreativeTab.defaultTabs[modId]

    @Suppress("OverridingDeprecatedMember")
    override fun getMapColor(state: IBlockState, worldIn: IBlockAccess, pos: BlockPos): MapColor = parent.getMapColor(worldIn, pos)

    override fun getExplosionResistance(world: World, pos: BlockPos, exploder: Entity?, explosion: Explosion) = parent.block.getExplosionResistance(world, pos, exploder, explosion)
    @Suppress("OverridingDeprecatedMember")
    override fun getBlockHardness(blockState: IBlockState, worldIn: World, pos: BlockPos) = parent.getBlockHardness(worldIn, pos)

    @SideOnly(Side.CLIENT)
    @Suppress("OverridingDeprecatedMember")
    override fun isTranslucent(state: IBlockState) = parent.isTranslucent

    @Suppress("OverridingDeprecatedMember")
    override fun getUseNeighborBrightness(state: IBlockState) = parent.useNeighborBrightness()

    override fun isToolEffective(type: String, state: IBlockState) = parent.block.isToolEffective(type, parent)
    override fun getHarvestTool(state: IBlockState): String? = parent.block.getHarvestTool(parent)

    override fun generateMissingBlockstate(block: IModBlockProvider, mapper: ((block: Block) -> Map<IBlockState, ModelResourceLocation>)?): Boolean {
        val name = ResourceLocation(parentName.namespace, "blocks/${parentName.path}").toString()
        val simpleName = key.path

        ModelHandler.generateBlockJson(this, {
            generateBlockStates(this, mapper) {
                if ("half=bottom" in it && "open=false" in it)
                    "model"("${registryName}_bottom")
                else if ("half=top" in it && "open=false" in it)
                    "model"("${registryName}_top")
                else {
                    "model"("${registryName}_open")

                    when (it.extract("facing=(\\w+)")) {
                        "south" -> "y"(180)
                        "east" -> "y"(90)
                        "west" -> "y"(270)
                    }
                }
            }
        }, {
            getPathForBlockModel(this, "${simpleName}_bottom") to {
                "parent"("block/trapdoor_bottom")
                "textures" {
                    "texture"(name)
                }

            }
            getPathForBlockModel(this, "${simpleName}_top") to {
                "parent"("block/trapdoor_top")
                "textures" {
                    "texture"(name)
                }

            }
            getPathForBlockModel(this, "${simpleName}_open") to {
                "parent"("block/trapdoor_open")
                "textures" {
                    "texture"(name)
                }

            }
        })
        return true
    }

    override fun generateMissingItem(item: IModItemProvider, variant: String): Boolean {
        val name = ResourceLocation(key.namespace, "block/${key.path}").toString()
        ModelHandler.generateItemJson(item) {
            getPathForItemModel(this) to jsonObject {
                "parent"(name + "_bottom")
            }
        }
        return true
    }
}
