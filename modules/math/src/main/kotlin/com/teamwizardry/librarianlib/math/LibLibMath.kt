package com.teamwizardry.librarianlib.math

import com.teamwizardry.librarianlib.core.LibLibModule
import net.fabricmc.api.ModInitializer

internal object LibLibMath: LibLibModule("math") {
    override fun onInitialize() {
        println("LibrarianLib Math: Initialized")
    }
}
