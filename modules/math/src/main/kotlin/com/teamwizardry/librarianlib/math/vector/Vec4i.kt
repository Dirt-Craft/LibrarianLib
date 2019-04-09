package com.teamwizardry.librarianlib.math.vector

import kotlin.math.absoluteValue

interface Vec4i: Vec4 {
    val x: Int
    val y: Int
    val z: Int
    val w: Int

    @JvmDefault override val xd: Double get() = x.toDouble()
    @JvmDefault override val yd: Double get() = y.toDouble()
    @JvmDefault override val zd: Double get() = z.toDouble()
    @JvmDefault override val wd: Double get() = w.toDouble()
    @JvmDefault override val xf: Float get() = x.toFloat()
    @JvmDefault override val yf: Float get() = y.toFloat()
    @JvmDefault override val zf: Float get() = z.toFloat()
    @JvmDefault override val wf: Float get() = w.toFloat()
    @JvmDefault override val xi: Int get() = x
    @JvmDefault override val yi: Int get() = y
    @JvmDefault override val zi: Int get() = z
    @JvmDefault override val wi: Int get() = w

    @JvmDefault override fun add(x: Int, y: Int, z: Int, w: Int): Vec4i = getPooled(this.x + x, this.y + y, this.z + z, this.w + w)
    @JvmDefault override fun add(x: Float, y: Float, z: Float, w: Float): Vec4d = Vec4d.getPooled(this.x + x, this.y + y, this.z + z, this.w + w)
    @JvmDefault override fun add(x: Double, y: Double, z: Double, w: Double): Vec4d = Vec4d.getPooled(this.x + x, this.y + y, this.z + z, this.w + w)

    @JvmDefault override fun sub(x: Int, y: Int, z: Int, w: Int): Vec4i = getPooled(this.x - x, this.y - y, this.z - z, this.w - w)
    @JvmDefault override fun sub(x: Float, y: Float, z: Float, w: Float): Vec4d = Vec4d.getPooled(this.x - x, this.y - y, this.z - z, this.w - w)
    @JvmDefault override fun sub(x: Double, y: Double, z: Double, w: Double): Vec4d = Vec4d.getPooled(this.x - x, this.y - y, this.z - z, this.w - w)

    @JvmDefault override fun mul(a: Int): Vec4i = getPooled(this.x * a, this.y * a, this.z * a, this.w * a)
    @JvmDefault override fun mul(a: Float): Vec4d = Vec4d.getPooled(this.x * a, this.y * a, this.z * a, this.w * a)
    @JvmDefault override fun mul(a: Double): Vec4d = Vec4d.getPooled(this.x * a, this.y * a, this.z * a, this.w * a)
    @JvmDefault override fun mul(x: Int, y: Int, z: Int, w: Int): Vec4i = getPooled(this.x * x, this.y * y, this.z * z, this.w * w)
    @JvmDefault override fun mul(x: Float, y: Float, z: Float, w: Float): Vec4d = Vec4d.getPooled(this.x * x, this.y * y, this.z * z, this.w * w)
    @JvmDefault override fun mul(x: Double, y: Double, z: Double, w: Double): Vec4d = Vec4d.getPooled(this.x * x, this.y * y, this.z * z, this.w * w)

    @JvmDefault override fun div(a: Int): Vec4d = Vec4d.getPooled(this.xd / a, this.yd / a, this.zd / a, this.wd / a)
    @JvmDefault override fun div(a: Float): Vec4d = Vec4d.getPooled(this.x / a, this.y / a, this.z / a, this.w / a)
    @JvmDefault override fun div(a: Double): Vec4d = Vec4d.getPooled(this.x / a, this.y / a, this.z / a, this.w / a)
    @JvmDefault override fun div(x: Int, y: Int, z: Int, w: Int): Vec4d = Vec4d.getPooled(this.xd / x, this.yd / y, this.zd / z, this.zd / z)
    @JvmDefault override fun div(x: Float, y: Float, z: Float, w: Float): Vec4d = Vec4d.getPooled(this.x / x, this.y / y, this.z / z, this.w / w)
    @JvmDefault override fun div(x: Double, y: Double, z: Double, w: Double): Vec4d = Vec4d.getPooled(this.x / x, this.y / y, this.z / z, this.w / w)

    @JvmDefault override fun pow(a: Int): Vec4i = getPooled(
        Math.pow(x.toDouble(), a.toDouble()).toInt(),
        Math.pow(y.toDouble(), a.toDouble()).toInt(),
        Math.pow(z.toDouble(), a.toDouble()).toInt(),
        Math.pow(w.toDouble(), a.toDouble()).toInt()
    )
    @JvmDefault override fun pow(a: Float): Vec4d = Vec4d.getPooled(
        Math.pow(xd, a.toDouble()),
        Math.pow(yd, a.toDouble()),
        Math.pow(zd, a.toDouble()),
        Math.pow(wd, a.toDouble())
    )
    @JvmDefault override fun pow(a: Double): Vec4d = Vec4d.getPooled(
        Math.pow(xd, a),
        Math.pow(yd, a),
        Math.pow(zd, a),
        Math.pow(wd, a)
    )

