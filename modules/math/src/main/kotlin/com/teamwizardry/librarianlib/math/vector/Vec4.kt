package com.teamwizardry.librarianlib.math.vector

interface Vec4 {
    val xd: Double
    val yd: Double
    val zd: Double
    val wd: Double
    val xf: Float
    val yf: Float
    val zf: Float
    val wf: Float
    val xi: Int
    val yi: Int
    val zi: Int
    val wi: Int

    fun add(x: Int, y: Int, z: Int, w: Int): Vec4
    fun add(x: Float, y: Float, z: Float, w: Float): Vec4d
    fun add(x: Double, y: Double, z: Double, w: Double): Vec4d

    fun sub(x: Int, y: Int, z: Int, w: Int): Vec4
    fun sub(x: Float, y: Float, z: Float, w: Float): Vec4d
    fun sub(x: Double, y: Double, z: Double, w: Double): Vec4d

    fun mul(a: Int): Vec4
    fun mul(a: Float): Vec4d
    fun mul(a: Double): Vec4d
    fun mul(x: Int, y: Int, z: Int, w: Int): Vec4
    fun mul(x: Float, y: Float, z: Float, w: Float): Vec4d
    fun mul(x: Double, y: Double, z: Double, w: Double): Vec4d

    operator fun div(a: Int): Vec4d
    operator fun div(a: Float): Vec4d
    operator fun div(a: Double): Vec4d
    fun div(x: Int, y: Int, z: Int, w: Int): Vec4d
    fun div(x: Float, y: Float, z: Float, w: Float): Vec4d
    fun div(x: Double, y: Double, z: Double, w: Double): Vec4d

    fun pow(a: Int): Vec4
    fun pow(a: Float): Vec4d
    fun pow(a: Double): Vec4d

    fun ceil(): Vec4i
    fun floor(): Vec4i
    fun round(): Vec4i
    fun abs(): Vec4
    fun negate(): Vec4

    fun min(x: Int, y: Int, z: Int, w: Int): Vec4
    fun min(x: Float, y: Float, z: Float, w: Float): Vec4d
    fun min(x: Double, y: Double, z: Double, w: Double): Vec4d

    fun max(x: Int, y: Int, z: Int, w: Int): Vec4
    fun max(x: Float, y: Float, z: Float, w: Float): Vec4d
    fun max(x: Double, y: Double, z: Double, w: Double): Vec4d

    @JvmDefault
    fun add(v: Vec4): Vec4 = when(v) {
        is Vec4i -> add(v.xi, v.yi, v.zi, v.wi)
        else -> add(v.xd, v.yd, v.zd, v.wd)
    }

    @JvmDefault
    fun mul(v: Vec4): Vec4 = when(v) {
        is Vec4i -> mul(v.xi, v.yi, v.zi, v.wi)
        else -> mul(v.xd, v.yd, v.zd, v.wd)
    }

    @JvmDefault
    fun sub(v: Vec4): Vec4 = when(v) {
        is Vec4i -> sub(v.xi, v.yi, v.zi, v.wi)
        else -> sub(v.xd, v.yd, v.zd, v.wd)
    }

    @JvmDefault
    operator fun div(v: Vec4): Vec4d = div(v.xd, v.yd, v.zd, v.wd)

    @JvmDefault
    infix fun dot(v: Vec4): Double = when(v) {
        is Vec4i -> dot(v.xi, v.yi, v.zi, v.wi)
        else -> dot(v.xd, v.yd, v.zd, v.wd)
    }

    @JvmDefault
    infix fun project(v: Vec4): Vec4d = when(v) {
        is Vec4i -> project(v.xi, v.yi, v.zi, v.wi)
        else -> project(v.xd, v.yd, v.zd, v.wd)
    }

    @JvmDefault
    fun min(v: Vec4): Vec4 = when(v) {
        is Vec4i -> min(v.xi, v.yi, v.zi, v.wi)
        else -> min(v.xd, v.yd, v.zd, v.wd)
    }

    @JvmDefault
    fun max(v: Vec4): Vec4 = when(v) {
        is Vec4i -> max(v.xi, v.yi, v.zi, v.wi)
        else -> max(v.xd, v.yd, v.zd, v.wd)
    }

