package com.flowpowered.math.vector;

import org.jetbrains.annotations.NotNull;

public interface Vectori {
    @NotNull
    public Vectori mul(int a);
    /** Operator function for Kotlin */
    @NotNull
    public Vectori times(int a);

    @NotNull
    public Vectori div(int a);

    @NotNull
    public Vectori pow(int pow);

    @NotNull
    public Vectori abs();

    @NotNull
    public Vectori negate();
    /** Operator function for Kotlin */
    @NotNull
    public Vectori unaryMinus();

    public double length();

    public double lengthSquared();

    public int getMinAxis();

    public int getMaxAxis();

    @NotNull
    public int[] toArray();

    @NotNull
    public Vectori toInt();

    @NotNull
    public Vectorl toLong();

    @NotNull
    public Vectorf toFloat();

    @NotNull
    public Vectord toDouble();
}
