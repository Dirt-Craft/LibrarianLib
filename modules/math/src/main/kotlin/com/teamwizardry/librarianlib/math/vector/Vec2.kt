package com.teamwizardry.librarianlib.math.vector

interface Vec2 {
    val xd: Double
    val yd: Double
    val xf: Float
    val yf: Float
    val xi: Int
    val yi: Int

    fun add(x: Int, y: Int): Vec2
    fun add(x: Float, y: Float): Vec2d
    fun add(x: Double, y: Double): Vec2d

    fun sub(x: Int, y: Int): Vec2
    fun sub(x: Float, y: Float): Vec2d
    fun sub(x: Double, y: Double): Vec2d

    fun mul(a: Int): Vec2
    fun mul(a: Float): Vec2d
    fun mul(a: Double): Vec2d
    fun mul(x: Int, y: Int): Vec2
    fun mul(x: Float, y: Float): Vec2d
    fun mul(x: Double, y: Double): Vec2d

    operator fun div(a: Int): Vec2
    operator fun div(a: Float): Vec2d
    operator fun div(a: Double): Vec2d
    fun div(x: Int, y: Int): Vec2
    fun div(x: Float, y: Float): Vec2d
    fun div(x: Double, y: Double): Vec2d

    fun pow(a: Int): Vec2
    fun pow(a: Float): Vec2d
    fun pow(a: Double): Vec2d

    fun ceil(): Vec2i
    fun floor(): Vec2i
    fun round(): Vec2i
    fun abs(): Vec2
    fun negate(): Vec2

    fun min(x: Int, y: Int): Vec2
    fun min(x: Float, y: Float): Vec2d
    fun min(x: Double, y: Double): Vec2d

    fun max(x: Int, y: Int): Vec2
    fun max(x: Float, y: Float): Vec2d
    fun max(x: Double, y: Double): Vec2d

    @JvmDefault
    fun add(v: Vec2): Vec2 = when(v) {
        is Vec2i -> add(v.xi, v.yi)
        else -> add(v.xd, v.yd)
    }

    @JvmDefault
    fun mul(v: Vec2): Vec2 = when(v) {
        is Vec2i -> mul(v.xi, v.yi)
        else -> mul(v.xd, v.yd)
    }

    @JvmDefault
    fun sub(v: Vec2): Vec2 = when(v) {
        is Vec2i -> sub(v.xi, v.yi)
        else -> sub(v.xd, v.yd)
    }

    @JvmDefault
    operator fun div(v: Vec2): Vec2 = when(v) {
        is Vec2i -> div(v.xi, v.yi)
        else -> div(v.xd, v.yd)
    }

    @JvmDefault
    infix fun dot(v: Vec2): Double = when(v) {
        is Vec2i -> dot(v.xi, v.yi)
        else -> dot(v.xd, v.yd)
    }

    @JvmDefault
    infix fun project(v: Vec2): Vec2 = when(v) {
        is Vec2i -> project(v.xi, v.yi)
        else -> project(v.xd, v.yd)
    }

    @JvmDefault
    fun min(v: Vec2): Vec2 = when(v) {
        is Vec2i -> min(v.xi, v.yi)
        else -> min(v.xd, v.yd)
    }

    @JvmDefault
    fun max(v: Vec2): Vec2 = when(v) {
        is Vec2i -> max(v.xi, v.yi)
        else -> max(v.xd, v.yd)
    }

    @JvmDefault fun dot(x: Int, y: Int): Double = this.xd * x + this.yd * y
    @JvmDefault fun dot(x: Float, y: Float): Double = this.xd * x + this.yd * y
    @JvmDefault fun dot(x: Double, y: Double): Double = this.xd * x + this.yd * y

    @JvmDefault fun distanceSquared(v: Vec2): Double = distanceSquared(v.xd, v.yd)
    @JvmDefault fun distanceSquared(x: Int, y: Int): Double = distanceSquared(x.toDouble(), y.toDouble())
    @JvmDefault fun distanceSquared(x: Float, y: Float): Double = distanceSquared(x.toDouble(), y.toDouble())
    @JvmDefault fun distanceSquared(x: Double, y: Double): Double {
        val Dx = xd - x
        val Dy = yd - y
        return Dx * Dx + Dy * Dy
    }

    @JvmDefault fun distance(v: Vec2): Double = distance(v.xd, v.yd)
    @JvmDefault fun distance(x: Int, y: Int): Double = distance(x.toDouble(), y.toDouble())
    @JvmDefault fun distance(x: Float, y: Float): Double = distance(x.toDouble(), y.toDouble())
    @JvmDefault fun distance(x: Double, y: Double): Double {
        val Dx = xd - x
        val Dy = yd - y
        return Math.sqrt(Dx * Dx + Dy * Dy)
    }

    @JvmDefault fun lengthSquared(): Double = xd * xd + yd * yd
    @JvmDefault fun length(): Double = Math.sqrt(lengthSquared())

    @JvmDefault fun normalize(): Vec2d {
        val length = length()
        if (Math.abs(length) < DBL_EPSILON) {
            throw ArithmeticException("Cannot normalize the zero vector")
        }
        return create(xd / length, yd / length)
    }

    @JvmDefault fun project(x: Int, y: Int): Vec2d = project(x.toDouble(), y.toDouble())
    @JvmDefault fun project(x: Float, y: Float): Vec2d = project(x.toDouble(), y.toDouble())
    @JvmDefault fun project(x: Double, y: Double): Vec2d {
        val lengthSquared = x * x + y * y
        if (Math.abs(lengthSquared) < DBL_EPSILON) {
            throw ArithmeticException("Cannot project onto the zero vector")
        }
        val a: Double = dot(x, y) / lengthSquared
        return create(a * x, a * y)
    }

    @JvmDefault fun toDouble(): Vec2d = create(xd, yd)
    @JvmDefault fun toInt(): Vec2i = create(xi, yi)

    //kotlin aliases

    @JvmDefault @JvmSynthetic operator fun plus(v: Vec2): Vec2 = add(v)
    @JvmDefault @JvmSynthetic operator fun minus(v: Vec2): Vec2 = sub(v)
    @JvmDefault @JvmSynthetic operator fun times(v: Vec2): Vec2 = sub(v)
    @JvmDefault @JvmSynthetic operator fun unaryMinus(): Vec2 = negate()

    @JvmDefault @JvmSynthetic operator fun times(a: Int): Vec2 = mul(a)
    @JvmDefault @JvmSynthetic operator fun times(a: Float): Vec2 = mul(a)
    @JvmDefault @JvmSynthetic operator fun times(a: Double): Vec2 = mul(a)

    companion object {
        @JvmStatic
        fun create(x: Double, y: Double): Vec2d {
            return Vec2d.getPooled(x, y)
        }

        @JvmStatic
        fun create(x: Int, y: Int): Vec2i {
            return Vec2i.getPooled(x, y)
        }

        /** "close to zero" value */
        val DBL_EPSILON = java.lang.Double.longBitsToDouble(0x3cb0000000000000L)
        /** "close to zero" value */
        val FLT_EPSILON = java.lang.Float.intBitsToFloat(0x34000000)

        @JvmSynthetic
        fun invoke(x: Int, y: Int): Vec2i = create(x, y)

        @Suppress("NOTHING_TO_INLINE")
        inline fun invoke(x: Number, y: Number): Vec2d = create(x.toDouble(), y.toDouble())
    }
}
