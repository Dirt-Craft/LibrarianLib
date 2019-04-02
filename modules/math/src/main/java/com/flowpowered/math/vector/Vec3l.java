package com.flowpowered.math.vector;

import java.io.Serializable;
import java.lang.Override;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import org.jetbrains.annotations.NotNull;

public class Vec3l implements Vectorl, Vec3, Comparable<Vec3l>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec3l> pool = new FlowPool(3, FlowPool.BITS_3D) {
        protected Vec3l hit(Vec3l value) {
            AllocationTracker.cacheHitsVec3l++;
            return value;
        }

        @NotNull
        protected Vec3l create(long x, long y, long z, long w) {
            return new Vec3l(x, y, z);
        }
    };

    public static final Vec3l ZERO = new Vec3l(0, 0, 0);
    public static final Vec3l UNIT_X = new Vec3l(1, 0, 0);
    public static final Vec3l UNIT_Y = new Vec3l(0, 1, 0);
    public static final Vec3l UNIT_Z = new Vec3l(0, 0, 1);
    public static final Vec3l ONE = new Vec3l(1, 1, 1);
    public static final Vec3l RIGHT = UNIT_X;
    public static final Vec3l UP = UNIT_Y;
    public static final Vec3l FORWARD = UNIT_Z;
    private final long x;
    private final long y;
    private final long z;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec3l() {
        this(0, 0, 0);
    }

    public Vec3l(@NotNull Vec2l v) {
        this(v, 0);
    }

    public Vec3l(@NotNull Vec2l v, double z) {
        this(v, GenericMath.floorl(z));
    }

    public Vec3l(@NotNull Vec2l v, long z) {
        this(v.getX(), v.getY(), z);
    }

    public Vec3l(@NotNull Vec3l v) {
        this(v.x, v.y, v.z);
    }

    public Vec3l(@NotNull Vec4l v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public Vec3l(@NotNull VecNl v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0);
    }

    public Vec3l(double x, double y, double z) {
        this(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public Vec3l(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
        AllocationTracker.instancesVec3l++;
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

    @NotNull
    public Vec3l add(@NotNull Vec3l v) {
        return add(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3l plus(@NotNull Vec3l v) {
        return add(v);
    }

    @NotNull
    public Vec3l add(double x, double y, double z) {
        return add(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    @NotNull
    public Vec3l add(long x, long y, long z) {
        return new Vec3l(this.x + x, this.y + y, this.z + z);
    }

    @NotNull
    public Vec3l sub(@NotNull Vec3l v) {
        return sub(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3l minus(@NotNull Vec3l v) {
        return sub(v);
    }

    @NotNull
    public Vec3l sub(double x, double y, double z) {
        return sub(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    @NotNull
    public Vec3l sub(long x, long y, long z) {
        return new Vec3l(this.x - x, this.y - y, this.z - z);
    }

    @NotNull
    public Vec3l mul(double a) {
        return mul(GenericMath.floorl(a));
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3l times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec3l mul(long a) {
        return mul(a, a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec3l times(long a) {
        return mul(a);
    }

    @NotNull
    public Vec3l mul(@NotNull Vec3l v) {
        return mul(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3l times(@NotNull Vec3l v) {
        return mul(v);
    }

    @NotNull
    public Vec3l mul(double x, double y, double z) {
        return mul(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    @NotNull
    public Vec3l mul(long x, long y, long z) {
        return new Vec3l(this.x * x, this.y * y, this.z * z);
    }

    @NotNull
    public Vec3l div(double a) {
        return div(GenericMath.floorl(a));
    }

    @NotNull
    @Override
    public Vec3l div(long a) {
        return div(a, a, a);
    }

    @NotNull
    public Vec3l div(@NotNull Vec3l v) {
        return div(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3l div(double x, double y, double z) {
        return div(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    @NotNull
    public Vec3l div(long x, long y, long z) {
        return new Vec3l(this.x / x, this.y / y, this.z / z);
    }

    public long dot(@NotNull Vec3l v) {
        return dot(v.x, v.y, v.z);
    }

    public long dot(double x, double y, double z) {
        return dot(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public long dot(long x, long y, long z) {
        return this.x * x + this.y * y + this.z * z;
    }

    @NotNull
    public Vec3l project(@NotNull Vec3l v) {
        return project(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3l project(double x, double y, double z) {
        return project(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    @NotNull
    public Vec3l project(long x, long y, long z) {
        final long lengthSquared = x * x + y * y + z * z;
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final double a = (double) dot(x, y, z) / lengthSquared;
        return new Vec3l(a * x, a * y, a * z);
    }

    @NotNull
    public Vec3l cross(@NotNull Vec3l v) {
        return cross(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3l cross(double x, double y, double z) {
        return cross(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    @NotNull
    public Vec3l cross(long x, long y, long z) {
        return new Vec3l(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
    }

    @NotNull
    public Vec3l pow(double pow) {
        return pow(GenericMath.floorl(pow));
    }

    @NotNull
    @Override
    public Vec3l pow(long power) {
        return new Vec3l(Math.pow(x, power), Math.pow(y, power), Math.pow(z, power));
    }

    @NotNull
    @Override
    public Vec3l abs() {
        return new Vec3l(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    @NotNull
    @Override
    public Vec3l negate() {
        return new Vec3l(-x, -y, -z);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec3l unaryMinus() {
        return negate();
    }

    @NotNull
    public Vec3l min(@NotNull Vec3l v) {
        return min(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3l min(double x, double y, double z) {
        return min(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    @NotNull
    public Vec3l min(long x, long y, long z) {
        return new Vec3l(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
    }

    @NotNull
    public Vec3l max(@NotNull Vec3l v) {
        return max(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3l max(double x, double y, double z) {
        return max(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    @NotNull
    public Vec3l max(long x, long y, long z) {
        return new Vec3l(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
    }

    public long distanceSquared(@NotNull Vec3l v) {
        return distanceSquared(v.x, v.y, v.z);
    }

    public long distanceSquared(double x, double y, double z) {
        return distanceSquared(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public long distanceSquared(long x, long y, long z) {
        final long dx = this.x - x;
        final long dy = this.y - y;
        final long dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(@NotNull Vec3l v) {
        return distance(v.x, v.y, v.z);
    }

    public double distance(double x, double y, double z) {
        return distance(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public double distance(long x, long y, long z) {
        return (double) Math.sqrt(distanceSquared(x, y, z));
    }

    @Override
    public long lengthSquared() {
        return x * x + y * y + z * z;
    }

    @Override
    public double length() {
        return (double) Math.sqrt(lengthSquared());
    }

    /**
     * Returns the axis with the minimal value.
     *
     * @return {@link int} axis with minimal value
     */
    @Override
    public int getMinAxis() {
        return x < y ? (x < z ? 0 : 2) : (y < z ? 1 : 2);
    }

    /**
     * Returns the axis with the maximum value.
     *
     * @return {@link int} axis with maximum value
     */
    @Override
    public int getMaxAxis() {
        return x < y ? (y < z ? 2 : 1) : (x < z ? 2 : 0);
    }

    @NotNull
    public Vec2l toVec2() {
        return new Vec2l(this);
    }

    @NotNull
    public Vec2l toVec2(boolean useZ) {
        return new Vec2l(x, useZ ? z : y);
    }

    @NotNull
    public Vec4l toVec4() {
        return toVec4(0);
    }

    @NotNull
    public Vec4l toVec4(double w) {
        return toVec4(GenericMath.floorl(w));
    }

    @NotNull
    public Vec4l toVec4(long w) {
        return new Vec4l(this, w);
    }

    @NotNull
    public VecNl toVecN() {
        return new VecNl(this);
    }

    @NotNull
    @Override
    public long[] toArray() {
        return new long[]{x, y, z};
    }

    @NotNull
    @Override
    public Vec3d toDouble() {
        return new Vec3d(x, y, z);
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

    @NotNull
    @Override
    public net.minecraft.util.math.Vec3d toMC() {
        return new net.minecraft.util.math.Vec3d(getXd(), getYd(), getZd());
    }

    @NotNull
    @Override
    public Vec3f toFloat() {
        return new Vec3f(x, y, z);
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

    @NotNull
    @Override
    public Vec3l toLong() {
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

    @NotNull
    @Override
    public Vec3i toInt() {
        return new Vec3i(x, y, z);
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

    @Override
    public int compareTo(@NotNull Vec3l v) {
        return (int) (lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec3l)) {
            return false;
        }
        final Vec3l Vec3 = (Vec3l) o;
        if (Vec3.x != x) {
            return false;
        }
        if (Vec3.y != y) {
            return false;
        }
        if (Vec3.z != z) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (!hashed) {
            hashCode = ((HashFunctions.hash(x) * 211 + HashFunctions.hash(y)) * 97 + HashFunctions.hash(z));
            hashed = true;
        }
        return hashCode;
    }

    @NotNull
    @Override
    public Vec3l clone() {
        return new Vec3l(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @NotNull
    public static Vec3l from(long n) {
         return n == 0 ? ZERO : new Vec3l(n, n, n);
    }

    @NotNull
    public static Vec3l from(long x, long y, long z) {
         return x == 0 && y == 0 && z == 0 ? ZERO : new Vec3l(x, y, z);
    }

    public static Vec3l createPooled(long x, long y, long z) {
        return pool.getPooled(x, y, z, 0);
    }
}
