package com.flowpowered.math.vector;

import java.io.Serializable;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import org.jetbrains.annotations.NotNull;

public class Vec2i implements Vectori, Vec2, Comparable<Vec2i>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec2i> pool = new FlowPool<Vec2i>(2, FlowPool.BITS_2D) {
        @NotNull
        protected Vec2i hit(Vec2i value) {
            AllocationTracker.cacheHitsVec2i++;
            return value;
        }

        @NotNull
        protected Vec2i create(int x, int y, int z, int w) {
            return new Vec2i(x, y);
        }
    };

    public static final Vec2i ZERO = new Vec2i(0, 0);
    public static final Vec2i UNIT_X = new Vec2i(1, 0);
    public static final Vec2i UNIT_Y = new Vec2i(0, 1);
    public static final Vec2i ONE = new Vec2i(1, 1);
    private final int x;
    private final int y;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec2i() {
        this(0, 0);
    }

    public Vec2i(@NotNull Vec2i v) {
        this(v.x, v.y);
    }

    public Vec2i(@NotNull Vec3i v) {
        this(v.getX(), v.getY());
    }

    public Vec2i(@NotNull Vec4i v) {
        this(v.getX(), v.getY());
    }

    public Vec2i(@NotNull VecNi v) {
        this(v.get(0), v.get(1));
    }

    public Vec2i(double x, double y) {
        this(GenericMath.floor(x), GenericMath.floor(y));
    }

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
        AllocationTracker.instancesVec2i++;
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

    @NotNull
    public Vec2i add(@NotNull Vec2i v) {
        return add(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2i plus(@NotNull Vec2i v) {
        return add(v);
    }

    @NotNull
    public Vec2i add(double x, double y) {
        return add(GenericMath.floor(x), GenericMath.floor(y));
    }

    @NotNull
    public Vec2i add(int x, int y) {
        return new Vec2i(this.x + x, this.y + y);
    }

    @NotNull
    public Vec2i sub(@NotNull Vec2i v) {
        return sub(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2i minus(@NotNull Vec2i v) {
        return sub(v);
    }

    @NotNull
    public Vec2i sub(double x, double y) {
        return sub(GenericMath.floor(x), GenericMath.floor(y));
    }

    @NotNull
    public Vec2i sub(int x, int y) {
        return new Vec2i(this.x - x, this.y - y);
    }

    @NotNull
    public Vec2i mul(double a) {
        return mul(GenericMath.floor(a));
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2i times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec2i mul(int a) {
        return mul(a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec2i times(int a) {
        return mul(a);
    }

    @NotNull
    public Vec2i mul(@NotNull Vec2i v) {
        return mul(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2i times(@NotNull Vec2i v) {
        return mul(v);
    }

    @NotNull
    public Vec2i mul(double x, double y) {
        return mul(GenericMath.floor(x), GenericMath.floor(y));
    }

    @NotNull
    public Vec2i mul(int x, int y) {
        return new Vec2i(this.x * x, this.y * y);
    }

    @NotNull
    public Vec2i div(double a) {
        return div(GenericMath.floor(a));
    }

    @NotNull
    @Override
    public Vec2i div(int a) {
        return div(a, a);
    }

    @NotNull
    public Vec2i div(@NotNull Vec2i v) {
        return div(v.x, v.y);
    }

    @NotNull
    public Vec2i div(double x, double y) {
        return div(GenericMath.floor(x), GenericMath.floor(y));
    }

    @NotNull
    public Vec2i div(int x, int y) {
        return new Vec2i(this.x / x, this.y / y);
    }

    public int dot(@NotNull Vec2i v) {
        return dot(v.x, v.y);
    }

    public int dot(double x, double y) {
        return dot(GenericMath.floor(x), GenericMath.floor(y));
    }

    public int dot(int x, int y) {
        return this.x * x + this.y * y;
    }

    @NotNull
    public Vec2i project(@NotNull Vec2i v) {
        return project(v.x, v.y);
    }

    @NotNull
    public Vec2i project(double x, double y) {
        return project(GenericMath.floor(x), GenericMath.floor(y));
    }

    @NotNull
    public Vec2i project(int x, int y) {
        final int lengthSquared = x * x + y * y;
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final float a = (float) dot(x, y) / lengthSquared;
        return new Vec2i(a * x, a * y);
    }

    @NotNull
    public Vec2i pow(double pow) {
        return pow(GenericMath.floor(pow));
    }

    @NotNull
    @Override
    public Vec2i pow(int power) {
        return new Vec2i(Math.pow(x, power), Math.pow(y, power));
    }

    @NotNull
    @Override
    public Vec2i abs() {
        return new Vec2i(Math.abs(x), Math.abs(y));
    }

    @NotNull
    @Override
    public Vec2i negate() {
        return new Vec2i(-x, -y);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec2i unaryMinus() {
        return negate();
    }

    @NotNull
    public Vec2i min(@NotNull Vec2i v) {
        return min(v.x, v.y);
    }

    @NotNull
    public Vec2i min(double x, double y) {
        return min(GenericMath.floor(x), GenericMath.floor(y));
    }

    @NotNull
    public Vec2i min(int x, int y) {
        return new Vec2i(Math.min(this.x, x), Math.min(this.y, y));
    }

    @NotNull
    public Vec2i max(@NotNull Vec2i v) {
        return max(v.x, v.y);
    }

    @NotNull
    public Vec2i max(double x, double y) {
        return max(GenericMath.floor(x), GenericMath.floor(y));
    }

    @NotNull
    public Vec2i max(int x, int y) {
        return new Vec2i(Math.max(this.x, x), Math.max(this.y, y));
    }

    public int distanceSquared(@NotNull Vec2i v) {
        return distanceSquared(v.x, v.y);
    }

    public int distanceSquared(double x, double y) {
        return distanceSquared(GenericMath.floor(x), GenericMath.floor(y));
    }

    public int distanceSquared(int x, int y) {
        final int dx = this.x - x;
        final int dy = this.y - y;
        return dx * dx + dy * dy;
    }

    public float distance(@NotNull Vec2i v) {
        return distance(v.x, v.y);
    }

    public float distance(double x, double y) {
        return distance(GenericMath.floor(x), GenericMath.floor(y));
    }

    public float distance(int x, int y) {
        return (float) Math.sqrt(distanceSquared(x, y));
    }

    @Override
    public int lengthSquared() {
        return x * x + y * y;
    }

    @Override
    public float length() {
        return (float) Math.sqrt(lengthSquared());
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
    public Vec3i toVec3() {
        return toVec3(0);
    }

    @NotNull
    public Vec3i toVec3(double z) {
        return toVec3(GenericMath.floor(z));
    }

    @NotNull
    public Vec3i toVec3(int z) {
        return new Vec3i(this, z);
    }

    @NotNull
    public Vec4i toVec4() {
        return toVec4(0, 0);
    }

    @NotNull
    public Vec4i toVec4(double z, double w) {
        return toVec4(GenericMath.floor(z), GenericMath.floor(w));
    }

    @NotNull
    public Vec4i toVec4(int z, int w) {
        return new Vec4i(this, z, w);
    }

    @NotNull
    public VecNi toVecN() {
        return new VecNi(this);
    }

    @NotNull
    @Override
    public int[] toArray() {
        return new int[]{x, y};
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
        return new Vec2l(x, y);
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
    public int compareTo(@NotNull Vec2i v) {
        return (int) (lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec2i)) {
            return false;
        }
        final Vec2i Vec2 = (Vec2i) o;
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
    public Vec2i clone() {
        return new Vec2i(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @NotNull
    public static Vec2i from(int n) {
         return n == 0 ? ZERO : new Vec2i(n, n);
    }

    @NotNull
    public static Vec2i from(int x, int y) {
         return x == 0 && y == 0 ? ZERO : new Vec2i(x, y);
    }

    public static Vec2i createPooled(int x, int y) {
        return pool.getPooled(x, y, 0, 0);
    }
}
