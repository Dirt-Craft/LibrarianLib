package com.teamwizardry.librarianlib.features.container.builtin

import com.teamwizardry.librarianlib.features.container.ITransferRule
import com.teamwizardry.librarianlib.features.container.internal.SlotBase
import com.teamwizardry.librarianlib.features.kotlin.isNotEmpty
import net.minecraft.item.ItemStack

/**
 * Created by TheCodeWarrior
 */
open class BasicTransferRule : ITransferRule {
    protected val fromSet = mutableSetOf<SlotBase>()
    protected var filter: (SlotBase) -> Boolean = { true }
    protected val targets = mutableListOf<List<SlotBase>>()

    fun from(slots: List<SlotBase>): BasicTransferRule {
        fromSet.addAll(slots)
        return this
    }

    fun from(vararg slots: SlotBase): BasicTransferRule {
        fromSet.addAll(slots)
        return this
    }

    fun filter(filter: (SlotBase) -> Boolean): BasicTransferRule {
        this.filter = filter
        return this
    }

    fun deposit(slots: List<SlotBase>): BasicTransferRule {
        return deposit(*slots.toTypedArray())
    }

    fun deposit(vararg slots: SlotBase): BasicTransferRule {
        targets.add(listOf(*slots))
        return this
    }

    override fun shouldApply(slot: SlotBase): Boolean {
        return slot in fromSet && filter(slot)
    }

    override fun putStack(stack: ItemStack) =
            targets.fold(stack) { itemStack, target ->
        if (itemStack.isNotEmpty) ITransferRule.mergeIntoRegion(itemStack, target.filter { it.visible }).remainingStack
        else return ItemStack.EMPTY
    }
}
