package com.flowpowered.math.vector;

import java.io.Serializable;
import java.util.Random;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import com.flowpowered.math.TrigMath;
import org.jetbrains.annotations.NotNull;

public class Vec3d implements Vectord, Vec3, Comparable<Vec3d>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec3d> pool = new FlowPool<Vec3d>(3, FlowPool.BITS_3D) {
        @NotNull
        protected Vec3d hit(Vec3d value) {
            AllocationTracker.cacheHitsVec3d++;
            return value;
        }

        @NotNull
        protected Vec3d create(double x, double y, double z, double w) {
            return new Vec3d(x, y, z);
        }
    };

    public static final Vec3d ZERO = new Vec3d(0, 0, 0);
    public static final Vec3d UNIT_X = new Vec3d(1, 0, 0);
    public static final Vec3d UNIT_Y = new Vec3d(0, 1, 0);
    public static final Vec3d UNIT_Z = new Vec3d(0, 0, 1);
    public static final Vec3d ONE = new Vec3d(1, 1, 1);
    public static final Vec3d RIGHT = UNIT_X;
    public static final Vec3d UP = UNIT_Y;
    public static final Vec3d FORWARD = UNIT_Z;
    private final double x;
    private final double y;
    private final double z;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec3d() {
        this(0, 0, 0);
    }

    public Vec3d(@NotNull Vec2d v) {
        this(v, 0);
    }

    public Vec3d(@NotNull Vec2d v, float z) {
        this(v, (double) z);
    }

    public Vec3d(@NotNull Vec2d v, double z) {
        this(v.getX(), v.getY(), z);
    }

    public Vec3d(@NotNull net.minecraft.util.math.Vec3d v) {
        this(v.x, v.y, v.z);
    }

    public Vec3d(@NotNull Vec3d v) {
        this(v.x, v.y, v.z);
    }

    public Vec3d(@NotNull Vec4d v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public Vec3d(@NotNull VecNd v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0);
    }

    public Vec3d(float x, float y, float z) {
        this((double) x, (double) y, (double) z);
    }

    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        AllocationTracker.instancesVec3d++;
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
        return getZ();
    }

    public double getZ() {
        return z;
    }
    /** Operator function for Kotlin */
    public double component3() {
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
    public Vec3d add(@NotNull Vec3d v) {
        return add(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3d plus(@NotNull Vec3d v) {
        return add(v);
    }

    @NotNull
    public Vec3d add(float x, float y, float z) {
        return add((double) x, (double) y, (double) z);
    }

    @NotNull
    public Vec3d add(double x, double y, double z) {
        return new Vec3d(this.x + x, this.y + y, this.z + z);
    }

    @NotNull
    public Vec3d sub(@NotNull Vec3d v) {
        return sub(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3d minus(@NotNull Vec3d v) {
        return sub(v);
    }

    @NotNull
    public Vec3d sub(float x, float y, float z) {
        return sub((double) x, (double) y, (double) z);
    }

    @NotNull
    public Vec3d sub(double x, double y, double z) {
        return new Vec3d(this.x - x, this.y - y, this.z - z);
    }

    @NotNull
    public Vec3d mul(float a) {
        return mul((double) a);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3d times(float a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec3d mul(double a) {
        return mul(a, a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec3d times(double a) {
        return mul(a);
    }

    @NotNull
    public Vec3d mul(@NotNull Vec3d v) {
        return mul(v.x, v.y, v.z);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec3d times(@NotNull Vec3d v) {
        return mul(v);
    }

    @NotNull
    public Vec3d mul(float x, float y, float z) {
        return mul((double) x, (double) y, (double) z);
    }

    @NotNull
    public Vec3d mul(double x, double y, double z) {
        return new Vec3d(this.x * x, this.y * y, this.z * z);
    }

    @NotNull
    public Vec3d div(float a) {
        return div((double) a);
    }

    @NotNull
    @Override
    public Vec3d div(double a) {
        return div(a, a, a);
    }

    @NotNull
    public Vec3d div(@NotNull Vec3d v) {
        return div(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3d div(float x, float y, float z) {
        return div((double) x, (double) y, (double) z);
    }

    @NotNull
    public Vec3d div(double x, double y, double z) {
        return new Vec3d(this.x / x, this.y / y, this.z / z);
    }

    public double dot(@NotNull Vec3d v) {
        return dot(v.x, v.y, v.z);
    }

    public double dot(float x, float y, float z) {
        return dot((double) x, (double) y, (double) z);
    }

    public double dot(double x, double y, double z) {
        return this.x * x + this.y * y + this.z * z;
    }

    @NotNull
    public Vec3d project(@NotNull Vec3d v) {
        return project(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3d project(float x, float y, float z) {
        return project((double) x, (double) y, (double) z);
    }

    @NotNull
    public Vec3d project(double x, double y, double z) {
        final double lengthSquared = x * x + y * y + z * z;
        if (Math.abs(lengthSquared) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final double a = dot(x, y, z) / lengthSquared;
        return new Vec3d(a * x, a * y, a * z);
    }

    @NotNull
    public Vec3d cross(@NotNull Vec3d v) {
        return cross(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3d cross(float x, float y, float z) {
        return cross((double) x, (double) y, (double) z);
    }

    @NotNull
    public Vec3d cross(double x, double y, double z) {
        return new Vec3d(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
    }

    @NotNull
    public Vec3d pow(float pow) {
        return pow((double) pow);
    }

    @NotNull
    @Override
    public Vec3d pow(double power) {
        return new Vec3d(Math.pow(x, power), Math.pow(y, power), Math.pow(z, power));
    }

    @NotNull
    @Override
    public Vec3d ceil() {
        return new Vec3d(Math.ceil(x), Math.ceil(y), Math.ceil(z));
    }

    @NotNull
    @Override
    public Vec3d floor() {
        return new Vec3d(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
    }

    @NotNull
    @Override
    public Vec3d round() {
        return new Vec3d(Math.round(x), Math.round(y), Math.round(z));
    }

    @NotNull
    @Override
    public Vec3d abs() {
        return new Vec3d(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    @NotNull
    @Override
    public Vec3d negate() {
        return new Vec3d(-x, -y, -z);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec3d unaryMinus() {
        return negate();
    }

    @NotNull
    public Vec3d min(@NotNull Vec3d v) {
        return min(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3d min(float x, float y, float z) {
        return min((double) x, (double) y, (double) z);
    }

    @NotNull
    public Vec3d min(double x, double y, double z) {
        return new Vec3d(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
    }

    @NotNull
    public Vec3d max(@NotNull Vec3d v) {
        return max(v.x, v.y, v.z);
    }

    @NotNull
    public Vec3d max(float x, float y, float z) {
        return max((double) x, (double) y, (double) z);
    }

    @NotNull
    public Vec3d max(double x, double y, double z) {
        return new Vec3d(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
    }

    public double distanceSquared(@NotNull Vec3d v) {
        return distanceSquared(v.x, v.y, v.z);
    }

    public double distanceSquared(float x, float y, float z) {
        return distanceSquared((double) x, (double) y, (double) z);
    }

    public double distanceSquared(double x, double y, double z) {
        final double dx = this.x - x;
        final double dy = this.y - y;
        final double dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(@NotNull Vec3d v) {
        return distance(v.x, v.y, v.z);
    }

    public double distance(float x, float y, float z) {
        return distance((double) x, (double) y, (double) z);
    }

    public double distance(double x, double y, double z) {
        return (double) Math.sqrt(distanceSquared(x, y, z));
    }

    @Override
    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    @Override
    public double length() {
        return (double) Math.sqrt(lengthSquared());
    }

    @NotNull
    @Override
    public Vec3d normalize() {
        final double length = length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        return new Vec3d(x / length, y / length, z / length);
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
    public Vec2d toVec2() {
        return new Vec2d(this);
    }

    @NotNull
    public Vec2d toVec2(boolean useZ) {
        return new Vec2d(x, useZ ? z : y);
    }

    @NotNull
    public Vec4d toVec4() {
        return toVec4(0);
    }

    @NotNull
    public Vec4d toVec4(float w) {
        return toVec4((double) w);
    }

    @NotNull
    public Vec4d toVec4(double w) {
        return new Vec4d(this, w);
    }

    @NotNull
    public VecNd toVecN() {
        return new VecNd(this);
    }

    @NotNull
    @Override
    public double[] toArray() {
        return new double[]{x, y, z};
    }

    @NotNull
    @Override
    public Vec3d toDouble() {
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
    public int compareTo(@NotNull Vec3d v) {
        return (int) Math.signum(lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec3d)) {
            return false;
        }
        final Vec3d Vec3 = (Vec3d) o;
        if (Double.compare(Vec3.x, x) != 0) {
            return false;
        }
        if (Double.compare(Vec3.y, y) != 0) {
            return false;
        }
        if (Double.compare(Vec3.z, z) != 0) {
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
    public Vec3d clone() {
        return new Vec3d(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @NotNull
    public static Vec3d from(double n) {
         return n == 0 ? ZERO : new Vec3d(n, n, n);
    }

    @NotNull
    public static Vec3d from(double x, double y, double z) {
         return x == 0 && y == 0 && z == 0 ? ZERO : new Vec3d(x, y, z);
    }

    /**
     * Gets the direction vector of a random pitch and yaw using the random specified.
     *
     * @param random to use
     * @return the random direction vector
     */
    public static Vec3d createRandomDirection(@NotNull Random random) {
        return createDirectionRad(random.nextDouble() * (double) TrigMath.TWO_PI,
                random.nextDouble() * (double) TrigMath.TWO_PI);
    }

    /**
     * Gets the direction vector of a certain theta and phi in degrees. This uses the standard math spherical coordinate system.
     *
     * @param theta in degrees
     * @param phi in degrees
     * @return the direction vector
     */
    public static Vec3d createDirectionDeg(float theta, float phi) {
        return createDirectionDeg((double) theta, (double) phi);
    }

    /**
     * Gets the direction vector of a certain theta and phi in degrees. This uses the standard math spherical coordinate system.
     *
     * @param theta in degrees
     * @param phi in degrees
     * @return the direction vector
     */
    public static Vec3d createDirectionDeg(double theta, double phi) {
        return createDirectionRad((double) Math.toRadians(theta), (double) Math.toRadians(phi));
    }

    /**
     * Gets the direction vector of a certain theta and phi in radians. This uses the standard math spherical coordinate system.
     *
     * @param theta in radians
     * @param phi in radians
     * @return the direction vector
     */
    public static Vec3d createDirectionRad(float theta, float phi) {
        return createDirectionRad((double) theta, (double) phi);
    }

    /**
     * Gets the direction vector of a certain theta and phi in radians. This uses the standard math spherical coordinate system.
     *
     * @param theta in radians
     * @param phi in radians
     * @return the direction vector
     */
    public static Vec3d createDirectionRad(double theta, double phi) {
        final double f = TrigMath.sin(phi);
        return new Vec3d(f * TrigMath.cos(theta), f * TrigMath.sin(theta), TrigMath.cos(phi));
    }

    public static Vec3d createPooled(double x, double y, double z) {
        return pool.getPooled(x, y, z, 0);
    }
}
