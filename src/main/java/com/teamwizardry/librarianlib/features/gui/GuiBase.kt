package com.teamwizardry.librarianlib.features.gui

import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents
import com.teamwizardry.librarianlib.features.gui.components.ComponentVoid
import com.teamwizardry.librarianlib.features.gui.debugger.ComponentDebugger
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.utilities.client.StencilUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import java.io.IOException

open class GuiBase(protected var guiWidth: Int, protected var guiHeight: Int) : GuiScreen() {
    val mainComponents: ComponentVoid = ComponentVoid(0, 0)
    val fullscreenComponents: ComponentVoid = ComponentVoid(0, 0)
    private val mainScaleWrapper: ComponentVoid = ComponentVoid(0, 0)
    private var isDebugMode = false
    private val debugger = ComponentDebugger()
//    protected var top: Int = 0
//    protected var left: Int = 0

    init {
        mainComponents.geometry.shouldCalculateOwnHover = false
        fullscreenComponents.geometry.shouldCalculateOwnHover = false
        mainScaleWrapper.zIndex = -100000 // really far back
        fullscreenComponents.add(mainScaleWrapper)
        mainScaleWrapper.add(mainComponents)

        mainComponents.size = vec(guiWidth, guiHeight)
        fullscreenComponents.add(debugger)
    }

    override fun initGui() {
        super.initGui()

        var s = 1.0
        if (!adjustGuiSize()) {
            var i = 1
            // find required scale, either 1x, 1/2x 1/3x, or 1/4x
            while ((guiWidth * s > width || guiHeight * s > height) && i < 4) {
                i++
                s = 1.0 / i
            }
        }

        val left = (width / 2 - guiWidth * s / 2).toInt()
        val top = (height / 2 - guiHeight * s / 2).toInt()

        if (mainScaleWrapper.pos.xi != left || mainScaleWrapper.pos.yi != top) {
            mainScaleWrapper.pos = vec(left, top)
            mainScaleWrapper.transform.scale = s
            mainScaleWrapper.size = vec(guiWidth * s, guiHeight * s)
        }

        fullscreenComponents.size = vec(width, height)
    }

    /**
     * Try to fit the gui in a [width] by [height] area, setting [guiWidth] and [guiHeight] to whatever size you manage
     * to clamp the GUI to.
     *
     * Return true from this function to cancel any auto resizing
     */
    open fun adjustGuiSize(): Boolean {
        return false
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)
        GlStateManager.enableBlend()
        StencilUtil.clear()
        GL11.glEnable(GL11.GL_STENCIL_TEST)
        val relPos = vec(mouseX, mouseY)
        GlStateManager.pushMatrix()

        if(isDebugMode) {
            GlStateManager.translate(width / 2.0, height / 2.0, 0.0)
            GlStateManager.rotate(-20f, 1f, 0f, 0f)
            GlStateManager.rotate(-20f, 0f, 1f, 0f)
            GlStateManager.translate(-width / 2.0, -height / 2.0, 0.0)
        }

        fullscreenComponents.geometry.calculateMouseOver(relPos)
        fullscreenComponents.render.draw(relPos, partialTicks)
        fullscreenComponents.render.drawLate(relPos, partialTicks)

        GlStateManager.popMatrix()
        GL11.glDisable(GL11.GL_STENCIL_TEST)
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        fullscreenComponents.guiEventHandler.mouseDown(vec(mouseX, mouseY), EnumMouseButton.getFromCode(mouseButton))
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        super.mouseReleased(mouseX, mouseY, state)
        fullscreenComponents.guiEventHandler.mouseUp(vec(mouseX, mouseY), EnumMouseButton.getFromCode(state))
    }

    override fun mouseClickMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick)
        fullscreenComponents.guiEventHandler.mouseDrag(vec(mouseX, mouseY), EnumMouseButton.getFromCode(clickedMouseButton))
    }

    @Throws(IOException::class)
    override fun handleKeyboardInput() {
        super.handleKeyboardInput()

        if(Keyboard.getEventKeyState()) {
            if(Keyboard.getEventKey() == Keyboard.KEY_D &&
                    ( Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) ) &&
                    ( Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA) )
            ) {
                isDebugMode = !isDebugMode
            }

            if(Keyboard.getEventKey() == Keyboard.KEY_B && Keyboard.isKeyDown(Keyboard.KEY_F3)) {
                Minecraft.getMinecraft().renderManager.isDebugBoundingBox = !Minecraft.getMinecraft().renderManager.isDebugBoundingBox
            }
        }

        if (Keyboard.getEventKeyState())
            fullscreenComponents.guiEventHandler.keyPressed(Keyboard.getEventCharacter(), Keyboard.getEventKey())
        else
            fullscreenComponents.guiEventHandler.keyReleased(Keyboard.getEventCharacter(), Keyboard.getEventKey())
    }

    @Throws(IOException::class)
    override fun handleMouseInput() {
        super.handleMouseInput()
        val mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth
        val mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1
        val wheelAmount = Mouse.getEventDWheel()

        if (wheelAmount != 0) {
            fullscreenComponents.guiEventHandler.mouseWheel(vec(mouseX, mouseY), GuiComponentEvents.MouseWheelDirection.fromSign(wheelAmount))
        }
    }

    fun tick() {
        fullscreenComponents.guiEventHandler.tick()
    }

    companion object {
        init {
            MinecraftForge.EVENT_BUS.register(this)
        }

        @SubscribeEvent
        @Suppress("UNUSED_PARAMETER")
        fun tick(e: TickEvent.ClientTickEvent) {
            val gui = Minecraft.getMinecraft().currentScreen
            if (gui is GuiBase) {
                gui.tick()
            }
        }
    }
}
