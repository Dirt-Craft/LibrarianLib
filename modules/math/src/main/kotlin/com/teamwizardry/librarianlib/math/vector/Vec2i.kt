package com.teamwizardry.librarianlib.math.vector

import kotlin.math.absoluteValue

interface Vec2i: Vec2 {
    val x: Int
    val y: Int

    override fun add(x: Int, y: Int): Vec2i

    override fun sub(x: Int, y: Int): Vec2i

    override fun mul(a: Int): Vec2i
    override fun mul(x: Int, y: Int): Vec2i

    override operator fun div(a: Int): Vec2i
    override fun div(x: Int, y: Int): Vec2i

    override fun pow(a: Int): Vec2i

    override fun ceil(): Vec2i
    override fun floor(): Vec2i
    override fun round(): Vec2i
    override fun abs(): Vec2i
    override fun negate(): Vec2i

    override fun min(x: Int, y: Int): Vec2i

    override fun max(x: Int, y: Int): Vec2i

    @JvmDefault
    fun add(v: Vec2i): Vec2i = super.add(v) as Vec2i

    @JvmDefault
    fun mul(v: Vec2i): Vec2i = super.mul(v) as Vec2i

    @JvmDefault
    fun sub(v: Vec2i): Vec2i = super.sub(v) as Vec2i

    @JvmDefault
    operator fun div(v: Vec2i): Vec2i = super.div(v) as Vec2i

    @JvmDefault
    infix fun dot(v: Vec2i): Vec2i = super.dot(v) as Vec2i

    @JvmDefault
    infix fun project(v: Vec2i): Vec2i = super.project(v) as Vec2i

    @JvmDefault
    fun min(v: Vec2i): Vec2i = super.min(v) as Vec2i

    @JvmDefault
    fun max(v: Vec2i): Vec2i = super.max(v) as Vec2i

    @JvmDefault
    operator fun component1(): Int = x

    @JvmDefault
    operator fun component2(): Int = y

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

    private class Impl(override val x: Int, override val y: Int): Vec2i {
        override val xd: Double get() = x.toDouble()
        override val yd: Double get() = y.toDouble()
        override val xf: Float get() = x.toFloat()
        override val yf: Float get() = y.toFloat()
        override val xi: Int get() = x
        override val yi: Int get() = y

        override fun add(x: Int, y: Int): Vec2i = getPooled(this.x + x, this.y + y)
        override fun add(x: Float, y: Float): Vec2d = Vec2d.getPooled(this.x + x, this.y + y)
        override fun add(x: Double, y: Double): Vec2d = Vec2d.getPooled(this.x + x, this.y + y)

        override fun sub(x: Int, y: Int): Vec2i = getPooled(this.x - x, this.y - y)
        override fun sub(x: Float, y: Float): Vec2d = Vec2d.getPooled(this.x - x, this.y - y)
        override fun sub(x: Double, y: Double): Vec2d = Vec2d.getPooled(this.x - x, this.y - y)

        override fun mul(a: Int): Vec2i = getPooled(this.x * a, this.y * a)
        override fun mul(a: Float): Vec2d = Vec2d.getPooled(this.x * a, this.y * a)
        override fun mul(a: Double): Vec2d = Vec2d.getPooled(this.x * a, this.y * a)
        override fun mul(x: Int, y: Int): Vec2i = getPooled(this.x * x, this.y * y)
        override fun mul(x: Float, y: Float): Vec2d = Vec2d.getPooled(this.x * x, this.y * y)
        override fun mul(x: Double, y: Double): Vec2d = Vec2d.getPooled(this.x * x, this.y * y)

        override fun div(a: Int): Vec2i = getPooled(this.x / a, this.y / a)
        override fun div(a: Float): Vec2d = Vec2d.getPooled(this.x / a, this.y / a)
        override fun div(a: Double): Vec2d = Vec2d.getPooled(this.x / a, this.y / a)
        override fun div(x: Int, y: Int): Vec2i = getPooled(this.x / x, this.y / y)
        override fun div(x: Float, y: Float): Vec2d = Vec2d.getPooled(this.x / x, this.y / y)
        override fun div(x: Double, y: Double): Vec2d = Vec2d.getPooled(this.x / x, this.y / y)

        override fun pow(a: Int): Vec2i = getPooled(
            Math.pow(this.xd, a.toDouble()).toInt(),
            Math.pow(this.yd, a.toDouble()).toInt()
        )
        override fun pow(a: Float): Vec2d = Vec2d.getPooled(
            Math.pow(this.xd, a.toDouble()),
            Math.pow(this.yd, a.toDouble())
        )
        override fun pow(a: Double): Vec2d = Vec2d.getPooled(
            Math.pow(this.xd, a),
            Math.pow(this.yd, a)
        )

        override fun ceil(): Vec2i = this
        override fun floor(): Vec2i = this
        override fun round(): Vec2i = this
        override fun abs(): Vec2i = getPooled(x.absoluteValue, y.absoluteValue)
        override fun negate(): Vec2i = getPooled(-x, -y)

        override fun min(x: Int, y: Int): Vec2i = getPooled(Math.min(this.x, x), Math.min(this.y, y))
        override fun min(x: Float, y: Float): Vec2d = Vec2d.getPooled(Math.min(this.xf, x), Math.min(this.yf, y))
        override fun min(x: Double, y: Double): Vec2d = Vec2d.getPooled(Math.min(this.xd, x), Math.min(this.yd, y))
        override fun max(x: Int, y: Int): Vec2i = getPooled(Math.max(this.x, x), Math.max(this.y, y))
        override fun max(x: Float, y: Float): Vec2d = Vec2d.getPooled(Math.max(this.xf, x), Math.max(this.yf, y))
        override fun max(x: Double, y: Double): Vec2d = Vec2d.getPooled(Math.max(this.xd, x), Math.max(this.yd, y))
    }
}
