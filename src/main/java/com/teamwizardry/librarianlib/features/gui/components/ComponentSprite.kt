package com.teamwizardry.librarianlib.features.gui.components

import com.teamwizardry.librarianlib.features.eventbus.Event
import com.teamwizardry.librarianlib.features.gui.component.GuiComponent
import com.teamwizardry.librarianlib.features.kotlin.glColor
import com.teamwizardry.librarianlib.features.math.Vec2d
import com.teamwizardry.librarianlib.features.sprite.ISprite
import org.lwjgl.opengl.GL11
import java.awt.Color

class ComponentSprite @JvmOverloads constructor(var sprite: ISprite?, x: Int, y: Int, width: Int = sprite?.width ?: 16, height: Int = sprite?.height ?: 16) : GuiComponent(x, y, width, height) {

    class AnimationLoopEvent(val component: ComponentSprite) : Event()


    var depth = true
    var color = Color.WHITE

    var lastAnim: Int = 0

    override fun drawComponent(mousePos: Vec2d, partialTicks: Float) {
        val alwaysTop = !depth
        val sp = sprite ?: return
        val animationTicks = animator.time.toInt()
        if (alwaysTop) {
            // store the current depth function
            GL11.glPushAttrib(GL11.GL_DEPTH_BUFFER_BIT)

            // by using GL_ALWAYS instead of disabling depth it writes to the depth buffer
            // imagine a mountain, that is the depth buffer. this causes the sprite to write
            // it's value to the depth buffer, cutting a hole down wherever it's drawn.
            GL11.glDepthFunc(GL11.GL_ALWAYS)
        }
        if (sp.frameCount > 0 && lastAnim / sp.frameCount < animationTicks / sp.frameCount) {
            BUS.fire(AnimationLoopEvent(this))
        }
        lastAnim = animationTicks
        color.glColor()
        sp.bind()
        sp.draw(animationTicks, 0f, 0f, size.xi.toFloat(), size.yi.toFloat())
        if (alwaysTop)
            GL11.glPopAttrib()
    }

}
