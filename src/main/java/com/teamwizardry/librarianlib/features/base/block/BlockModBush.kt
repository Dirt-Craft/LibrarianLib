package com.teamwizardry.librarianlib.features.base.block

import com.teamwizardry.librarianlib.core.client.ModelHandler
import com.teamwizardry.librarianlib.features.base.IModelGenerator
import com.teamwizardry.librarianlib.features.base.ModCreativeTab
import com.teamwizardry.librarianlib.features.base.item.IModItemProvider
import com.teamwizardry.librarianlib.features.helpers.VariantHelper
import com.teamwizardry.librarianlib.features.helpers.currentModId
import com.teamwizardry.librarianlib.features.utilities.*
import net.minecraft.block.Block
import net.minecraft.block.BlockBush
import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.ItemBlock

/**
 * The default implementation for an IModBlock.
 */
@Suppress("LeakingThis")
open class BlockModBush(name: String, materialIn: Material, color: MapColor, vararg variants: String) : BlockBush(materialIn, color), IModBlock, IModelGenerator {

    constructor(name: String, materialIn: Material, vararg variants: String) : this(name, materialIn, materialIn.materialMapColor, *variants)
    constructor(name: String, vararg variants: String) : this(name, Material.PLANTS, *variants)


    override val bareName: String = VariantHelper.toSnakeCase(name)
    override val variants: Array<out String> = VariantHelper.beginSetupBlock(bareName, variants)
    val modId = currentModId

    override val itemForm: ItemBlock? by lazy { createItemForm() }

    init {
        soundType = SoundType.PLANT
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

    override fun generateMissingBlockstate(block: IModBlockProvider, mapper: ((block: Block) -> Map<IBlockState, ModelResourceLocation>)?): Boolean {
        ModelHandler.generateBlockJson(block, {
            generateBaseBlockStates(this, mapper)
        }, {
            getPathForBlockModel(this) to {
                "parent"("block/cross")
                "textures" {
                    "cross"("${key.namespace}:blocks/${key.path}")
                }
            }
        })
        return true
    }

    override fun generateMissingItem(item: IModItemProvider, variant: String): Boolean {
        ModelHandler.generateItemJson(item) {
            getPathForItemModel(this, variant) to
                    generateRegularItemModel(this, variant)
        }
        return true
    }
}
