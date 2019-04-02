package com.flowpowered.math.vector;

import java.io.Serializable;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import org.jetbrains.annotations.NotNull;

public class Vec4l implements Vectorl, Vec4, Comparable<Vec4l>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec4l> pool = new FlowPool<Vec4l>(4, FlowPool.BITS_4D) {
        @NotNull
        protected Vec4l hit(Vec4l value) {
            AllocationTracker.cacheHitsVec4l++;
            return value;
        }

        @NotNull
        protected Vec4l create(long x, long y, long z, long w) {
            return new Vec4l(x, y, z, w);
        }
    };

    public static final Vec4l ZERO = new Vec4l(0, 0, 0, 0);
    public static final Vec4l UNIT_X = new Vec4l(1, 0, 0, 0);
    public static final Vec4l UNIT_Y = new Vec4l(0, 1, 0, 0);
    public static final Vec4l UNIT_Z = new Vec4l(0, 0, 1, 0);
    public static final Vec4l UNIT_W = new Vec4l(0, 0, 0, 1);
    public static final Vec4l ONE = new Vec4l(1, 1, 1, 1);
    private final long x;
    private final long y;
    private final long z;
    private final long w;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec4l() {
        this(0, 0, 0, 0);
    }

    public Vec4l(@NotNull Vec2l v) {
        this(v, 0, 0);
    }

    public Vec4l(@NotNull Vec2l v, double z, double w) {
        this(v, GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vec4l(@NotNull Vec2l v, long z, long w) {
        this(v.getX(), v.getY(), z, w);
    }

    public Vec4l(@NotNull Vec3l v) {
        this(v, 0);
    }

    public Vec4l(@NotNull Vec3l v, double w) {
        this(v, GenericMath.floorl(w));
    }

    public Vec4l(@NotNull Vec3l v, long w) {
        this(v.getX(), v.getY(), v.getZ(), w);
    }

    public Vec4l(@NotNull Vec4l v) {
        this(v.x, v.y, v.z, v.w);
    }

    public Vec4l(@NotNull VecNl v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0, v.size() > 3 ? v.get(3) : 0);
    }

    public Vec4l(double x, double y, double z, double w) {
        this(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vec4l(long x, long y, long z, long w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        AllocationTracker.instancesVec4l++;
    }

    public long getX() {
        return x;
    }
    /** Operator function for Kotlin */
    public long component1() {
        return getX();
    }

    public long getY() {
        return y;
    }
    /** Operator function for Kotlin */
    public long component2() {
        return getY();
    }

    public long getZ() {
        return z;
    }
    /** Operator function for Kotlin */
    public long component3() {
        return getZ();
    }

    public long getW() {
        return w;
    }
    /** Operator function for Kotlin */
    public long component4() {
        return getW();
    }

    @NotNull
    public Vec4l add(@NotNull Vec4l v) {
        return add(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4l plus(@NotNull Vec4l v) {
        return add(v);
    }

    @NotNull
    public Vec4l add(double x, double y, double z, double w) {
        return add(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    @NotNull
    public Vec4l add(long x, long y, long z, long w) {
        return new Vec4l(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    @NotNull
    public Vec4l sub(@NotNull Vec4l v) {
        return sub(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4l minus(@NotNull Vec4l v) {
        return sub(v);
    }

    @NotNull
    public Vec4l sub(double x, double y, double z, double w) {
        return sub(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    @NotNull
    public Vec4l sub(long x, long y, long z, long w) {
        return new Vec4l(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    @NotNull
    public Vec4l mul(double a) {
        return mul(GenericMath.floorl(a));
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4l times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec4l mul(long a) {
        return mul(a, a, a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec4l times(long a) {
        return mul(a);
    }

    @NotNull
    public Vec4l mul(@NotNull Vec4l v) {
        return mul(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4l times(@NotNull Vec4l v) {
        return mul(v);
    }

    @NotNull
    public Vec4l mul(double x, double y, double z, double w) {
        return mul(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    @NotNull
    public Vec4l mul(long x, long y, long z, long w) {
        return new Vec4l(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    @NotNull
    public Vec4l div(double a) {
        return div(GenericMath.floorl(a));
    }

    @NotNull
    @Override
    public Vec4l div(long a) {
        return div(a, a, a, a);
    }

    @NotNull
    public Vec4l div(@NotNull Vec4l v) {
        return div(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4l div(double x, double y, double z, double w) {
        return div(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    @NotNull
    public Vec4l div(long x, long y, long z, long w) {
        return new Vec4l(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    public long dot(@NotNull Vec4l v) {
        return dot(v.x, v.y, v.z, v.w);
    }

    public long dot(double x, double y, double z, double w) {
        return dot(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public long dot(long x, long y, long z, long w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    @NotNull
    public Vec4l project(@NotNull Vec4l v) {
        return project(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4l project(double x, double y, double z, double w) {
        return project(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    @NotNull
    public Vec4l project(long x, long y, long z, long w) {
        final long lengthSquared = x * x + y * y + z * z + w * w;
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final double a = (double) dot(x, y, z, w) / lengthSquared;
        return new Vec4l(a * x, a * y, a * z, a * w);
    }

    @NotNull
    public Vec4l pow(double pow) {
        return pow(GenericMath.floorl(pow));
    }

    @NotNull
    @Override
    public Vec4l pow(long power) {
        return new Vec4l(Math.pow(x, power), Math.pow(y, power), Math.pow(z, power), Math.pow(w, power));
    }

    @NotNull
    @Override
    public Vec4l abs() {
        return new Vec4l(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
    }

    @NotNull
    @Override
    public Vec4l negate() {
        return new Vec4l(-x, -y, -z, -w);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec4l unaryMinus() {
        return negate();
    }

    @NotNull
    public Vec4l min(@NotNull Vec4l v) {
        return min(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4l min(double x, double y, double z, double w) {
        return min(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    @NotNull
    public Vec4l min(long x, long y, long z, long w) {
        return new Vec4l(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
    }

    @NotNull
    public Vec4l max(@NotNull Vec4l v) {
        return max(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4l max(double x, double y, double z, double w) {
        return max(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    @NotNull
    public Vec4l max(long x, long y, long z, long w) {
        return new Vec4l(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
    }

    public long distanceSquared(@NotNull Vec4l v) {
        return distanceSquared(v.x, v.y, v.z, v.w);
    }

    public long distanceSquared(double x, double y, double z, double w) {
        return distanceSquared(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public long distanceSquared(long x, long y, long z, long w) {
        final long dx = this.x - x;
        final long dy = this.y - y;
        final long dz = this.z - z;
        final long dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public double distance(@NotNull Vec4l v) {
        return distance(v.x, v.y, v.z, v.w);
    }

    public double distance(double x, double y, double z, double w) {
        return distance(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public double distance(long x, long y, long z, long w) {
        return (double) Math.sqrt(distanceSquared(x, y, z, w));
    }

    @Override
    public long lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    @Override
    public double length() {
        return (double) Math.sqrt(lengthSquared());
    }

    /**
     * Return the axis with the minimal value.
     *
     * @return {@link int} axis with minimal value
     */
    @Override
    public int getMinAxis() {
        long value = x;
        int axis = 0;
        if (y < value) {
            value = y;
            axis = 1;
        }
        if (z < value) {
            value = z;
            axis = 2;
        }
        if (w < value) {
            axis = 3;
        }
        return axis;
    }

    /**
     * Return the axis with the maximum value.
     *
     * @return {@link int} axis with maximum value
     */
    @Override
    public int getMaxAxis() {
        long value = x;
        int axis = 0;
        if (y > value) {
            value = y;
            axis = 1;
        }
        if (z > value) {
            value = z;
            axis = 2;
        }
        if (w > value) {
            axis = 3;
        }
        return axis;
    }

    @NotNull
    public Vec2l toVec2() {
        return new Vec2l(this);
    }

    @NotNull
    public Vec3l toVec3() {
        return new Vec3l(this);
    }

    @NotNull
    public VecNl toVecN() {
        return new VecNl(this);
    }

    @NotNull
    @Override
    public long[] toArray() {
        return new long[]{x, y, z, w};
    }

    @NotNull
    @Override
    public Vec4d toDouble() {
        return new Vec4d(x, y, z, w);
    }

    @Override
    public double getXd() {
        return (double) getX();
    }

    @Override
    public double getYd() {
        return (double) getY();
    }

    @Override
    public double getZd() {
        return (double) getZ();
    }

    @Override
    public double getWd() {
        return (double) getW();
    }

    @NotNull
    @Override
    public Vec4f toFloat() {
        return new Vec4f(x, y, z, w);
    }

    @Override
    public float getXf() {
        return (float) getX();
    }

    @Override
    public float getYf() {
        return (float) getY();
    }

    @Override
    public float getZf() {
        return (float) getZ();
    }

    @Override
    public float getWf() {
        return (float) getW();
    }

    @NotNull
    @Override
    public Vec4l toLong() {
        return this;
    }

    @Override
    public long getXl() {
        return (long) getX();
    }

    @Override
    public long getYl() {
        return (long) getY();
    }

    @Override
    public long getZl() {
        return (long) getZ();
    }

    @Override
    public long getWl() {
        return (long) getW();
    }

    @NotNull
    @Override
    public Vec4i toInt() {
        return new Vec4i(x, y, z, w);
    }

    @Override
    public int getXi() {
        return (int) getX();
    }

    @Override
    public int getYi() {
        return (int) getY();
    }

    @Override
    public int getZi() {
        return (int) getZ();
    }

    @Override
    public int getWi() {
        return (int) getW();
    }

    @NotNull
    @Override
    public Number getXn() {
        return getX();
    }

    @NotNull
    @Override
    public Number getYn() {
        return getY();
    }

    @NotNull
    @Override
    public Number getZn() {
        return getZ();
    }

    @NotNull
    @Override
    public Number getWn() {
        return getW();
    }

    @Override
    public int compareTo(@NotNull Vec4l v) {
        return (int) (lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec4l)) {
            return false;
        }
        final Vec4l Vec4 = (Vec4l) o;
        if (Vec4.x != x) {
            return false;
        }
        if (Vec4.y != y) {
            return false;
        }
        if (Vec4.z != z) {
            return false;
        }
        if (Vec4.w != w) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (!hashed) {
            int result = (x != +0.0f ? HashFunctions.hash(x) : 0);
            result = 31 * result + (y != +0.0f ? HashFunctions.hash(y) : 0);
            result = 31 * result + (z != +0.0f ? HashFunctions.hash(z) : 0);
            hashCode = 31 * result + (w != +0.0f ? HashFunctions.hash(w) : 0);
            hashed = true;
        }
        return hashCode;
    }

    @NotNull
    @Override
    public Vec4l clone() {
        return new Vec4l(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

    @NotNull
    public static Vec4l from(long n) {
         return n == 0 ? ZERO : new Vec4l(n, n, n, n);
    }

    @NotNull
    public static Vec4l from(long x, long y, long z, long w) {
         return x == 0 && y == 0 && z == 0 && w == 0 ? ZERO : new Vec4l(x, y, z, w);
    }

    public static Vec4l createPooled(long x, long y, long z, long w) {
        return pool.getPooled(x, y, z, w);
    }
}
