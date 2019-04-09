package com.teamwizardry.librarianlib.math.vector

import net.minecraft.util.math.BlockPos

interface Vec3 {
    val xd: Double
    val yd: Double
    val zd: Double
    val xf: Float
    val yf: Float
    val zf: Float
    val xi: Int
    val yi: Int
    val zi: Int

    fun add(x: Int, y: Int, z: Int): Vec3
    fun add(x: Float, y: Float, z: Float): Vec3d
    fun add(x: Double, y: Double, z: Double): Vec3d

    fun sub(x: Int, y: Int, z: Int): Vec3
    fun sub(x: Float, y: Float, z: Float): Vec3d
    fun sub(x: Double, y: Double, z: Double): Vec3d

    fun mul(a: Int): Vec3
    fun mul(a: Float): Vec3d
    fun mul(a: Double): Vec3d
    fun mul(x: Int, y: Int, z: Int): Vec3
    fun mul(x: Float, y: Float, z: Float): Vec3d
    fun mul(x: Double, y: Double, z: Double): Vec3d

    operator fun div(a: Int): Vec3d
    operator fun div(a: Float): Vec3d
    operator fun div(a: Double): Vec3d
    fun div(x: Int, y: Int, z: Int): Vec3d
    fun div(x: Float, y: Float, z: Float): Vec3d
    fun div(x: Double, y: Double, z: Double): Vec3d

    fun pow(a: Int): Vec3
    fun pow(a: Float): Vec3d
    fun pow(a: Double): Vec3d

    fun ceil(): Vec3i
    fun floor(): Vec3i
    fun round(): Vec3i
    fun abs(): Vec3
    fun negate(): Vec3

    fun min(x: Int, y: Int, z: Int): Vec3
    fun min(x: Float, y: Float, z: Float): Vec3d
    fun min(x: Double, y: Double, z: Double): Vec3d

    fun max(x: Int, y: Int, z: Int): Vec3
    fun max(x: Float, y: Float, z: Float): Vec3d
    fun max(x: Double, y: Double, z: Double): Vec3d

    @JvmDefault
    fun add(v: Vec3): Vec3 = when(v) {
        is Vec3i -> add(v.xi, v.yi, v.zi)
        else -> add(v.xd, v.yd, v.zd)
    }

    @JvmDefault
    fun mul(v: Vec3): Vec3 = when(v) {
        is Vec3i -> mul(v.xi, v.yi, v.zi)
        else -> mul(v.xd, v.yd, v.zd)
    }

    @JvmDefault
    fun sub(v: Vec3): Vec3 = when(v) {
        is Vec3i -> sub(v.xi, v.yi, v.zi)
        else -> sub(v.xd, v.yd, v.zd)
    }

    @JvmDefault
    operator fun div(v: Vec3): Vec3d = div(v.xd, v.yd, v.zd)

    @JvmDefault
    infix fun dot(v: Vec3): Double = when(v) {
        is Vec3i -> dot(v.xi, v.yi, v.zi)
        else -> dot(v.xd, v.yd, v.zd)
    }

    @JvmDefault
    infix fun project(v: Vec3): Vec3d = when(v) {
        is Vec3i -> project(v.xi, v.yi, v.zi)
        else -> project(v.xd, v.yd, v.zd)
    }

    @JvmDefault
    infix fun cross(v: Vec3): Vec3 = when(v) {
        is Vec3i -> project(v.xi, v.yi, v.zi)
        else -> project(v.xd, v.yd, v.zd)
    }

    @JvmDefault
    fun min(v: Vec3): Vec3 = when(v) {
        is Vec3i -> min(v.xi, v.yi, v.zi)
        else -> min(v.xd, v.yd, v.zd)
    }

    @JvmDefault
    fun max(v: Vec3): Vec3 = when(v) {
        is Vec3i -> max(v.xi, v.yi, v.zi)
        else -> max(v.xd, v.yd, v.zd)
    }

    @JvmDefault fun dot(x: Int, y: Int, z: Int): Double = this.xd * x + this.yd * y + this.zd * z
    @JvmDefault fun dot(x: Float, y: Float, z: Float): Double = this.xd * x + this.yd * y + this.zd * z
    @JvmDefault fun dot(x: Double, y: Double, z: Double): Double = this.xd * x + this.yd * y + this.zd * z

    @JvmDefault fun cross(x: Int, y: Int, z: Int): Vec3
    @JvmDefault fun cross(x: Float, y: Float, z: Float): Vec3d = cross(x.toDouble(), y.toDouble(), z.toDouble())
    @JvmDefault fun cross(x: Double, y: Double, z: Double): Vec3d = create(
        yd * z - zd * y,
        zd * x - xd * z,
        xd * y - yd * x
    )

