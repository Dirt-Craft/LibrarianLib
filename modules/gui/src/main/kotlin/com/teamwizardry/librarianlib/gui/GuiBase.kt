package com.teamwizardry.librarianlib.gui

import com.teamwizardry.librarianlib.gui.component.GuiComponent
import com.teamwizardry.librarianlib.utils.kotlin.getValue
import com.teamwizardry.librarianlib.utils.kotlin.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.MultiInputListener
import net.minecraft.client.gui.Screen
import java.io.IOException
import kotlin.coroutines.CoroutineContext

/**
 * The base class for all LibrarianLib GUIs.
 *
 * The [root] component represents the entire screen, while the [main] component is where the main content of your GUI
 * should be added.
 *
 * [main] is automatically repositioned to remain centered on the screen, so setting its size is the equivalent of
 * setting its size is equivalent to setting [xSize][GuiContainer.xSize] and [ySize][GuiContainer.ySize].
 * If [main] is too tall or too wide to fit on the screen at the current GUI scale it will attempt to downscale to fit
 * (decreasing the effective GUI scale setting until either the GUI fits or the scale reaches "Small"). [root] doesn't
 * scale with [main] and so always reflects Minecraft's GUI scale.
 *
 * In development environments any crashes from the GUI code will be caught and displayed as an error screen instead of
 * crashing the game. However, it is impossible to wrap subclass constructors in try-catch statements so those will
 * still crash.
 */
open class GuiBase private constructor(
    /**
     * The GUI implementation code common between [GuiBase] and [GuiContainerBase]
     */
    val impl: LibGuiImpl
) : Screen(null), Element by impl, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Client

    constructor() : this(
        LibGuiImpl {
            LibLibGui.logger.error("The safety net caught an error", it)
            MinecraftClient.getInstance().openScreen(GuiSafetyNetError(it))
        }
    )

    /**
     * The main component of the GUI, within which the contents of most GUIs will be placed. It will always center
     * itself in the screen and will dynamically adjust its effective GUI scale if it can't fit on the screen.
     */
    val main: GuiComponent by impl::main
    /**
     * The root component, whose position and size represents the entirety of the game screen.
     */
    val root: GuiComponent by impl::root
    /**
     * Whether to enable the Safety Net feature. By default this is true in development environments and false
     * elsewhere, but can be enabled/disabled manually.
     */
    val safetyNet: Boolean by impl::safetyNet
    /**
     * Whether to enable Minecraft's standard translucent GUI background.
     */
    var useDefaultBackground by impl::useDefaultBackground

    override fun init() {
        impl.init()
    }

    override fun onClose() {
        impl.onClose()
    }

    override fun isFocused(): Boolean {
        return impl.root.focusedComponent != null
    }

    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)
        impl.drawScreen(mouseX, mouseY, partialTicks)
    }

    @Throws(IOException::class)
    override fun handleMouseInput() {
        super.handleMouseInput()
        impl.handleMouseInput()
    }

    override fun tick() {
        impl.update()
    }

    internal fun runTick() {
        impl.tick()
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
