@file:JvmName("JsonMaker")
package com.teamwizardry.librarianlib.common.core

import com.google.gson.*

/**
 * @author WireSegal
 * Created at 7:34 PM on 9/28/16.
 */

object JSON {

    fun array(vararg args: Any?) : JsonArray {
        val arr = JsonArray()
        args.forEach { arr.add(convert(it)) }
        return arr
    }

    fun obj(vararg args: Pair<String, *>): JsonObject {
        val obj = JsonObject()
        args.forEach { obj.add(it.first, convert(it.second)) }
        return obj
    }
}

fun convert(value: Any?) : JsonElement = when (value) {
    is Number -> JsonPrimitive(value)
    is String -> JsonPrimitive(value)
    is JsonElement -> value
    null -> JsonNull.INSTANCE
    else -> throw IllegalArgumentException("Unrecognized type: " + value)
}

fun json(lambda: JSON.() -> JsonObject): JsonObject {
    return JSON.lambda()
}
