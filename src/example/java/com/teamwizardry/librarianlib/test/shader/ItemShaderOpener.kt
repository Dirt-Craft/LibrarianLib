package com.teamwizardry.librarianlib.test.shader

import com.teamwizardry.librarianlib.features.base.item.ItemMod
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

/**
 * Created by TheCodeWarrior
 */
class ItemShaderOpener : ItemMod("shader_opener") {

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(hand)
        if(worldIn.isRemote) {
            Minecraft.getMinecraft().displayGuiScreen(ShaderTestSelector())
        }
        return ActionResult(EnumActionResult.SUCCESS, stack)
    }
}