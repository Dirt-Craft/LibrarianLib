package com.flowpowered.math.vector;

import java.io.Serializable;
import java.lang.Override;
import java.util.Random;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import com.flowpowered.math.TrigMath;
import org.jetbrains.annotations.NotNull;

public class Vec2f implements Vectorf, Vec2, Comparable<Vec2f>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec2f> pool = new FlowPool<Vec2f>(2, FlowPool.BITS_2D) {
        @NotNull
        protected Vec2f hit(Vec2f value) {
            AllocationTracker.cacheHitsVec2f++;
            return value;
        }

        @NotNull
        protected Vec2f create(float x, float y, float z, float w) {
            return new Vec2f(x, y);
        }
    };

    public static final Vec2f ZERO = new Vec2f(0, 0);
    public static final Vec2f UNIT_X = new Vec2f(1, 0);
    public static final Vec2f UNIT_Y = new Vec2f(0, 1);
    public static final Vec2f ONE = new Vec2f(1, 1);
    private final float x;
    private final float y;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec2f() {
        this(0, 0);
    }

    public Vec2f(@NotNull Vec2f v) {
        this(v.x, v.y);
    }

    public Vec2f(@NotNull Vec3f v) {
        this(v.getX(), v.getY());
    }

    public Vec2f(@NotNull Vec4f v) {
        this(v.getX(), v.getY());
    }

    public Vec2f(@NotNull VecNf v) {
        this(v.get(0), v.get(1));
    }

    public Vec2f(double x, double y) {
        this((float) x, (float) y);
    }

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
        AllocationTracker.instancesVec2f++;
    }

    public float getX() {
        return x;
    }
    /** Operator function for Kotlin */
    public float component1() {
        return getX();
    }

    public float getY() {
        return y;
    }
    /** Operator function for Kotlin */
    public float component2() {
        return getY();
    }

    public int getFloorX() {
        return GenericMath.floor(x);
    }

    public int getFloorY() {
        return GenericMath.floor(y);
    }

    @NotNull
    public Vec2f add(@NotNull Vec2f v) {
        return add(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2f plus(@NotNull Vec2f v) {
        return add(v);
    }

    @NotNull
    public Vec2f add(double x, double y) {
        return add((float) x, (float) y);
    }

    @NotNull
    public Vec2f add(float x, float y) {
        return new Vec2f(this.x + x, this.y + y);
    }

    @NotNull
    public Vec2f sub(@NotNull Vec2f v) {
        return sub(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2f minus(@NotNull Vec2f v) {
        return sub(v);
    }

    @NotNull
    public Vec2f sub(double x, double y) {
        return sub((float) x, (float) y);
    }

    @NotNull
    public Vec2f sub(float x, float y) {
        return new Vec2f(this.x - x, this.y - y);
    }

    @NotNull
    public Vec2f mul(double a) {
        return mul((float) a);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2f times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec2f mul(float a) {
        return mul(a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec2f times(float a) {
        return mul(a);
    }

    @NotNull
    public Vec2f mul(@NotNull Vec2f v) {
        return mul(v.x, v.y);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec2f times(@NotNull Vec2f v) {
        return mul(v);
    }

    @NotNull
    public Vec2f mul(double x, double y) {
        return mul((float) x, (float) y);
    }

    @NotNull
    public Vec2f mul(float x, float y) {
        return new Vec2f(this.x * x, this.y * y);
    }

    @NotNull
    public Vec2f div(double a) {
        return div((float) a);
    }

    @NotNull
    @Override
    public Vec2f div(float a) {
        return div(a, a);
    }

    @NotNull
    public Vec2f div(@NotNull Vec2f v) {
        return div(v.x, v.y);
    }

    @NotNull
    public Vec2f div(double x, double y) {
        return div((float) x, (float) y);
    }

    @NotNull
    public Vec2f div(float x, float y) {
        return new Vec2f(this.x / x, this.y / y);
    }

    public float dot(@NotNull Vec2f v) {
        return dot(v.x, v.y);
    }

    public float dot(double x, double y) {
        return dot((float) x, (float) y);
    }

    public float dot(float x, float y) {
        return this.x * x + this.y * y;
    }

    @NotNull
    public Vec2f project(@NotNull Vec2f v) {
        return project(v.x, v.y);
    }

    @NotNull
    public Vec2f project(double x, double y) {
        return project((float) x, (float) y);
    }

    @NotNull
    public Vec2f project(float x, float y) {
        final float lengthSquared = x * x + y * y;
        if (Math.abs(lengthSquared) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final float a = dot(x, y) / lengthSquared;
        return new Vec2f(a * x, a * y);
    }

    @NotNull
    public Vec2f pow(double pow) {
        return pow((float) pow);
    }

    @NotNull
    @Override
    public Vec2f pow(float power) {
        return new Vec2f(Math.pow(x, power), Math.pow(y, power));
    }

    @NotNull
    @Override
    public Vec2f ceil() {
        return new Vec2f(Math.ceil(x), Math.ceil(y));
    }

    @NotNull
    @Override
    public Vec2f floor() {
        return new Vec2f(GenericMath.floor(x), GenericMath.floor(y));
    }

    @NotNull
    @Override
    public Vec2f round() {
        return new Vec2f(Math.round(x), Math.round(y));
    }

    @NotNull
    @Override
    public Vec2f abs() {
        return new Vec2f(Math.abs(x), Math.abs(y));
    }

    @NotNull
    @Override
    public Vec2f negate() {
        return new Vec2f(-x, -y);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec2f unaryMinus() {
        return negate();
    }

    @NotNull
    public Vec2f min(@NotNull Vec2f v) {
        return min(v.x, v.y);
    }

    @NotNull
    public Vec2f min(double x, double y) {
        return min((float) x, (float) y);
    }

    @NotNull
    public Vec2f min(float x, float y) {
        return new Vec2f(Math.min(this.x, x), Math.min(this.y, y));
    }

    @NotNull
    public Vec2f max(@NotNull Vec2f v) {
        return max(v.x, v.y);
    }

    @NotNull
    public Vec2f max(double x, double y) {
        return max((float) x, (float) y);
    }

    @NotNull
    public Vec2f max(float x, float y) {
        return new Vec2f(Math.max(this.x, x), Math.max(this.y, y));
    }

    public float distanceSquared(@NotNull Vec2f v) {
        return distanceSquared(v.x, v.y);
    }

    public float distanceSquared(double x, double y) {
        return distanceSquared((float) x, (float) y);
    }

    public float distanceSquared(float x, float y) {
        final float dx = this.x - x;
        final float dy = this.y - y;
        return dx * dx + dy * dy;
    }

    public float distance(@NotNull Vec2f v) {
        return distance(v.x, v.y);
    }

    public float distance(double x, double y) {
        return distance((float) x, (float) y);
    }

    public float distance(float x, float y) {
        return (float) Math.sqrt(distanceSquared(x, y));
    }

    @Override
    public float lengthSquared() {
        return x * x + y * y;
    }

    @Override
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    @NotNull
    @Override
    public Vec2f normalize() {
        final float length = length();
        if (Math.abs(length) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        return new Vec2f(x / length, y / length);
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
    public Vec3f toVec3() {
        return toVec3(0);
    }

    @NotNull
    public Vec3f toVec3(double z) {
        return toVec3((float) z);
    }

    @NotNull
    public Vec3f toVec3(float z) {
        return new Vec3f(this, z);
    }

    @NotNull
    public Vec4f toVec4() {
        return toVec4(0, 0);
    }

    @NotNull
    public Vec4f toVec4(double z, double w) {
        return toVec4((float) z, (float) w);
    }

    @NotNull
    public Vec4f toVec4(float z, float w) {
        return new Vec4f(this, z, w);
    }

    @NotNull
    public VecNf toVecN() {
        return new VecNf(this);
    }

    @NotNull
    @Override
    public float[] toArray() {
        return new float[]{x, y};
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
        return this;
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
    public int compareTo(@NotNull Vec2f v) {
        return (int) Math.signum(lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec2f)) {
            return false;
        }
        final Vec2f Vec2 = (Vec2f) o;
        if (Float.compare(Vec2.x, x) != 0) {
            return false;
        }
        if (Float.compare(Vec2.y, y) != 0) {
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
    public Vec2f clone() {
        return new Vec2f(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @NotNull
    public static Vec2f from(float n) {
         return n == 0 ? ZERO : new Vec2f(n, n);
    }

    @NotNull
    public static Vec2f from(float x, float y) {
         return x == 0 && y == 0 ? ZERO : new Vec2f(x, y);
    }

    /**
     * Gets the direction vector of a random angle using the random specified.
     *
     * @param random to use
     * @return the random direction vector
     */
    public static Vec2f createRandomDirection(@NotNull Random random) {
        return createDirectionRad(random.nextFloat() * (float) TrigMath.TWO_PI);
    }

    /**
     * Gets the direction vector of a certain angle in degrees.
     *
     * @param angle in degrees
     * @return the direction vector
     */
    public static Vec2f createDirectionDeg(double angle) {
        return createDirectionDeg((float) angle);
    }

    /**
     * Gets the direction vector of a certain angle in degrees.
     *
     * @param angle in degrees
     * @return the direction vector
     */
    public static Vec2f createDirectionDeg(float angle) {
        return createDirectionRad((float) Math.toRadians(angle));
    }

    /**
     * Gets the direction vector of a certain angle in radians.
     *
     * @param angle in radians
     * @return the direction vector
     */
    public static Vec2f createDirectionRad(double angle) {
        return createDirectionRad((float) angle);
    }

    /**
     * Gets the direction vector of a certain angle in radians.
     *
     * @param angle in radians
     * @return the direction vector
     */
    public static Vec2f createDirectionRad(float angle) {
        return new Vec2f(TrigMath.cos(angle), TrigMath.sin(angle));
    }

    public static Vec2f createPooled(float x, float y) {
        return pool.getPooled(x, y, 0, 0);
    }
}
