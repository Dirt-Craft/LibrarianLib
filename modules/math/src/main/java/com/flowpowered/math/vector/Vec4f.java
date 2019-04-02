package com.flowpowered.math.vector;

import java.io.Serializable;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import org.jetbrains.annotations.NotNull;

public class Vec4f implements Vectorf, Vec4, Comparable<Vec4f>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec4f> pool = new FlowPool<Vec4f>(4, FlowPool.BITS_4D) {
        @NotNull
        protected Vec4f hit(Vec4f value) {
            AllocationTracker.cacheHitsVec4f++;
            return value;
        }

        @NotNull
        protected Vec4f create(float x, float y, float z, float w) {
            return new Vec4f(x, y, z, w);
        }
    };

    public static final Vec4f ZERO = new Vec4f(0, 0, 0, 0);
    public static final Vec4f UNIT_X = new Vec4f(1, 0, 0, 0);
    public static final Vec4f UNIT_Y = new Vec4f(0, 1, 0, 0);
    public static final Vec4f UNIT_Z = new Vec4f(0, 0, 1, 0);
    public static final Vec4f UNIT_W = new Vec4f(0, 0, 0, 1);
    public static final Vec4f ONE = new Vec4f(1, 1, 1, 1);
    private final float x;
    private final float y;
    private final float z;
    private final float w;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec4f() {
        this(0, 0, 0, 0);
    }

    public Vec4f(@NotNull Vec2f v) {
        this(v, 0, 0);
    }

    public Vec4f(@NotNull Vec2f v, double z, double w) {
        this(v, (float) z, (float) w);
    }

    public Vec4f(@NotNull Vec2f v, float z, float w) {
        this(v.getX(), v.getY(), z, w);
    }

    public Vec4f(@NotNull Vec3f v) {
        this(v, 0);
    }

    public Vec4f(@NotNull Vec3f v, double w) {
        this(v, (float) w);
    }

    public Vec4f(@NotNull Vec3f v, float w) {
        this(v.getX(), v.getY(), v.getZ(), w);
    }

    public Vec4f(@NotNull Vec4f v) {
        this(v.x, v.y, v.z, v.w);
    }

    public Vec4f(@NotNull VecNf v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0, v.size() > 3 ? v.get(3) : 0);
    }

    public Vec4f(double x, double y, double z, double w) {
        this((float) x, (float) y, (float) z, (float) w);
    }

    public Vec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        AllocationTracker.instancesVec4f++;
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

    public float getZ() {
        return z;
    }
    /** Operator function for Kotlin */
    public float component3() {
        return getZ();
    }

    public float getW() {
        return w;
    }
    /** Operator function for Kotlin */
    public float component4() {
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
    public Vec4f add(@NotNull Vec4f v) {
        return add(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4f plus(@NotNull Vec4f v) {
        return add(v);
    }

    @NotNull
    public Vec4f add(double x, double y, double z, double w) {
        return add((float) x, (float) y, (float) z, (float) w);
    }

    @NotNull
    public Vec4f add(float x, float y, float z, float w) {
        return new Vec4f(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    @NotNull
    public Vec4f sub(@NotNull Vec4f v) {
        return sub(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4f minus(@NotNull Vec4f v) {
        return sub(v);
    }

    @NotNull
    public Vec4f sub(double x, double y, double z, double w) {
        return sub((float) x, (float) y, (float) z, (float) w);
    }

    @NotNull
    public Vec4f sub(float x, float y, float z, float w) {
        return new Vec4f(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    @NotNull
    public Vec4f mul(double a) {
        return mul((float) a);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4f times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec4f mul(float a) {
        return mul(a, a, a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec4f times(float a) {
        return mul(a);
    }

    @NotNull
    public Vec4f mul(@NotNull Vec4f v) {
        return mul(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4f times(@NotNull Vec4f v) {
        return mul(v);
    }

    @NotNull
    public Vec4f mul(double x, double y, double z, double w) {
        return mul((float) x, (float) y, (float) z, (float) w);
    }

    @NotNull
    public Vec4f mul(float x, float y, float z, float w) {
        return new Vec4f(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    @NotNull
    public Vec4f div(double a) {
        return div((float) a);
    }

    @NotNull
    @Override
    public Vec4f div(float a) {
        return div(a, a, a, a);
    }

    @NotNull
    public Vec4f div(@NotNull Vec4f v) {
        return div(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4f div(double x, double y, double z, double w) {
        return div((float) x, (float) y, (float) z, (float) w);
    }

    @NotNull
    public Vec4f div(float x, float y, float z, float w) {
        return new Vec4f(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    public float dot(@NotNull Vec4f v) {
        return dot(v.x, v.y, v.z, v.w);
    }

    public float dot(double x, double y, double z, double w) {
        return dot((float) x, (float) y, (float) z, (float) w);
    }

    public float dot(float x, float y, float z, float w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    @NotNull
    public Vec4f project(@NotNull Vec4f v) {
        return project(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4f project(double x, double y, double z, double w) {
        return project((float) x, (float) y, (float) z, (float) w);
    }

    @NotNull
    public Vec4f project(float x, float y, float z, float w) {
        final float lengthSquared = x * x + y * y + z * z + w * w;
        if (Math.abs(lengthSquared) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final float a = dot(x, y, z, w) / lengthSquared;
        return new Vec4f(a * x, a * y, a * z, a * w);
    }

    @NotNull
    public Vec4f pow(double pow) {
        return pow((float) pow);
    }

    @NotNull
    @Override
    public Vec4f pow(float power) {
        return new Vec4f(Math.pow(x, power), Math.pow(y, power), Math.pow(z, power), Math.pow(w, power));
    }

    @NotNull
    @Override
    public Vec4f ceil() {
        return new Vec4f(Math.ceil(x), Math.ceil(y), Math.ceil(z), Math.ceil(w));
    }

    @NotNull
    @Override
    public Vec4f floor() {
        return new Vec4f(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    @NotNull
    @Override
    public Vec4f round() {
        return new Vec4f(Math.round(x), Math.round(y), Math.round(z), Math.round(w));
    }

    @NotNull
    @Override
    public Vec4f abs() {
        return new Vec4f(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
    }

    @NotNull
    @Override
    public Vec4f negate() {
        return new Vec4f(-x, -y, -z, -w);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec4f unaryMinus() {
        return negate();
    }


    @NotNull
    public Vec4f min(@NotNull Vec4f v) {
        return min(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4f min(double x, double y, double z, double w) {
        return min((float) x, (float) y, (float) z, (float) w);
    }

    @NotNull
    public Vec4f min(float x, float y, float z, float w) {
        return new Vec4f(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
    }

    @NotNull
    public Vec4f max(@NotNull Vec4f v) {
        return max(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4f max(double x, double y, double z, double w) {
        return max((float) x, (float) y, (float) z, (float) w);
    }

    @NotNull
    public Vec4f max(float x, float y, float z, float w) {
        return new Vec4f(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
    }

    public float distanceSquared(@NotNull Vec4f v) {
        return distanceSquared(v.x, v.y, v.z, v.w);
    }

    public float distanceSquared(double x, double y, double z, double w) {
        return distanceSquared((float) x, (float) y, (float) z, (float) w);
    }

    public float distanceSquared(float x, float y, float z, float w) {
        final float dx = this.x - x;
        final float dy = this.y - y;
        final float dz = this.z - z;
        final float dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public float distance(@NotNull Vec4f v) {
        return distance(v.x, v.y, v.z, v.w);
    }

    public float distance(double x, double y, double z, double w) {
        return distance((float) x, (float) y, (float) z, (float) w);
    }

    public float distance(float x, float y, float z, float w) {
        return (float) Math.sqrt(distanceSquared(x, y, z, w));
    }

    @Override
    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    @Override
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    @NotNull
    @Override
    public Vec4f normalize() {
        final float length = length();
        if (Math.abs(length) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        return new Vec4f(x / length, y / length, z / length, w / length);
    }

    /**
     * Return the axis with the minimal value.
     *
     * @return {@link int} axis with minimal value
     */
    @Override
    public int getMinAxis() {
        float value = x;
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
        float value = x;
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
    public Vec2f toVec2() {
        return new Vec2f(this);
    }

    @NotNull
    public Vec3f toVec3() {
        return new Vec3f(this);
    }

    @NotNull
    public VecNf toVecN() {
        return new VecNf(this);
    }

    @NotNull
    @Override
    public float[] toArray() {
        return new float[]{x, y, z, w};
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
    public int compareTo(@NotNull Vec4f v) {
        return (int) Math.signum(lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec4f)) {
            return false;
        }
        final Vec4f Vec4 = (Vec4f) o;
        if (Float.compare(Vec4.w, w) != 0) {
            return false;
        }
        if (Float.compare(Vec4.x, x) != 0) {
            return false;
        }
        if (Float.compare(Vec4.y, y) != 0) {
            return false;
        }
        if (Float.compare(Vec4.z, z) != 0) {
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
    public Vec4f clone() {
        return new Vec4f(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

    @NotNull
    public static Vec4f from(float n) {
         return n == 0 ? ZERO : new Vec4f(n, n, n, n);
    }

    @NotNull
    public static Vec4f from(float x, float y, float z, float w) {
         return x == 0 && y == 0 && z == 0 && w == 0 ? ZERO : new Vec4f(x, y, z, w);
    }

    public static Vec4f createPooled(float x, float y, float z, float w) {
        return pool.getPooled(x, y, z, w);
    }
}
