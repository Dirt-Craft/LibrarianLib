package com.teamwizardry.librarianlib.test.gui.tests

import com.teamwizardry.librarianlib.features.animator.Easing
import com.teamwizardry.librarianlib.features.gui.GuiBase
import com.teamwizardry.librarianlib.features.gui.components.ComponentRect
import com.teamwizardry.librarianlib.features.gui.layers.ArcLayer
import com.teamwizardry.librarianlib.features.gui.layers.ColorLayer
import com.teamwizardry.librarianlib.features.helpers.vec
import java.awt.Color
import java.lang.Math.PI

/**
 * Created by TheCodeWarrior
 */
class GuiTestArc : GuiBase() {
    init {
        main.size = vec(100, 100)

        val bg = ColorLayer(Color.WHITE, 0, 0, 100, 100)
        main.add(bg)

        val arc = ArcLayer(Color.BLACK, 50, 50, 100, 100)

        arc.endAngle_im.animateKeyframes(0.0)
            .add(60f, PI*2, Easing.easeInOutSine)
            .add(60f, PI*2)
            .finish().repeatCount = -1
        arc.startAngle_im.animateKeyframes(0.0)
            .add(60f, 0.0)
            .add(60f, PI*2, Easing.easeInOutSine)
            .finish().repeatCount = -1

        main.add(arc)
    }
}