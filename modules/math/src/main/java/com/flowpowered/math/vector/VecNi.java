package com.flowpowered.math.vector;

import java.io.Serializable;
import java.util.Arrays;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import org.jetbrains.annotations.NotNull;

public class VecNi implements Vectori, VecN, Comparable<VecNi>, Serializable, Cloneable {
    @NotNull
    public static VecNi ZERO_2 = new ImmutableZeroVecN(0, 0);
    @NotNull
    public static VecNi ZERO_3 = new ImmutableZeroVecN(0, 0, 0);
    @NotNull
    public static VecNi ZERO_4 = new ImmutableZeroVecN(0, 0, 0, 0);
    private static final long serialVersionUID = 1;
    private final int[] vec;

    public VecNi(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum vector size is 2");
        }
        vec = new int[size];
    }

    public VecNi(@NotNull Vec2i v) {
        this(v.getX(), v.getY());
    }

    public VecNi(@NotNull Vec3i v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public VecNi(@NotNull Vec4i v) {
        this(v.getX(), v.getY(), v.getZ(), v.getW());
    }

    public VecNi(@NotNull VecNi v) {
        this(v.vec);
    }

    public VecNi(int... v) {
        vec = v.clone();
        AllocationTracker.instancesVecNi++;
        AllocationTracker.componentsVecNi += vec.length;
    }

    public int size() {
        return vec.length;
    }

    public int get(int comp) {
        return vec[comp];
    }

    public void set(int comp, int val) {
        vec[comp] = val;
    }

    public void setZero() {
        Arrays.fill(vec, 0);
    }

    @NotNull
    public VecNi resize(int size) {
        final VecNi d = new VecNi(size);
        System.arraycopy(vec, 0, d.vec, 0, Math.min(size, size()));
        return d;
    }

    @NotNull
    public VecNi add(@NotNull VecNi v) {
        return add(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNi plus(@NotNull VecNi v) {
        return add(v);
    }

    @NotNull
    public VecNi add(@NotNull int... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] + v[comp];
        }
        return d;
    }

    @NotNull
    public VecNi sub(@NotNull VecNi v) {
        return sub(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNi minus(@NotNull VecNi v) {
        return sub(v);
    }

    @NotNull
    public VecNi sub(@NotNull int... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] - v[comp];
        }
        return d;
    }

    @NotNull
    public VecNi mul(double a) {
        return mul(GenericMath.floor(a));
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNi times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public VecNi mul(int a) {
        final int size = size();
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] * a;
        }
        return d;
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public VecNi times(int a) {
        return mul(a);
    }

    @NotNull
    public VecNi mul(@NotNull VecNi v) {
        return mul(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNi times(@NotNull VecNi v) {
        return mul(v);
    }

    @NotNull
    public VecNi mul(@NotNull int... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] * v[comp];
        }
        return d;
    }

    @NotNull
    public VecNi div(double a) {
        return div(GenericMath.floor(a));
    }

    @NotNull
    @Override
    public VecNi div(int a) {
        final int size = size();
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] / a;
        }
        return d;
    }

    @NotNull
    public VecNi div(@NotNull VecNi v) {
        return div(v.vec);
    }

    @NotNull
    public VecNi div(@NotNull int... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] / v[comp];
        }
        return d;
    }

    public int dot(@NotNull VecNi v) {
        return dot(v.vec);
    }

    public int dot(@NotNull int... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        int d = 0;
        for (int comp = 0; comp < size; comp++) {
            d += vec[comp] * v[comp];
        }
        return d;
    }

    @NotNull
    public VecNi project(@NotNull VecNi v) {
        return project(v.vec);
    }

    @NotNull
    public VecNi project(@NotNull int... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        int lengthSquared = 0;
        for (int comp = 0; comp < size; comp++) {
            lengthSquared += v[comp] * v[comp];
        }
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final float a = (float) dot(v) / lengthSquared;
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = GenericMath.floor(a * v[comp]);
        }
        return d;
    }

    @NotNull
    public VecNi pow(double pow) {
        return pow(GenericMath.floor(pow));
    }

    @NotNull
    @Override
    public VecNi pow(int power) {
        final int size = size();
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = GenericMath.floor(Math.pow(vec[comp], power));
        }
        return d;
    }

    @NotNull
    @Override
    public VecNi abs() {
        final int size = size();
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.abs(vec[comp]);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNi negate() {
        final int size = size();
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = -vec[comp];
        }
        return d;
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public VecNi unaryMinus() {
        return negate();
    }

    @NotNull
    public VecNi min(@NotNull VecNi v) {
        return min(v.vec);
    }

    @NotNull
    public VecNi min(@NotNull int... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.min(vec[comp], v[comp]);
        }
        return d;
    }

    @NotNull
    public VecNi max(@NotNull VecNi v) {
        return max(v.vec);
    }

    @NotNull
    public VecNi max(@NotNull int... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNi d = new VecNi(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.max(vec[comp], v[comp]);
        }
        return d;
    }

    public int distanceSquared(@NotNull VecNi v) {
        return distanceSquared(v.vec);
    }

    public int distanceSquared(@NotNull int... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        int d = 0;
        for (int comp = 0; comp < size; comp++) {
            final int delta = vec[comp] - v[comp];
            d += delta * delta;
        }
        return d;
    }

    public float distance(@NotNull VecNi v) {
        return distance(v.vec);
    }

    public float distance(int... v) {
        return (float) Math.sqrt(distanceSquared(v));
    }

    @Override
    public int lengthSquared() {
        final int size = size();
        int l = 0;
        for (int comp = 0; comp < size; comp++) {
            l += vec[comp] * vec[comp];
        }
        return l;
    }

    @Override
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    @Override
    public int getMinAxis() {
        int axis = 0;
        int value = vec[axis];
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
        int value = vec[axis];
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
    public Vec2i toVec2() {
        return new Vec2i(this);
    }

    @NotNull
    public Vec3i toVec3() {
        return new Vec3i(this);
    }

    @NotNull
    public Vec4i toVec4() {
        return new Vec4i(this);
    }

    @NotNull
    @Override
    public int[] toArray() {
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
        final int size = size();
        final long[] longVec = new long[size];
        for (int comp = 0; comp < size; comp++) {
            longVec[comp] = GenericMath.floorl(vec[comp]);
        }
        return new VecNl(longVec);
    }

    @Override
    public long getNl(int i) {
        return (long) get(i);
    }

    @NotNull
    @Override
    public VecNi toInt() {
        return this;
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
    public int compareTo(@NotNull VecNi v) {
        return (int) (lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VecNi)) {
            return false;
        }
        return Arrays.equals(vec, ((VecNi) obj).vec);
    }

    @Override
    public int hashCode() {
        return 67 * 5 + Arrays.hashCode(vec);
    }

    @NotNull
    @Override
    public VecNi clone() {
        return new VecNi(this);
    }

    @NotNull
    @Override
    public String toString() {
        return Arrays.toString(vec).replace('[', '(').replace(']', ')');
    }

    private static class ImmutableZeroVecN extends VecNi {
        public ImmutableZeroVecN(int... v) {
            super(v);
        }

        @Override
        public void set(int comp, int val) {
            throw new UnsupportedOperationException("You may not alter this vector");
        }
    }
}
