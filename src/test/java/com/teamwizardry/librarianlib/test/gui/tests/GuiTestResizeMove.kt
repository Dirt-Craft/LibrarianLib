package com.teamwizardry.librarianlib.test.gui.tests

import com.teamwizardry.librarianlib.features.gui.GuiBase
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents
import com.teamwizardry.librarianlib.features.gui.components.ComponentRect
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.kotlin.plus
import java.awt.Color

/**
 * Created by TheCodeWarrior
 */
class GuiTestResizeMove : GuiBase(100, 100) {
    init {

        val c = ComponentRect(25, 25, 50, 50)
        c.color = Color.RED
        mainComponents.add(c)

        c.BUS.hook(GuiComponentEvents.MouseClickEvent::class.java) {
            c.pos += vec(1, 1)
            c.size += vec(1, 0)
        }

    }
}
