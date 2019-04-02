package com.flowpowered.math.vector;

import java.io.Serializable;
import java.lang.Override;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import org.jetbrains.annotations.NotNull;

public class Vec3i implements Vectori, Vec3, Comparable<Vec3i>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec3i> pool = new FlowPool<Vec3i>(3, FlowPool.BITS_3D) {
        @NotNull
        protected Vec3i hit(Vec3i value) {
            AllocationTracker.cacheHitsVec3i++;
            return value;
        }

        @NotNull
        protected Vec3i create(int x, int y, int z, int w) {
            return new Vec3i(x, y, z);
        }
    };

    public static final Vec3i ZERO = new Vec3i(0, 0, 0);
    public static final Vec3i UNIT_X = new Vec3i(1, 0, 0);
    public static final Vec3i UNIT_Y = new Vec3i(0, 1, 0);
    public static final Vec3i UNIT_Z = new Vec3i(0, 0, 1);
    public static final Vec3i ONE = new Vec3i(1, 1, 1);
    public static final Vec3i RIGHT = UNIT_X;
    public static final Vec3i UP = UNIT_Y;
    public static final Vec3i FORWARD = UNIT_Z;
    private final int x;
    private final int y;
    private final int z;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec3i() {
        this(0, 0, 0);
    }

    public Vec3i(@NotNull Vec2i v) {
        this(v, 0);
    }

    public Vec3i(@NotNull Vec2i v, double z) {
        this(v, GenericMath.floor(z));
    }

    public Vec3i(@NotNull Vec2i v, int z) {
        this(v.getX(), v.getY(), z);
    }

    public Vec3i(@NotNull Vec3i v) {
        this(v.x, v.y, v.z);
    }

    public Vec3i(@NotNull Vec4i v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public Vec3i(@NotNull VecNi v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0);
    }

    public Vec3i(double x, double y, double z) {
        this(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        AllocationTracker.instancesVec3i++;
    }

    public int getX() {
        return x;
    }
    /** Operator function for Kotlin */
    public int component1() {
        return getX();
    }

    public int getY() {
        return y;
    }
    /** Operator function for Kotlin */
    public int component2() {
        return getY();
    }

    public int getZ() {
        return z;
    }
    /** Operator function for Kotlin */
    public int component3() {
        return getZ();
    }

    @NotNull
    public Vec3i add(@NotNull Vec3i v) {
        return add(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3i plus(@NotNull Vec3i v) {
        return add(v);
    }

    @NotNull
    public Vec3i add(double x, double y, double z) {
        return add(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    @NotNull
    public Vec3i add(int x, int y, int z) {
        return new Vec3i(this.x + x, this.y + y, this.z + z);
    }

    @NotNull
    public Vec3i sub(@NotNull Vec3i v) {
        return sub(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3i minus(@NotNull Vec3i v) {
        return sub(v);
    }

    @NotNull
    public Vec3i sub(double x, double y, double z) {
        return sub(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    @NotNull
    public Vec3i sub(int x, int y, int z) {
        return new Vec3i(this.x - x, this.y - y, this.z - z);
    }

    @NotNull
    public Vec3i mul(double a) {
        return mul(GenericMath.floor(a));
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3i times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec3i mul(int a) {
        return mul(a, a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec3i times(int a) {
        return mul(a);
    }

    @NotNull
    public Vec3i mul(@NotNull Vec3i v) {
        return mul(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3i times(@NotNull Vec3i v) {
        return mul(v);
    }

    @NotNull
    public Vec3i mul(double x, double y, double z) {
        return mul(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    @NotNull
    public Vec3i mul(int x, int y, int z) {
        return new Vec3i(this.x * x, this.y * y, this.z * z);
    }

    @NotNull
    public Vec3i div(double a) {
        return div(GenericMath.floor(a));
    }

    @NotNull
    @Override
    public Vec3i div(int a) {
        return div(a, a, a);
    }

    @NotNull
    public Vec3i div(@NotNull Vec3i v) {
        return div(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3i div(double x, double y, double z) {
        return div(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    @NotNull
    public Vec3i div(int x, int y, int z) {
        return new Vec3i(this.x / x, this.y / y, this.z / z);
    }

    public int dot(@NotNull Vec3i v) {
        return dot(v.x, v.y, v.z);
    }

    public int dot(double x, double y, double z) {
        return dot(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    public int dot(int x, int y, int z) {
        return this.x * x + this.y * y + this.z * z;
    }

    @NotNull
    public Vec3i project(@NotNull Vec3i v) {
        return project(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3i project(double x, double y, double z) {
        return project(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    @NotNull
    public Vec3i project(int x, int y, int z) {
        final int lengthSquared = x * x + y * y + z * z;
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final float a = (float) dot(x, y, z) / lengthSquared;
        return new Vec3i(a * x, a * y, a * z);
    }

    @NotNull
    public Vec3i cross(@NotNull Vec3i v) {
        return cross(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3i cross(double x, double y, double z) {
        return cross(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    @NotNull
    public Vec3i cross(int x, int y, int z) {
        return new Vec3i(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
    }

    @NotNull
    public Vec3i pow(double pow) {
        return pow(GenericMath.floor(pow));
    }

    @NotNull
    @Override
    public Vec3i pow(int power) {
        return new Vec3i(Math.pow(x, power), Math.pow(y, power), Math.pow(z, power));
    }

    @NotNull
    @Override
    public Vec3i abs() {
        return new Vec3i(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    @NotNull
    @Override
    public Vec3i negate() {
        return new Vec3i(-x, -y, -z);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec3i unaryMinus() {
        return negate();
    }

    @NotNull
    public Vec3i min(@NotNull Vec3i v) {
        return min(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3i min(double x, double y, double z) {
        return min(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    @NotNull
    public Vec3i min(int x, int y, int z) {
        return new Vec3i(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
    }

    @NotNull
    public Vec3i max(@NotNull Vec3i v) {
        return max(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3i max(double x, double y, double z) {
        return max(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    @NotNull
    public Vec3i max(int x, int y, int z) {
        return new Vec3i(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
    }

    public int distanceSquared(@NotNull Vec3i v) {
        return distanceSquared(v.x, v.y, v.z);
    }

    public int distanceSquared(double x, double y, double z) {
        return distanceSquared(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    public int distanceSquared(int x, int y, int z) {
        final int dx = this.x - x;
        final int dy = this.y - y;
        final int dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public float distance(@NotNull Vec3i v) {
        return distance(v.x, v.y, v.z);
    }

    public float distance(double x, double y, double z) {
        return distance(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    public float distance(int x, int y, int z) {
        return (float) Math.sqrt(distanceSquared(x, y, z));
    }

    @Override
    public int lengthSquared() {
        return x * x + y * y + z * z;
    }

    @Override
    public float length() {
        return (float) Math.sqrt(lengthSquared());
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
    public Vec2i toVec2() {
        return new Vec2i(this);
    }

    @NotNull
    public Vec2i toVec2(boolean useZ) {
        return new Vec2i(x, useZ ? z : y);
    }

    @NotNull
    public Vec4i toVec4() {
        return toVec4(0);
    }

    @NotNull
    public Vec4i toVec4(double w) {
        return toVec4(GenericMath.floor(w));
    }

    @NotNull
    public Vec4i toVec4(int w) {
        return new Vec4i(this, w);
    }

    @NotNull
    public VecNi toVecN() {
        return new VecNi(this);
    }

    @NotNull
    @Override
    public int[] toArray() {
        return new int[]{x, y, z};
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
        return new Vec3l(x, y, z);
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
        return this;
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
    public int compareTo(@NotNull Vec3i v) {
        return (int) (lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec3i)) {
            return false;
        }
        final Vec3i Vec3 = (Vec3i) o;
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
    public Vec3i clone() {
        return new Vec3i(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @NotNull
    public static Vec3i from(int n) {
         return n == 0 ? ZERO : new Vec3i(n, n, n);
    }

    @NotNull
    public static Vec3i from(int x, int y, int z) {
         return x == 0 && y == 0 && z == 0 ? ZERO : new Vec3i(x, y, z);
    }

    public static Vec3i createPooled(int x, int y, int z) {
        return pool.getPooled(x, y, z, 0);
    }
}
