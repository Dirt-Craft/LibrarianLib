package com.teamwizardry.librarianlib.features.base.item

import com.teamwizardry.librarianlib.core.client.ModelHandler
import com.teamwizardry.librarianlib.features.base.IModelGenerator
import com.teamwizardry.librarianlib.features.base.ModCreativeTab
import com.teamwizardry.librarianlib.features.base.item.ItemModTool.Companion.classAttackDamage
import com.teamwizardry.librarianlib.features.base.item.ItemModTool.Companion.classAttackSpeed
import com.teamwizardry.librarianlib.features.helpers.VariantHelper
import com.teamwizardry.librarianlib.features.helpers.currentModId
import com.teamwizardry.librarianlib.features.utilities.generateBaseItemModel
import com.teamwizardry.librarianlib.features.utilities.getPathForItemModel
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.*
import net.minecraft.util.NonNullList

/**
 * The default implementation for a generic IVariantHolder tool.
 */
@Suppress("LeakingThis")
open class ItemModTool(name: String, attackDamage: Float, attackSpeed: Float, toolMaterial: ToolMaterial, effectiveBlocks: Set<Block>) : ItemTool(attackDamage, attackSpeed, toolMaterial, effectiveBlocks), IModItemProvider, IModelGenerator {

    companion object {
        private val AXE_EFFECTIVE_ON = setOf(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER)
        private val PICKAXE_EFFECTIVE_ON = setOf(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB)
        private val SHOVEL_EFFECTIVE_ON = setOf(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND)

        @JvmStatic
        fun blocksClassIsEffectiveOn(toolClass: String) = when (toolClass) {
            "axe" -> AXE_EFFECTIVE_ON
            "shovel" -> SHOVEL_EFFECTIVE_ON
            "pickaxe" -> PICKAXE_EFFECTIVE_ON
            else -> emptySet()
        }

        @JvmStatic
        fun classAttackDamage(toolClass: String, material: ToolMaterial) = when (toolClass) {
            "axe" -> when (material.harvestLevel) {
                0 -> 6f
                else -> 8f
            } - material.attackDamage
            "shovel" -> 1.5f
            "pickaxe" -> 1.0f
            else -> 0f
        }

        @JvmStatic
        fun classAttackSpeed(toolClass: String, material: ToolMaterial) = when (toolClass) {
            "axe" -> when {
                material.efficiency <= 5f -> -3.2f
                material.efficiency <= 7f -> -3.1f
                else -> -3.0f
            }
            "shovel" -> -3.0f
            "pickaxe" -> -2.8f
            else -> 0f
        }
    }

    var toolClass: String? = null
        protected set

    constructor(name: String, attackDamage: Float, attackSpeed: Float, toolMaterial: ToolMaterial, toolClass: String) : this(name, attackDamage, attackSpeed, toolMaterial, blocksClassIsEffectiveOn(toolClass)) {
        this.toolClass = toolClass
    }

    constructor(name: String, toolMaterial: ToolMaterial, effectiveBlocks: Set<Block>) : this(name, 0F, 0F, toolMaterial, effectiveBlocks)
    constructor(name: String, toolMaterial: ToolMaterial, toolClass: String) : this(name, classAttackDamage(toolClass, toolMaterial), classAttackSpeed(toolClass, toolMaterial), toolMaterial, blocksClassIsEffectiveOn(toolClass)) {
        this.toolClass = toolClass
    }

    override val providedItem: Item
        get() = this

    private val bareName = VariantHelper.toSnakeCase(name)
    private val modId = currentModId
    override val variants = VariantHelper.setupItem(this, bareName, arrayOf(), this::creativeTab)

    override fun setTranslationKey(name: String): Item {
        VariantHelper.setTranslationKeyForItem(this, modId, name)
        return super.setTranslationKey(name)
    }

    override fun getTranslationKey(stack: ItemStack): String {
        val dmg = stack.itemDamage
        val variants = this.variants
        val name = if (dmg >= variants.size) this.bareName else variants[dmg]

        return "item.$modId:$name"
    }

    override fun getSubItems(tab: CreativeTabs, subItems: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab))
            variants.indices.mapTo(subItems) { ItemStack(this, 1, it) }
    }

    /**
     * Override this to have a custom creative tab. Leave blank to have a default tab (or none if no default tab is set).
     */
    open val creativeTab: ModCreativeTab?
        get() = ModCreativeTab.defaultTabs[modId]

    override fun getHarvestLevel(stack: ItemStack, toolClass: String, player: EntityPlayer?, blockState: IBlockState?): Int {
        val level = super.getHarvestLevel(stack, toolClass, player, blockState)
        return if (level == -1 && toolClass == this.toolClass) this.toolMaterial.harvestLevel else level
    }

    override fun getToolClasses(stack: ItemStack): Set<String> {
        val clazz = toolClass
        return if (clazz != null) setOf(clazz) else super.getToolClasses(stack)
    }

    // Model Generation

    override fun generateMissingItem(item: IModItemProvider, variant: String): Boolean {
        ModelHandler.generateItemJson(this) {
            getPathForItemModel(this, variant) to
                    generateBaseItemModel(this, variant, "item/handheld")
        }
        return true
    }
}

