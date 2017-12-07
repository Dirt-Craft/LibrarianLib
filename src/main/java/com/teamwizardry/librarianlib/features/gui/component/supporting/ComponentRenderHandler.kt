package com.teamwizardry.librarianlib.features.gui.component.supporting

import com.teamwizardry.librarianlib.core.LibrarianLib
import com.teamwizardry.librarianlib.features.animator.Animation
import com.teamwizardry.librarianlib.features.animator.Animator
import com.teamwizardry.librarianlib.features.gui.CallbackValue
import com.teamwizardry.librarianlib.features.gui.component.GuiComponent
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents
import com.teamwizardry.librarianlib.features.math.Vec2d
import com.teamwizardry.librarianlib.features.utilities.client.LibCursor
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraftforge.fml.client.config.GuiUtils
import org.lwjgl.opengl.GL11.GL_LINES
import org.lwjgl.opengl.GL11.GL_LINE_STRIP

/**
 * TODO: Document file ComponentRenderHandler
 *
 * Created by TheCodeWarrior
 */
class ComponentRenderHandler(private val component: GuiComponent) {
    val tooltipFunc = CallbackValue<List<String>?>(null)
    var tooltip by tooltipFunc.Delegate()
    var tooltipFont: FontRenderer? = null

    /**
     * If nonnull, the cursor will switch to this when hovering.
     */
    var hoverCursor: LibCursor? = null

    var cursor: LibCursor? = null
        get() {
            val parent = component.parent
            return if (parent == null)
                field
            else
                parent.render.cursor
        }
        set(value) {
            val parent = component.parent
            return if (parent == null)
                field = value
            else
                parent.render.cursor = value
        }

    private var wasMouseOver = false

    /**
     * Sets the tooltip to be drawn, overriding the existing value. Pass null for the font to use the default font renderer.
     */
    fun setTooltip(text: List<String>, font: FontRenderer?) {
        tooltip = text
        tooltipFont = font
    }

    var animator: Animator
        get() {
            var a = animatorStorage ?: component.parent?.animator
            if(a == null) {
                a = Animator()
                animatorStorage = a
            }
            return a
        }
        set(value) {
            animatorStorage = value
        }

    private var animatorStorage: Animator? = null

    /**
     * Adds animations to [animator]
     */
    fun add(vararg animations: Animation<*>) {
        animator.add(*animations)
    }

    /**
     * Draw this component, don't override in subclasses unless you know what you're doing.
     *
     * @param mousePos Mouse position relative to the position of this component
     * @param partialTicks From 0-1 the additional fractional ticks, used for smooth animations that aren't dependant on wall-clock time
     */
    fun draw(mousePos: Vec2d, partialTicks: Float) {

        val components = component.relationships.components
        components.sortBy { it.relationships.zIndex }
        if (!component.isVisible) return

        components.removeAll { e ->
            var b = e.isInvalid
            if (component.BUS.fire(GuiComponentEvents.RemoveChildEvent(component, e)).isCanceled())
                b = false
            if (e.BUS.fire(GuiComponentEvents.RemoveFromParentEvent(e, component)).isCanceled())
                b = false
            if (b) {
                e.relationships.parent = null
            }
            b
        }

        if (wasMouseOver != component.mouseOver) {
            if (component.mouseOver) {
                component.BUS.fire(GuiComponentEvents.MouseInEvent(component, mousePos))
            } else {
                component.BUS.fire(GuiComponentEvents.MouseOutEvent(component, mousePos))
            }
        }
        wasMouseOver = component.mouseOver

        if(component.mouseOver && hoverCursor != null) {
            cursor = hoverCursor
        }

        GlStateManager.pushMatrix()

        component.transform.glApply()

        component.clipping.pushEnable()

        component.BUS.fire(GuiComponentEvents.PreDrawEvent(component, mousePos, partialTicks))

        component.drawComponent(mousePos, partialTicks)

        if (LibrarianLib.DEV_ENVIRONMENT && Minecraft.getMinecraft().renderManager.isDebugBoundingBox) {
            GlStateManager.pushAttrib()
            GlStateManager.color(1f, 1f, 1f)
            if (!component.mouseOver) GlStateManager.color(1f, 0f, 1f)
            GlStateManager.disableTexture2D()
            val tessellator = Tessellator.getInstance()
            val vb = tessellator.buffer
            vb.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION)
            vb.pos(0.0, 0.0, 0.0).endVertex()
            vb.pos(component.size.x, 0.0, 0.0).endVertex()
            vb.pos(component.size.x, component.size.y, 0.0).endVertex()
            vb.pos(0.0, component.size.y, 0.0).endVertex()
            vb.pos(0.0, 0.0, 0.0).endVertex()
            tessellator.draw()

            val big = 1000.0
            vb.begin(GL_LINES, DefaultVertexFormats.POSITION)
            vb.pos(0.0, 0.0, 0.0).endVertex()
            vb.pos(0.0, 0.0, -big).endVertex()
            vb.pos(component.size.x, 0.0, 0.0).endVertex()
            vb.pos(component.size.x, 0.0, -big).endVertex()
            vb.pos(component.size.x, component.size.y, 0.0).endVertex()
            vb.pos(component.size.x, component.size.y, -big).endVertex()
            vb.pos(0.0, component.size.y, 0.0).endVertex()
            vb.pos(0.0, component.size.y, -big).endVertex()
            tessellator.draw()

            GlStateManager.color(0f, 1f, 1f)
            vb.begin(GL_LINES, DefaultVertexFormats.POSITION)
            vb.pos(mousePos.x, mousePos.y, 0.0).endVertex()
            vb.pos(mousePos.x, mousePos.y, big).endVertex()
            tessellator.draw()
            GlStateManager.enableTexture2D()
            GlStateManager.popAttrib()
        }

        GlStateManager.pushAttrib()

        component.BUS.fire(GuiComponentEvents.PreChildrenDrawEvent(component, mousePos, partialTicks))
        component.relationships.forEachChild { it.render.draw(mousePos, partialTicks) }

        GlStateManager.popAttrib()

        component.BUS.fire(GuiComponentEvents.PostDrawEvent(component, mousePos, partialTicks))

        component.clipping.popDisable()

        GlStateManager.popMatrix()
    }

    /**
     * Draw late stuff this component, like tooltips. This method is executed in the root context
     *
     * @param mousePos Mouse position in the root context
     * @param partialTicks From 0-1 the additional fractional ticks, used for smooth animations that aren't dependant on wall-clock time
     */
    fun drawLate(mousePos: Vec2d, partialTicks: Float) {
        if (component.mouseOver) {
            if (tooltip?.isNotEmpty() == true) {
                GuiUtils.drawHoveringText(tooltip, mousePos.xi, mousePos.yi, component.root.size.xi, component.root.size.yi, -1,
                        tooltipFont ?: Minecraft.getMinecraft().fontRenderer)
            }
        }

        component.relationships.forEachChild { it.render.drawLate(mousePos, partialTicks) }
    }

}
