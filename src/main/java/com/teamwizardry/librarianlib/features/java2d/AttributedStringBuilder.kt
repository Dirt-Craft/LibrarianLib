package com.teamwizardry.librarianlib.features.java2d

import java.text.AttributedString

class AttributedStringBuilder {
    var value: AttributedString = AttributedString("")
    private set

    fun append(strings: AttributedStringBuilder?) {
        if (strings == null) {
            return
        }
        this.append(strings.value)

    }

    fun append(string: AttributedString?) {
        if (string == null) {
            return
        }
        this.value = AttributedStringUtil.concat(this.value, string)
    }

    override fun toString(): String {
        return AttributedStringUtil.getString(this.value)
    }

}