/**
 * The default implementation for an IVariantHolder axe.
 */
@Suppress("LeakingThis")
open class ItemModAxe(name: String, attackDamage: Float, attackSpeed: Float, toolMaterial: ToolMaterial) : ItemAxe(toolMaterial, attackDamage, attackSpeed), IModItemProvider, IModelGenerator {

    constructor(name: String, toolMaterial: ToolMaterial) : this(name, classAttackDamage("axe", toolMaterial), classAttackSpeed("axe", toolMaterial), toolMaterial)

    override val providedItem: Item
        get() = this

    private val bareName = VariantHelper.toSnakeCase(name)
    private val modId = currentModId
    override val variants = VariantHelper.setupItem(this, bareName, arrayOf(), this::creativeTab)

    override fun setTranslationKey(name: String): Item {
        VariantHelper.setTranslationKeyForItem(this, modId, name)
        return super.setTranslationKey(name)
    }

    override fun getTranslationKey(stack: ItemStack): String {
        val dmg = stack.itemDamage
        val variants = this.variants
        val name = if (dmg >= variants.size) this.bareName else variants[dmg]

        return "item.$modId:$name"
    }

    override fun getSubItems(tab: CreativeTabs, subItems: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab))
            variants.indices.mapTo(subItems) { ItemStack(this, 1, it) }
    }

    /**
     * Override this to have a custom creative tab. Leave blank to have a default tab (or none if no default tab is set).
     */
    open val creativeTab: ModCreativeTab?
        get() = ModCreativeTab.defaultTabs[modId]

    // Model Generation

    override fun generateMissingItem(item: IModItemProvider, variant: String): Boolean {
        ModelHandler.generateItemJson(this) {
            getPathForItemModel(this, variant) to
                    generateBaseItemModel(this, variant, "item/handheld")
        }
        return true
    }
}

/**
 * The default implementation for an IVariantHolder shovel.
 */
@Suppress("LeakingThis")
open class ItemModSpade(name: String, toolMaterial: ToolMaterial) : ItemSpade(toolMaterial), IModItemProvider, IModelGenerator {

    override val providedItem: Item
        get() = this

    private val bareName = VariantHelper.toSnakeCase(name)
    private val modId = currentModId
    override val variants = VariantHelper.setupItem(this, bareName, arrayOf(), this::creativeTab)

    override fun setTranslationKey(name: String): Item {
        VariantHelper.setTranslationKeyForItem(this, modId, name)
        return super.setTranslationKey(name)
    }

    override fun getTranslationKey(stack: ItemStack): String {
        val dmg = stack.itemDamage
        val variants = this.variants
        val name = if (dmg >= variants.size) this.bareName else variants[dmg]

        return "item.$modId:$name"
    }

    override fun getSubItems(tab: CreativeTabs, subItems: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab))
            variants.indices.mapTo(subItems) { ItemStack(this, 1, it) }
    }

    /**
     * Override this to have a custom creative tab. Leave blank to have a default tab (or none if no default tab is set).
     */
    open val creativeTab: ModCreativeTab?
        get() = ModCreativeTab.defaultTabs[modId]

    // Model Generation

    override fun generateMissingItem(item: IModItemProvider, variant: String): Boolean {
        ModelHandler.generateItemJson(this) {
            getPathForItemModel(this, variant) to
                    generateBaseItemModel(this, variant, "item/handheld")
        }
        return true
    }
}

/**
 * The default implementation for an IVariantHolder pickaxe.
 */
@Suppress("LeakingThis")
open class ItemModPickaxe(name: String, toolMaterial: ToolMaterial) : ItemPickaxe(toolMaterial), IModItemProvider, IModelGenerator {

    override val providedItem: Item
        get() = this

    private val bareName = VariantHelper.toSnakeCase(name)
    private val modId = currentModId
    override val variants = VariantHelper.setupItem(this, bareName, arrayOf(), this::creativeTab)

    override fun setTranslationKey(name: String): Item {
        VariantHelper.setTranslationKeyForItem(this, modId, name)
        return super.setTranslationKey(name)
    }

    override fun getTranslationKey(stack: ItemStack): String {
        val dmg = stack.itemDamage
        val variants = this.variants
        val name = if (dmg >= variants.size) this.bareName else variants[dmg]

        return "item.$modId:$name"
    }

    override fun getSubItems(tab: CreativeTabs, subItems: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab))
            variants.indices.mapTo(subItems) { ItemStack(this, 1, it) }
    }

    /**
     * Override this to have a custom creative tab. Leave blank to have a default tab (or none if no default tab is set).
     */
    open val creativeTab: ModCreativeTab?
        get() = ModCreativeTab.defaultTabs[modId]

    // Model Generation

    override fun generateMissingItem(item: IModItemProvider, variant: String): Boolean {
        ModelHandler.generateItemJson(this) {
            getPathForItemModel(this, variant) to
                    generateBaseItemModel(this, variant, "item/handheld")
        }
        return true
    }
}
