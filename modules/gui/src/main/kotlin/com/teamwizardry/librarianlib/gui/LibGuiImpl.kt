package com.teamwizardry.librarianlib.gui

import com.mojang.blaze3d.platform.GlStateManager
import com.teamwizardry.librarianlib.core.LibLibCore
import com.teamwizardry.librarianlib.core.LibrarianLib
import com.teamwizardry.librarianlib.gui.component.GuiComponent
import com.teamwizardry.librarianlib.gui.component.GuiComponentEvents
import com.teamwizardry.librarianlib.gui.component.GuiLayer
import com.teamwizardry.librarianlib.gui.components.StandaloneRootComponent
import com.teamwizardry.librarianlib.gui.layers.GradientLayer
import com.teamwizardry.librarianlib.helpers.vec
import com.teamwizardry.librarianlib.kotlin.delegate
import com.teamwizardry.librarianlib.kotlin.minus
import com.teamwizardry.librarianlib.math.Axis2d
import com.teamwizardry.librarianlib.math.Vec2d
import net.minecraft.client.MinecraftClient
import net.minecraft.client.Mouse
import net.minecraft.client.gui.Element
import net.minecraft.client.gui.InputListener
import net.minecraft.client.gui.MultiInputListener
import net.minecraft.client.gui.Screen
import net.minecraft.client.util.InputUtil
import java.awt.Color
import java.io.IOException

open class LibGuiImpl(
    catchSafetyNet: (e: Exception) -> Unit
): Element {

    val root: StandaloneRootComponent = StandaloneRootComponent(catchSafetyNet)
    val safetyNet: Boolean by root::safetyNet
    val main: GuiComponent = object: GuiComponent(0, 0) {
        override var pos: Vec2d
            get() = vec(root.size.xi/2, root.size.yi/2)
            set(value) {}
        override var anchor: Vec2d
            get() = vec(0.5, 0.5)
            set(value) {}

        override fun layoutChildren() {
            val parentSize = this.parent!!.size - vec(20, 20)
            val maxScale = MinecraftClient.getInstance().window.scaleFactor
            var scale = 1
            while((size.x / scale > parentSize.x || size.y / scale > parentSize.y) && scale < maxScale) {
                scale++
            }
            this.scale = 1.0/scale
        }
    }
    private val background = GradientLayer(Axis2d.Y,
        Color(0x10, 0x10, 0x10, 0xC0),
        Color(0x10, 0x10, 0x10, 0xD0),
        0, 0, 0, 0
    )
    var useDefaultBackground = false
        set(value) {
            field = value
            if(value && background.parent == null) {
                root.add(background)
            } else if(!value && background.parent != null) {
                root.remove(background)
            }
        }

    init {
        background.zIndex = Double.NEGATIVE_INFINITY
        main.disableMouseCollision = true
        root.disableMouseCollision = true
        root.add(main)

        init()
    }

    fun init() {
        val window = MinecraftClient.getInstance().window
        val resolution = vec(window.scaledWidth, window.scaledHeight)
        root.size_rm.set(resolution) //
        background.size = resolution
        main.setNeedsLayout()
    }

    fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        GlStateManager.enableBlend()
        val relPos = vec(mouseX, mouseY)
        GlStateManager.pushMatrix()

        root.renderRoot(partialTicks, relPos)

        GlStateManager.popMatrix()
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (LibLibCore.isDevEnvironment) {
            if (InputUtil.getKeyCode(keyCode, scanCode) == Keyyboard.KEY_D && Screen.hasShiftDown() && Screen.hasControlDown()) {
                GuiLayer.showDebugTilt = !GuiLayer.showDebugTilt
            }
            if (InputUtil.getKeyCode(keyCode, scanCode) == Keyyboard.KEY_B && InputUtil.isKeyPressed(Keyyboard.KEY_F3)) {
                GuiLayer.showDebugBoundingBox = !GuiLayer.showDebugBoundingBox
            }
        }
        return root.keyPressed(Keyyboard.getEventCharacter(), Keyyboard.getEventKey())
    }

    override fun charTyped(char_1: Char, int_1: Int): Boolean {
        return root.charTyped(char_1, int_1)
    }

    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        return root.keyReleased(keyCode, scanCode, modifiers)
    }

    @Throws(IOException::class)
    fun handleMouseInput() {
        val wheelAmount = Mouse.getEventDWheel()

        if (wheelAmount != 0) {
            root.mouseWheel(GuiComponentEvents.MouseWheelDirection.fromSign(wheelAmount))
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        return root.mouseDown(EnumMouseButton.getFromCode(button)) || super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        return root.mouseUp(EnumMouseButton.getFromCode(button)) || super.mouseReleased(mouseX, mouseY, button)
    }

    override fun mouseScrolled(double_1: Double, double_2: Double, amount: Double): Boolean {
        return super.mouseScrolled(double_1, double_2, amount)
    }

    fun tick() {
        root.update()
    }

    fun runTick() {
        root.tick()
    }

    fun onClose() {
        Keyyboard.enableRepeatEvents(false)
        Mouse.setNativeCursor(null)
    }
}
