package com.teamwizardry.librarianlib.test.gui.tests

import com.teamwizardry.librarianlib.features.gui.GuiBase
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents
import com.teamwizardry.librarianlib.features.gui.components.ComponentRect
import com.teamwizardry.librarianlib.features.gui.components.ComponentScrolledView
import com.teamwizardry.librarianlib.features.gui.components.ComponentSprite
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.sprite.Sprite
import net.minecraft.util.ResourceLocation
import java.awt.Color

/**
 * Created by TheCodeWarrior
 */
class GuiTestScrolledView : GuiBase(100, 100) {
    init {

        val left = ComponentRect(-30, -30, 10, 10)
        val right = ComponentRect(110, 40, 10, 20)
        val up = ComponentRect(40, -20, 20, 10)
        val down = ComponentRect(40, 110, 20, 10)

        val bg = ComponentRect(0, 0, 100, 0)

        bg.color = Color.BLUE
        left.color = Color.RED
        right.color = Color.RED
        up.color = Color.RED
        down.color = Color.RED

        mainComponents.add(bg, left, right, up, down)

        val scroll = ComponentScrolledView(0, 0, 100, 100)
        val gold = ComponentSprite(Sprite(ResourceLocation("minecraft:textures/blocks/gold_ore.png")), -100, -100, 300, 300)

        scroll.add(gold)
        mainComponents.add(scroll)

        left.BUS.hook(GuiComponentEvents.MouseClickEvent::class.java) {
            scroll.scrollOffset(vec(-1, 0))
        }
        right.BUS.hook(GuiComponentEvents.MouseClickEvent::class.java) {
            scroll.scrollOffset(vec(1, 0))
        }
        up.BUS.hook(GuiComponentEvents.MouseClickEvent::class.java) {
            scroll.scrollOffset(vec(0, 1))
        }
        down.BUS.hook(GuiComponentEvents.MouseClickEvent::class.java) {
            scroll.scrollOffset(vec(0, -1))
        }

    }
}
