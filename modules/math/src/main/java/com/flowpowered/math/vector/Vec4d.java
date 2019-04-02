package com.flowpowered.math.vector;

import java.io.Serializable;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import org.jetbrains.annotations.NotNull;

public class Vec4d implements Vectord, Vec4, Comparable<Vec4d>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec4d> pool = new FlowPool<Vec4d>(4, FlowPool.BITS_4D) {
        @NotNull
        protected Vec4d hit(Vec4d value) {
            AllocationTracker.cacheHitsVec4d++;
            return value;
        }

        @NotNull
        protected Vec4d create(double x, double y, double z, double w) {
            return new Vec4d(x, y, z, w);
        }
    };

    public static final Vec4d ZERO = new Vec4d(0, 0, 0, 0);
    public static final Vec4d UNIT_X = new Vec4d(1, 0, 0, 0);
    public static final Vec4d UNIT_Y = new Vec4d(0, 1, 0, 0);
    public static final Vec4d UNIT_Z = new Vec4d(0, 0, 1, 0);
    public static final Vec4d UNIT_W = new Vec4d(0, 0, 0, 1);
    public static final Vec4d ONE = new Vec4d(1, 1, 1, 1);
    private final double x;
    private final double y;
    private final double z;
    private final double w;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec4d() {
        this(0, 0, 0, 0);
    }

    public Vec4d(@NotNull Vec2d v) {
        this(v, 0, 0);
    }

    public Vec4d(@NotNull Vec2d v, float z, float w) {
        this(v, (double) z, (double) w);
    }

    public Vec4d(@NotNull Vec2d v, double z, double w) {
        this(v.getX(), v.getY(), z, w);
    }

    public Vec4d(@NotNull Vec3d v) {
        this(v, 0);
    }

    public Vec4d(@NotNull Vec3d v, float w) {
        this(v, (double) w);
    }

    public Vec4d(@NotNull Vec3d v, double w) {
        this(v.getX(), v.getY(), v.getZ(), w);
    }

    public Vec4d(@NotNull Vec4d v) {
        this(v.x, v.y, v.z, v.w);
    }

    public Vec4d(@NotNull VecNd v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0, v.size() > 3 ? v.get(3) : 0);
    }

    public Vec4d(float x, float y, float z, float w) {
        this((double) x, (double) y, (double) z, (double) w);
    }

    public Vec4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        AllocationTracker.instancesVec4d++;
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

    public double getZ() {
        return z;
    }
    /** Operator function for Kotlin */
    public double component3() {
        return getZ();
    }

    public double getW() {
        return w;
    }
    /** Operator function for Kotlin */
    public double component4() {
        return getW();
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

    public int getFloorW() {
        return GenericMath.floor(w);
    }

    @NotNull
    public Vec4d add(@NotNull Vec4d v) {
        return add(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4d plus(@NotNull Vec4d v) {
        return add(v);
    }

    @NotNull
    public Vec4d add(float x, float y, float z, float w) {
        return add((double) x, (double) y, (double) z, (double) w);
    }

    @NotNull
    public Vec4d add(double x, double y, double z, double w) {
        return new Vec4d(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    @NotNull
    public Vec4d sub(@NotNull Vec4d v) {
        return sub(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4d minus(@NotNull Vec4d v) {
        return sub(v);
    }

    @NotNull
    public Vec4d sub(float x, float y, float z, float w) {
        return sub((double) x, (double) y, (double) z, (double) w);
    }

    @NotNull
    public Vec4d sub(double x, double y, double z, double w) {
        return new Vec4d(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    @NotNull
    public Vec4d mul(float a) {
        return mul((double) a);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4d times(float a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec4d mul(double a) {
        return mul(a, a, a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec4d times(double a) {
        return mul(a);
    }

    @NotNull
    public Vec4d mul(@NotNull Vec4d v) {
        return mul(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4d times(@NotNull Vec4d v) {
        return mul(v);
    }

    @NotNull
    public Vec4d mul(float x, float y, float z, float w) {
        return mul((double) x, (double) y, (double) z, (double) w);
    }

    @NotNull
    public Vec4d mul(double x, double y, double z, double w) {
        return new Vec4d(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    @NotNull
    public Vec4d div(float a) {
        return div((double) a);
    }

    @NotNull
    @Override
    public Vec4d div(double a) {
        return div(a, a, a, a);
    }

    @NotNull
    public Vec4d div(@NotNull Vec4d v) {
        return div(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4d div(float x, float y, float z, float w) {
        return div((double) x, (double) y, (double) z, (double) w);
    }

    @NotNull
    public Vec4d div(double x, double y, double z, double w) {
        return new Vec4d(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    public double dot(@NotNull Vec4d v) {
        return dot(v.x, v.y, v.z, v.w);
    }

    public double dot(float x, float y, float z, float w) {
        return dot((double) x, (double) y, (double) z, (double) w);
    }

    public double dot(double x, double y, double z, double w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    @NotNull
    public Vec4d project(@NotNull Vec4d v) {
        return project(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4d project(float x, float y, float z, float w) {
        return project((double) x, (double) y, (double) z, (double) w);
    }

    @NotNull
    public Vec4d project(double x, double y, double z, double w) {
        final double lengthSquared = x * x + y * y + z * z + w * w;
        if (Math.abs(lengthSquared) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final double a = dot(x, y, z, w) / lengthSquared;
        return new Vec4d(a * x, a * y, a * z, a * w);
    }

    @NotNull
    public Vec4d pow(float pow) {
        return pow((double) pow);
    }

    @NotNull
    @Override
    public Vec4d pow(double power) {
        return new Vec4d(Math.pow(x, power), Math.pow(y, power), Math.pow(z, power), Math.pow(w, power));
    }

    @NotNull
    @Override
    public Vec4d ceil() {
        return new Vec4d(Math.ceil(x), Math.ceil(y), Math.ceil(z), Math.ceil(w));
    }

    @NotNull
    @Override
    public Vec4d floor() {
        return new Vec4d(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    @NotNull
    @Override
    public Vec4d round() {
        return new Vec4d(Math.round(x), Math.round(y), Math.round(z), Math.round(w));
    }

    @NotNull
    @Override
    public Vec4d abs() {
        return new Vec4d(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
    }

    @NotNull
    @Override
    public Vec4d negate() {
        return new Vec4d(-x, -y, -z, -w);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec4d unaryMinus() {
        return negate();
    }


    @NotNull
    public Vec4d min(@NotNull Vec4d v) {
        return min(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4d min(float x, float y, float z, float w) {
        return min((double) x, (double) y, (double) z, (double) w);
    }

    @NotNull
    public Vec4d min(double x, double y, double z, double w) {
        return new Vec4d(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
    }

    @NotNull
    public Vec4d max(@NotNull Vec4d v) {
        return max(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4d max(float x, float y, float z, float w) {
        return max((double) x, (double) y, (double) z, (double) w);
    }

    @NotNull
    public Vec4d max(double x, double y, double z, double w) {
        return new Vec4d(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
    }

    public double distanceSquared(@NotNull Vec4d v) {
        return distanceSquared(v.x, v.y, v.z, v.w);
    }

    public double distanceSquared(float x, float y, float z, float w) {
        return distanceSquared((double) x, (double) y, (double) z, (double) w);
    }

    public double distanceSquared(double x, double y, double z, double w) {
        final double dx = this.x - x;
        final double dy = this.y - y;
        final double dz = this.z - z;
        final double dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public double distance(@NotNull Vec4d v) {
        return distance(v.x, v.y, v.z, v.w);
    }

    public double distance(float x, float y, float z, float w) {
        return distance((double) x, (double) y, (double) z, (double) w);
    }

    public double distance(double x, double y, double z, double w) {
        return (double) Math.sqrt(distanceSquared(x, y, z, w));
    }

    @Override
    public double lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    @Override
    public double length() {
        return (double) Math.sqrt(lengthSquared());
    }

    @NotNull
    @Override
    public Vec4d normalize() {
        final double length = length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        return new Vec4d(x / length, y / length, z / length, w / length);
    }

    /**
     * Return the axis with the minimal value.
     *
     * @return {@link int} axis with minimal value
     */
    @Override
    public int getMinAxis() {
        double value = x;
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
        double value = x;
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
    public Vec2d toVec2() {
        return new Vec2d(this);
    }

    @NotNull
    public Vec3d toVec3() {
        return new Vec3d(this);
    }

    @NotNull
    public VecNd toVecN() {
        return new VecNd(this);
    }

    @NotNull
    @Override
    public double[] toArray() {
        return new double[]{x, y, z, w};
    }

    @NotNull
    @Override
    public Vec4d toDouble() {
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
        return new Vec4l(x, y, z, w);
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
    public int compareTo(@NotNull Vec4d v) {
        return (int) Math.signum(lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec4d)) {
            return false;
        }
        final Vec4d Vec4 = (Vec4d) o;
        if (Double.compare(Vec4.w, w) != 0) {
            return false;
        }
        if (Double.compare(Vec4.x, x) != 0) {
            return false;
        }
        if (Double.compare(Vec4.y, y) != 0) {
            return false;
        }
        if (Double.compare(Vec4.z, z) != 0) {
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
    public Vec4d clone() {
        return new Vec4d(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

    @NotNull
    public static Vec4d from(double n) {
         return n == 0 ? ZERO : new Vec4d(n, n, n, n);
    }

    @NotNull
    public static Vec4d from(double x, double y, double z, double w) {
         return x == 0 && y == 0 && z == 0 && w == 0 ? ZERO : new Vec4d(x, y, z, w);
    }

    public static Vec4d createPooled(double x, double y, double z, double w) {
        return pool.getPooled(x, y, z, w);
    }
}
