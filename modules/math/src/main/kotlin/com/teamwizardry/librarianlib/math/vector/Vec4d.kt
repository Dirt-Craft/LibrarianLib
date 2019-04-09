package com.teamwizardry.librarianlib.math.vector

import com.teamwizardry.librarianlib.math.utils.ceilToInt
import com.teamwizardry.librarianlib.math.utils.floorToInt
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

interface Vec4d: Vec4 {
    val x: Double
    val y: Double
    val z: Double
    val w: Double

    @JvmDefault override val xd: Double get() = x
    @JvmDefault override val yd: Double get() = y
    @JvmDefault override val zd: Double get() = z
    @JvmDefault override val wd: Double get() = w
    @JvmDefault override val xf: Float get() = x.toFloat()
    @JvmDefault override val yf: Float get() = y.toFloat()
    @JvmDefault override val zf: Float get() = z.toFloat()
    @JvmDefault override val wf: Float get() = w.toFloat()
    @JvmDefault override val xi: Int get() = x.toInt()
    @JvmDefault override val yi: Int get() = y.toInt()
    @JvmDefault override val zi: Int get() = z.toInt()
    @JvmDefault override val wi: Int get() = w.toInt()

    @JvmDefault override fun add(x: Int, y: Int, z: Int, w: Int): Vec4d = getPooled(this.x + x, this.y + y, this.z + z, this.w + w)
    @JvmDefault override fun add(x: Float, y: Float, z: Float, w: Float): Vec4d = getPooled(this.x + x, this.y + y, this.z + z, this.w + w)
    @JvmDefault override fun add(x: Double, y: Double, z: Double, w: Double): Vec4d = getPooled(this.x + x, this.y + y, this.z + z, this.w + w)

    @JvmDefault override fun sub(x: Int, y: Int, z: Int, w: Int): Vec4d = getPooled(this.x - x, this.y - y, this.z - z, this.w - w)
    @JvmDefault override fun sub(x: Float, y: Float, z: Float, w: Float): Vec4d = getPooled(this.x - x, this.y - y, this.z - z, this.w - w)
    @JvmDefault override fun sub(x: Double, y: Double, z: Double, w: Double): Vec4d = getPooled(this.x - x, this.y - y, this.z - z, this.w - w)

    @JvmDefault override fun mul(a: Int): Vec4d = getPooled(this.x * a, this.y * a, this.z * a, this.w * a)
    @JvmDefault override fun mul(a: Float): Vec4d = getPooled(this.x * a, this.y * a, this.z * a, this.w * a)
    @JvmDefault override fun mul(a: Double): Vec4d = getPooled(this.x * a, this.y * a, this.z * a, this.w * a)
    @JvmDefault override fun mul(x: Int, y: Int, z: Int, w: Int): Vec4d = getPooled(this.x * x, this.y * y, this.z * z, this.w * w)
    @JvmDefault override fun mul(x: Float, y: Float, z: Float, w: Float): Vec4d = getPooled(this.x * x, this.y * y, this.z * z, this.w * w)
    @JvmDefault override fun mul(x: Double, y: Double, z: Double, w: Double): Vec4d = getPooled(this.x * x, this.y * y, this.z * z, this.w * w)

    @JvmDefault override fun div(a: Int): Vec4d = getPooled(this.x / a, this.y / a, this.z / a, this.w / a)
    @JvmDefault override fun div(a: Float): Vec4d = getPooled(this.x / a, this.y / a, this.z / a, this.w / a)
    @JvmDefault override fun div(a: Double): Vec4d = getPooled(this.x / a, this.y / a, this.z / a, this.w / a)
    @JvmDefault override fun div(x: Int, y: Int, z: Int, w: Int): Vec4d = getPooled(this.x / x, this.y / y, this.z / z, this.w / w)
    @JvmDefault override fun div(x: Float, y: Float, z: Float, w: Float): Vec4d = getPooled(this.x / x, this.y / y, this.z / z, this.w / w)
    @JvmDefault override fun div(x: Double, y: Double, z: Double, w: Double): Vec4d = getPooled(this.x / x, this.y / y, this.z / z, this.w / w)

    @JvmDefault override fun pow(a: Int): Vec4d = getPooled(Math.pow(x, a.toDouble()), Math.pow(y, a.toDouble()), Math.pow(z, a.toDouble()), Math.pow(w, a.toDouble()))
    @JvmDefault override fun pow(a: Float): Vec4d = getPooled(Math.pow(x, a.toDouble()), Math.pow(y, a.toDouble()), Math.pow(z, a.toDouble()), Math.pow(w, a.toDouble()))
    @JvmDefault override fun pow(a: Double): Vec4d = getPooled(Math.pow(x, a), Math.pow(y, a), Math.pow(z, a), Math.pow(w, a))

