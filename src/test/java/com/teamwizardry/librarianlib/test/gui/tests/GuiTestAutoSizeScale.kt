package com.teamwizardry.librarianlib.test.gui.tests

import com.teamwizardry.librarianlib.features.gui.GuiBase
import com.teamwizardry.librarianlib.features.gui.components.ComponentRect
import java.awt.Color

/**
 * Created by TheCodeWarrior
 */
class GuiTestAutoSizeScale : GuiBase(100, 500) {
    init {

        val c = ComponentRect(25, 0, 50, 500)
        c.color = Color.RED
        mainComponents.add(c)

    }
}
