package com.teamwizardry.librarianlib.math.vector

import net.minecraft.util.math.BlockPos
import kotlin.math.absoluteValue
import net.minecraft.util.math.Vec3d as MCVec3d

interface Vec3i: Vec3 {
    val x: Int
    val y: Int
    val z: Int

    override fun add(x: Int, y: Int, z: Int): Vec3i
    override fun sub(x: Int, y: Int, z: Int): Vec3i
    override fun mul(a: Int): Vec3i
    override fun mul(x: Int, y: Int, z: Int): Vec3i
    override fun pow(a: Int): Vec3i
    override fun abs(): Vec3i
    override fun negate(): Vec3i
    override fun min(x: Int, y: Int, z: Int): Vec3i
    override fun max(x: Int, y: Int, z: Int): Vec3i

    @JvmDefault fun add(v: Vec3i): Vec3i = add(v.x, v.y, v.z)
    @JvmDefault fun mul(v: Vec3i): Vec3i = mul(v.x, v.y, v.z)
    @JvmDefault fun sub(v: Vec3i): Vec3i = sub(v.x, v.y, v.z)
    @JvmDefault fun min(v: Vec3i): Vec3i = min(v.x, v.y, v.z)
    @JvmDefault fun max(v: Vec3i): Vec3i = max(v.x, v.y, v.z)
    @JvmDefault override fun cross(x: Int, y: Int, z: Int): Vec3i = getPooled(
        y * z - z * y,
        z * x - x * z,
        x * y - y * x
    )
    @JvmDefault @JvmSynthetic operator fun plus(v: Vec3i): Vec3i = add(v.xi, v.yi, v.zi)
    @JvmDefault @JvmSynthetic operator fun minus(v: Vec3i): Vec3i = sub(v.xi, v.yi, v.zi)
    @JvmDefault @JvmSynthetic operator fun times(v: Vec3i): Vec3i = mul(v.xi, v.yi, v.zi)
    @JvmDefault @JvmSynthetic override operator fun unaryMinus(): Vec3i = negate()
    @JvmDefault @JvmSynthetic override operator fun times(a: Int): Vec3i = mul(a)

    companion object {
        private val pool = object: VectorPool<Vec3i>(3, VectorPool.BITS_3D) {
            override fun create(x: Int, y: Int, z: Int, w: Int): Vec3i {
                return Impl(x, y, z)
            }

            override fun hit(value: Vec3i): Vec3i {
                return value
            }
        }
        private val mcPool = object: VectorPool<BlockPos>(3, VectorPool.BITS_3D) {
            override fun create(x: Int, y: Int, z: Int, w: Int): BlockPos {
                return BlockPos(x, y, z)
            }

            override fun hit(value: BlockPos): BlockPos {
                return value
            }
        }

        @JvmStatic
        fun getPooled(x: Int, y: Int, z: Int): Vec3i = pool.getPooled(x, y, z, 0)

        @JvmStatic
        fun getPooledMC(x: Int, y: Int, z: Int): BlockPos = mcPool.getPooled(x, y, z, 0)
    }

    private class Impl(override val x: Int, override val y: Int, override val z: Int): Vec3i {
        override val xd: Double get() = x.toDouble()
        override val yd: Double get() = y.toDouble()
        override val zd: Double get() = z.toDouble()
        override val xf: Float get() = x.toFloat()
        override val yf: Float get() = y.toFloat()
        override val zf: Float get() = z.toFloat()
        override val xi: Int get() = x
        override val yi: Int get() = y
        override val zi: Int get() = z

        override fun add(x: Int, y: Int, z: Int): Vec3i = getPooled(this.x + x, this.y + y, this.z + z)
        override fun add(x: Float, y: Float, z: Float): Vec3d = Vec3d.getPooled(this.x + x, this.y + y, this.z + z)
        override fun add(x: Double, y: Double, z: Double): Vec3d = Vec3d.getPooled(this.x + x, this.y + y, this.z + z)

        override fun sub(x: Int, y: Int, z: Int): Vec3i = getPooled(this.x - x, this.y - y, this.z - z)
        override fun sub(x: Float, y: Float, z: Float): Vec3d = Vec3d.getPooled(this.x - x, this.y - y, this.z - z)
        override fun sub(x: Double, y: Double, z: Double): Vec3d = Vec3d.getPooled(this.x - x, this.y - y, this.z - z)

