package com.teamwizardry.librarianlib.math.vector

import com.teamwizardry.librarianlib.math.utils.ceilToInt
import com.teamwizardry.librarianlib.math.utils.floorToInt
import net.minecraft.util.math.BlockPos
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import net.minecraft.util.math.Vec3d as MCVec3d

interface Vec3d: Vec3 {
    val x: Double
    val y: Double
    val z: Double

    @JvmDefault override val xd: Double get() = x
    @JvmDefault override val yd: Double get() = y
    @JvmDefault override val zd: Double get() = z
    @JvmDefault override val xf: Float get() = x.toFloat()
    @JvmDefault override val yf: Float get() = y.toFloat()
    @JvmDefault override val zf: Float get() = z.toFloat()
    @JvmDefault override val xi: Int get() = x.toInt()
    @JvmDefault override val yi: Int get() = y.toInt()
    @JvmDefault override val zi: Int get() = z.toInt()

    @JvmDefault override fun add(x: Int, y: Int, z: Int): Vec3d = getPooled(this.x + x, this.y + y, this.z + z)
    @JvmDefault override fun add(x: Float, y: Float, z: Float): Vec3d = getPooled(this.x + x, this.y + y, this.z + z)
    @JvmDefault override fun add(x: Double, y: Double, z: Double): Vec3d = getPooled(this.x + x, this.y + y, this.z + z)

    @JvmDefault override fun sub(x: Int, y: Int, z: Int): Vec3d = getPooled(this.x - x, this.y - y, this.z - z)
    @JvmDefault override fun sub(x: Float, y: Float, z: Float): Vec3d = getPooled(this.x - x, this.y - y, this.z - z)
    @JvmDefault override fun sub(x: Double, y: Double, z: Double): Vec3d = getPooled(this.x - x, this.y - y, this.z - z)

    @JvmDefault override fun mul(a: Int): Vec3d = getPooled(this.x * a, this.y * a, this.z * a)
    @JvmDefault override fun mul(a: Float): Vec3d = getPooled(this.x * a, this.y * a, this.z * a)
    @JvmDefault override fun mul(a: Double): Vec3d = getPooled(this.x * a, this.y * a, this.z * a)
    @JvmDefault override fun mul(x: Int, y: Int, z: Int): Vec3d = getPooled(this.x * x, this.y * y, this.z * z)
    @JvmDefault override fun mul(x: Float, y: Float, z: Float): Vec3d = getPooled(this.x * x, this.y * y, this.z * z)
    @JvmDefault override fun mul(x: Double, y: Double, z: Double): Vec3d = getPooled(this.x * x, this.y * y, this.z * z)

    @JvmDefault override fun div(a: Int): Vec3d = getPooled(this.x / a, this.y / a, this.z / a)
    @JvmDefault override fun div(a: Float): Vec3d = getPooled(this.x / a, this.y / a, this.z / a)
    @JvmDefault override fun div(a: Double): Vec3d = getPooled(this.x / a, this.y / a, this.z / a)
    @JvmDefault override fun div(x: Int, y: Int, z: Int): Vec3d = getPooled(this.x / x, this.y / y, this.z / z)
    @JvmDefault override fun div(x: Float, y: Float, z: Float): Vec3d = getPooled(this.x / x, this.y / y, this.z / z)
    @JvmDefault override fun div(x: Double, y: Double, z: Double): Vec3d = getPooled(this.x / x, this.y / y, this.z / z)

    @JvmDefault override fun pow(a: Int): Vec3d = getPooled(Math.pow(x, a.toDouble()), Math.pow(y, a.toDouble()), Math.pow(z, a.toDouble()))
    @JvmDefault override fun pow(a: Float): Vec3d = getPooled(Math.pow(x, a.toDouble()), Math.pow(y, a.toDouble()), Math.pow(z, a.toDouble()))
    @JvmDefault override fun pow(a: Double): Vec3d = getPooled(Math.pow(x, a), Math.pow(y, a), Math.pow(z, a))

    @JvmDefault override fun ceil(): Vec3i = Vec3i.getPooled(ceilToInt(x), ceilToInt(y), ceilToInt(z))
    @JvmDefault override fun floor(): Vec3i = Vec3i.getPooled(floorToInt(x), floorToInt(y), floorToInt(z))
    @JvmDefault override fun round(): Vec3i = Vec3i.getPooled(x.roundToInt(), y.roundToInt(), z.roundToInt())
    @JvmDefault override fun abs(): Vec3d = getPooled(x.absoluteValue, y.absoluteValue, z.absoluteValue)
    @JvmDefault override fun negate(): Vec3d = getPooled(-x, -y, -z)

