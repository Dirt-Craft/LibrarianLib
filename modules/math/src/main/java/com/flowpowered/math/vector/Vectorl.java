package com.flowpowered.math.vector;

import org.jetbrains.annotations.NotNull;

public interface Vectorl {
    @NotNull
    public Vectorl mul(long a);
    /** Operator function for Kotlin */
    @NotNull
    public Vectorl times(long a);

    @NotNull
    public Vectorl div(long a);

    @NotNull
    public Vectorl pow(long pow);

    @NotNull
    public Vectorl abs();

    @NotNull
    public Vectorl negate();
    /** Operator function for Kotlin */
    @NotNull
    public Vectorl unaryMinus();

    public double length();

    public double lengthSquared();

    public int getMinAxis();

    public int getMaxAxis();

    @NotNull
    public long[] toArray();

    @NotNull
    public Vectori toInt();

    @NotNull
    public Vectorl toLong();

    @NotNull
    public Vectorf toFloat();

    @NotNull
    public Vectord toDouble();
}
