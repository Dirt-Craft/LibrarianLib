package com.flowpowered.math.matrix;

import java.io.Serializable;
import java.lang.Override;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import com.flowpowered.math.imaginary.Complexd;
import com.flowpowered.math.vector.Vec2d;
import org.jetbrains.annotations.NotNull;

public class Matrix2d implements Matrixd, Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    public static final Matrix2d ZERO = new Matrix2d(
            0, 0,
            0, 0);
    public static final Matrix2d IDENTITY = new Matrix2d();
    private final double m00, m01;
    private final double m10, m11;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Matrix2d() {
        this(
                1, 0,
                0, 1);
    }

    public Matrix2d(@NotNull Matrix2d m) {
        this(
                m.m00, m.m01,
                m.m10, m.m11);
    }

    public Matrix2d(@NotNull Matrix3d m) {
        this(
                m.get(0, 0), m.get(0, 1),
                m.get(1, 0), m.get(1, 1));
    }

    public Matrix2d(@NotNull Matrix4d m) {
        this(
                m.get(0, 0), m.get(0, 1),
                m.get(1, 0), m.get(1, 1));
    }

    public Matrix2d(@NotNull MatrixNd m) {
        this(
                m.get(0, 0), m.get(0, 1),
                m.get(1, 0), m.get(1, 1));
    }

    public Matrix2d(
            float m00, float m01,
            float m10, float m11) {
        this(
                (double) m00, (double) m01,
                (double) m10, (double) m11);
    }

    public Matrix2d(
            double m00, double m01,
            double m10, double m11) {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
        AllocationTracker.instancesMatrix2d++;
    }

    @Override
    public double get(int row, int col) {
        switch (row) {
        case 0:
            switch (col) {
            case 0:
                return m00;
            case 1:
                return m01;
            }
        case 1:
            switch (col) {
            case 0:
                return m10;
            case 1:
                return m11;
            }
        }
        throw new IllegalArgumentException(
                (row < 0 || row > 1 ? "row must be greater than zero and smaller than 2. " : "") +
                (col < 0 || col > 1 ? "col must be greater than zero and smaller than 2." : ""));
    }

    @NotNull
    @Override
    public Vec2d getRow(int row) {
        return new Vec2d(get(row, 0), get(row, 1));
    }

    @NotNull
    @Override
    public Vec2d getColumn(int col) {
        return new Vec2d(get(0, col), get(1, col));
    }

    @NotNull
    public Matrix2d add(@NotNull Matrix2d m) {
        return new Matrix2d(
                m00 + m.m00, m01 + m.m01,
                m10 + m.m10, m11 + m.m11);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Matrix2d plus(@NotNull Matrix2d m) {
        return add(m);
    }

    @NotNull
    public Matrix2d sub(@NotNull Matrix2d m) {
        return new Matrix2d(
                m00 - m.m00, m01 - m.m01,
                m10 - m.m10, m11 - m.m11);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Matrix2d minus(@NotNull Matrix2d m) {
        return sub(m);
    }

    @NotNull
    public Matrix2d mul(float a) {
        return mul((double) a);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Matrix2d times(float a) {
        return mul(a);
    }

    @NotNull
    @Override
    public Matrix2d mul(double a) {
        return new Matrix2d(
                m00 * a, m01 * a,
                m10 * a, m11 * a);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Matrix2d times(double a) {
        return mul(a);
    }

    @NotNull
    public Matrix2d mul(@NotNull Matrix2d m) {
        return new Matrix2d(
                m00 * m.m00 + m01 * m.m10, m00 * m.m01 + m01 * m.m11,
                m10 * m.m00 + m11 * m.m10, m10 * m.m01 + m11 * m.m11);
    }
    /** Operator function for Kotlin */
    @NotNull
    public Matrix2d times(@NotNull Matrix2d m) {
        return mul(m);
    }

    @NotNull
    public Matrix2d div(float a) {
        return div((double) a);
    }

    @NotNull
    @Override
    public Matrix2d div(double a) {
        return new Matrix2d(
                m00 / a, m01 / a,
                m10 / a, m11 / a);
    }

    @NotNull
    public Matrix2d div(@NotNull Matrix2d m) {
        return mul(m.invert());
    }

    @NotNull
    public Matrix2d pow(float pow) {
        return pow((double) pow);
    }

    @NotNull
    @Override
    public Matrix2d pow(double pow) {
        return new Matrix2d(
                Math.pow(m00, pow), Math.pow(m01, pow),
                Math.pow(m10, pow), Math.pow(m11, pow));
    }

    @NotNull
    public Matrix2d translate(float x) {
        return translate((double) x);
    }

    @NotNull
    public Matrix2d translate(double x) {
        return createTranslation(x).mul(this);
    }

    @NotNull
    public Matrix2d scale(float scale) {
        return scale((double) scale);
    }

    @NotNull
    public Matrix2d scale(double scale) {
        return scale(scale, scale);
    }

    @NotNull
    public Matrix2d scale(@NotNull Vec2d v) {
        return scale(v.getX(), v.getY());
    }

    @NotNull
    public Matrix2d scale(float x, float y) {
        return scale((double) x, (double) y);
    }

    @NotNull
    public Matrix2d scale(double x, double y) {
        return createScaling(x, y).mul(this);
    }

    @NotNull
    public Matrix2d rotate(@NotNull Complexd rot) {
        return createRotation(rot).mul(this);
    }

    @NotNull
    public Vec2d transform(@NotNull Vec2d v) {
        return transform(v.getX(), v.getY());
    }

    @NotNull
    public Vec2d transform(float x, float y) {
        return transform((double) x, (double) y);
    }

    @NotNull
    public Vec2d transform(double x, double y) {
        return new Vec2d(
                m00 * x + m01 * y,
                m10 * x + m11 * y);
    }

    @NotNull
    @Override
    public Matrix2d floor() {
        return new Matrix2d(
                GenericMath.floor(m00), GenericMath.floor(m01),
                GenericMath.floor(m10), GenericMath.floor(m11));
    }

    @NotNull
    @Override
    public Matrix2d ceil() {
        return new Matrix2d(
                Math.ceil(m00), Math.ceil(m01),
                Math.ceil(m10), Math.ceil(m11));
    }

    @NotNull
    @Override
    public Matrix2d round() {
        return new Matrix2d(
                Math.round(m00), Math.round(m01),
                Math.round(m10), Math.round(m11));
    }

    @NotNull
    @Override
    public Matrix2d abs() {
        return new Matrix2d(
                Math.abs(m00), Math.abs(m01),
                Math.abs(m10), Math.abs(m11));
    }

    @NotNull
    @Override
    public Matrix2d negate() {
        return new Matrix2d(
                -m00, -m01,
                -m10, -m11);
    }
    /** Operator function for Kotlin */
    @NotNull
    @Override
    public Matrix2d unaryMinus() {
        return negate();
    }

    @NotNull
    @Override
    public Matrix2d transpose() {
        return new Matrix2d(
                m00, m10,
                m01, m11);
    }

    @Override
    public double trace() {
        return m00 + m11;
    }

    @Override
    public double determinant() {
        return m00 * m11 - m01 * m10;
    }

    @NotNull
    @Override
    public Matrix2d invert() {
        final double det = determinant();
        if (Math.abs(det) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot inverse a matrix with a zero determinant");
        }
        return new Matrix2d(
                m11 / det, -m01 / det,
                -m10 / det, m00 / det);
    }

    @NotNull
    public Matrix3d toMatrix3() {
        return new Matrix3d(this);
    }

    @NotNull
    public Matrix4d toMatrix4() {
        return new Matrix4d(this);
    }

    @NotNull
    public MatrixNd toMatrixN() {
        return new MatrixNd(this);
    }

    @NotNull
    public double[] toArray() {
        return toArray(false);
    }

    @NotNull
    @Override
    public double[] toArray(boolean columnMajor) {
        if (columnMajor) {
            return new double[]{
                    m00, m10,
                    m01, m11
            };
        } else {
            return new double[]{
                    m00, m01,
                    m10, m11
            };
        }
    }

    @NotNull
    @Override
    public Matrix2f toFloat() {
        return new Matrix2f(m00, m01, m10, m11);
    }

    @NotNull
    @Override
    public Matrix2d toDouble() {
        return new Matrix2d(m00, m01, m10, m11);
    }

    @NotNull
    @Override
    public String toString() {
        return m00 + " " + m01 + "\n" + m10 + " " + m11;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matrix2d)) {
            return false;
        }
        final Matrix2d matrix2 = (Matrix2d) o;
        if (Double.compare(matrix2.m00, m00) != 0) {
            return false;
        }
        if (Double.compare(matrix2.m01, m01) != 0) {
            return false;
        }
        if (Double.compare(matrix2.m10, m10) != 0) {
            return false;
        }
        if (Double.compare(matrix2.m11, m11) != 0) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (!hashed) {
            int result = (m00 != +0.0f ? HashFunctions.hash(m00) : 0);
            result = 31 * result + (m01 != +0.0f ? HashFunctions.hash(m01) : 0);
            result = 31 * result + (m10 != +0.0f ? HashFunctions.hash(m10) : 0);
            hashCode = 31 * result + (m11 != +0.0f ? HashFunctions.hash(m11) : 0);
            hashed = true;
        }
        return hashCode;
    }

    @NotNull
    @Override
    public Matrix2d clone() {
        return new Matrix2d(this);
    }

    @NotNull
    public static Matrix2d from(double n) {
         return n == 0 ? ZERO : new Matrix2d(n, n, n, n);
    }

    @NotNull
    public static Matrix2d from(double m00, double m01, double m10, double m11) {
         return m00 == 0 && m01 == 0 && m10 == 0 && m11 == 0 ? ZERO : new Matrix2d(m00, m01, m10, m11);
    }

    @NotNull
    public static Matrix2d fromDiagonal(double m00, double m11) {
         return m00 == 0 && m11 == 0 ? ZERO : new Matrix2d(m00, 0, 0, m11);
    }

    public static Matrix2d createScaling(float scale) {
        return createScaling((double) scale);
    }

    public static Matrix2d createScaling(double scale) {
        return createScaling(scale, scale);
    }

    public static Matrix2d createScaling(@NotNull Vec2d v) {
        return createScaling(v.getX(), v.getY());
    }

    public static Matrix2d createScaling(float x, float y) {
        return createScaling((double) x, (double) y);
    }

    public static Matrix2d createScaling(double x, double y) {
        return new Matrix2d(
                x, 0,
                0, y);
    }

    public static Matrix2d createTranslation(float x) {
        return createTranslation((double) x);
    }

    public static Matrix2d createTranslation(double x) {
        return new Matrix2d(
                1, x,
                0, 1);
    }

    public static Matrix2d createRotation(@NotNull Complexd rot) {
        rot = rot.normalize();
        return new Matrix2d(
                rot.getX(), -rot.getY(),
                rot.getY(), rot.getX());
    }
}
