package com.flowpowered.math.vector;

import java.io.Serializable;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import org.jetbrains.annotations.NotNull;

public class Vec2l implements Vectorl, Vec2, Comparable<Vec2l>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec2l> pool = new FlowPool<Vec2l>(2, FlowPool.BITS_2D) {
        @NotNull
        protected Vec2l hit(Vec2l value) {
            AllocationTracker.cacheHitsVec2l++;
            return value;
        }

        @NotNull
        protected Vec2l create(long x, long y, long z, long w) {
            return new Vec2l(x, y);
        }
    };

    public static final Vec2l ZERO = new Vec2l(0, 0);
    public static final Vec2l UNIT_X = new Vec2l(1, 0);
    public static final Vec2l UNIT_Y = new Vec2l(0, 1);
    public static final Vec2l ONE = new Vec2l(1, 1);
    private final long x;
    private final long y;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec2l() {
        this(0, 0);
    }

    public Vec2l(@NotNull Vec2l v) {
        this(v.x, v.y);
    }

    public Vec2l(@NotNull Vec3l v) {
        this(v.getX(), v.getY());
    }

    public Vec2l(@NotNull Vec4l v) {
        this(v.getX(), v.getY());
    }

    public Vec2l(@NotNull VecNl v) {
        this(v.get(0), v.get(1));
    }

    public Vec2l(double x, double y) {
        this(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public Vec2l(long x, long y) {
        this.x = x;
        this.y = y;
        AllocationTracker.instancesVec2l++;
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

    @NotNull
    public Vec2l add(@NotNull Vec2l v) {
        return add(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2l plus(@NotNull Vec2l v) {
        return add(v);
    }

    @NotNull
    public Vec2l add(double x, double y) {
        return add(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    @NotNull
    public Vec2l add(long x, long y) {
        return new Vec2l(this.x + x, this.y + y);
    }

    @NotNull
    public Vec2l sub(@NotNull Vec2l v) {
        return sub(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2l minus(@NotNull Vec2l v) {
        return sub(v);
    }

    @NotNull
    public Vec2l sub(double x, double y) {
        return sub(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    @NotNull
    public Vec2l sub(long x, long y) {
        return new Vec2l(this.x - x, this.y - y);
    }

    @NotNull
    public Vec2l mul(double a) {
        return mul(GenericMath.floorl(a));
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2l times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec2l mul(long a) {
        return mul(a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec2l times(long a) {
        return mul(a);
    }

    @NotNull
    public Vec2l mul(@NotNull Vec2l v) {
        return mul(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2l times(@NotNull Vec2l v) {
        return mul(v);
    }

    @NotNull
    public Vec2l mul(double x, double y) {
        return mul(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    @NotNull
    public Vec2l mul(long x, long y) {
        return new Vec2l(this.x * x, this.y * y);
    }

    @NotNull
    public Vec2l div(double a) {
        return div(GenericMath.floorl(a));
    }

    @NotNull
    @Override
    public Vec2l div(long a) {
        return div(a, a);
    }

    @NotNull
    public Vec2l div(@NotNull Vec2l v) {
        return div(v.x, v.y);
    }

    @NotNull
    public Vec2l div(double x, double y) {
        return div(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    @NotNull
    public Vec2l div(long x, long y) {
        return new Vec2l(this.x / x, this.y / y);
    }

    public long dot(@NotNull Vec2l v) {
        return dot(v.x, v.y);
    }

    public long dot(double x, double y) {
        return dot(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public long dot(long x, long y) {
        return this.x * x + this.y * y;
    }

    @NotNull
    public Vec2l project(@NotNull Vec2l v) {
        return project(v.x, v.y);
    }

    @NotNull
    public Vec2l project(double x, double y) {
        return project(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    @NotNull
    public Vec2l project(long x, long y) {
        final long lengthSquared = x * x + y * y;
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final double a = (double) dot(x, y) / lengthSquared;
        return new Vec2l(a * x, a * y);
    }

    @NotNull
    public Vec2l pow(double pow) {
        return pow(GenericMath.floorl(pow));
    }

    @NotNull
    @Override
    public Vec2l pow(long power) {
        return new Vec2l(Math.pow(x, power), Math.pow(y, power));
    }

    @NotNull
    @Override
    public Vec2l abs() {
        return new Vec2l(Math.abs(x), Math.abs(y));
    }

    @NotNull
    @Override
    public Vec2l negate() {
        return new Vec2l(-x, -y);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec2l unaryMinus() {
        return negate();
    }

    @NotNull
    public Vec2l min(@NotNull Vec2l v) {
        return min(v.x, v.y);
    }

    @NotNull
    public Vec2l min(double x, double y) {
        return min(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    @NotNull
    public Vec2l min(long x, long y) {
        return new Vec2l(Math.min(this.x, x), Math.min(this.y, y));
    }

    @NotNull
    public Vec2l max(@NotNull Vec2l v) {
        return max(v.x, v.y);
    }

    @NotNull
    public Vec2l max(double x, double y) {
        return max(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    @NotNull
    public Vec2l max(long x, long y) {
        return new Vec2l(Math.max(this.x, x), Math.max(this.y, y));
    }

    public long distanceSquared(@NotNull Vec2l v) {
        return distanceSquared(v.x, v.y);
    }

    public long distanceSquared(double x, double y) {
        return distanceSquared(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public long distanceSquared(long x, long y) {
        final long dx = this.x - x;
        final long dy = this.y - y;
        return dx * dx + dy * dy;
    }

    public double distance(@NotNull Vec2l v) {
        return distance(v.x, v.y);
    }

    public double distance(double x, double y) {
        return distance(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public double distance(long x, long y) {
        return (double) Math.sqrt(distanceSquared(x, y));
    }

    @Override
    public long lengthSquared() {
        return x * x + y * y;
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
        return x < y ? 0 : 1;
    }

    /**
     * Return the axis with the maximum value.
     *
     * @return {@link int} axis with maximum value
     */
    @Override
    public int getMaxAxis() {
        return x > y ? 0 : 1;
    }

    @NotNull
    public Vec3l toVec3() {
        return toVec3(0);
    }

    @NotNull
    public Vec3l toVec3(double z) {
        return toVec3(GenericMath.floorl(z));
    }

    @NotNull
    public Vec3l toVec3(long z) {
        return new Vec3l(this, z);
    }

    @NotNull
    public Vec4l toVec4() {
        return toVec4(0, 0);
    }

    @NotNull
    public Vec4l toVec4(double z, double w) {
        return toVec4(GenericMath.floorl(z), GenericMath.floorl(w));
    }

    @NotNull
    public Vec4l toVec4(long z, long w) {
        return new Vec4l(this, z, w);
    }

    @NotNull
    public VecNl toVecN() {
        return new VecNl(this);
    }

    @NotNull
    @Override
    public long[] toArray() {
        return new long[]{x, y};
    }

    @NotNull
    @Override
    public Vec2d toDouble() {
        return new Vec2d(x, y);
    }

    @Override
    public double getXd() {
        return (double) getX();
    }

    @Override
    public double getYd() {
        return (double) getY();
    }

    @NotNull
    @Override
    public Vec2f toFloat() {
        return new Vec2f(x, y);
    }

    @Override
    public float getXf() {
        return (float) getX();
    }

    @Override
    public float getYf() {
        return (float) getY();
    }

    @NotNull
    @Override
    public Vec2l toLong() {
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

    @NotNull
    @Override
    public Vec2i toInt() {
        return new Vec2i(x, y);
    }

    @Override
    public int getXi() {
        return (int) getX();
    }

    @Override
    public int getYi() {
        return (int) getY();
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

    @Override
    public int compareTo(@NotNull Vec2l v) {
        return (int) (lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec2l)) {
            return false;
        }
        final Vec2l Vec2 = (Vec2l) o;
        if (Vec2.x != x) {
            return false;
        }
        if (Vec2.y != y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (!hashed) {
            final int result = (x != +0.0f ? HashFunctions.hash(x) : 0);
            hashCode = 31 * result + (y != +0.0f ? HashFunctions.hash(y) : 0);
            hashed = true;
        }
        return hashCode;
    }

    @NotNull
    @Override
    public Vec2l clone() {
        return new Vec2l(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @NotNull
    public static Vec2l from(long n) {
         return n == 0 ? ZERO : new Vec2l(n, n);
    }

    @NotNull
    public static Vec2l from(long x, long y) {
         return x == 0 && y == 0 ? ZERO : new Vec2l(x, y);
    }

    public static Vec2l createPooled(long x, long y) {
        return pool.getPooled(x, y, 0, 0);
    }
}