    @JvmDefault fun dot(x: Int, y: Int, z: Int, w: Int): Double = this.xd * x + this.yd * y + this.zd * z + this.wd * w
    @JvmDefault fun dot(x: Float, y: Float, z: Float, w: Float): Double = this.xd * x + this.yd * y + this.zd * z + this.wd * w
    @JvmDefault fun dot(x: Double, y: Double, z: Double, w: Double): Double = this.xd * x + this.yd * y + this.zd * z + this.wd * w

    @JvmDefault fun distanceSquared(v: Vec4): Double = distanceSquared(v.xd, v.yd, v.zd, v.wd)
    @JvmDefault fun distanceSquared(x: Int, y: Int, z: Int, w: Int): Double = distanceSquared(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())
    @JvmDefault fun distanceSquared(x: Float, y: Float, z: Float, w: Float): Double = distanceSquared(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())
    @JvmDefault fun distanceSquared(x: Double, y: Double, z: Double, w: Double): Double {
        val Dx = xd - x
        val Dy = yd - y
        val Dz = zd - z
        val Dw = wd - w
        return Dx * Dx + Dy * Dy + Dz * Dz + Dw * Dw
    }

    @JvmDefault fun distance(v: Vec4): Double = distance(v.xd, v.yd, v.zd, v.wd)
    @JvmDefault fun distance(x: Int, y: Int, z: Int, w: Int): Double = distance(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())
    @JvmDefault fun distance(x: Float, y: Float, z: Float, w: Float): Double = distance(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())
    @JvmDefault fun distance(x: Double, y: Double, z: Double, w: Double): Double {
        val Dx = xd - x
        val Dy = yd - y
        val Dz = zd - z
        val Dw = wd - w
        return Math.sqrt(Dx * Dx + Dy * Dy + Dz * Dz + Dw * Dw)
    }

    @JvmDefault fun lengthSquared(): Double = xd * xd + yd * yd + zd * zd + wd * wd
    @JvmDefault fun length(): Double = Math.sqrt(lengthSquared())

    @JvmDefault fun normalize(): Vec4d {
        val length = length()
        if (Math.abs(length) < DBL_EPSILON) {
            throw ArithmeticException("Cannot normalize the zero vector")
        }
        return create(xd / length, yd / length, zd / length, wd / length)
    }

    @JvmDefault fun project(x: Int, y: Int, z: Int, w: Int): Vec4d = project(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())
    @JvmDefault fun project(x: Float, y: Float, z: Float, w: Float): Vec4d = project(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())
    @JvmDefault fun project(x: Double, y: Double, z: Double, w: Double): Vec4d {
        val lengthSquared = x * x + y * y + z * z + w * w
        if (Math.abs(lengthSquared) < DBL_EPSILON) {
            throw ArithmeticException("Cannot project onto the zero vector")
        }
        val a: Double = dot(x, y, z, w) / lengthSquared
        return create(a * x, a * y, a * z, a * w)
    }

    fun toDouble(): Vec4d
    fun toInt(): Vec4i

    //kotlin aliases

    @JvmDefault @JvmSynthetic operator fun plus(v: Vec4): Vec4 = add(v)
    @JvmDefault @JvmSynthetic operator fun minus(v: Vec4): Vec4 = sub(v)
    @JvmDefault @JvmSynthetic operator fun times(v: Vec4): Vec4 = sub(v)
    @JvmDefault @JvmSynthetic operator fun unaryMinus(): Vec4 = negate()

    @JvmDefault @JvmSynthetic operator fun times(a: Int): Vec4 = mul(a)
    @JvmDefault @JvmSynthetic operator fun times(a: Float): Vec4d = mul(a)
    @JvmDefault @JvmSynthetic operator fun times(a: Double): Vec4d = mul(a)

    companion object {
        @JvmStatic
        fun create(x: Double, y: Double, z: Double, w: Double): Vec4d {
            return Vec4d.getPooled(x, y, z, w)
        }

        @JvmStatic
        fun create(x: Int, y: Int, z: Int, w: Int): Vec4i {
            return Vec4i.getPooled(x, y, z, w)
        }

        /** "close to zero" value */
        val DBL_EPSILON = java.lang.Double.longBitsToDouble(0x3cb0000000000000L)
        /** "close to zero" value */
        val FLT_EPSILON = java.lang.Float.intBitsToFloat(0x34000000)

        @JvmSynthetic
        fun invoke(x: Int, y: Int, z: Int, w: Int): Vec4i = create(x, y, z, w)

        @Suppress("NOTHING_TO_INLINE")
        inline fun invoke(x: Number, y: Number, z: Number, w: Number): Vec4d = create(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())
    }
}
