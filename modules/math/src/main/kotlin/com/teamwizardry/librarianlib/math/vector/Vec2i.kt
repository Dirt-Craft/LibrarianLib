package com.teamwizardry.librarianlib.math.vector

import kotlin.math.absoluteValue

interface Vec2i: Vec2 {
    val x: Int
    val y: Int

    @JvmDefault override val xd: Double get() = x.toDouble()
    @JvmDefault override val yd: Double get() = y.toDouble()
    @JvmDefault override val xf: Float get() = x.toFloat()
    @JvmDefault override val yf: Float get() = y.toFloat()
    @JvmDefault override val xi: Int get() = x
    @JvmDefault override val yi: Int get() = y

    @JvmDefault override fun add(x: Int, y: Int): Vec2i = getPooled(this.x + x, this.y + y)
    @JvmDefault override fun add(x: Float, y: Float): Vec2d = Vec2d.getPooled(this.x + x, this.y + y)
    @JvmDefault override fun add(x: Double, y: Double): Vec2d = Vec2d.getPooled(this.x + x, this.y + y)

    @JvmDefault override fun sub(x: Int, y: Int): Vec2i = getPooled(this.x - x, this.y - y)
    @JvmDefault override fun sub(x: Float, y: Float): Vec2d = Vec2d.getPooled(this.x - x, this.y - y)
    @JvmDefault override fun sub(x: Double, y: Double): Vec2d = Vec2d.getPooled(this.x - x, this.y - y)

    @JvmDefault override fun mul(a: Int): Vec2i = getPooled(this.x * a, this.y * a)
    @JvmDefault override fun mul(a: Float): Vec2d = Vec2d.getPooled(this.x * a, this.y * a)
    @JvmDefault override fun mul(a: Double): Vec2d = Vec2d.getPooled(this.x * a, this.y * a)
    @JvmDefault override fun mul(x: Int, y: Int): Vec2i = getPooled(this.x * x, this.y * y)
    @JvmDefault override fun mul(x: Float, y: Float): Vec2d = Vec2d.getPooled(this.x * x, this.y * y)
    @JvmDefault override fun mul(x: Double, y: Double): Vec2d = Vec2d.getPooled(this.x * x, this.y * y)

    @JvmDefault override fun div(a: Int): Vec2d = Vec2d.getPooled(this.xd / a, this.yd / a)
    @JvmDefault override fun div(a: Float): Vec2d = Vec2d.getPooled(this.x / a, this.y / a)
    @JvmDefault override fun div(a: Double): Vec2d = Vec2d.getPooled(this.x / a, this.y / a)
    @JvmDefault override fun div(x: Int, y: Int): Vec2d = Vec2d.getPooled(this.xd / x, this.yd / y)
    @JvmDefault override fun div(x: Float, y: Float): Vec2d = Vec2d.getPooled(this.x / x, this.y / y)
    @JvmDefault override fun div(x: Double, y: Double): Vec2d = Vec2d.getPooled(this.x / x, this.y / y)

    @JvmDefault override fun pow(a: Int): Vec2i = getPooled(
        Math.pow(this.xd, a.toDouble()).toInt(),
        Math.pow(this.yd, a.toDouble()).toInt()
    )
    @JvmDefault override fun pow(a: Float): Vec2d = Vec2d.getPooled(
        Math.pow(this.xd, a.toDouble()),
        Math.pow(this.yd, a.toDouble())
    )
    @JvmDefault override fun pow(a: Double): Vec2d = Vec2d.getPooled(
        Math.pow(this.xd, a),
        Math.pow(this.yd, a)
    )

    @JvmDefault override fun ceil(): Vec2i = this
    @JvmDefault override fun floor(): Vec2i = this
    @JvmDefault override fun round(): Vec2i = this
    @JvmDefault override fun abs(): Vec2i = getPooled(x.absoluteValue, y.absoluteValue)
    @JvmDefault override fun negate(): Vec2i = getPooled(-x, -y)

    @JvmDefault override fun min(x: Int, y: Int): Vec2i = getPooled(Math.min(this.x, x), Math.min(this.y, y))
    @JvmDefault override fun min(x: Float, y: Float): Vec2d = Vec2d.getPooled(Math.min(this.xf, x), Math.min(this.yf, y))
    @JvmDefault override fun min(x: Double, y: Double): Vec2d = Vec2d.getPooled(Math.min(this.xd, x), Math.min(this.yd, y))
    @JvmDefault override fun max(x: Int, y: Int): Vec2i = getPooled(Math.max(this.x, x), Math.max(this.y, y))
    @JvmDefault override fun max(x: Float, y: Float): Vec2d = Vec2d.getPooled(Math.max(this.xf, x), Math.max(this.yf, y))
    @JvmDefault override fun max(x: Double, y: Double): Vec2d = Vec2d.getPooled(Math.max(this.xd, x), Math.max(this.yd, y))

    @JvmDefault override fun toDouble(): Vec2d = Vec2d.getPooled(xd, yd)
    @JvmDefault override fun toInt(): Vec2i = this

    @JvmDefault fun add(v: Vec2i): Vec2i = add(v.x, v.y)
    @JvmDefault fun mul(v: Vec2i): Vec2i = mul(v.x, v.y)
    @JvmDefault fun sub(v: Vec2i): Vec2i = sub(v.x, v.y)
    @JvmDefault fun min(v: Vec2i): Vec2i = min(v.x, v.y)
    @JvmDefault fun max(v: Vec2i): Vec2i = max(v.x, v.y)
    @JvmDefault @JvmSynthetic operator fun plus(v: Vec2i): Vec2i = add(v.x, v.y)
    @JvmDefault @JvmSynthetic operator fun minus(v: Vec2i): Vec2i = sub(v.x, v.y)
    @JvmDefault @JvmSynthetic operator fun times(v: Vec2i): Vec2i = mul(v.x, v.y)
    @JvmDefault @JvmSynthetic override operator fun unaryMinus(): Vec2i = negate()
    @JvmDefault @JvmSynthetic override operator fun times(a: Int): Vec2i = mul(a)

    companion object {
        private val pool = object: VectorPool<Vec2i>(2, VectorPool.BITS_2D) {
            override fun create(x: Int, y: Int, z: Int, w: Int): Vec2i {
                return Impl(x, y)
            }

            override fun hit(value: Vec2i): Vec2i {
                return value
            }
        }

        @JvmStatic
        fun getPooled(x: Int, y: Int): Vec2i = pool.getPooled(x, y, 0, 0)
    }

    private class Impl(override val x: Int, override val y: Int): Vec2i
}