    @JvmDefault override fun min(x: Int, y: Int, z: Int): Vec3d = getPooled(Math.min(this.x, x.toDouble()), Math.min(this.y, y.toDouble()), Math.max(this.z, z.toDouble()))
    @JvmDefault override fun min(x: Float, y: Float, z: Float): Vec3d = getPooled(Math.min(this.x, x.toDouble()), Math.min(this.y, y.toDouble()), Math.max(this.z, z.toDouble()))
    @JvmDefault override fun min(x: Double, y: Double, z: Double): Vec3d = getPooled(Math.min(this.x, x), Math.min(this.y, y), Math.max(this.z, z))
    @JvmDefault override fun max(x: Int, y: Int, z: Int): Vec3d = getPooled(Math.max(this.x, x.toDouble()), Math.max(this.y, y.toDouble()), Math.max(this.z, z.toDouble()))
    @JvmDefault override fun max(x: Float, y: Float, z: Float): Vec3d = getPooled(Math.max(this.x, x.toDouble()), Math.max(this.y, y.toDouble()), Math.max(this.z, z.toDouble()))
    @JvmDefault override fun max(x: Double, y: Double, z: Double): Vec3d = getPooled(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z))

    @JvmDefault override fun toDouble(): Vec3d = this
    @JvmDefault override fun toInt(): Vec3i = Vec3i.getPooled(xi, yi, zi)
    @JvmDefault override fun toMC(): MCVec3d = getPooledMC(x, y, z)
    @JvmDefault override fun toBlockPos(): BlockPos = Vec3i.getPooledMC(xi, yi, zi)

    @JvmDefault override fun add(v: Vec3): Vec3d = super.add(v) as Vec3d
    @JvmDefault override fun mul(v: Vec3): Vec3d = super.mul(v) as Vec3d
    @JvmDefault override fun sub(v: Vec3): Vec3d = super.sub(v) as Vec3d
    @JvmDefault override fun min(v: Vec3): Vec3d = super.min(v) as Vec3d
    @JvmDefault override fun max(v: Vec3): Vec3d = super.max(v) as Vec3d
    @JvmDefault override fun cross(x: Int, y: Int, z: Int): Vec3d = cross(x.toDouble(), y.toDouble(), z.toDouble())
    @JvmDefault @JvmSynthetic override operator fun plus(v: Vec3): Vec3d = add(v)
    @JvmDefault @JvmSynthetic override operator fun minus(v: Vec3): Vec3d = sub(v)
    @JvmDefault @JvmSynthetic override operator fun times(v: Vec3): Vec3d = mul(v)
    @JvmDefault @JvmSynthetic override operator fun unaryMinus(): Vec3d = negate()
    @JvmDefault @JvmSynthetic override operator fun times(a: Int): Vec3d = mul(a)
    @JvmDefault @JvmSynthetic override operator fun times(a: Float): Vec3d = mul(a)
    @JvmDefault @JvmSynthetic override operator fun times(a: Double): Vec3d = mul(a)

    companion object {
        private val pool = object: VectorPool<Vec3d>(3, VectorPool.BITS_3D) {
            override fun create(x: Double, y: Double, z: Double, w: Double): Vec3d {
                return Impl(x, y, z)
            }

            override fun hit(value: Vec3d): Vec3d {
                return value
            }
        }
        private val mcPool = object: VectorPool<MCVec3d>(3, VectorPool.BITS_3D) {
            override fun create(x: Double, y: Double, z: Double, w: Double): MCVec3d {
                return MCVec3d(x, y, z)
            }

            override fun hit(value: MCVec3d): net.minecraft.util.math.Vec3d {
                return value
            }
        }

        @JvmStatic
        fun getPooled(x: Double, y: Double, z: Double): Vec3d = pool.getPooled(x, y, z, 0.0)
        @JvmStatic
        fun getPooled(x: Float, y: Float, z: Float): Vec3d = pool.getPooled(x.toDouble(), y.toDouble(), z.toDouble(), 0.0)

        @JvmStatic
        fun getPooledMC(x: Double, y: Double, z: Double): MCVec3d = mcPool.getPooled(x, y, z, 0.0)
        @JvmStatic
        fun getPooledMC(x: Float, y: Float, z: Float): MCVec3d = mcPool.getPooled(x.toDouble(), y.toDouble(), z.toDouble(), 0.0)
    }

    private class Impl(override val x: Double, override val y: Double, override val z: Double): Vec3d
}
