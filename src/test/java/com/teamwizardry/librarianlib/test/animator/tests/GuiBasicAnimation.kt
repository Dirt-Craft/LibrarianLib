package com.teamwizardry.librarianlib.test.animator.tests

import com.teamwizardry.librarianlib.features.animator.Animator
import com.teamwizardry.librarianlib.features.animator.Easing
import com.teamwizardry.librarianlib.features.animator.animations.BasicAnimation
import com.teamwizardry.librarianlib.features.animator.animations.Keyframe
import com.teamwizardry.librarianlib.features.animator.animations.KeyframeAnimation
import com.teamwizardry.librarianlib.features.gui.GuiBase
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents
import com.teamwizardry.librarianlib.features.gui.components.ComponentRect
import com.teamwizardry.librarianlib.features.helpers.vec
import java.awt.Color

/**
 * Created by TheCodeWarrior
 */
class GuiBasicAnimation : GuiBase(200, 100) {
    val animator = Animator()

    init {


        val background = ComponentRect(0, 0, 200, 100)
        background.color = Color(127, 127, 127)
        mainComponents.add(background)

        val bouncer = ComponentRect(0, 0, 25, 15)
        bouncer.color = Color(255, 0, 0)
        mainComponents.add(bouncer)

        val bouncer2 = ComponentRect(0, 95, 50, 5)
        bouncer2.color = Color(0, 0, 255)
        mainComponents.add(bouncer2)

        val anim = BasicAnimation(bouncer2, "pos.x")
        anim.duration = 60f
        anim.to = 150
        anim.repeatCount = -1
        anim.easing = Easing.easeInOutCubic
        animator.add(anim)

//        animator.add(bouncer2.animate("pos", Vec2d(10, 10), 40f))

        mainComponents.BUS.hook(GuiComponentEvents.MouseClickEvent::class.java) {
            val anim = KeyframeAnimation(bouncer, "pos")
            anim.duration = 120f
            anim.shouldReverse = true

            anim.keyframes = arrayOf(
                    Keyframe(0f, vec(0, 0)),
                    Keyframe(0.333f, vec(175, 0), Easing.easeOutBounce),
                    Keyframe(0.666f, vec(175, 75), Easing.easeInOutCubic),
                    Keyframe(1f, vec(0, 75), Easing.easeOutBack)
            )

            animator.add(anim)
        }

    }

    override fun doesGuiPauseGame(): Boolean {
        return true
    }
}
