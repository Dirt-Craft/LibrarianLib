@file:Suppress("NOTHING_TO_INLINE")

package com.teamwizardry.librarianlib.math.utils

import com.teamwizardry.librarianlib.math.vector.Vec2d
import com.teamwizardry.librarianlib.math.vector.Vec2i
import net.minecraft.client.render.BufferBuilder

inline fun vec2d(x: Number, y: Number): Vec2d = Vec2d.getPooled(x.toDouble(), y.toDouble())
inline fun vec2i(x: Int, y: Int): Vec2i = Vec2i.getPooled(x, y)

inline fun BufferBuilder.vertex(x: Number, y: Number, z: Number): BufferBuilder = this.vertex(x.toDouble(), y.toDouble(), z.toDouble())
inline fun BufferBuilder.vertex(x: Number, y: Number): BufferBuilder = this.vertex(x.toDouble(), y.toDouble(), 0.0)
