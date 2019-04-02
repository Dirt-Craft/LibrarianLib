package com.flowpowered.math.imaginary;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an imaginary number.
 */
public interface Imaginaryd {
    /**
     * Multiplies the imaginary number by the given scalar.
     *
     * @param a The scalar to multiply by
     * @return The multiplied imaginary number
     */
    @NotNull
    public Imaginaryd mul(double a);
    /** Operator function for Kotlin */
    @NotNull
    public Imaginaryd times(double a);

    /**
     * Divides the imaginary number by the given scalar.
     *
     * @param a The scalar to divide by
     * @return The multiplied imaginary number
     */
    @NotNull
    public Imaginaryd div(double a);

    /**
     * Returns the conjugated imaginary number.
     *
     * @return The conjugate
     */
    @NotNull
    public Imaginaryd conjugate();

    /**
     * Returns the inverts imaginary number.
     *
     * @return The inverse
     */
    @NotNull
    public Imaginaryd invert();

    /**
     * Returns the length of the imaginary number.
     *
     * @return The length
     */
    public double length();

    /**
     * Returns the square of the length of the imaginary number.
     *
     * @return The square of the length
     */
    public double lengthSquared();

    /**
     * Normalizes the imaginary number.
     *
     * @return The imaginary number, but of unit length
     */
    @NotNull
    public Imaginaryd normalize();

    @NotNull
    public Imaginaryf toFloat();

    @NotNull
    public Imaginaryd toDouble();
}
