package com.flowpowered.math.vector;

import java.io.Serializable;
import java.util.Arrays;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import org.jetbrains.annotations.NotNull;

public class VecNl implements Vectorl, VecN, Comparable<VecNl>, Serializable, Cloneable {
    @NotNull
    public static VecNl ZERO_2 = new ImmutableZeroVecN(0, 0);
    @NotNull
    public static VecNl ZERO_3 = new ImmutableZeroVecN(0, 0, 0);
    @NotNull
    public static VecNl ZERO_4 = new ImmutableZeroVecN(0, 0, 0, 0);
    private static final long serialVersionUID = 1;
    private final long[] vec;

    public VecNl(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum vector size is 2");
        }
        vec = new long[size];
    }

    public VecNl(@NotNull Vec2l v) {
        this(v.getX(), v.getY());
    }

    public VecNl(@NotNull Vec3l v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public VecNl(@NotNull Vec4l v) {
        this(v.getX(), v.getY(), v.getZ(), v.getW());
    }

    public VecNl(@NotNull VecNl v) {
        this(v.vec);
    }

    public VecNl(long... v) {
        vec = v.clone();
        AllocationTracker.instancesVecNl++;
        AllocationTracker.componentsVecNl += vec.length;
    }

    public int size() {
        return vec.length;
    }

    public long get(int comp) {
        return vec[comp];
    }

    public void set(int comp, long val) {
        vec[comp] = val;
    }

    public void setZero() {
        Arrays.fill(vec, 0);
    }

    @NotNull
    public VecNl resize(int size) {
        final VecNl d = new VecNl(size);
        System.arraycopy(vec, 0, d.vec, 0, Math.min(size, size()));
        return d;
    }

    @NotNull
    public VecNl add(@NotNull VecNl v) {
        return add(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNl plus(@NotNull VecNl v) {
        return add(v);
    }

    @NotNull
    public VecNl add(@NotNull long... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] + v[comp];
        }
        return d;
    }

    @NotNull
    public VecNl sub(@NotNull VecNl v) {
        return sub(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNl minus(@NotNull VecNl v) {
        return sub(v);
    }

    @NotNull
    public VecNl sub(@NotNull long... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] - v[comp];
        }
        return d;
    }

    @NotNull
    public VecNl mul(double a) {
        return mul(GenericMath.floorl(a));
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNl times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public VecNl mul(long a) {
        final int size = size();
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] * a;
        }
        return d;
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public VecNl times(long a) {
        return mul(a);
    }

    @NotNull
    public VecNl mul(@NotNull VecNl v) {
        return mul(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNl times(@NotNull VecNl v) {
        return mul(v);
    }

    @NotNull
    public VecNl mul(@NotNull long... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] * v[comp];
        }
        return d;
    }

    @NotNull
    public VecNl div(double a) {
        return div(GenericMath.floorl(a));
    }

    @NotNull
    @Override
    public VecNl div(long a) {
        final int size = size();
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] / a;
        }
        return d;
    }

    @NotNull
    public VecNl div(@NotNull VecNl v) {
        return div(v.vec);
    }

    @NotNull
    public VecNl div(@NotNull long... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] / v[comp];
        }
        return d;
    }

    public long dot(@NotNull VecNl v) {
        return dot(v.vec);
    }

    public long dot(@NotNull long... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        long d = 0;
        for (int comp = 0; comp < size; comp++) {
            d += vec[comp] * v[comp];
        }
        return d;
    }

    @NotNull
    public VecNl project(@NotNull VecNl v) {
        return project(v.vec);
    }

    @NotNull
    public VecNl project(@NotNull long... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        long lengthSquared = 0;
        for (int comp = 0; comp < size; comp++) {
            lengthSquared += v[comp] * v[comp];
        }
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final double a = (double) dot(v) / lengthSquared;
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = GenericMath.floorl(a * v[comp]);
        }
        return d;
    }

    @NotNull
    public VecNl pow(double pow) {
        return pow(GenericMath.floorl(pow));
    }

    @NotNull
    @Override
    public VecNl pow(long power) {
        final int size = size();
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = GenericMath.floorl(Math.pow(vec[comp], power));
        }
        return d;
    }

    @NotNull
    @Override
    public VecNl abs() {
        final int size = size();
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.abs(vec[comp]);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNl negate() {
        final int size = size();
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = -vec[comp];
        }
        return d;
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public VecNl unaryMinus() {
        return negate();
    }

    @NotNull
    public VecNl min(@NotNull VecNl v) {
        return min(v.vec);
    }

    @NotNull
    public VecNl min(@NotNull long... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.min(vec[comp], v[comp]);
        }
        return d;
    }

    @NotNull
    public VecNl max(@NotNull VecNl v) {
        return max(v.vec);
    }

    @NotNull
    public VecNl max(@NotNull long... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNl d = new VecNl(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.max(vec[comp], v[comp]);
        }
        return d;
    }

    public long distanceSquared(@NotNull VecNl v) {
        return distanceSquared(v.vec);
    }

    public long distanceSquared(@NotNull long... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        long d = 0;
        for (int comp = 0; comp < size; comp++) {
            final long delta = vec[comp] - v[comp];
            d += delta * delta;
        }
        return d;
    }

    public double distance(@NotNull VecNl v) {
        return distance(v.vec);
    }

    public double distance(long... v) {
        return (double) Math.sqrt(distanceSquared(v));
    }

    @Override
    public long lengthSquared() {
        final int size = size();
        long l = 0;
        for (int comp = 0; comp < size; comp++) {
            l += vec[comp] * vec[comp];
        }
        return l;
    }

    @Override
    public double length() {
        return (double) Math.sqrt(lengthSquared());
    }

    @Override
    public int getMinAxis() {
        int axis = 0;
        long value = vec[axis];
        final int size = size();
        for (int comp = 1; comp < size; comp++) {
            if (vec[comp] < value) {
                value = vec[comp];
                axis = comp;
            }
        }
        return axis;
    }

    @Override
    public int getMaxAxis() {
        int axis = 0;
        long value = vec[axis];
        final int size = size();
        for (int comp = 1; comp < size; comp++) {
            if (vec[comp] > value) {
                value = vec[comp];
                axis = comp;
            }
        }
        return axis;
    }

    @NotNull
    public Vec2l toVec2() {
        return new Vec2l(this);
    }

    @NotNull
    public Vec3l toVec3() {
        return new Vec3l(this);
    }

    @NotNull
    public Vec4l toVec4() {
        return new Vec4l(this);
    }

    @NotNull
    @Override
    public long[] toArray() {
        return vec.clone();
    }

    @NotNull
    @Override
    public VecNd toDouble() {
        final int size = size();
        final double[] doubleVec = new double[size];
        for (int comp = 0; comp < size; comp++) {
            doubleVec[comp] = (double) vec[comp];
        }
        return new VecNd(doubleVec);
    }

    @Override
    public double getNd(int i) {
        return (double) get(i);
    }

    @NotNull
    @Override
    public VecNf toFloat() {
        final int size = size();
        final float[] floatVec = new float[size];
        for (int comp = 0; comp < size; comp++) {
            floatVec[comp] = (float) vec[comp];
        }
        return new VecNf(floatVec);
    }

    @Override
    public float getNf(int i) {
        return (float) get(i);
    }

    @NotNull
    @Override
    public VecNl toLong() {
        return this;
    }

    @Override
    public long getNl(int i) {
        return (long) get(i);
    }

    @NotNull
    @Override
    public VecNi toInt() {
        final int size = size();
        final int[] intVec = new int[size];
        for (int comp = 0; comp < size; comp++) {
            intVec[comp] = GenericMath.floor(vec[comp]);
        }
        return new VecNi(intVec);
    }

    @Override
    public int getNi(int i) {
        return (int) get(i);
    }

    @NotNull
    @Override
    public Number getNn(int i) {
        return get(i);
    }

    @Override
    public int compareTo(@NotNull VecNl v) {
        return (int) (lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VecNl)) {
            return false;
        }
        return Arrays.equals(vec, ((VecNl) obj).vec);
    }

    @Override
    public int hashCode() {
        return 67 * 5 + Arrays.hashCode(vec);
    }

    @NotNull
    @Override
    public VecNl clone() {
        return new VecNl(this);
    }

    @NotNull
    @Override
    public String toString() {
        return Arrays.toString(vec).replace('[', '(').replace(']', ')');
    }

    private static class ImmutableZeroVecN extends VecNl {
        public ImmutableZeroVecN(long... v) {
            super(v);
        }

        @Override
        public void set(int comp, long val) {
            throw new UnsupportedOperationException("You may not alter this vector");
        }
    }
}
