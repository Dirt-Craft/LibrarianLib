package com.teamwizardry.librarianlib.features.math

import com.teamwizardry.librarianlib.features.kotlin.minus
import com.teamwizardry.librarianlib.features.kotlin.plus
import com.teamwizardry.librarianlib.features.kotlin.times

class BoundingBox2D(val min: Vec2d, val max: Vec2d) {

    constructor(minX: Double, minY: Double, maxX: Double, maxY: Double) : this(Vec2d(minX, minY), Vec2d(maxX, maxY))

    fun union(other: BoundingBox2D): BoundingBox2D {
        return BoundingBox2D(
                Math.min(min.x, other.min.x),
                Math.min(min.y, other.min.y),
                Math.max(max.x, other.max.x),
                Math.max(max.y, other.max.y))
    }

    fun offset(pos: Vec2d): BoundingBox2D {
        return BoundingBox2D(min + pos, max + pos)
    }

    fun scale(amount: Double): BoundingBox2D {
        return BoundingBox2D(min * amount, max * amount)
    }

    operator fun contains(other: Vec2d): Boolean {
        return other.x <= max.x && other.x >= min.x && other.y <= max.y && other.y >= min.y
    }

    fun height(): Double {
        return max.y - min.y
    }

    fun width(): Double {
        return max.x - min.x
    }

    fun heightF(): Float {
        return max.yf - min.yf
    }

    fun widthF(): Float {
        return max.xf - min.xf
    }

    fun heightI(): Int {
        return max.yi - min.yi
    }

    fun widthI(): Int {
        return max.xi - min.xi
    }

    val pos: Vec2d
        get() = min
    val size: Vec2d
        get() = max - min
}
