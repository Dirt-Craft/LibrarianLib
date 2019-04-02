package com.teamwizardry.librarianlib.core

import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.launch.knot.Knot

/**
 * Core: code necessary for the functioning of every module
 */
object LibLibCore: LibLibModule("core") {
    /**
     * True if LibLib is running in a fabric development environment
     */
    val isDevEnvironment: Boolean = Knot.getLauncher().isDevelopment

    override fun onInitialize() {
    }
}
