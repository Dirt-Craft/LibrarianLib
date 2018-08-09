package com.teamwizardry.librarianlib.features.base

import com.teamwizardry.librarianlib.features.kotlin.toNonnullList
import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemStackHandler

/**
 * @author WireSegal
 * Created at 4:35 PM on 10/28/16.
 */
class ExpandedItemStackHandler : ItemStackHandler {

    var changedCallback: (ExpandedItemStackHandler.(Int) -> Unit)? = null
    var slotPredicate: (ExpandedItemStackHandler.(Int, ItemStack) -> Boolean)? = null

    constructor(size: Int = 1) : super(size)
    constructor(stacks: Array<ItemStack>) : super(stacks.toNonnullList())
    constructor(stacks: Iterable<ItemStack>) : super(stacks.toNonnullList())

    override fun getStackLimit(slot: Int, stack: ItemStack) =
            if (canInsertIntoSlot(slot, stack)) super.getStackLimit(slot, stack) else 0

    fun canInsertIntoSlot(slot: Int, stack: ItemStack) =
            slotPredicate?.invoke(this, slot, stack) ?: true

    override fun onContentsChanged(slot: Int) {
        changedCallback?.invoke(this, slot)
    }
}
