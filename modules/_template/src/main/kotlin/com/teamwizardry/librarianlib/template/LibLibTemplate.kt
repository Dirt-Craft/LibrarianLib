package com.teamwizardry.librarianlib.template

import com.teamwizardry.librarianlib.core.LibLibModule
import net.fabricmc.api.ModInitializer

internal object LibLibTemplate: LibLibModule("template") {
    override fun onInitialize() {
        println("LibrarianLib Template: Initialized")
    }
}
