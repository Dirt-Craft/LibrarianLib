package com.teamwizardry.librarianlib.test.gui.tests

/*
 * Created by Bluexin.
 * Made for LibrarianLib, under GNU LGPL v3.0
 * (a copy of which can be found at the repo root)
 */

import com.teamwizardry.librarianlib.features.gui.components.ComponentFluidStack
import com.teamwizardry.librarianlib.features.gui.components.ComponentSprite
import com.teamwizardry.librarianlib.features.gui.components.ComponentText
import com.teamwizardry.librarianlib.features.gui.pastry.PastrySlot
import com.teamwizardry.librarianlib.features.gui.GuiContainerBase
import com.teamwizardry.librarianlib.features.gui.pastry.PastryInventoryPlayer
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.math.Vec2d
import com.teamwizardry.librarianlib.features.sprite.Texture
import com.teamwizardry.librarianlib.test.container.FluidTankContainer
import com.teamwizardry.librarianlib.test.container.TEFluidTank
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

/**
 * The gui for our fluid tank.
 * Nothing fancy here ^-^
 */
open class GuiFluidTank(inventorySlotsIn: FluidTankContainer) : GuiContainerBase(inventorySlotsIn, 176, 166) {

    init {
        val te = inventorySlotsIn.invBlock.inventory as TEFluidTank
        val bg = ComponentSprite(BG, 0, 0)
        mainComponents.add(bg)

        val inventory = PastryInventoryPlayer(inventorySlotsIn.invPlayer)
        bg.add(inventory)
        inventory.main.pos = vec(8, 84)

        bg.add(ComponentSprite(SLOT, 50, 33))
        bg.add(ComponentSprite(SLOT, 100, 33))

        val input = PastrySlot(inventorySlotsIn.invBlock.input, 51, 34)
        bg.add(input)

        val output = PastrySlot(inventorySlotsIn.invBlock.output, 101, 34)
        bg.add(output)

        val state = te.world.getBlockState(te.pos)
        val label = ComponentText(88, 6).centered()
        label.text = I18n.format(ItemStack(state.block, 1, state.block.damageDropped(state)).displayName)
        bg.add(label)

        val fluidBar = ComponentFluidStack(null, FLUID_BG, 15, 15,
                bgWidth = 16,
                direction = Vec2d.Direction.NEGATIVE_Y,
                tankProps = te.fluidHandler.tankPropertiesImpl[0])
        fluidBar.render.tooltipFunc {
            val f = te.fluidHandler.fluid
            listOf(if (f != null) I18n.format("llt:gui.fluid", f.localizedName, f.amount) else I18n.format("llt:gui.fluidEmpty"))
        }
        bg.add(fluidBar)
    }

    companion object {
        private val TEX = Texture(ResourceLocation("librarianlibtest", "textures/guis/gui_powered_machine.png"))
        private val BG = TEX.getSprite("bg", 176, 166)

        private val FLUID_BG = TEX.getSprite("power_bg", 8, 56)

        private val SLOT = TEX.getSprite("slot", 18, 18)
    }
}
