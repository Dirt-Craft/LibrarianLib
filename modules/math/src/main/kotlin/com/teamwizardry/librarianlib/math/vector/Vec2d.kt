package com.teamwizardry.librarianlib.math.vector

import com.teamwizardry.librarianlib.math.utils.ceilToInt
import com.teamwizardry.librarianlib.math.utils.floorToInt
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

interface Vec2d: Vec2 {
    val x: Double
    val y: Double

    override fun add(x: Int, y: Int): Vec2d
    override fun sub(x: Int, y: Int): Vec2d
    override fun mul(a: Int): Vec2d
    override fun mul(x: Int, y: Int): Vec2d
    override fun pow(a: Int): Vec2d
    override fun abs(): Vec2d
    override fun negate(): Vec2d
    override fun min(x: Int, y: Int): Vec2d
    override fun max(x: Int, y: Int): Vec2d
    @JvmDefault override fun add(v: Vec2): Vec2d = super.add(v) as Vec2d
    @JvmDefault override fun mul(v: Vec2): Vec2d = super.mul(v) as Vec2d
    @JvmDefault override fun sub(v: Vec2): Vec2d = super.sub(v) as Vec2d
    @JvmDefault override fun min(v: Vec2): Vec2d = super.min(v) as Vec2d
    @JvmDefault override fun max(v: Vec2): Vec2d = super.max(v) as Vec2d
    @JvmDefault @JvmSynthetic override operator fun plus(v: Vec2): Vec2d = super.plus(v) as Vec2d
    @JvmDefault @JvmSynthetic override operator fun minus(v: Vec2): Vec2d = super.minus(v) as Vec2d
    @JvmDefault @JvmSynthetic override operator fun times(v: Vec2): Vec2d = super.times(v) as Vec2d
    @JvmDefault @JvmSynthetic override operator fun unaryMinus(): Vec2d = negate()
    @JvmDefault @JvmSynthetic override operator fun times(a: Int): Vec2d = mul(a)

    companion object {
        private val pool = object: VectorPool<Vec2d>(2, VectorPool.BITS_2D) {
            override fun create(x: Double, y: Double, z: Double, w: Double): Vec2d {
                return Impl(x, y)
            }

            override fun hit(value: Vec2d): Vec2d {
                return value
            }
        }

        @JvmStatic
        fun getPooled(x: Double, y: Double): Vec2d = pool.getPooled(x, y, 0.0, 0.0)
        @JvmStatic
        fun getPooled(x: Float, y: Float): Vec2d = pool.getPooled(x.toDouble(), y.toDouble(), 0.0, 0.0)
    }

    private class Impl(override val x: Double, override val y: Double): Vec2d {
        override val xd: Double get() = x
        override val yd: Double get() = y
        override val xf: Float get() = x.toFloat()
        override val yf: Float get() = y.toFloat()
        override val xi: Int get() = x.toInt()
        override val yi: Int get() = y.toInt()

        override fun add(x: Int, y: Int): Vec2d = getPooled(this.x + x, this.y + y)
        override fun add(x: Float, y: Float): Vec2d = getPooled(this.x + x, this.y + y)
        override fun add(x: Double, y: Double): Vec2d = getPooled(this.x + x, this.y + y)

        override fun sub(x: Int, y: Int): Vec2d = getPooled(this.x - x, this.y - y)
        override fun sub(x: Float, y: Float): Vec2d = getPooled(this.x - x, this.y - y)
        override fun sub(x: Double, y: Double): Vec2d = getPooled(this.x - x, this.y - y)

        override fun mul(a: Int): Vec2d = getPooled(this.x * a, this.y * a)
        override fun mul(a: Float): Vec2d = getPooled(this.x * a, this.y * a)
        override fun mul(a: Double): Vec2d = getPooled(this.x * a, this.y * a)
        override fun mul(x: Int, y: Int): Vec2d = getPooled(this.x * x, this.y * y)
        override fun mul(x: Float, y: Float): Vec2d = getPooled(this.x * x, this.y * y)
        override fun mul(x: Double, y: Double): Vec2d = getPooled(this.x * x, this.y * y)

        override fun div(a: Int): Vec2d = getPooled(this.x / a, this.y / a)
        override fun div(a: Float): Vec2d = getPooled(this.x / a, this.y / a)
        override fun div(a: Double): Vec2d = getPooled(this.x / a, this.y / a)
        override fun div(x: Int, y: Int): Vec2d = getPooled(this.x / x, this.y / y)
        override fun div(x: Float, y: Float): Vec2d = getPooled(this.x / x, this.y / y)
        override fun div(x: Double, y: Double): Vec2d = getPooled(this.x / x, this.y / y)

        override fun pow(a: Int): Vec2d = getPooled(Math.pow(x, a.toDouble()), Math.pow(y, a.toDouble()))
        override fun pow(a: Float): Vec2d = getPooled(Math.pow(x, a.toDouble()), Math.pow(y, a.toDouble()))
        override fun pow(a: Double): Vec2d = getPooled(Math.pow(x, a), Math.pow(y, a))

        override fun ceil(): Vec2i = Vec2i.getPooled(ceilToInt(x), ceilToInt(y))
        override fun floor(): Vec2i = Vec2i.getPooled(floorToInt(x), floorToInt(y))
        override fun round(): Vec2i = Vec2i.getPooled(x.roundToInt(), y.roundToInt())
        override fun abs(): Vec2d = getPooled(x.absoluteValue, y.absoluteValue)
        override fun negate(): Vec2d = getPooled(-x, -y)

        override fun min(x: Int, y: Int): Vec2d = getPooled(Math.min(this.x, x.toDouble()), Math.min(this.y, y.toDouble()))
        override fun min(x: Float, y: Float): Vec2d = getPooled(Math.min(this.x, x.toDouble()), Math.min(this.y, y.toDouble()))
        override fun min(x: Double, y: Double): Vec2d = getPooled(Math.min(this.x, x), Math.min(this.y, y))
        override fun max(x: Int, y: Int): Vec2d = getPooled(Math.max(this.x, x.toDouble()), Math.max(this.y, y.toDouble()))
        override fun max(x: Float, y: Float): Vec2d = getPooled(Math.max(this.x, x.toDouble()), Math.max(this.y, y.toDouble()))
        override fun max(x: Double, y: Double): Vec2d = getPooled(Math.max(this.x, x), Math.max(this.y, y))

        override fun toDouble(): Vec2d = this
        override fun toInt(): Vec2i = Vec2i.getPooled(xi, yi)
    }
}
