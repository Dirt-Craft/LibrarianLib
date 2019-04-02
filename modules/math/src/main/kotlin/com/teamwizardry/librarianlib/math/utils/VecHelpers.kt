@file:Suppress("NOTHING_TO_INLINE")

package com.teamwizardry.librarianlib.math.utils

import com.flowpowered.math.vector.*
import net.minecraft.util.math.Vec3d as MCVec3d
import net.minecraft.client.render.BufferBuilder


inline fun vec2d(x: Number, y: Number): Vec2d = Vectors.vec2d(x.toDouble(), y.toDouble())
inline fun vec2f(x: Number, y: Number): Vec2f = Vectors.vec2f(x.toFloat(), y.toFloat())
inline fun vec2i(x: Number, y: Number): Vec2i = Vectors.vec2i(x.toInt(), y.toInt())
inline fun vec2l(x: Number, y: Number): Vec2l = Vectors.vec2l(x.toLong(), y.toLong())

inline fun mcVec3d(x: Number, y: Number, z: Number): MCVec3d = Vectors.mcVec3d(x.toDouble(), y.toDouble(), z.toDouble())
inline fun vec3d(x: Number, y: Number, z: Number): Vec3d = Vectors.vec3d(x.toDouble(), y.toDouble(), z.toDouble())
inline fun vec3f(x: Number, y: Number, z: Number): Vec3f = Vectors.vec3f(x.toFloat(), y.toFloat(), z.toFloat())
inline fun vec3i(x: Number, y: Number, z: Number): Vec3i = Vectors.vec3i(x.toInt(), y.toInt(), z.toInt())
inline fun vec3l(x: Number, y: Number, z: Number): Vec3l = Vectors.vec3l(x.toLong(), y.toLong(), z.toLong())

inline fun vec4d(x: Number, y: Number, z: Number, w: Number): Vec4d = Vectors.vec4d(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())
inline fun vec4f(x: Number, y: Number, z: Number, w: Number): Vec4f = Vectors.vec4f(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())
inline fun vec4i(x: Number, y: Number, z: Number, w: Number): Vec4i = Vectors.vec4i(x.toInt(), y.toInt(), z.toInt(), w.toInt())
inline fun vec4l(x: Number, y: Number, z: Number, w: Number): Vec4l = Vectors.vec4l(x.toLong(), y.toLong(), z.toLong(), w.toLong())

inline fun vecNd(vararg components: Number): VecNd = Vectors.vecNd(*DoubleArray(components.size) { components[it].toDouble() })
inline fun vecNf(vararg components: Number): VecNf = Vectors.vecNf(*FloatArray(components.size) { components[it].toFloat() })
inline fun vecNi(vararg components: Number): VecNi = Vectors.vecNi(*IntArray(components.size) { components[it].toInt() })
inline fun vecNl(vararg components: Number): VecNl = Vectors.vecNl(*LongArray(components.size) { components[it].toLong() })


inline fun BufferBuilder.vertex(x: Number, y: Number, z: Number): BufferBuilder = this.vertex(x.toDouble(), y.toDouble(), z.toDouble())
inline fun BufferBuilder.vertex(x: Number, y: Number): BufferBuilder = this.vertex(x.toDouble(), y.toDouble(), 0.0)
