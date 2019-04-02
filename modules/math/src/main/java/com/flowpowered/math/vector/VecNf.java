package com.flowpowered.math.vector;

import java.io.Serializable;
import java.util.Arrays;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import org.jetbrains.annotations.NotNull;

public class VecNf implements Vectorf, VecN, Comparable<VecNf>, Serializable, Cloneable {
    @NotNull
    public static VecNf ZERO_2 = new ImmutableZeroVecN(0, 0);
    @NotNull
    public static VecNf ZERO_3 = new ImmutableZeroVecN(0, 0, 0);
    @NotNull
    public static VecNf ZERO_4 = new ImmutableZeroVecN(0, 0, 0, 0);
    private static final long serialVersionUID = 1;
    private final float[] vec;

    public VecNf(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum vector size is 2");
        }
        vec = new float[size];
    }

    public VecNf(@NotNull Vec2f v) {
        this(v.getX(), v.getY());
    }

    public VecNf(@NotNull Vec3f v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public VecNf(@NotNull Vec4f v) {
        this(v.getX(), v.getY(), v.getZ(), v.getW());
    }

    public VecNf(@NotNull VecNf v) {
        this(v.vec);
    }

    public VecNf(float... v) {
        vec = v.clone();
        AllocationTracker.instancesVecNf++;
        AllocationTracker.componentsVecNf += vec.length;
    }

    public int size() {
        return vec.length;
    }

    public float get(int comp) {
        return vec[comp];
    }

    public int getFloored(int comp) {
        return GenericMath.floor(get(comp));
    }

    public void set(int comp, double val) {
        set(comp, (float) val);
    }

    public void set(int comp, float val) {
        vec[comp] = val;
    }

    public void setZero() {
        Arrays.fill(vec, 0);
    }

    @NotNull
    public VecNf resize(int size) {
        final VecNf d = new VecNf(size);
        System.arraycopy(vec, 0, d.vec, 0, Math.min(size, size()));
        return d;
    }

    @NotNull
    public VecNf add(@NotNull VecNf v) {
        return add(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNf plus(@NotNull VecNf v) {
        return add(v);
    }

    @NotNull
    public VecNf add(@NotNull float... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] + v[comp];
        }
        return d;
    }

    @NotNull
    public VecNf sub(@NotNull VecNf v) {
        return sub(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNf minus(@NotNull VecNf v) {
        return sub(v);
    }

    @NotNull
    public VecNf sub(@NotNull float... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] - v[comp];
        }
        return d;
    }

    @NotNull
    public VecNf mul(double a) {
        return mul((float) a);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNf times(double a) {
        return mul(a);
    }

    @NotNull
    @Override
    public VecNf mul(float a) {
        final int size = size();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] * a;
        }
        return d;
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public VecNf times(float a) {
        return mul(a);
    }

    @NotNull
    public VecNf mul(@NotNull VecNf v) {
        return mul(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNf times(@NotNull VecNf v) {
        return mul(v);
    }

    @NotNull
    public VecNf mul(@NotNull float... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] * v[comp];
        }
        return d;
    }

    @NotNull
    public VecNf div(double a) {
        return div((float) a);
    }

    @NotNull
    @Override
    public VecNf div(float a) {
        final int size = size();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] / a;
        }
        return d;
    }

    @NotNull
    public VecNf div(@NotNull VecNf v) {
        return div(v.vec);
    }

    @NotNull
    public VecNf div(@NotNull float... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] / v[comp];
        }
        return d;
    }

    public float dot(@NotNull VecNf v) {
        return dot(v.vec);
    }

    public float dot(@NotNull float... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        float d = 0;
        for (int comp = 0; comp < size; comp++) {
            d += vec[comp] * v[comp];
        }
        return d;
    }

    @NotNull
    public VecNf project(@NotNull VecNf v) {
        return project(v.vec);
    }

    @NotNull
    public VecNf project(@NotNull float... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        float lengthSquared = 0;
        for (int comp = 0; comp < size; comp++) {
            lengthSquared += v[comp] * v[comp];
        }
        if (Math.abs(lengthSquared) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final float a = dot(v) / lengthSquared;
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = a * v[comp];
        }
        return d;
    }

    @NotNull
    public VecNf pow(double pow) {
        return pow((float) pow);
    }

    @NotNull
    @Override
    public VecNf pow(float power) {
        final int size = size();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = (float) Math.pow(vec[comp], power);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNf ceil() {
        final int size = size();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = (float) Math.ceil(vec[comp]);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNf floor() {
        final int size = size();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = GenericMath.floor(vec[comp]);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNf round() {
        final int size = size();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.round(vec[comp]);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNf abs() {
        final int size = size();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.abs(vec[comp]);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNf negate() {
        final int size = size();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = -vec[comp];
        }
        return d;
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public VecNf unaryMinus() {
        return negate();
    }

    @NotNull
    public VecNf min(@NotNull VecNf v) {
        return min(v.vec);
    }

    @NotNull
    public VecNf min(@NotNull float... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.min(vec[comp], v[comp]);
        }
        return d;
    }

    @NotNull
    public VecNf max(@NotNull VecNf v) {
        return max(v.vec);
    }

    @NotNull
    public VecNf max(@NotNull float... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.max(vec[comp], v[comp]);
        }
        return d;
    }

    public float distanceSquared(@NotNull VecNf v) {
        return distanceSquared(v.vec);
    }

    public float distanceSquared(@NotNull float... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        float d = 0;
        for (int comp = 0; comp < size; comp++) {
            final float delta = vec[comp] - v[comp];
            d += delta * delta;
        }
        return d;
    }

    public float distance(@NotNull VecNf v) {
        return distance(v.vec);
    }

    public float distance(float... v) {
        return (float) Math.sqrt(distanceSquared(v));
    }

    @Override
    public float lengthSquared() {
        final int size = size();
        float l = 0;
        for (int comp = 0; comp < size; comp++) {
            l += vec[comp] * vec[comp];
        }
        return l;
    }

    @Override
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    @NotNull
    @Override
    public VecNf normalize() {
        final float length = length();
        if (Math.abs(length) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        final int size = size();
        final VecNf d = new VecNf(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = (float) (vec[comp] / length);
        }
        return d;
    }

    @Override
    public int getMinAxis() {
        int axis = 0;
        float value = vec[axis];
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
        float value = vec[axis];
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
    public Vec2f toVec2() {
        return new Vec2f(this);
    }

    @NotNull
    public Vec3f toVec3() {
        return new Vec3f(this);
    }

    @NotNull
    public Vec4f toVec4() {
        return new Vec4f(this);
    }

    @NotNull
    @Override
    public float[] toArray() {
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
        return this;
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
    public int compareTo(@NotNull VecNf v) {
        return (int) Math.signum(lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VecNf)) {
            return false;
        }
        return Arrays.equals(vec, ((VecNf) obj).vec);
    }

    @Override
    public int hashCode() {
        return 67 * 5 + Arrays.hashCode(vec);
    }

    @NotNull
    @Override
    public VecNf clone() {
        return new VecNf(this);
    }

    @NotNull
    @Override
    public String toString() {
        return Arrays.toString(vec).replace('[', '(').replace(']', ')');
    }

    private static class ImmutableZeroVecN extends VecNf {
        public ImmutableZeroVecN(float... v) {
            super(v);
        }

        @Override
        public void set(int comp, float val) {
            throw new UnsupportedOperationException("You may not alter this vector");
        }
    }
}
