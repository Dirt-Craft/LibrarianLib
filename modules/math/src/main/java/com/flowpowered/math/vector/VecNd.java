package com.flowpowered.math.vector;

import java.io.Serializable;
import java.util.Arrays;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import org.jetbrains.annotations.NotNull;

public class VecNd implements Vectord, VecN, Comparable<VecNd>, Serializable, Cloneable {
    @NotNull
    public static VecNd ZERO_2 = new ImmutableZeroVecN(0, 0);
    @NotNull
    public static VecNd ZERO_3 = new ImmutableZeroVecN(0, 0, 0);
    @NotNull
    public static VecNd ZERO_4 = new ImmutableZeroVecN(0, 0, 0, 0);
    private static final long serialVersionUID = 1;
    private final double[] vec;

    public VecNd(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum vector size is 2");
        }
        vec = new double[size];
    }

    public VecNd(@NotNull Vec2d v) {
        this(v.getX(), v.getY());
    }

    public VecNd(@NotNull Vec3d v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public VecNd(@NotNull Vec4d v) {
        this(v.getX(), v.getY(), v.getZ(), v.getW());
    }

    public VecNd(@NotNull VecNd v) {
        this(v.vec);
    }

    public VecNd(double... v) {
        vec = v.clone();
        AllocationTracker.instancesVecNd++;
        AllocationTracker.componentsVecNd += vec.length;
    }

    public int size() {
        return vec.length;
    }

    public double get(int comp) {
        return vec[comp];
    }

    public int getFloored(int comp) {
        return GenericMath.floor(get(comp));
    }

    public void set(int comp, float val) {
        set(comp, (double) val);
    }

    public void set(int comp, double val) {
        vec[comp] = val;
    }

    public void setZero() {
        Arrays.fill(vec, 0);
    }

    @NotNull
    public VecNd resize(int size) {
        final VecNd d = new VecNd(size);
        System.arraycopy(vec, 0, d.vec, 0, Math.min(size, size()));
        return d;
    }

    @NotNull
    public VecNd add(@NotNull VecNd v) {
        return add(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNd plus(@NotNull VecNd v) {
        return add(v);
    }

    @NotNull
    public VecNd add(@NotNull double... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] + v[comp];
        }
        return d;
    }

    @NotNull
    public VecNd sub(@NotNull VecNd v) {
        return sub(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNd minus(@NotNull VecNd v) {
        return sub(v);
    }

    @NotNull
    public VecNd sub(@NotNull double... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] - v[comp];
        }
        return d;
    }

    @NotNull
    public VecNd mul(float a) {
        return mul((double) a);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNd times(float a) {
        return mul(a);
    }

    @NotNull
    @Override
    public VecNd mul(double a) {
        final int size = size();
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] * a;
        }
        return d;
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public VecNd times(double a) {
        return mul(a);
    }

    @NotNull
    public VecNd mul(@NotNull VecNd v) {
        return mul(v.vec);
    }
    /** Operator function for Kotlin */
    @NotNull
    public VecNd times(@NotNull VecNd v) {
        return mul(v);
    }

    @NotNull
    public VecNd mul(@NotNull double... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] * v[comp];
        }
        return d;
    }

    @NotNull
    public VecNd div(float a) {
        return div((double) a);
    }

    @NotNull
    @Override
    public VecNd div(double a) {
        final int size = size();
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] / a;
        }
        return d;
    }

    @NotNull
    public VecNd div(@NotNull VecNd v) {
        return div(v.vec);
    }

    @NotNull
    public VecNd div(@NotNull double... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = vec[comp] / v[comp];
        }
        return d;
    }

    public double dot(@NotNull VecNd v) {
        return dot(v.vec);
    }

    public double dot(@NotNull double... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        double d = 0;
        for (int comp = 0; comp < size; comp++) {
            d += vec[comp] * v[comp];
        }
        return d;
    }

    @NotNull
    public VecNd project(@NotNull VecNd v) {
        return project(v.vec);
    }

    @NotNull
    public VecNd project(@NotNull double... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        double lengthSquared = 0;
        for (int comp = 0; comp < size; comp++) {
            lengthSquared += v[comp] * v[comp];
        }
        if (Math.abs(lengthSquared) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        final double a = dot(v) / lengthSquared;
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = a * v[comp];
        }
        return d;
    }

    @NotNull
    public VecNd pow(float pow) {
        return pow((double) pow);
    }

    @NotNull
    @Override
    public VecNd pow(double power) {
        final int size = size();
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = (double) Math.pow(vec[comp], power);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNd ceil() {
        final int size = size();
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = (double) Math.ceil(vec[comp]);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNd floor() {
        final int size = size();
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = GenericMath.floor(vec[comp]);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNd round() {
        final int size = size();
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.round(vec[comp]);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNd abs() {
        final int size = size();
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.abs(vec[comp]);
        }
        return d;
    }

    @NotNull
    @Override
    public VecNd negate() {
        final int size = size();
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = -vec[comp];
        }
        return d;
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public VecNd unaryMinus() {
        return negate();
    }

    @NotNull
    public VecNd min(@NotNull VecNd v) {
        return min(v.vec);
    }

    @NotNull
    public VecNd min(@NotNull double... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.min(vec[comp], v[comp]);
        }
        return d;
    }

    @NotNull
    public VecNd max(@NotNull VecNd v) {
        return max(v.vec);
    }

    @NotNull
    public VecNd max(@NotNull double... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = Math.max(vec[comp], v[comp]);
        }
        return d;
    }

    public double distanceSquared(@NotNull VecNd v) {
        return distanceSquared(v.vec);
    }

    public double distanceSquared(@NotNull double... v) {
        final int size = size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        double d = 0;
        for (int comp = 0; comp < size; comp++) {
            final double delta = vec[comp] - v[comp];
            d += delta * delta;
        }
        return d;
    }

    public double distance(@NotNull VecNd v) {
        return distance(v.vec);
    }

    public double distance(double... v) {
        return (double) Math.sqrt(distanceSquared(v));
    }

    @Override
    public double lengthSquared() {
        final int size = size();
        double l = 0;
        for (int comp = 0; comp < size; comp++) {
            l += vec[comp] * vec[comp];
        }
        return l;
    }

    @Override
    public double length() {
        return (double) Math.sqrt(lengthSquared());
    }

    @NotNull
    @Override
    public VecNd normalize() {
        final double length = length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        final int size = size();
        final VecNd d = new VecNd(size);
        for (int comp = 0; comp < size; comp++) {
            d.vec[comp] = (double) (vec[comp] / length);
        }
        return d;
    }

    @Override
    public int getMinAxis() {
        int axis = 0;
        double value = vec[axis];
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
        double value = vec[axis];
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
    public Vec2d toVec2() {
        return new Vec2d(this);
    }

    @NotNull
    public Vec3d toVec3() {
        return new Vec3d(this);
    }

    @NotNull
    public Vec4d toVec4() {
        return new Vec4d(this);
    }

    @NotNull
    @Override
    public double[] toArray() {
        return vec.clone();
    }

    @NotNull
    @Override
    public VecNd toDouble() {
        return this;
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
    public int compareTo(@NotNull VecNd v) {
        return (int) Math.signum(lengthSquared() - v.lengthSquared());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VecNd)) {
            return false;
        }
        return Arrays.equals(vec, ((VecNd) obj).vec);
    }

    @Override
    public int hashCode() {
        return 67 * 5 + Arrays.hashCode(vec);
    }

    @NotNull
    @Override
    public VecNd clone() {
        return new VecNd(this);
    }

    @NotNull
    @Override
    public String toString() {
        return Arrays.toString(vec).replace('[', '(').replace(']', ')');
    }

    private static class ImmutableZeroVecN extends VecNd {
        public ImmutableZeroVecN(double... v) {
            super(v);
        }

        @Override
        public void set(int comp, double val) {
            throw new UnsupportedOperationException("You may not alter this vector");
        }
    }
}
