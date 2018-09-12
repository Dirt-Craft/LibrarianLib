package com.teamwizardry.librarianlib.features.gui.value

import kotlin.reflect.KProperty

/**
 * A kotlin delegate that behaves almost identically to a plain `var`, except that it supports implicit animations. It
 * represents a Retained Mode Value, in contrast to [IMValue], which represents an Immediate Mode Value
 */
class RMValueBoolean @JvmOverloads constructor(private var value: Boolean, private val change: (Boolean) -> Unit) : GuiAnimatable {
    /**
     * Gets the current value
     */
    fun get(): Boolean {
        return value
    }

    /**
     * Sets a new value
     */
    fun set(value: Boolean) {
        GuiAnimator.current.add(this)
        val oldValue = this.value
        this.value = value
        if(oldValue != value) {
            change(oldValue)
        }
    }

    /**
     * A kotlin delegate method, used to allow properties to delegate to this RMValue
     * (`var property by RMValueBoolean()`)
     */
    operator fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return this.get()
    }

    /**
     * A kotlin delegate method, used to allow properties to delegate to this RMValue
     * (`var property by RMValueBoolean()`)
     */
    operator fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        this.set(value)
    }

    override fun getAnimatableValue(): Any? {
        return value
    }

    @Suppress("UNCHECKED_CAST")
    override fun setAnimatableValue(value: Any?) {
        this.value = value as Boolean
    }

    override fun getAnimatableCallback(): Any? {
        return null
    }

    override fun setAnimatableCallback(supplier: Any) {
        // NO-OP
    }
}
