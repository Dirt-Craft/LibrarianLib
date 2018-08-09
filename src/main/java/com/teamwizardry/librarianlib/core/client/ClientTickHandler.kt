package com.teamwizardry.librarianlib.core.client

import com.teamwizardry.librarianlib.features.kotlin.minus
import com.teamwizardry.librarianlib.features.kotlin.plus
import com.teamwizardry.librarianlib.features.kotlin.times
import net.minecraft.client.Minecraft
import net.minecraft.util.math.Vec3d
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase

object ClientTickHandler {

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    @JvmStatic
    var ticks = 0
        private set
    @JvmStatic
    var ticksInGame = 0
        private set
    @JvmStatic
    var partialTicks = 0f
        private set
    @JvmStatic
    var delta = 0f
        private set
    @JvmStatic
    var total = 0f
        private set

    private fun calcDelta() {
        val oldTotal = total
        total = ticksInGame + partialTicks
        delta = total - oldTotal
    }

    @SubscribeEvent
    fun renderTick(event: TickEvent.RenderTickEvent) {
        if (event.phase == Phase.START)
            partialTicks = event.renderTickTime
    }

    @SubscribeEvent
    fun clientTickEnd(event: ClientTickEvent) {
        if (event.phase == Phase.END) {
            val mc = Minecraft.getMinecraft()
            val gui = mc.currentScreen
            if (gui == null || !gui.doesGuiPauseGame()) {
                ticksInGame++
                partialTicks = 0f
            }

            ticks++

            calcDelta()
        }
    }

    @JvmStatic
    fun interpPartialTicks(previousValue: Double, value: Double): Double {
        return previousValue + (value - previousValue) * partialTicks
    }

    @JvmStatic
    fun interpPartialTicks(previousValue: Vec3d, value: Vec3d): Vec3d {
        return previousValue + (value - previousValue) * partialTicks
    }
}
