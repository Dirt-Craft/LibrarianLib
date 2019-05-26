package com.teamwizardry.librarianlib.features.gui.components

import com.teamwizardry.librarianlib.features.gui.component.GuiComponent
import com.teamwizardry.librarianlib.features.math.Vec2d
import com.teamwizardry.librarianlib.features.sprite.DrawingUtil
import com.teamwizardry.librarianlib.features.sprite.ISprite
import com.teamwizardry.librarianlib.features.sprite.Sprite

@Deprecated("Use a ComponentSprite and specify caps in the mcmeta file")
class ComponentSpriteCapped(internal var topLeft:

                            Sprite, internal var middle: Sprite, internal var bottomRight: Sprite, internal var horizontal: Boolean, posX: Int, posY: Int, width: Int, height: Int) : GuiComponent(posX, posY, width, height) {

    private val middleClipped: ISprite = object: ISprite by middle {
        override val hardScaleU: Boolean = true
        override val hardScaleV: Boolean = true
    }

    override fun drawComponent(mousePos: Vec2d, partialTicks: Float) {
        topLeft.tex.bind()
        DrawingUtil.startDrawingSession()
        val animationTicks = animator.time.toInt()
        if (horizontal) {
            topLeft.draw(animationTicks, 0f, 0f)
            bottomRight.draw(animationTicks, (size.x - bottomRight.width).toFloat(), 0f)
            middleClipped.draw(animationTicks, topLeft.width.toFloat(), 0f, (size.x - (topLeft.width + bottomRight.width)).toFloat(), middle.height.toFloat())
        } else {
            topLeft.draw(animationTicks, 0f, 0f)
            bottomRight.draw(animationTicks, 0f, (size.y - bottomRight.height).toFloat())
            middleClipped.draw(animationTicks, 0f, topLeft.height.toFloat(), middle.height.toFloat(), (size.y - (topLeft.height + bottomRight.height)).toFloat())
        }
        DrawingUtil.endDrawingSession()
    }

}
