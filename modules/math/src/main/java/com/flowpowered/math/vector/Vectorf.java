package com.flowpowered.math.vector;

import org.jetbrains.annotations.NotNull;

public interface Vectorf {
    @NotNull
    public Vectorf mul(float a);
    /** Operator for Kotlin */
    @NotNull
    public Vectorf times(float a);

    @NotNull
    public Vectorf div(float a);

    @NotNull
    public Vectorf pow(float pow);

    @NotNull
    public Vectorf ceil();

    @NotNull
    public Vectorf floor();

    @NotNull
    public Vectorf round();

    @NotNull
    public Vectorf abs();

    @NotNull
    public Vectorf negate();
    /** Operator for Kotlin */
    @NotNull
    public Vectorf unaryMinus();

    public double length();

    public double lengthSquared();

    @NotNull
    public Vectorf normalize();

    public int getMinAxis();

    public int getMaxAxis();

    @NotNull
    public float[] toArray();

    @NotNull
    public Vectori toInt();

    @NotNull
    public Vectorl toLong();

    @NotNull
    public Vectorf toFloat();

    @NotNull
    public Vectord toDouble();
}
