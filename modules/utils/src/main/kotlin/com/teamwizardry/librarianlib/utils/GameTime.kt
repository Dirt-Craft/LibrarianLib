package com.teamwizardry.librarianlib.utils

import kotlin.math.truncate
import kotlin.math.ulp

object GameTime {
    /**
     * The current world render time in ticks. Always zero on dedicated servers
     *
     * This is based on time since the client/server started and pauses whenever the world does, making it appropriate
     * for things rendered within the world. For a timer that doesn't pause, use [screenTime]
     */
    val worldTime: Float get() = worldTimeTicks + worldTimePartial
    /**
     * The current world render time in whole ticks. Always zero on dedicated servers
     *
     * This is based on time since the client/server started and pauses whenever the world does, making it appropriate
     * for things rendered within the world. For a timer that doesn't pause, use [screenTimeTicks]
     */
    var worldTimeTicks: Int = 0
        private set
    /**
     * The fractional component of the current world render time in ticks. Always zero on dedicated servers
     *
     * This is based on time since the client/server started and pauses whenever the world does, making it appropriate
     * for things rendered within the world. For a timer that doesn't pause, use [screenTimePartial]
     */
    var worldTimePartial: Float = 0f
        private set

    /**
     * The current screen render time in ticks. Always zero on dedicated servers.
     *
     * This is based on time since the client/server started and doesn't pause when the world does, making it
     * appropriate for things rendered separate from the world, such as GUIs. For a timer that pauses with the world,
     * use [worldTime]
     */
    val screenTime: Float get() = screenTimeTicks + screenTimePartial
    /**
     * The current screen render time in whole ticks. Always zero on dedicated servers
     *
     * This is based on time since the client/server started and doesn't pause when the world does, making it
     * appropriate for things rendered separate from the world, such as GUIs. For a timer that pauses with the world,
     * use [worldTimeTicks]
     */
    var screenTimeTicks: Int = 0
        private set
    /**
     * The fractional component of the current screen render time in ticks. Always zero on dedicated servers
     *
     * This is based on time since the client/server started and doesn't pause when the world does, making it
     * appropriate for things rendered separate from the world, such as GUIs. For a timer that pauses with the world,
     * use [worldTimePartial]
     */
    var screenTimePartial: Float = 0f
        private set

    /**
     * The current system time in ticks.
     */
    val systemTime: Float get() = System.currentTimeMillis() / 50f
    /**
     * The current system time in whole ticks.
     */
    val systemTimeTicks: Int get() = truncate(systemTime).toInt()
    /**
     * The fractional component of the current system time in ticks.
     */
    val systemTimePartial: Float get() = 1 - systemTime.ulp
}