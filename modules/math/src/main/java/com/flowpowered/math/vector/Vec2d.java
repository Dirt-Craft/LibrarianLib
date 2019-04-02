package com.flowpowered.math.vector;

import java.io.Serializable;
import java.lang.Override;
import java.util.Random;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import com.flowpowered.math.TrigMath;
import org.jetbrains.annotations.NotNull;

public class Vec2d implements Vectord, Vec2, Comparable<Vec2d>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec2d> pool = new FlowPool<Vec2d>(2, FlowPool.BITS_2D) {
        @NotNull
        protected Vec2d hit(Vec2d value) {
            AllocationTracker.cacheHitsVec2d++;
            return value;
        }

        @NotNull
        protected Vec2d create(double x, double y, double z, double w) {
            return new Vec2d(x, y);
        }
    };

    public static final Vec2d ZERO = new Vec2d(0, 0);
    public static final Vec2d UNIT_X = new Vec2d(1, 0);
    public static final Vec2d UNIT_Y = new Vec2d(0, 1);
    public static final Vec2d ONE = new Vec2d(1, 1);
    private final double x;
    private final double y;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec2d() {
        this(0, 0);
    }

    public Vec2d(@NotNull Vec2d v) {
        this(v.x, v.y);
    }

    public Vec2d(@NotNull Vec3d v) {
        this(v.getX(), v.getY());
    }

    public Vec2d(@NotNull Vec4d v) {
        this(v.getX(), v.getY());
    }

    public Vec2d(@NotNull VecNd v) {
        this(v.get(0), v.get(1));
    }

    public Vec2d(float x, float y) {
        this((double) x, (double) y);
    }

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
        AllocationTracker.instancesVec2d++;
    }

    public double getX() {
        return x;
    }
    /** Operator function for Kotlin */
    public double component1() {
        return getX();
    }

    public double getY() {
        return y;
    }
    /** Operator function for Kotlin */
    public double component2() {
        return getY();
    }

    public int getFloorX() {
        return GenericMath.floor(x);
    }

    public int getFloorY() {
        return GenericMath.floor(y);
    }

    @NotNull
    public Vec2d add(@NotNull Vec2d v) {
        return add(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2d plus(@NotNull Vec2d v) {
        return add(v);
    }

    @NotNull
    public Vec2d add(float x, float y) {
        return add((double) x, (double) y);
    }

    @NotNull
    public Vec2d add(double x, double y) {
        return new Vec2d(this.x + x, this.y + y);
    }

    @NotNull
    public Vec2d sub(@NotNull Vec2d v) {
        return sub(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2d minus(@NotNull Vec2d v) {
        return sub(v);
    }

    @NotNull
    public Vec2d sub(float x, float y) {
        return sub((double) x, (double) y);
    }

    @NotNull
    public Vec2d sub(double x, double y) {
        return new Vec2d(this.x - x, this.y - y);
    }

    @NotNull
    public Vec2d mul(float a) {
        return mul((double) a);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2d times(float a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec2d mul(double a) {
        return mul(a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec2d times(double a) {
        return mul(a);
    }

    @NotNull
    public Vec2d mul(@NotNull Vec2d v) {
        return mul(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2d times(@NotNull Vec2d v) {
        return mul(v);
    }

    @NotNull
    public Vec2d mul(float x, float y) {
        return mul((double) x, (double) y);
    }

    @NotNull
    public Vec2d mul(double x, double y) {
        return new Vec2d(this.x * x, this.y * y);
    }

    @NotNull
    public Vec2d div(float a) {
        return div((double) a);
    }

    @NotNull
    @Override
    public Vec2d div(double a) {
        return div(a, a);
    }

    @NotNull
    public Vec2d div(@NotNull Vec2d v) {
        return div(v.x, v.y);
    }

    @NotNull
    public Vec2d div(float x, float y) {
        return div((double) x, (double) y);
    }

    @NotNull
    public Vec2d div(double x, double y) {
        return new Vec2d(this.x / x, this.y / y);
    }

    public double dot(@NotNull Vec2d v) {
        return dot(v.x, v.y);
    }

    public double dot(float x, float y) {
        return dot((double) x, (double) y);
    }

    public double dot(double x, double y) {
        return this.x * x + this.y * y;
    }

    @NotNull
    public Vec2d project(@NotNull Vec2d v) {
        return project(v.x, v.y);
    }

    @NotNull
    public Vec2d project(float x, float y) {
        return project((double) x, (double) y);
    }

    @NotNull
    public Vec2d project(double x, double y) {
        final double lengthSquared = x * x + y * y;
        if (Math.abs(lengthSquared) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final double a = dot(x, y) / lengthSquared;
        return new Vec2d(a * x, a * y);
    }

    @NotNull
    public Vec2d pow(float pow) {
        return pow((double) pow);
    }

    @NotNull
    @Override
    public Vec2d pow(double power) {
        return new Vec2d(Math.pow(x, power), Math.pow(y, power));
    }

    @NotNull
    @Override
    public Vec2d ceil() {
        return new Vec2d(Math.ceil(x), Math.ceil(y));
    }

    @NotNull
    @Override
    public Vec2d floor() {
        return new Vec2d(GenericMath.floor(x), GenericMath.floor(y));
    }

    @NotNull
    @Override
    public Vec2d round() {
        return new Vec2d(Math.round(x), Math.round(y));
    }

    @NotNull
    @Override
    public Vec2d abs() {
        return new Vec2d(Math.abs(x), Math.abs(y));
    }

    @NotNull
    @Override
    public Vec2d negate() {
        return new Vec2d(-x, -y);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec2d unaryMinus() {
        return negate();
    }

    @NotNull
    public Vec2d min(@NotNull Vec2d v) {
        return min(v.x, v.y);
    }

    @NotNull
    public Vec2d min(float x, float y) {
        return min((double) x, (double) y);
    }

    @NotNull
    public Vec2d min(double x, double y) {
        return new Vec2d(Math.min(this.x, x), Math.min(this.y, y));
    }

    @NotNull
    public Vec2d max(@NotNull Vec2d v) {
        return max(v.x, v.y);
    }

    @NotNull
    public Vec2d max(float x, float y) {
        return max((double) x, (double) y);
    }

    @NotNull
    public Vec2d max(double x, double y) {
        return new Vec2d(Math.max(this.x, x), Math.max(this.y, y));
    }

    public double distanceSquared(@NotNull Vec2d v) {
        return distanceSquared(v.x, v.y);
    }

    public double distanceSquared(float x, float y) {
        return distanceSquared((double) x, (double) y);
    }

    public double distanceSquared(double x, double y) {
        final double dx = this.x - x;
        final double dy = this.y - y;
        return dx * dx + dy * dy;
    }

    public double distance(@NotNull Vec2d v) {
        return distance(v.x, v.y);
    }

    public double distance(float x, float y) {
        return distance((double) x, (double) y);
    }

    public double distance(double x, double y) {
        return (double) Math.sqrt(distanceSquared(x, y));
    }

    @Override
    public double lengthSquared() {
        return x * x + y * y;
    }

    @Override
    public double length() {
        return (double) Math.sqrt(lengthSquared());
    }

    @NotNull
    @Override
    public Vec2d normalize() {
        final double length = length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        return new Vec2d(x / length, y / length);
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
    public Vec3d toVec3() {
        return toVec3(0);
    }

    @NotNull
    public Vec3d toVec3(float z) {
        return toVec3((double) z);
    }

    @NotNull
    public Vec3d toVec3(double z) {
        return new Vec3d(this, z);
    }

    @NotNull
    public Vec4d toVec4() {
        return toVec4(0, 0);
    }

    @NotNull
    public Vec4d toVec4(float z, float w) {
        return toVec4((double) z, (double) w);
    }

    @NotNull
    public Vec4d toVec4(double z, double w) {
        return new Vec4d(this, z, w);
    }

    @NotNull
    public VecNd toVecN() {
        return new VecNd(this);
    }

    @NotNull
    @Override
    public double[] toArray() {
        return new double[]{x, y};
    }

    @NotNull
    @Override
    public Vec2d toDouble() {
        return this;
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
    public int compareTo(@NotNull Vec2d v) {
        return (int) Math.signum(lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec2d)) {
            return false;
        }
        final Vec2d Vec2 = (Vec2d) o;
        if (Double.compare(Vec2.x, x) != 0) {
            return false;
        }
        if (Double.compare(Vec2.y, y) != 0) {
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
    public Vec2d clone() {
        return new Vec2d(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @NotNull
    public static Vec2d from(double n) {
         return n == 0 ? ZERO : new Vec2d(n, n);
    }

    @NotNull
    public static Vec2d from(double x, double y) {
         return x == 0 && y == 0 ? ZERO : new Vec2d(x, y);
    }

    /**
     * Gets the direction vector of a random angle using the random specified.
     *
     * @param random to use
     * @return the random direction vector
     */
    public static Vec2d createRandomDirection(@NotNull Random random) {
        return createDirectionRad(random.nextDouble() * (double) TrigMath.TWO_PI);
    }

    /**
     * Gets the direction vector of a certain angle in degrees.
     *
     * @param angle in degrees
     * @return the direction vector
     */
    public static Vec2d createDirectionDeg(float angle) {
        return createDirectionDeg((double) angle);
    }

    /**
     * Gets the direction vector of a certain angle in degrees.
     *
     * @param angle in degrees
     * @return the direction vector
     */
    public static Vec2d createDirectionDeg(double angle) {
        return createDirectionRad((double) Math.toRadians(angle));
    }

    /**
     * Gets the direction vector of a certain angle in radians.
     *
     * @param angle in radians
     * @return the direction vector
     */
    public static Vec2d createDirectionRad(float angle) {
        return createDirectionRad((double) angle);
    }

    /**
     * Gets the direction vector of a certain angle in radians.
     *
     * @param angle in radians
     * @return the direction vector
     */
    public static Vec2d createDirectionRad(double angle) {
        return new Vec2d(TrigMath.cos(angle), TrigMath.sin(angle));
    }

    public static Vec2d createPooled(double x, double y) {
        return pool.getPooled(x, y, 0, 0);
    }
}
