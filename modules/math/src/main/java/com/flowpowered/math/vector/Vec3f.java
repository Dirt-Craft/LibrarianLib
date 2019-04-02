package com.flowpowered.math.vector;

import java.io.Serializable;
import java.util.Random;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import com.flowpowered.math.TrigMath;
import org.jetbrains.annotations.NotNull;

public class Vec3f implements Vectorf, Vec3, Comparable<Vec3f>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec3f> pool = new FlowPool<Vec3f>(3, FlowPool.BITS_3D) {
        @NotNull
        protected Vec3f hit(Vec3f value) {
            AllocationTracker.cacheHitsVec3f++;
            return value;
        }

        @NotNull
        protected Vec3f create(float x, float y, float z, float w) {
            return new Vec3f(x, y, z);
        }
    };

    public static final Vec3f ZERO = new Vec3f(0, 0, 0);
    public static final Vec3f UNIT_X = new Vec3f(1, 0, 0);
    public static final Vec3f UNIT_Y = new Vec3f(0, 1, 0);
    public static final Vec3f UNIT_Z = new Vec3f(0, 0, 1);
    public static final Vec3f ONE = new Vec3f(1, 1, 1);
    public static final Vec3f RIGHT = UNIT_X;
    public static final Vec3f UP = UNIT_Y;
    public static final Vec3f FORWARD = UNIT_Z;
    private final float x;
    private final float y;
    private final float z;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec3f() {
        this(0, 0, 0);
    }

    public Vec3f(@NotNull Vec2f v) {
        this(v, 0);
    }

    public Vec3f(@NotNull Vec2f v, double z) {
        this(v, (float) z);
    }

    public Vec3f(@NotNull Vec2f v, float z) {
        this(v.getX(), v.getY(), z);
    }

    public Vec3f(@NotNull Vec3f v) {
        this(v.x, v.y, v.z);
    }

    public Vec3f(@NotNull Vec4f v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public Vec3f(@NotNull VecNf v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0);
    }

    public Vec3f(double x, double y, double z) {
        this((float) x, (float) y, (float) z);
    }

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        AllocationTracker.instancesVec3f++;
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
        return getZ();
    }

    public float getZ() {
        return z;
    }
    /** Operator function for Kotlin */
    public float component3() {
        return getZ();
    }

    public int getFloorX() {
        return GenericMath.floor(x);
    }

    public int getFloorY() {
        return GenericMath.floor(y);
    }

    public int getFloorZ() {
        return GenericMath.floor(z);
    }

    @NotNull
    public Vec3f add(@NotNull Vec3f v) {
        return add(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3f plus(@NotNull Vec3f v) {
        return add(v);
    }

    @NotNull
    public Vec3f add(double x, double y, double z) {
        return add((float) x, (float) y, (float) z);
    }

    @NotNull
    public Vec3f add(float x, float y, float z) {
        return new Vec3f(this.x + x, this.y + y, this.z + z);
    }

    @NotNull
    public Vec3f sub(@NotNull Vec3f v) {
        return sub(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3f minus(@NotNull Vec3f v) {
        return sub(v);
    }

    @NotNull
    public Vec3f sub(double x, double y, double z) {
        return sub((float) x, (float) y, (float) z);
    }

    @NotNull
    public Vec3f sub(float x, float y, float z) {
        return new Vec3f(this.x - x, this.y - y, this.z - z);
    }

    @NotNull
    public Vec3f mul(double a) {
        return mul((float) a);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3f times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec3f mul(float a) {
        return mul(a, a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec3f times(float a) {
        return mul(a);
    }

    @NotNull
    public Vec3f mul(@NotNull Vec3f v) {
        return mul(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3f times(@NotNull Vec3f v) {
        return mul(v);
    }

    @NotNull
    public Vec3f mul(double x, double y, double z) {
        return mul((float) x, (float) y, (float) z);
    }

    @NotNull
    public Vec3f mul(float x, float y, float z) {
        return new Vec3f(this.x * x, this.y * y, this.z * z);
    }

    @NotNull
    public Vec3f div(double a) {
        return div((float) a);
    }

    @NotNull
    @Override
    public Vec3f div(float a) {
        return div(a, a, a);
    }

    @NotNull
    public Vec3f div(@NotNull Vec3f v) {
        return div(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3f div(double x, double y, double z) {
        return div((float) x, (float) y, (float) z);
    }

    @NotNull
    public Vec3f div(float x, float y, float z) {
        return new Vec3f(this.x / x, this.y / y, this.z / z);
    }

    public float dot(@NotNull Vec3f v) {
        return dot(v.x, v.y, v.z);
    }

    public float dot(double x, double y, double z) {
        return dot((float) x, (float) y, (float) z);
    }

    public float dot(float x, float y, float z) {
        return this.x * x + this.y * y + this.z * z;
    }

    @NotNull
    public Vec3f project(@NotNull Vec3f v) {
        return project(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3f project(double x, double y, double z) {
        return project((float) x, (float) y, (float) z);
    }

    @NotNull
    public Vec3f project(float x, float y, float z) {
        final float lengthSquared = x * x + y * y + z * z;
        if (Math.abs(lengthSquared) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final float a = dot(x, y, z) / lengthSquared;
        return new Vec3f(a * x, a * y, a * z);
    }

    @NotNull
    public Vec3f cross(@NotNull Vec3f v) {
        return cross(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3f cross(double x, double y, double z) {
        return cross((float) x, (float) y, (float) z);
    }

    @NotNull
    public Vec3f cross(float x, float y, float z) {
        return new Vec3f(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
    }

    @NotNull
    public Vec3f pow(double pow) {
        return pow((float) pow);
    }

    @NotNull
    @Override
    public Vec3f pow(float power) {
        return new Vec3f(Math.pow(x, power), Math.pow(y, power), Math.pow(z, power));
    }

    @NotNull
    @Override
    public Vec3f ceil() {
        return new Vec3f(Math.ceil(x), Math.ceil(y), Math.ceil(z));
    }

    @NotNull
    @Override
    public Vec3f floor() {
        return new Vec3f(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    @NotNull
    @Override
    public Vec3f round() {
        return new Vec3f(Math.round(x), Math.round(y), Math.round(z));
    }

    @NotNull
    @Override
    public Vec3f abs() {
        return new Vec3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    @NotNull
    @Override
    public Vec3f negate() {
        return new Vec3f(-x, -y, -z);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec3f unaryMinus() {
        return negate();
    }

    @NotNull
    public Vec3f min(@NotNull Vec3f v) {
        return min(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3f min(double x, double y, double z) {
        return min((float) x, (float) y, (float) z);
    }

    @NotNull
    public Vec3f min(float x, float y, float z) {
        return new Vec3f(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
    }

    @NotNull
    public Vec3f max(@NotNull Vec3f v) {
        return max(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3f max(double x, double y, double z) {
        return max((float) x, (float) y, (float) z);
    }

    @NotNull
    public Vec3f max(float x, float y, float z) {
        return new Vec3f(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
    }

    public float distanceSquared(@NotNull Vec3f v) {
        return distanceSquared(v.x, v.y, v.z);
    }

    public float distanceSquared(double x, double y, double z) {
        return distanceSquared((float) x, (float) y, (float) z);
    }

    public float distanceSquared(float x, float y, float z) {
        final float dx = this.x - x;
        final float dy = this.y - y;
        final float dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public float distance(@NotNull Vec3f v) {
        return distance(v.x, v.y, v.z);
    }

    public float distance(double x, double y, double z) {
        return distance((float) x, (float) y, (float) z);
    }

    public float distance(float x, float y, float z) {
        return (float) Math.sqrt(distanceSquared(x, y, z));
    }

    @Override
    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    @Override
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    @NotNull
    @Override
    public Vec3f normalize() {
        final float length = length();
        if (Math.abs(length) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        return new Vec3f(x / length, y / length, z / length);
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
    public Vec2f toVec2() {
        return new Vec2f(this);
    }

    @NotNull
    public Vec2f toVec2(boolean useZ) {
        return new Vec2f(x, useZ ? z : y);
    }

    @NotNull
    public Vec4f toVec4() {
        return toVec4(0);
    }

    @NotNull
    public Vec4f toVec4(double w) {
        return toVec4((float) w);
    }

    @NotNull
    public Vec4f toVec4(float w) {
        return new Vec4f(this, w);
    }

    @NotNull
    public VecNf toVecN() {
        return new VecNf(this);
    }

    @NotNull
    @Override
    public float[] toArray() {
        return new float[]{x, y, z};
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
    public int compareTo(@NotNull Vec3f v) {
        return (int) Math.signum(lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec3f)) {
            return false;
        }
        final Vec3f Vec3 = (Vec3f) o;
        if (Float.compare(Vec3.x, x) != 0) {
            return false;
        }
        if (Float.compare(Vec3.y, y) != 0) {
            return false;
        }
        if (Float.compare(Vec3.z, z) != 0) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (!hashed) {
            int result = (x != +0.0f ? HashFunctions.hash(x) : 0);
            result = 31 * result + (y != +0.0f ? HashFunctions.hash(y) : 0);
            hashCode = 31 * result + (z != +0.0f ? HashFunctions.hash(z) : 0);
            hashed = true;
        }
        return hashCode;
    }

    @NotNull
    @Override
    public Vec3f clone() {
        return new Vec3f(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @NotNull
    public static Vec3f from(float n) {
         return n == 0 ? ZERO : new Vec3f(n, n, n);
    }

    @NotNull
    public static Vec3f from(float x, float y, float z) {
         return x == 0 && y == 0 && z == 0 ? ZERO : new Vec3f(x, y, z);
    }

    /**
     * Gets the direction vector of a random pitch and yaw using the random specified.
     *
     * @param random to use
     * @return the random direction vector
     */
    public static Vec3f createRandomDirection(@NotNull Random random) {
        return createDirectionRad(random.nextFloat() * (float) TrigMath.TWO_PI,
                random.nextFloat() * (float) TrigMath.TWO_PI);
    }

    /**
     * Gets the direction vector of a certain theta and phi in degrees. This uses the standard math spherical coordinate system.
     *
     * @param theta in degrees
     * @param phi in degrees
     * @return the direction vector
     */
    public static Vec3f createDirectionDeg(double theta, double phi) {
        return createDirectionDeg((float) theta, (float) phi);
    }

    /**
     * Gets the direction vector of a certain theta and phi in degrees. This uses the standard math spherical coordinate system.
     *
     * @param theta in degrees
     * @param phi in degrees
     * @return the direction vector
     */
    public static Vec3f createDirectionDeg(float theta, float phi) {
        return createDirectionRad((float) Math.toRadians(theta), (float) Math.toRadians(phi));
    }

    /**
     * Gets the direction vector of a certain theta and phi in radians. This uses the standard math spherical coordinate system.
     *
     * @param theta in radians
     * @param phi in radians
     * @return the direction vector
     */
    public static Vec3f createDirectionRad(double theta, double phi) {
        return createDirectionRad((float) theta, (float) phi);
    }

    /**
     * Gets the direction vector of a certain theta and phi in radians. This uses the standard math spherical coordinate system.
     *
     * @param theta in radians
     * @param phi in radians
     * @return the direction vector
     */
    public static Vec3f createDirectionRad(float theta, float phi) {
        final float f = TrigMath.sin(phi);
        return new Vec3f(f * TrigMath.cos(theta), f * TrigMath.sin(theta), TrigMath.cos(phi));
    }

    public static Vec3f createPooled(float x, float y, float z) {
        return pool.getPooled(x, y, z, 0);
    }
}
