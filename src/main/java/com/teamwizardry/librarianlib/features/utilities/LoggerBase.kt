package com.teamwizardry.librarianlib.features.utilities

import com.teamwizardry.librarianlib.core.LibrarianLib
import com.teamwizardry.librarianlib.features.kotlin.sendMessage
import com.teamwizardry.librarianlib.features.kotlin.times
import com.teamwizardry.librarianlib.features.kotlin.toComponent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.fml.common.FMLCommonHandler
import org.apache.commons.lang3.exception.ExceptionUtils
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import kotlin.reflect.jvm.internal.impl.utils.ExceptionUtilsKt

abstract class LoggerBase(name: String) {
    val debugMode = LibrarianLib.DEV_ENVIRONMENT
    private val logger = LogManager.getLogger(name)

    /**
     * Performs special processing on formatting arguments, such as replacing Worlds with "WORLD_NAME (DIM_ID)"
     */
    open fun processFormatArg(value: Any): Any {

        if (value is World) {
            return "${value.providerName} (${value.provider.dimension})"
        }

        return value
    }

    /**
     * Process the passed formatting args, passing each non-null element through [processFormatArg]
     *
     * @return a modified array
     */
    fun processFormatting(value: Array<out Any?>): Array<Any?> {
        val arr = arrayOfNulls<Any?>(value.size)
        for (i in value.indices) {
            val v = value[i]
            if (v != null)
                arr[i] = processFormatArg(v)
            else
                arr[i] = null
        }
        return arr
    }

    fun error(message: String, vararg args: Any?) {
        logger.log(Level.ERROR, String.format(message, *processFormatting(args)))
    }

    fun error(e: Exception, message: String, vararg args: Any?) {
        logger.log(Level.ERROR, String.format(message, *processFormatting(args)))
        errorStackTrace(e)
    }

    fun warn(message: String, vararg args: Any?) {
        logger.log(Level.WARN, String.format(message, *processFormatting(args)))
    }

    fun info(message: String, vararg args: Any?) {
        logger.log(Level.INFO, String.format(message, *processFormatting(args)))
    }

    fun debug(message: String, vararg args: Any?) {
        if (debugMode) logger.log(Level.INFO, String.format(message, *processFormatting(args)))
    }

    fun message(player: EntityPlayer, message: String, vararg args: Any?) {
        player.sendMessage(String.format(message, *processFormatting(args)))
    }

    fun warn(player: EntityPlayer, message: String, vararg args: Any?) {
        player.sendStatusMessage(String.format(message, *processFormatting(args)).toComponent().setStyle(Style().setColor(TextFormatting.RED)), false)
    }

    fun errorStackTrace(error: Throwable? = null) {
        getStackTrace(error, 1).forEach { logger.log(Level.ERROR, "| " + it) }
    }

    fun warnStackTrace(error: Throwable? = null) {
        getStackTrace(error, 1).forEach { logger.log(Level.WARN, "| " + it) }
    }

    fun infoStackTrace(error: Throwable? = null) {
        getStackTrace(error, 1).forEach { logger.log(Level.INFO, "| " + it) }
    }

    fun debugStackTrace(error: Throwable? = null) {
        if(debugMode) getStackTrace(error, 1).forEach { logger.log(Level.INFO, "| " + it) }
    }

    fun complain(error: Throwable) {
        getStackTrace(error, 0).forEach { logger.log(Level.ERROR, "| " + it) }
    }

    private fun getStackTrace(error: Throwable? = null, callsToTrim: Int = 0): List<String> {
        var trace = ExceptionUtils.getStackTrace(error ?: Throwable()).lines()
        trace = trace.subList(if(error == null) 2 + callsToTrim else callsToTrim, trace.size)
        return trace
    }

    /**
     * **Only use this if the person seeing it can do something about it**
     *
     * Prints the passed lines to the log, surrounded with asterisks, and immediately dies.
     *
     * Prints: ```
     * ******* **** TITLE **** ********
     * * your lines will appear here, *
     * * each preceded with asterisks *
     * ******* **** TITLE **** ********
     * ```
     *
     * The stars at the end of the lines are controlled with the [endStar] parameter.
     *
     * The title will never print more than 25 characters from the left
     */
    fun bigDie(title: String, lines: List<String>, endStar: Boolean = true) {
        val maxWidth = lines.fold(0, { cur, value ->
            Math.max(cur,
                    value.split("\n").fold(0, { curS, valueS ->
                        Math.max(curS, valueS.length)
                    })
            )
        })

        var titleStarred = " **** $title **** "
        var starPadLeft = (maxWidth + 4 - titleStarred.length) / 2
        if (starPadLeft >= 20)
            starPadLeft = 19
        var starPadRight = (maxWidth + 4 - titleStarred.length) - starPadLeft

        if (starPadLeft < 0) starPadLeft = 0
        if (starPadRight < 0) starPadRight = 0

        titleStarred = "*" * starPadLeft + titleStarred + "*" * starPadRight

        val text = mutableListOf<String>()

        text.add("")
        text.add(titleStarred)

        text.addAll(lines.flatMap { it.split("\n") }.map {
            if (endStar) {
                "* " + it.padEnd(maxWidth, ' ') + " *"
            } else {
                "* " + it
            }
        })

        text.add(titleStarred)

        warn(text.joinToString("\n"))

        FMLCommonHandler.instance().handleExit(0)
    }
}
