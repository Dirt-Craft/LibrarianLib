package com.teamwizardry.librarianlib.features.gui.provided.book.structure

import com.teamwizardry.librarianlib.features.animator.Easing
import com.teamwizardry.librarianlib.features.animator.animations.BasicAnimation
import com.teamwizardry.librarianlib.features.gui.EnumMouseButton
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents
import com.teamwizardry.librarianlib.features.gui.components.ComponentAnimatableVoid
import com.teamwizardry.librarianlib.features.gui.components.ComponentText
import com.teamwizardry.librarianlib.features.gui.components.ComponentVoid
import com.teamwizardry.librarianlib.features.gui.provided.book.IBookGui
import com.teamwizardry.librarianlib.features.gui.provided.book.ModGuiBook
import com.teamwizardry.librarianlib.features.gui.provided.book.helper.TranslationHolder
import com.teamwizardry.librarianlib.features.math.Vec2d
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.util.math.MathHelper

/**
 * Property of Demoniaque.
 * All rights reserved.
 */
@Suppress("LeakingThis")
abstract class ComponentStructurePage(val book: IBookGui, x: Int, y: Int, width: Int, height: Int, val subtext: TranslationHolder?) : ComponentAnimatableVoid(x, y, width, height) {

    protected var dragging = false
    protected var prevPos = Vec2d.ZERO
    protected var panVec = Vec2d.ZERO
    protected var rotVec = Vec2d.ZERO

    var released = true

    var ticks = 0

    abstract fun render(time: Int)

    abstract fun preShift()

    abstract fun failed(): Boolean

    abstract fun copy(): ComponentStructurePage

    init {

        animX = 1.0

        BUS.hook(GuiComponentEvents.ComponentTickEvent::class.java) {
            if (!GuiScreen.isShiftKeyDown() && isVisible)
                ticks++
        }

        BUS.hook(GuiComponentEvents.MouseWheelEvent::class.java) { event ->
            if (event.component.children.none { it is ComponentMaterialList }
                    && released
                    && event.component.isVisible) {
                var tmpZoom = this.animX
                if (event.direction === GuiComponentEvents.MouseWheelDirection.UP)
                    tmpZoom += 3.0
                else
                    tmpZoom -= 3.0

                tmpZoom = MathHelper.clamp(tmpZoom, 0.0, 30.0)

                val mouseOutAnim = BasicAnimation(this, "animX")
                mouseOutAnim.duration = 4f
                mouseOutAnim.easing = Easing.easeOutQuart
                mouseOutAnim.to = tmpZoom
                add(mouseOutAnim)
            }
        }

        BUS.hook(GuiComponentEvents.MouseDragEvent::class.java) { event ->
            if (event.component.children.none { it is ComponentMaterialList }
                    && released
                    && event.component.isVisible) {
                val untransform = event.mousePos
                val diff: Vec2d
                diff = if (dragging)
                    untransform.sub(prevPos).mul(1 / 2.0)
                else
                    event.mousePos.mul(1 / 100.0)

                if (event.button == EnumMouseButton.RIGHT)
                    rotVec = rotVec.add(diff)
                else if (event.button == EnumMouseButton.LEFT)
                    panVec = panVec.add(diff.mul(2.0))

                prevPos = untransform
                dragging = true
            }
        }

        BUS.hook(GuiComponentEvents.MouseUpEvent::class.java) { event ->
            if (event.component.children.none { it is ComponentMaterialList }
                    && event.component.isVisible) {
                prevPos = Vec2d.ZERO
                dragging = false
                released = true
            }
        }

        val drawPlatform = ComponentVoid(0, 0, size.xi, size.yi)
        drawPlatform.clipping.clipToBounds = true
        drawPlatform.BUS.hook(GuiComponentEvents.PreDrawEvent::class.java) { event ->
            if (event.component.isVisible) {
                if (failed()) {
                    GlStateManager.pushMatrix()
                    ModGuiBook.ERROR.bind()
                    ModGuiBook.ERROR.draw(event.partialTicks.toInt(),
                            (size.x / 2.0 - ModGuiBook.ERROR.width / 2.0).toInt().toFloat(),
                            (size.y / 2.0 - ModGuiBook.ERROR.height).toInt().toFloat())

                    ModGuiBook.FOF.bind()
                    ModGuiBook.FOF.draw(event.partialTicks.toInt(),
                            (size.x / 2.0 - ModGuiBook.FOF.width / 2.0).toInt().toFloat(),
                            (size.y / 2.0 - ModGuiBook.FOF.height / 2.0).toInt().toFloat())

                    GlStateManager.popMatrix()
                } else {
                    GlStateManager.pushMatrix()

                    GlStateManager.enableAlpha()
                    GlStateManager.enableBlend()
                    GlStateManager.enableCull()

                    GlStateManager.translate(panVec.x, panVec.y, 0.0)
                    GlStateManager.translate(size.x / 2.0, size.y / 2.0, 250.0)

                    GlStateManager.scale(5 + this.animX, -5 - this.animX, 5 + this.animX)

                    preShift()

                    GlStateManager.rotate((35 + rotVec.y).toFloat(), 1f, 0f, 0f)
                    GlStateManager.rotate((45 + rotVec.x).toFloat(), 0f, 1f, 0f)

                    Minecraft.getMinecraft().textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)
                    render(ticks / 4)

                    GlStateManager.disableLighting()
                    GlStateManager.disableRescaleNormal()

                    GlStateManager.popMatrix()
                }
            }
        }

        add(drawPlatform)

        if (subtext != null) {
            val text = ComponentText(size.xi / 2, size.yi * 3 / 4, ComponentText.TextAlignH.CENTER, ComponentText.TextAlignV.TOP)
            text.text.setValue(subtext.toString())
            text.wrap.setValue(size.xi * 3 / 4)
            add(text)
        }
    }
}
