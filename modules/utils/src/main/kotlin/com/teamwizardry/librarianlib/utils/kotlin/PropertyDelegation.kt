package com.teamwizardry.librarianlib.utils.kotlin

import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.KMutableProperty0

operator fun <T> KProperty0<T>.getValue(thisRef: Any, property: KProperty<*>): T {
    return this.get()
}

operator fun <T> KMutableProperty0<T>.setValue(thisRef: Any, property: KProperty<*>, value: T) {
    this.set(value)
}