        override fun mul(a: Int): Vec3i = getPooled(this.x * a, this.y * a, this.z * a)
        override fun mul(a: Float): Vec3d = Vec3d.getPooled(this.x * a, this.y * a, this.z * a)
        override fun mul(a: Double): Vec3d = Vec3d.getPooled(this.x * a, this.y * a, this.z * a)
        override fun mul(x: Int, y: Int, z: Int): Vec3i = getPooled(this.x * x, this.y * y, this.z * z)
        override fun mul(x: Float, y: Float, z: Float): Vec3d = Vec3d.getPooled(this.x * x, this.y * y, this.z * z)
        override fun mul(x: Double, y: Double, z: Double): Vec3d = Vec3d.getPooled(this.x * x, this.y * y, this.z * z)

        override fun div(a: Int): Vec3d = Vec3d.getPooled(this.xd / a, this.yd / a, this.zd / a)
        override fun div(a: Float): Vec3d = Vec3d.getPooled(this.x / a, this.y / a, this.z / a)
        override fun div(a: Double): Vec3d = Vec3d.getPooled(this.x / a, this.y / a, this.z / a)
        override fun div(x: Int, y: Int, z: Int): Vec3d = Vec3d.getPooled(this.xd / x, this.yd / y, this.zd / z)
        override fun div(x: Float, y: Float, z: Float): Vec3d = Vec3d.getPooled(this.x / x, this.y / y, this.z / z)
        override fun div(x: Double, y: Double, z: Double): Vec3d = Vec3d.getPooled(this.x / x, this.y / y, this.z / z)

        override fun pow(a: Int): Vec3i = getPooled(
            Math.pow(x.toDouble(), a.toDouble()).toInt(),
            Math.pow(y.toDouble(), a.toDouble()).toInt(),
            Math.pow(z.toDouble(), a.toDouble()).toInt()
        )
        override fun pow(a: Float): Vec3d = Vec3d.getPooled(Math.pow(xd, a.toDouble()), Math.pow(yd, a.toDouble()), Math.pow(zd, a.toDouble()))
        override fun pow(a: Double): Vec3d = Vec3d.getPooled(Math.pow(xd, a), Math.pow(yd, a), Math.pow(zd, a))

        override fun ceil(): Vec3i = this
        override fun floor(): Vec3i = this
        override fun round(): Vec3i = this
        override fun abs(): Vec3i = getPooled(x.absoluteValue, y.absoluteValue, z.absoluteValue)
        override fun negate(): Vec3i = getPooled(-x, -y, -z)

        override fun min(x: Int, y: Int, z: Int): Vec3i = getPooled(
            Math.min(this.xd, x.toDouble()).toInt(),
            Math.min(this.yd, y.toDouble()).toInt(),
            Math.min(this.zd, z.toDouble()).toInt()
        )
        override fun min(x: Float, y: Float, z: Float): Vec3d = Vec3d.getPooled(Math.min(this.xd, x.toDouble()), Math.min(this.yd, y.toDouble()), Math.max(this.zd, z.toDouble()))
        override fun min(x: Double, y: Double, z: Double): Vec3d = Vec3d.getPooled(Math.min(this.xd, x), Math.min(this.yd, y), Math.max(this.zd, z))
        override fun max(x: Int, y: Int, z: Int): Vec3i = getPooled(
            Math.max(this.xd, x.toDouble()).toInt(),
            Math.max(this.yd, y.toDouble()).toInt(),
            Math.max(this.zd, z.toDouble()).toInt()
        )
        override fun max(x: Float, y: Float, z: Float): Vec3d = Vec3d.getPooled(Math.max(this.xd, x.toDouble()), Math.max(this.yd, y.toDouble()), Math.max(this.zd, z.toDouble()))
        override fun max(x: Double, y: Double, z: Double): Vec3d = Vec3d.getPooled(Math.max(this.xd, x), Math.max(this.yd, y), Math.max(this.zd, z))

        override fun toDouble(): Vec3d = Vec3d.getPooled(xd, yd, zd)
        override fun toInt(): Vec3i = this
        override fun toMC(): MCVec3d = Vec3d.getPooledMC(xd, yd, zd)
        override fun toBlockPos(): BlockPos = Vec3i.getPooledMC(xi, yi, zi)
    }
}
