package com.flowpowered.math.vector;

import org.jetbrains.annotations.NotNull;

public interface Vectord {
    @NotNull
    public Vectord mul(double a);
    /** Operator for Kotlin */
    @NotNull
    public Vectord times(double a);

    @NotNull
    public Vectord div(double a);

    @NotNull
    public Vectord pow(double pow);

    @NotNull
    public Vectord ceil();

    @NotNull
    public Vectord floor();

    @NotNull
    public Vectord round();

    @NotNull
    public Vectord abs();

    @NotNull
    public Vectord negate();
    /** Operator for Kotlin */
    @NotNull
    public Vectord unaryMinus();

    public double length();

    public double lengthSquared();

    @NotNull
    public Vectord normalize();

    public int getMinAxis();

    public int getMaxAxis();

    @NotNull
    public double[] toArray();

    @NotNull
    public Vectori toInt();

    @NotNull
    public Vectorl toLong();

    @NotNull
    public Vectorf toFloat();

    @NotNull
    public Vectord toDouble();
}