    @JvmDefault override fun ceil(): Vec4i = this
    @JvmDefault override fun floor(): Vec4i = this
    @JvmDefault override fun round(): Vec4i = this
    @JvmDefault override fun abs(): Vec4i = getPooled(x.absoluteValue, y.absoluteValue, z.absoluteValue, w.absoluteValue)
    @JvmDefault override fun negate(): Vec4i = getPooled(-x, -y, -z, -w)

    @JvmDefault override fun min(x: Int, y: Int, z: Int, w: Int): Vec4i = getPooled(
        Math.min(this.xd, x.toDouble()).toInt(),
        Math.min(this.yd, y.toDouble()).toInt(),
        Math.min(this.zd, z.toDouble()).toInt(),
        Math.min(this.wd, w.toDouble()).toInt()
    )
    @JvmDefault override fun min(x: Float, y: Float, z: Float, w: Float): Vec4d = Vec4d.getPooled(
        Math.min(this.xd, x.toDouble()),
        Math.min(this.yd, y.toDouble()),
        Math.max(this.zd, z.toDouble()),
        Math.max(this.wd, w.toDouble())
    )
    @JvmDefault override fun min(x: Double, y: Double, z: Double, w: Double): Vec4d = Vec4d.getPooled(
        Math.min(this.xd, x),
        Math.min(this.yd, y),
        Math.max(this.zd, z),
        Math.max(this.wd, w)
    )
    @JvmDefault override fun max(x: Int, y: Int, z: Int, w: Int): Vec4i = getPooled(
        Math.max(this.xd, x.toDouble()).toInt(),
        Math.max(this.yd, y.toDouble()).toInt(),
        Math.max(this.zd, z.toDouble()).toInt(),
        Math.max(this.wd, w.toDouble()).toInt()
    )
    @JvmDefault override fun max(x: Float, y: Float, z: Float, w: Float): Vec4d = Vec4d.getPooled(
        Math.max(this.xd, x.toDouble()),
        Math.max(this.yd, y.toDouble()),
        Math.max(this.zd, z.toDouble()),
        Math.max(this.wd, w.toDouble())
    )
    @JvmDefault override fun max(x: Double, y: Double, z: Double, w: Double): Vec4d = Vec4d.getPooled(
        Math.max(this.xd, x),
        Math.max(this.yd, y),
        Math.max(this.zd, z),
        Math.max(this.wd, w)
    )

    @JvmDefault override fun toDouble(): Vec4d = Vec4d.getPooled(xd, yd, zd, wd)
    @JvmDefault override fun toInt(): Vec4i = this

    @JvmDefault fun add(v: Vec4i): Vec4i = add(v.x, v.y, v.z, v.w)
    @JvmDefault fun mul(v: Vec4i): Vec4i = mul(v.x, v.y, v.z, v.w)
    @JvmDefault fun sub(v: Vec4i): Vec4i = sub(v.x, v.y, v.z, v.w)
    @JvmDefault fun min(v: Vec4i): Vec4i = min(v.x, v.y, v.z, v.w)
    @JvmDefault fun max(v: Vec4i): Vec4i = max(v.x, v.y, v.z, v.w)
    @JvmDefault @JvmSynthetic operator fun plus(v: Vec4i): Vec4i = add(v.xi, v.yi, v.zi, v.wi)
    @JvmDefault @JvmSynthetic operator fun minus(v: Vec4i): Vec4i = sub(v.xi, v.yi, v.zi, v.wi)
    @JvmDefault @JvmSynthetic operator fun times(v: Vec4i): Vec4i = mul(v.xi, v.yi, v.zi, v.wi)
    @JvmDefault @JvmSynthetic override operator fun unaryMinus(): Vec4i = negate()
    @JvmDefault @JvmSynthetic override operator fun times(a: Int): Vec4i = mul(a)

    companion object {
        private val pool = object: VectorPool<Vec4i>(4, VectorPool.BITS_4D) {
            override fun create(x: Int, y: Int, z: Int, w: Int): Vec4i {
                return Impl(x, y, z, w)
            }

            override fun hit(value: Vec4i): Vec4i {
                return value
            }
        }

        @JvmStatic
        fun getPooled(x: Int, y: Int, z: Int, w: Int): Vec4i = pool.getPooled(x, y, z, w)
    }

    private class Impl(override val x: Int, override val y: Int, override val z: Int, override val w: Int): Vec4i
}