    @JvmDefault fun distanceSquared(v: Vec3): Double = distanceSquared(v.xd, v.yd, v.zd)
    @JvmDefault fun distanceSquared(x: Int, y: Int, z: Int): Double = distanceSquared(x.toDouble(), y.toDouble(), z.toDouble())
    @JvmDefault fun distanceSquared(x: Float, y: Float, z: Float): Double = distanceSquared(x.toDouble(), y.toDouble(), z.toDouble())
    @JvmDefault fun distanceSquared(x: Double, y: Double, z: Double): Double {
        val Dx = xd - x
        val Dy = yd - y
        val Dz = zd - z
        return Dx * Dx + Dy * Dy + Dz * Dz
    }

    @JvmDefault fun distance(v: Vec3): Double = distance(v.xd, v.yd, v.zd)
    @JvmDefault fun distance(x: Int, y: Int, z: Int): Double = distance(x.toDouble(), y.toDouble(), z.toDouble())
    @JvmDefault fun distance(x: Float, y: Float, z: Float): Double = distance(x.toDouble(), y.toDouble(), z.toDouble())
    @JvmDefault fun distance(x: Double, y: Double, z: Double): Double {
        val Dx = xd - x
        val Dy = yd - y
        val Dz = zd - z
        return Math.sqrt(Dx * Dx + Dy * Dy + Dz * Dz)
    }

    @JvmDefault fun lengthSquared(): Double = xd * xd + yd * yd + zd * zd
    @JvmDefault fun length(): Double = Math.sqrt(lengthSquared())

    @JvmDefault fun normalize(): Vec3d {
        val length = length()
        if (Math.abs(length) < DBL_EPSILON) {
            throw ArithmeticException("Cannot normalize the zero vector")
        }
        return create(xd / length, yd / length, zd / length)
    }

    @JvmDefault fun project(x: Int, y: Int, z: Int): Vec3d = project(x.toDouble(), y.toDouble(), z.toDouble())
    @JvmDefault fun project(x: Float, y: Float, z: Float): Vec3d = project(x.toDouble(), y.toDouble(), z.toDouble())
    @JvmDefault fun project(x: Double, y: Double, z: Double): Vec3d {
        val lengthSquared = x * x + y * y + z * z
        if (Math.abs(lengthSquared) < DBL_EPSILON) {
            throw ArithmeticException("Cannot project onto the zero vector")
        }
        val a: Double = dot(x, y, z) / lengthSquared
        return create(a * x, a * y, a * z)
    }

    fun toDouble(): Vec3d
    fun toInt(): Vec3i
    fun toMC(): net.minecraft.util.math.Vec3d
    fun toBlockPos(): BlockPos

    //kotlin aliases

    @JvmDefault @JvmSynthetic operator fun plus(v: Vec3): Vec3 = add(v)
    @JvmDefault @JvmSynthetic operator fun minus(v: Vec3): Vec3 = sub(v)
    @JvmDefault @JvmSynthetic operator fun times(v: Vec3): Vec3 = sub(v)
    @JvmDefault @JvmSynthetic operator fun unaryMinus(): Vec3 = negate()

    @JvmDefault @JvmSynthetic operator fun times(a: Int): Vec3 = mul(a)
    @JvmDefault @JvmSynthetic operator fun times(a: Float): Vec3d = mul(a)
    @JvmDefault @JvmSynthetic operator fun times(a: Double): Vec3d = mul(a)

    companion object {
        @JvmStatic
        fun create(x: Double, y: Double, z: Double): Vec3d {
            return Vec3d.getPooled(x, y, z)
        }

        @JvmStatic
        fun create(x: Int, y: Int, z: Int): Vec3i {
            return Vec3i.getPooled(x, y, z)
        }

        /** "close to zero" value */
        val DBL_EPSILON = java.lang.Double.longBitsToDouble(0x3cb0000000000000L)
        /** "close to zero" value */
        val FLT_EPSILON = java.lang.Float.intBitsToFloat(0x34000000)

        @JvmSynthetic
        fun invoke(x: Int, y: Int, z: Int): Vec3i = create(x, y, z)

        @Suppress("NOTHING_TO_INLINE")
        inline fun invoke(x: Number, y: Number, z: Number): Vec3d = create(x.toDouble(), y.toDouble(), z.toDouble())
    }
}
