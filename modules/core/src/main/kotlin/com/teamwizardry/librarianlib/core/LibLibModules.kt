package com.teamwizardry.librarianlib.core

import java.util.Collections

/**
 * Used to dynamically query the loaded LibLib modules
 */
object LibLibModules {
    internal val mutableModules = mutableListOf<LibLibModule>()
    val modules: List<LibLibModule> = Collections.unmodifiableList(mutableModules)

    @Suppress("UNCHECKED_CAST")
    fun <T: LibLibModule> getModule(name: String): T? {
        return modules.find { it.name == name } as? T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T: LibLibModule> getModule(clazz: Class<T>): T? {
        return modules.find { it.javaClass == clazz } as T
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T: LibLibModule> getModule(): T? {
        return modules.find { it.javaClass == T::class.java } as T
    }
}