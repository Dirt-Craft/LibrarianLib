package com.teamwizardry.librarianlib.core

import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * The base class of all liblib modules. Automatically registers with LibLibModules.
 */
abstract class LibLibModule(
    val name: String
): ModInitializer {
    /**
     * A logger for the module
     */
    val logger: Logger = LogManager.getLogger(javaClass)

    init {
        @Suppress("LeakingThis")
        LibLibModules.mutableModules.add(this)
    }

    override fun onInitialize() {
        logger.info("LibrarianLib module $name initializing...")
    }
}
