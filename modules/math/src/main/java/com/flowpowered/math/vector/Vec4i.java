package com.flowpowered.math.vector;

import java.io.Serializable;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import org.jetbrains.annotations.NotNull;

public class Vec4i implements Vectori, Vec4, Comparable<Vec4i>, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private static final FlowPool<Vec4i> pool = new FlowPool<Vec4i>(4, FlowPool.BITS_4D) {
        @NotNull
        protected Vec4i hit(Vec4i value) {
            AllocationTracker.cacheHitsVec4i++;
            return value;
        }

        @NotNull
        protected Vec4i create(int x, int y, int z, int w) {
            return new Vec4i(x, y, z, w);
        }
    };

    public static final Vec4i ZERO = new Vec4i(0, 0, 0, 0);
    public static final Vec4i UNIT_X = new Vec4i(1, 0, 0, 0);
    public static final Vec4i UNIT_Y = new Vec4i(0, 1, 0, 0);
    public static final Vec4i UNIT_Z = new Vec4i(0, 0, 1, 0);
    public static final Vec4i UNIT_W = new Vec4i(0, 0, 0, 1);
    public static final Vec4i ONE = new Vec4i(1, 1, 1, 1);
    private final int x;
    private final int y;
    private final int z;
    private final int w;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Vec4i() {
        this(0, 0, 0, 0);
    }

    public Vec4i(@NotNull Vec2i v) {
        this(v, 0, 0);
    }

    public Vec4i(@NotNull Vec2i v, double z, double w) {
        this(v, GenericMath.floor(z), GenericMath.floor(w));
    }

    public Vec4i(@NotNull Vec2i v, int z, int w) {
        this(v.getX(), v.getY(), z, w);
    }

    public Vec4i(@NotNull Vec3i v) {
        this(v, 0);
    }

    public Vec4i(@NotNull Vec3i v, double w) {
        this(v, GenericMath.floor(w));
    }

    public Vec4i(@NotNull Vec3i v, int w) {
        this(v.getX(), v.getY(), v.getZ(), w);
    }

    public Vec4i(@NotNull Vec4i v) {
        this(v.x, v.y, v.z, v.w);
    }

    public Vec4i(@NotNull VecNi v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0, v.size() > 3 ? v.get(3) : 0);
    }

    public Vec4i(double x, double y, double z, double w) {
        this(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public Vec4i(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        AllocationTracker.instancesVec4i++;
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

    public int getW() {
        return w;
    }
    /** Operator function for Kotlin */
    public int component4() {
        return getW();
    }

    @NotNull
    public Vec4i add(@NotNull Vec4i v) {
        return add(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4i plus(@NotNull Vec4i v) {
        return add(v);
    }

    @NotNull
    public Vec4i add(double x, double y, double z, double w) {
        return add(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    @NotNull
    public Vec4i add(int x, int y, int z, int w) {
        return new Vec4i(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    @NotNull
    public Vec4i sub(@NotNull Vec4i v) {
        return sub(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4i minus(@NotNull Vec4i v) {
        return sub(v);
    }

    @NotNull
    public Vec4i sub(double x, double y, double z, double w) {
        return sub(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    @NotNull
    public Vec4i sub(int x, int y, int z, int w) {
        return new Vec4i(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    @NotNull
    public Vec4i mul(double a) {
        return mul(GenericMath.floor(a));
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4i times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Vec4i mul(int a) {
        return mul(a, a, a, a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec4i times(int a) {
        return mul(a);
    }

    @NotNull
    public Vec4i mul(@NotNull Vec4i v) {
        return mul(v.x, v.y, v.z, v.w);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Vec4i times(@NotNull Vec4i v) {
        return mul(v);
    }

    @NotNull
    public Vec4i mul(double x, double y, double z, double w) {
        return mul(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    @NotNull
    public Vec4i mul(int x, int y, int z, int w) {
        return new Vec4i(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    @NotNull
    public Vec4i div(double a) {
        return div(GenericMath.floor(a));
    }

    @NotNull
    @Override
    public Vec4i div(int a) {
        return div(a, a, a, a);
    }

    @NotNull
    public Vec4i div(@NotNull Vec4i v) {
        return div(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4i div(double x, double y, double z, double w) {
        return div(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    @NotNull
    public Vec4i div(int x, int y, int z, int w) {
        return new Vec4i(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    public int dot(@NotNull Vec4i v) {
        return dot(v.x, v.y, v.z, v.w);
    }

    public int dot(double x, double y, double z, double w) {
        return dot(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public int dot(int x, int y, int z, int w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    @NotNull
    public Vec4i project(@NotNull Vec4i v) {
        return project(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4i project(double x, double y, double z, double w) {
        return project(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    @NotNull
    public Vec4i project(int x, int y, int z, int w) {
        final int lengthSquared = x * x + y * y + z * z + w * w;
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final float a = (float) dot(x, y, z, w) / lengthSquared;
        return new Vec4i(a * x, a * y, a * z, a * w);
    }

    @NotNull
    public Vec4i pow(double pow) {
        return pow(GenericMath.floor(pow));
    }

    @NotNull
    @Override
    public Vec4i pow(int power) {
        return new Vec4i(Math.pow(x, power), Math.pow(y, power), Math.pow(z, power), Math.pow(w, power));
    }

    @NotNull
    @Override
    public Vec4i abs() {
        return new Vec4i(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
    }

    @NotNull
    @Override
    public Vec4i negate() {
        return new Vec4i(-x, -y, -z, -w);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Vec4i unaryMinus() {
        return negate();
    }

    @NotNull
    public Vec4i min(@NotNull Vec4i v) {
        return min(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4i min(double x, double y, double z, double w) {
        return min(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    @NotNull
    public Vec4i min(int x, int y, int z, int w) {
        return new Vec4i(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
    }

    @NotNull
    public Vec4i max(@NotNull Vec4i v) {
        return max(v.x, v.y, v.z, v.w);
    }

    @NotNull
    public Vec4i max(double x, double y, double z, double w) {
        return max(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    @NotNull
    public Vec4i max(int x, int y, int z, int w) {
        return new Vec4i(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
    }

    public int distanceSquared(@NotNull Vec4i v) {
        return distanceSquared(v.x, v.y, v.z, v.w);
    }

    public int distanceSquared(double x, double y, double z, double w) {
        return distanceSquared(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public int distanceSquared(int x, int y, int z, int w) {
        final int dx = this.x - x;
        final int dy = this.y - y;
        final int dz = this.z - z;
        final int dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public float distance(@NotNull Vec4i v) {
        return distance(v.x, v.y, v.z, v.w);
    }

    public float distance(double x, double y, double z, double w) {
        return distance(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public float distance(int x, int y, int z, int w) {
        return (float) Math.sqrt(distanceSquared(x, y, z, w));
    }

    @Override
    public int lengthSquared() {
        return x * x + y * y + z * z + w * w;
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
        int value = x;
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
        int value = x;
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
    public Vec2i toVec2() {
        return new Vec2i(this);
    }

    @NotNull
    public Vec3i toVec3() {
        return new Vec3i(this);
    }

    @NotNull
    public VecNi toVecN() {
        return new VecNi(this);
    }

    @NotNull
    @Override
    public int[] toArray() {
        return new int[]{x, y, z, w};
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
    public int compareTo(@NotNull Vec4i v) {
        return (int) (lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec4i)) {
            return false;
        }
        final Vec4i Vec4 = (Vec4i) o;
        if (Vec4.x != x) {
            return false;
        }
        if (Vec4.y != y) {
            return false;
        }
        if (Vec4.z != z) {
            return false;
        }
        if (Vec4.w != w) {
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
    public Vec4i clone() {
        return new Vec4i(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

    @NotNull
    public static Vec4i from(int n) {
         return n == 0 ? ZERO : new Vec4i(n, n, n, n);
    }

    @NotNull
    public static Vec4i from(int x, int y, int z, int w) {
         return x == 0 && y == 0 && z == 0 && w == 0 ? ZERO : new Vec4i(x, y, z, w);
    }

    public static Vec4i createPooled(int x, int y, int z, int w) {
        return pool.getPooled(x, y, z, w);
    }
}
