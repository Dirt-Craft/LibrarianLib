package com.teamwizardry.librarianlib.features.math

import net.minecraft.util.math.MathHelper

class Vec2d(val x: Double, val y: Double) {

    @Transient val xf: Float = x.toFloat()
    @Transient val yf: Float = y.toFloat()
    @Transient val xi: Int = Math.floor(x).toInt()
    @Transient val yi: Int = Math.floor(y).toInt()

    fun setX(value: Double): Vec2d {
        return Vec2d(value, y)
    }

    fun setY(value: Double): Vec2d {
        return Vec2d(x, value)
    }

    fun setAxis(value: Double, axis: Axis): Vec2d {
        return when(axis) {
            Axis.X -> this.setX(value)
            Axis.Y -> this.setY(value)
        }
    }

    fun getAxis(axis: Axis): Double {
        return when(axis) {
            Axis.X -> x
            Axis.Y -> y
        }
    }

    fun floor(): Vec2d {
        return Vec2d(Math.floor(x), Math.floor(y))
    }

    fun ceil(): Vec2d {
        return Vec2d(Math.ceil(x), Math.ceil(y))
    }

    fun add(other: Vec2d): Vec2d {
        return Vec2d(x + other.x, y + other.y)
    }

    fun add(otherX: Double, otherY: Double): Vec2d {
        return Vec2d(x + otherX, y + otherY)
    }

    fun sub(other: Vec2d): Vec2d {
        return Vec2d(x - other.x, y - other.y)
    }

    fun sub(otherX: Double, otherY: Double): Vec2d {
        return Vec2d(x - otherX, y - otherY)
    }

    fun mul(other: Vec2d): Vec2d {
        return Vec2d(x * other.x, y * other.y)
    }

    fun mul(otherX: Double, otherY: Double): Vec2d {
        return Vec2d(x * otherX, y * otherY)
    }

    fun mul(amount: Double): Vec2d {
        return Vec2d(x * amount, y * amount)
    }

    fun divide(other: Vec2d): Vec2d {
        return Vec2d(x / other.x, y / other.y)
    }

    fun divide(otherX: Double, otherY: Double): Vec2d {
        return Vec2d(x / otherX, y / otherY)
    }

    fun divide(amount: Double): Vec2d {
        return Vec2d(x / amount, y / amount)
    }

    infix fun dot(point: Vec2d): Double {
        return x * point.x + y * point.y
    }

    @delegate:Transient
    private val len by lazy { Math.sqrt(x * x + y * y) }

    fun length(): Double {
        return len
    }

    fun normalize(): Vec2d {
        val norm = length()
        return Vec2d(x / norm, y / norm)
    }

    fun squareDist(vec: Vec2d): Double {
        val d0 = vec.x - x
        val d1 = vec.y - y
        return d0 * d0 + d1 * d1
    }

    fun projectOnTo(other: Vec2d): Vec2d {
        val norm = other.normalize()
        return norm.mul(this.dot(norm))
    }

    fun rotate(theta: Number): Vec2d {
        val cs = MathHelper.cos(theta.toFloat())
        val sn = MathHelper.sin(theta.toFloat())
        return Vec2d(
                this.x * cs - this.y * sn,
                this.x * sn + this.y * cs
        )
    }

    //=============================================================================

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        var temp: Long
        temp = java.lang.Double.doubleToLongBits(x)
        result = prime * result + (temp xor temp.ushr(32)).toInt()
        temp = java.lang.Double.doubleToLongBits(y)
        result = prime * result + (temp xor temp.ushr(32)).toInt()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (javaClass != other.javaClass)
            return false
        return x == (other as Vec2d).x && y == other.y
    }

    override fun toString(): String {
        return "($x,$y)"
    }

    companion object {

        @JvmField val ZERO = Vec2d(0.0, 0.0)
        @JvmField val ONE = Vec2d(1.0, 1.0)

        @JvmStatic
        fun min(a: Vec2d, b: Vec2d): Vec2d {
            return Vec2d(Math.min(a.x, b.x), Math.min(a.y, b.y))
        }

        @JvmStatic
        fun max(a: Vec2d, b: Vec2d): Vec2d {
            return Vec2d(Math.max(a.x, b.x), Math.max(a.y, b.y))
        }
    }

    enum class Axis { X, Y }
    enum class Direction(val axis: Axis, val sign: Int, val screenRelativeName: String) {
        POSITIVE_Y(Axis.Y, +1, "DOWN"),
        NEGATIVE_Y(Axis.Y, -1, "UP"),
        POSITIVE_X(Axis.X, +1, "RIGHT"),
        NEGATIVE_X(Axis.X, -1, "LEFT");

        val normal: Vec2d = Vec2d.ZERO.setAxis(sign.toDouble(), axis)
    }
}
