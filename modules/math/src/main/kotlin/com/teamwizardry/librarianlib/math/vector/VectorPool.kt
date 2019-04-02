package com.teamwizardry.librarianlib.math.vector

internal abstract class VectorPool<T>(val axes: Int, val bits: Int) {
    val mask: Int
    val min: Int
    val max: Int
    val cache: Array<T>

    init {
        this.mask = (1 shl bits) - 1
        this.max = (1 shl bits - 1) - 1
        this.min = -(1 shl bits - 1)

        @Suppress("UNCHECKED_CAST")
        this.cache = arrayOfNulls<Any>(1 shl bits * axes) as Array<T>

        val xMin = if (axes < 1) 0 else min
        val xMax = if (axes < 1) 0 else max
        val yMin = if (axes < 2) 0 else min
        val yMax = if (axes < 2) 0 else max
        val zMin = if (axes < 3) 0 else min
        val zMax = if (axes < 3) 0 else max
        val wMin = if (axes < 4) 0 else min
        val wMax = if (axes < 4) 0 else max

        for (x in xMin..xMax) {
            for (y in yMin..yMax) {
                for (z in zMin..zMax) {
                    for (w in wMin..wMax) {
                        cache[index(x, y, z, w)] = create(x, y, z, w)
                    }
                }
            }
        }
    }

    private fun index(x: Int, y: Int, z: Int, w: Int): Int {
        return (if (axes < 1) 0 else x - min and mask) or
            (if (axes < 2) 0 else y - min and mask shl bits) or
            (if (axes < 3) 0 else z - min and mask shl bits * 2) or
            if (axes < 4) 0 else w - min and mask shl bits * 3
    }

    private fun isPooled(x: Int, y: Int, z: Int, w: Int): Boolean {
        return min <= x && x <= max &&
            (axes < 2 || min <= y && y <= max) &&
            (axes < 3 || min <= z && z <= max) &&
            (axes < 4 || min <= w && w <= max)
    }

    private fun isPooled(x: Float, y: Float, z: Float, w: Float): Boolean {
        return isPooled(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())
    }

    private fun isPooled(x: Double, y: Double, z: Double, w: Double): Boolean {
        return x.toInt().toDouble() == x && y.toInt().toDouble() == y && z.toInt().toDouble() == z && w.toInt().toDouble() == w &&
            isPooled(x.toInt(), y.toInt(), z.toInt(), w.toInt())
    }

    fun getPooled(x: Int, y: Int, z: Int, w: Int): T {
        return if (isPooled(x, y, z, w)) cache[index(x, y, z, w)] else create(x, y, z, w)
    }

    fun getPooled(x: Float, y: Float, z: Float, w: Float): T {
        return if (isPooled(x, y, z, w)) cache[index(x.toInt(), y.toInt(), z.toInt(), w.toInt())] else create(x, y, z, w)
    }

    fun getPooled(x: Double, y: Double, z: Double, w: Double): T {
        return if (isPooled(x, y, z, w)) cache[index(x.toInt(), y.toInt(), z.toInt(), w.toInt())] else create(x, y, z, w)
    }

    protected open fun create(x: Int, y: Int, z: Int, w: Int): T {
        return create(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())
    }

    protected open fun create(x: Float, y: Float, z: Float, w: Float): T {
        return create(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())
    }

    protected open fun create(x: Double, y: Double, z: Double, w: Double): T {
        throw UnsupportedOperationException()
    }

    protected open fun hit(value: T): T {
        return value
    }

    companion object {
        val BITS_2D = 5
        val BITS_3D = 4
        val BITS_4D = 3
    }
}