    @JvmDefault override fun ceil(): Vec4i = Vec4i.getPooled(ceilToInt(x), ceilToInt(y), ceilToInt(z), ceilToInt(w))
    @JvmDefault override fun floor(): Vec4i = Vec4i.getPooled(floorToInt(x), floorToInt(y), floorToInt(z), ceilToInt(w))
    @JvmDefault override fun round(): Vec4i = Vec4i.getPooled(x.roundToInt(), y.roundToInt(), z.roundToInt(), w.roundToInt())
    @JvmDefault override fun abs(): Vec4d = getPooled(x.absoluteValue, y.absoluteValue, z.absoluteValue, w.absoluteValue)
    @JvmDefault override fun negate(): Vec4d = getPooled(-x, -y, -z, -w)

    @JvmDefault override fun min(x: Int, y: Int, z: Int, w: Int): Vec4d = getPooled(
        Math.min(this.x, x.toDouble()),
        Math.min(this.y, y.toDouble()),
        Math.max(this.z, z.toDouble()),
        Math.max(this.w, w.toDouble())
    )
    @JvmDefault override fun min(x: Float, y: Float, z: Float, w: Float): Vec4d = getPooled(
        Math.min(this.x, x.toDouble()),
        Math.min(this.y, y.toDouble()),
        Math.max(this.z, z.toDouble()),
        Math.max(this.w, w.toDouble())
    )
    @JvmDefault override fun min(x: Double, y: Double, z: Double, w: Double): Vec4d = getPooled(
        Math.min(this.x, x),
        Math.min(this.y, y),
        Math.max(this.z, z),
        Math.max(this.w, w)
    )
    @JvmDefault override fun max(x: Int, y: Int, z: Int, w: Int): Vec4d = getPooled(
        Math.max(this.x, x.toDouble()),
        Math.max(this.y, y.toDouble()),
        Math.max(this.z, z.toDouble()),
        Math.max(this.w, w.toDouble())
    )
    @JvmDefault override fun max(x: Float, y: Float, z: Float, w: Float): Vec4d = getPooled(
        Math.max(this.x, x.toDouble()),
        Math.max(this.y, y.toDouble()),
        Math.max(this.z, z.toDouble()),
        Math.max(this.w, w.toDouble())
    )
    @JvmDefault override fun max(x: Double, y: Double, z: Double, w: Double): Vec4d = getPooled(
        Math.max(this.x, x),
        Math.max(this.y, y),
        Math.max(this.z, z),
        Math.max(this.w, w)
    )

    @JvmDefault override fun toDouble(): Vec4d = this
    @JvmDefault override fun toInt(): Vec4i = Vec4i.getPooled(xi, yi, zi, wi)

    @JvmDefault override fun add(v: Vec4): Vec4d = super.add(v) as Vec4d
    @JvmDefault override fun mul(v: Vec4): Vec4d = super.mul(v) as Vec4d
    @JvmDefault override fun sub(v: Vec4): Vec4d = super.sub(v) as Vec4d
    @JvmDefault override fun min(v: Vec4): Vec4d = super.min(v) as Vec4d
    @JvmDefault override fun max(v: Vec4): Vec4d = super.max(v) as Vec4d
    @JvmDefault @JvmSynthetic override operator fun plus(v: Vec4): Vec4d = add(v)
    @JvmDefault @JvmSynthetic override operator fun minus(v: Vec4): Vec4d = sub(v)
    @JvmDefault @JvmSynthetic override operator fun times(v: Vec4): Vec4d = mul(v)
    @JvmDefault @JvmSynthetic override operator fun unaryMinus(): Vec4d = negate()
    @JvmDefault @JvmSynthetic override operator fun times(a: Int): Vec4d = mul(a)
    @JvmDefault @JvmSynthetic override operator fun times(a: Float): Vec4d = mul(a)
    @JvmDefault @JvmSynthetic override operator fun times(a: Double): Vec4d = mul(a)

    companion object {
        private val pool = object: VectorPool<Vec4d>(4, VectorPool.BITS_4D) {
            override fun create(x: Double, y: Double, z: Double, w: Double): Vec4d {
                return Impl(x, y, z, w)
            }

            override fun hit(value: Vec4d): Vec4d {
                return value
            }
        }

        @JvmStatic
        fun getPooled(x: Double, y: Double, z: Double, w: Double): Vec4d = pool.getPooled(x, y, z, w)
        @JvmStatic
        fun getPooled(x: Float, y: Float, z: Float, w: Float): Vec4d = pool.getPooled(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())
    }

    private class Impl(override val x: Double, override val y: Double, override val z: Double, override val w: Double): Vec4d
}
