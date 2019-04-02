package com.flowpowered.math;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.Random;

import com.flowpowered.math.imaginary.*;
import com.flowpowered.math.vector.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class containing generic mathematical functions.
 */
public class GenericMath {
    /**
     * A "close to zero" double epsilon value for use
     */
    public static final double DBL_EPSILON = Double.longBitsToDouble(0x3cb0000000000000L);
    /**
     * A "close to zero" float epsilon value for use
     */
    public static final float FLT_EPSILON = Float.intBitsToFloat(0x34000000);
    private static final ThreadLocal<Random> THREAD_LOCAL_RANDOM = new ThreadLocal<Random>() {
        private final Random random = new SecureRandom();

        @Override
        protected Random initialValue() {
            synchronized (random) {
                return new Random(random.nextLong());
            }
        }
    };

    private GenericMath() {
    }

    /**
     * Gets the difference between two angles This value is always positive (0 - 180)
     *
     * @param angle1 The first angle
     * @param angle2 The second angle
     * @return the positive angle difference
     */
    public static float getDegreeDifference(float angle1, float angle2) {
        return Math.abs(wrapAngleDeg(angle1 - angle2));
    }

    /**
     * Gets the difference between two radians This value is always positive (0 - PI)
     *
     * @param radian1 The first angle
     * @param radian2 The second angle
     * @return the positive radian difference
     */
    public static double getRadianDifference(double radian1, double radian2) {
        return Math.abs(wrapAngleRad(radian1 - radian2));
    }

    /**
     * Wraps the angle between -180 and 180 degrees
     *
     * @param angle to wrap
     * @return -180 < angle <= 180
     */
    public static float wrapAngleDeg(float angle) {
        angle %= 360f;
        if (angle <= -180) {
            return angle + 360;
        } else if (angle > 180) {
            return angle - 360;
        } else {
            return angle;
        }
    }

    /**
     * Wraps the radian between -PI and PI
     *
     * @param angle to wrap
     * @return -PI < radian <= PI
     */
    public static double wrapAngleRad(double angle) {
        angle %= TrigMath.TWO_PI;
        if (angle <= -TrigMath.PI) {
            return angle + TrigMath.TWO_PI;
        }
        if (angle > TrigMath.PI) {
            return angle - TrigMath.TWO_PI;
        }
        return angle;
    }

    /**
     * Wraps the pitch angle between -90 and 90 degrees
     *
     * @param angle to wrap
     * @return -90 < angle < 90
     */
    public static float wrapAnglePitchDeg(float angle) {
        angle = wrapAngleDeg(angle);
        if (angle < -90) {
            return -90;
        }
        if (angle > 90) {
            return 90;
        }
        return angle;
    }

    /**
     * Wraps a byte between 0 and 256
     *
     * @param value to wrap
     * @return 0 < byte < 256
     */
    public static byte wrapByte(int value) {
        value %= 256;
        if (value < 0) {
            value += 256;
        }
        return (byte) value;
    }

    /**
     * Rounds a number to the amount of decimals specified
     *
     * @param input to round
     * @param decimals to round to
     * @return the rounded number
     */
    public static double round(double input, int decimals) {
        final double p = Math.pow(10, decimals);
        return Math.round(input * p) / p;
    }

    /**
     * Calculates the linear interpolation between a and b with the given percent
     *
     * @param a The first know value
     * @param b The second know value
     * @param percent The percent
     * @return the interpolated value
     */
    public static double lerp(double a, double b, double percent) {
        return (1 - percent) * a + percent * b;
    }

    /**
     * Calculates the linear interpolation between a and b with the given percent
     *
     * @param a The first know value
     * @param b The second know value
     * @param percent The percent
     * @return the interpolated value
     */
    public static float lerp(float a, float b, float percent) {
        return (1 - percent) * a + percent * b;
    }

    /**
     * Calculates the linear interpolation between a and b with the given percent
     *
     * @param a The first know value
     * @param b The second know value
     * @param percent The percent
     * @return the interpolated value
     */
    public static int lerp(int a, int b, int percent) {
        return (1 - percent) * a + percent * b;
    }

    /**
     * Calculates the linear interpolation between a and b with the given percent
     *
     * @param a The first know value
     * @param b The second know value
     * @param percent The percent
     * @return the interpolated vector
     */
    @NotNull
    public static Vec3f lerp(@NotNull Vec3f a, @NotNull Vec3f b, float percent) {
        return a.mul(1 - percent).add(b.mul(percent));
    }

    /**
     * Calculates the linear interpolation between a and b with the given percent
     *
     * @param a The first know value
     * @param b The second know value
     * @param percent  percent
     * @return the interpolated vector
     */
    @NotNull
    public static Vec3d lerp(@NotNull Vec3d a, @NotNull Vec3d b, double percent) {
        return a.mul(1 - percent).add(b.mul(percent));
    }

    /**
     * Calculates the linear interpolation between a and b with the given percent
     *
     * @param a The first know value
     * @param b The second know value
     * @param percent The percent
     * @return the interpolated vector
     */
    @NotNull
    public static Vec2f lerp(@NotNull Vec2f a, @NotNull Vec2f b, float percent) {
        return a.mul(1 - percent).add(b.mul(percent));
    }

    /**
     * Calculates the linear interpolation between a and b with the given percent
     *
     * @param a The first know value
     * @param b The second know value
     * @param percent The percent
     * @return the interpolated vector
     */
    @NotNull
    public static Vec2d lerp(@NotNull Vec2d a, @NotNull Vec2d b, double percent) {
        return a.mul(1 - percent).add(b.mul(percent));
    }

    /**
     * Calculates the value at x using linear interpolation
     *
     * @param x the X coord of the value to interpolate
     * @param x1 the X coord of q0
     * @param x2 the X coord of q1
     * @param q0 the first known value (x1)
     * @param q1 the second known value (x2)
     * @return the interpolated value
     */
    public static double lerp(double x, double x1, double x2, double q0, double q1) {
        return ((x2 - x) / (x2 - x1)) * q0 + ((x - x1) / (x2 - x1)) * q1;
    }

    /**
     * Calculates the linear interpolation between a and b with the given percent
     *
     * @param a The first know value
     * @param b The second know value
     * @param percent The percent
     * @return Color
     */
    public static Color lerp(@NotNull Color a, @NotNull Color b, float percent) {
        int red = (int) lerp(a.getRed(), b.getRed(), percent);
        int blue = (int) lerp(a.getBlue(), b.getBlue(), percent);
        int green = (int) lerp(a.getGreen(), b.getGreen(), percent);
        int alpha = (int) lerp(a.getAlpha(), b.getAlpha(), percent);
        return new Color(red, green, blue, alpha);
    }

    /**
     * Interpolates a quaternion between two others using spherical linear interpolation.
     *
     * @param a The first quaternion
     * @param b The second quaternion
     * @param percent The percent for the interpolation, between 0 and 1 inclusively
     * @return The interpolated quaternion
     */
    @NotNull
    public static Quaternionf slerp(@NotNull Quaternionf a, @NotNull Quaternionf b, float percent) {
        final float inverted;
        float cosineTheta = a.dot(b);
        if (cosineTheta < 0) {
            cosineTheta = -cosineTheta;
            inverted = -1;
        } else {
            inverted = 1;
        }
        if (1 - cosineTheta < GenericMath.FLT_EPSILON) {
            return a.mul(1 - percent).add(b.mul(percent * inverted));
        }
        final float theta = (float) TrigMath.acos(cosineTheta);
        final float sineTheta = TrigMath.sin(theta);
        final float coefficient1 = TrigMath.sin((1 - percent) * theta) / sineTheta;
        final float coefficient2 = TrigMath.sin(percent * theta) / sineTheta * inverted;
        return a.mul(coefficient1).add(b.mul(coefficient2));
    }

    /**
     * Interpolates a quaternion between two others using spherical linear interpolation.
     *
     * @param a The first quaternion
     * @param b The second quaternion
     * @param percent The percent for the interpolation, between 0 and 1 inclusively
     * @return The interpolated quaternion
     */
    @NotNull
    public static Quaterniond slerp(@NotNull Quaterniond a, @NotNull Quaterniond b, double percent) {
        final double inverted;
        double cosineTheta = a.dot(b);
        if (cosineTheta < 0) {
            cosineTheta = -cosineTheta;
            inverted = -1;
        } else {
            inverted = 1;
        }
        if (1 - cosineTheta < GenericMath.DBL_EPSILON) {
            return a.mul(1 - percent).add(b.mul(percent * inverted));
        }
        final double theta = (double) TrigMath.acos(cosineTheta);
        final double sineTheta = TrigMath.sin(theta);
        final double coefficient1 = TrigMath.sin((1 - percent) * theta) / sineTheta;
        final double coefficient2 = TrigMath.sin(percent * theta) / sineTheta * inverted;
        return a.mul(coefficient1).add(b.mul(coefficient2));
    }

    /**
     * Interpolates a quaternion between two others using linear interpolation.
     *
     * @param a The first quaternion
     * @param b The second quaternion
     * @param percent The percent for the interpolation, between 0 and 1 inclusively
     * @return The interpolated quaternion
     */
    @NotNull
    public static Quaternionf lerp(@NotNull Quaternionf a, @NotNull Quaternionf b, float percent) {
        return a.mul(1 - percent).add(b.mul(percent));
    }

    /**
     * Interpolates a quaternion between two others using linear interpolation.
     *
     * @param a The first quaternion
     * @param b The second quaternion
     * @param percent The percent for the interpolation, between 0 and 1 inclusively
     * @return The interpolated quaternion
     */
    @NotNull
    public static Quaterniond lerp(@NotNull Quaterniond a, @NotNull Quaterniond b, double percent) {
        return a.mul(1 - percent).add(b.mul(percent));
    }

    /**
     * Calculates the value at x,y using bilinear interpolation
     *
     * @param x the X coord of the value to interpolate
     * @param y the Y coord of the value to interpolate
     * @param q00 the first known value (x1, y1)
     * @param q01 the second known value (x1, y2)
     * @param q10 the third known value (x2, y1)
     * @param q11 the fourth known value (x2, y2)
     * @param x1 the X coord of q00 and q01
     * @param x2 the X coord of q10 and q11
     * @param y1 the Y coord of q00 and q10
     * @param y2 the Y coord of q01 and q11
     * @return the interpolated value
     */
    public static double biLerp(double x, double y, double q00, double q01,
                                double q10, double q11, double x1, double x2, double y1, double y2) {
        double q0 = lerp(x, x1, x2, q00, q10);
        double q1 = lerp(x, x1, x2, q01, q11);
        return lerp(y, y1, y2, q0, q1);
    }

    /**
     * Calculates the value at x,y,z using trilinear interpolation
     *
     * @param x the X coord of the value to interpolate
     * @param y the Y coord of the value to interpolate
     * @param z the Z coord of the value to interpolate
     * @param q000 the first known value (x1, y1, z1)
     * @param q001 the second known value (x1, y2, z1)
     * @param q010 the third known value (x1, y1, z2)
     * @param q011 the fourth known value (x1, y2, z2)
     * @param q100 the fifth known value (x2, y1, z1)
     * @param q101 the sixth known value (x2, y2, z1)
     * @param q110 the seventh known value (x2, y1, z2)
     * @param q111 the eighth known value (x2, y2, z2)
     * @param x1 the X coord of q000, q001, q010 and q011
     * @param x2 the X coord of q100, q101, q110 and q111
     * @param y1 the Y coord of q000, q010, q100 and q110
     * @param y2 the Y coord of q001, q011, q101 and q111
     * @param z1 the Z coord of q000, q001, q100 and q101
     * @param z2 the Z coord of q010, q011, q110 and q111
     * @return the interpolated value
     */
    public static double triLerp(double x, double y, double z, double q000, double q001,
                                 double q010, double q011, double q100, double q101, double q110, double q111,
                                 double x1, double x2, double y1, double y2, double z1, double z2) {
        double q00 = lerp(x, x1, x2, q000, q100);
        double q01 = lerp(x, x1, x2, q010, q110);
        double q10 = lerp(x, x1, x2, q001, q101);
        double q11 = lerp(x, x1, x2, q011, q111);
        double q0 = lerp(y, y1, y2, q00, q10);
        double q1 = lerp(y, y1, y2, q01, q11);
        return lerp(z, z1, z2, q0, q1);
    }

    /**
     * Blends two colors into one.
     *
     * @param a The first color
     * @param b The second color
     * @return The blended color
     */
    public static Color blend(@NotNull Color a, @NotNull Color b) {
        return lerp(a, b, a.getAlpha() / 255f);
    }

    /**
     * Clamps the value between the low and high boundaries
     *
     * @param value The value to clamp
     * @param low The low bound of the clamp
     * @param high The high bound of the clamp
     * @return the clamped value
     */
    public static double clamp(double value, double low, double high) {
        if (value < low) {
            return low;
        }
        if (value > high) {
            return high;
        }
        return value;
    }

    /**
     * Clamps the value between the low and high boundaries
     *
     * @param value The value to clamp
     * @param low The low bound of the clamp
     * @param high The high bound of the clamp
     * @return the clamped value
     */
    public static int clamp(int value, int low, int high) {
        if (value < low) {
            return low;
        }
        if (value > high) {
            return high;
        }
        return value;
    }

    /**
     * Returns a fast estimate of the inverse square root of the value
     *
     * @param a The value
     * @return The estimate of the inverse square root
     */
    public static double inverseSqrt(double a) {
        final double halfA = 0.5d * a;
        a = Double.longBitsToDouble(0x5FE6EB50C7B537AAl - (Double.doubleToRawLongBits(a) >> 1));
        return a * (1.5d - halfA * a * a);
    }

    /**
     * Returns a fast estimate of the square root of the value
     *
     * @param a The value
     * @return The estimate of the square root
     */
    public static double sqrt(double a) {
        return a * inverseSqrt(a);
    }

    /**
     * Rounds 'a' down to the closest integer
     *
     * @param a The value to floor
     * @return The closest integer
     */
    public static int floor(double a) {
        int y = (int) a;
        if (a < y) {
            return y - 1;
        }
        return y;
    }

    /**
     * Rounds 'a' down to the closest integer
     *
     * @param a The value to floor
     * @return The closest integer
     */
    public static int floor(float a) {
        int y = (int) a;
        if (a < y) {
            return y - 1;
        }
        return y;
    }

    /**
     * Rounds 'a' down to the closest long
     *
     * @param a The value to floor
     * @return The closest long
     */
    public static long floorl(double a) {
        long y = (long) a;
        if (a < y) {
            return y - 1;
        }
        return y;
    }

    /**
     * Rounds 'a' down to the closest long
     *
     * @param a The value to floor
     * @return The closest long
     */
    public static long floorl(float a) {
        long y = (long) a;
        if (a < y) {
            return y - 1;
        }
        return y;
    }

    /**
     * Gets the maximum byte value from two values
     *
     * @param value1 The first value
     * @param value2 The second value
     * @return the maximum of value1 and value2
     */
    public static byte max(byte value1, byte value2) {
        return value1 > value2 ? value1 : value2;
    }

    /**
     * Rounds an integer up to the next power of 2.
     *
     * @param a The integer to round
     * @return the lowest power of 2 greater or equal to 'a'
     */
    public static int roundUpPow2(int a) {
        if (a <= 0) {
            return 1;
        } else if (a > 0x40000000) {
            throw new IllegalArgumentException("Rounding " + a + " to the next highest power of two would exceed the int range");
        } else {
            a--;
            a |= a >> 1;
            a |= a >> 2;
            a |= a >> 4;
            a |= a >> 8;
            a |= a >> 16;
            a++;
            return a;
        }
    }

    /**
     * Rounds an integer up to the next power of 2.
     *
     * @param a The long to round
     * @return the lowest power of 2 greater or equal to 'a'
     */
    public static long roundUpPow2(long a) {
        if (a <= 0) {
            return 1;
        } else if (a > 0x4000000000000000L) {
            throw new IllegalArgumentException("Rounding " + a + " to the next highest power of two would exceed the int range");
        } else {
            a--;
            a |= a >> 1;
            a |= a >> 2;
            a |= a >> 4;
            a |= a >> 8;
            a |= a >> 16;
            a |= a >> 32;
            a++;
            return a;
        }
    }

    /**
     * Casts a value to a float. May return null.
     *
     * @param o The object to attempt to cast
     * @return The object as a float
     */
    @Nullable
    public static Float castFloat(@Nullable Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).floatValue();
        }
        try {
            return Float.valueOf(o.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Casts a value to a byte. May return null.
     *
     * @param o The object to attempt to cast
     * @return The object as a byte
     */
    @Nullable
    public static Byte castByte(@Nullable Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).byteValue();
        }
        try {
            return Byte.valueOf(o.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Casts a value to a short. May return null.
     *
     * @param o The object to attempt to cast
     * @return The object as a short
     */
    @Nullable
    public static Short castShort(@Nullable Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).shortValue();
        }
        try {
            return Short.valueOf(o.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Casts a value to an integer. May return null.
     *
     * @param o The object to attempt to cast
     * @return The object as an int
     */
    @Nullable
    public static Integer castInt(@Nullable Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).intValue();
        }
        try {
            return Integer.valueOf(o.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Casts a value to a double. May return null.
     *
     * @param o The object to attempt to cast
     * @return The object as a double
     */
    @Nullable
    public static Double castDouble(@Nullable Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }
        try {
            return Double.valueOf(o.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Casts a value to a long. May return null.
     *
     * @param o The object to attempt to cast
     * @return The object as a long
     */
    @Nullable
    public static Long castLong(@Nullable Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).longValue();
        }
        try {
            return Long.valueOf(o.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Casts a value to a boolean. May return null.
     *
     * @param o The object to attempt to cast
     * @return The object as a boolean
     */
    @Nullable
    public static Boolean castBoolean(@Nullable Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Boolean) {
            return (Boolean) o;
        } else if (o instanceof String) {
            try {
                return Boolean.parseBoolean((String) o);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        return null;
    }

    /**
     * Calculates the mean of a set of values
     *
     * @param values to calculate the mean of
     * @return the mean of the values
     */
    public static int mean(int... values) {
        int sum = 0;
        for (int v : values) {
            sum += v;
        }
        return sum / values.length;
    }

    /**
     * Calculates the mean of a set of values.
     *
     * @param values to calculate the mean of
     * @return the mean of the values
     */
    public static double mean(double... values) {
        double sum = 0;
        for (double v : values) {
            sum += v;
        }
        return sum / values.length;
    }

    /**
     * Converts an integer to hexadecimal form with at least the minimum of digits specified (by adding leading zeros).
     *
     * @param dec The integer to convert
     * @param minDigits The minimum of digits in the hexadecimal form
     * @return The integer in hexadecimal form
     */
    @NotNull
    public static String decToHex(int dec, int minDigits) {
        String ret = Integer.toHexString(dec);
        while (ret.length() < minDigits) {
            ret = '0' + ret;
        }
        return ret;
    }

    /**
     * Returns the modulo of 'a' by 'div' with corrections for negative numbers.
     *
     * @param a The number as an int
     * @param div The div as an int
     * @return The corrected modulo
     */
    public static int mod(int a, int div) {
        final int remainder = a % div;
        return remainder < 0 ? remainder + div : remainder;
    }

    /**
     * Returns the modulo of 'a' by 'div' with corrections for negative numbers.
     *
     * @param a The dividend
     * @param div The divider
     * @return The corrected modulo
     */
    public static float mod(float a, float div) {
        final float remainder = a % div;
        return remainder < 0 ? remainder + div : remainder;
    }

    /**
     * Returns the modulo of 'a' by 'div' with corrections for negative numbers.
     *
     * @param a The dividend
     * @param div The divider
     * @return The corrected modulo
     */
    public static double mod(double a, double div) {
        final double remainder = a % div;
        return remainder < 0 ? remainder + div : remainder;
    }

    /**
     * Gets a thread local Random object that is seeded using SecureRandom. Only one Random is created per thread.
     *
     * @return The random for the thread
     */
    public static Random getRandom() {
        return THREAD_LOCAL_RANDOM.get();
    }

    /**
     * Determines if the given number is a power of two. A number is a power of 2 if it is 1 or greater, and it contains no similar bits of the given number - 1.
     *
     * @return true if num is a power of two
     */
    public static boolean isPowerOfTwo(int num) {
        return num > 0 && ((num & (num - 1)) == 0);
    }

    /**
     * Converts a multiplication into a shift.
     *
     * @param a the multiplicand
     * @return the left shift required to multiply by the multiplicand
     */
    public static int multiplyToShift(int a) {
        if (a < 1) {
            throw new IllegalArgumentException("Multiplicand must be at least 1");
        }
        int shift = 31 - Integer.numberOfLeadingZeros(a);
        if ((1 << shift) != a) {
            throw new IllegalArgumentException("Multiplicand must be a power of 2");
        }
        return shift;
    }

    /**
     * Attempts to normalize a vector. If this fails, the method catches the exception and return a zero vector of the same dimension instead.
     *
     * @param v The vector to attempt to normalize
     * @return The normalized vector, or the zero vector if it couldn't be normalized.
     */
    @NotNull
    public static Vec2f normalizeSafe(@NotNull Vec2f v) {
        try {
            return v.normalize();
        } catch (ArithmeticException ex) {
            return Vec2f.ZERO;
        }
    }

    /**
     * Attempts to normalize a vector. If this fails, the method catches the exception and return a zero vector of the same dimension instead.
     *
     * @param v The vector to attempt to normalize
     * @return The normalized vector, or the zero vector if it couldn't be normalized.
     */
    @NotNull
    public static Vec2d normalizeSafe(@NotNull Vec2d v) {
        try {
            return v.normalize();
        } catch (ArithmeticException ex) {
            return Vec2d.ZERO;
        }
    }

    /**
     * Attempts to normalize a vector. If this fails, the method catches the exception and return a zero vector of the same dimension instead.
     *
     * @param v The vector to attempt to normalize
     * @return The normalized vector, or the zero vector if it couldn't be normalized.
     */
    @NotNull
    public static Vec3f normalizeSafe(@NotNull Vec3f v) {
        try {
            return v.normalize();
        } catch (ArithmeticException ex) {
            return Vec3f.ZERO;
        }
    }

    /**
     * Attempts to normalize a vector. If this fails, the method catches the exception and return a zero vector of the same dimension instead.
     *
     * @param v The vector to attempt to normalize
     * @return The normalized vector, or the zero vector if it couldn't be normalized.
     */
    @NotNull
    public static Vec3d normalizeSafe(@NotNull Vec3d v) {
        try {
            return v.normalize();
        } catch (ArithmeticException ex) {
            return Vec3d.ZERO;
        }
    }

    /**
     * Attempts to normalize a vector. If this fails, the method catches the exception and return a zero vector of the same dimension instead.
     *
     * @param v The vector to attempt to normalize
     * @return The normalized vector, or the zero vector if it couldn't be normalized.
     */
    @NotNull
    public static Vec4f normalizeSafe(@NotNull Vec4f v) {
        try {
            return v.normalize();
        } catch (ArithmeticException ex) {
            return Vec4f.ZERO;
        }
    }

    /**
     * Attempts to normalize a vector. If this fails, the method catches the exception and return a zero vector of the same dimension instead.
     *
     * @param v The vector to attempt to normalize
     * @return The normalized vector, or the zero vector if it couldn't be normalized.
     */
    @NotNull
    public static Vec4d normalizeSafe(@NotNull Vec4d v) {
        try {
            return v.normalize();
        } catch (ArithmeticException ex) {
            return Vec4d.ZERO;
        }
    }

    /**
     * Attempts to normalize a vector. If this fails, the method catches the exception and return a zero vector of the same dimension instead.
     *
     * @param v The vector to attempt to normalize
     * @return The normalized vector, or the zero vector if it couldn't be normalized.
     */
    @NotNull
    public static VecNf normalizeSafe(@NotNull VecNf v) {
        try {
            return v.normalize();
        } catch (ArithmeticException ex) {
            return new VecNf(v.size());
        }
    }

    /**
     * Attempts to normalize a vector. If this fails, the method catches the exception and return a zero vector of the same dimension instead.
     *
     * @param v The vector to attempt to normalize
     * @return The normalized vector, or the zero vector if it couldn't be normalized.
     */
    @NotNull
    public static VecNd normalizeSafe(@NotNull VecNd v) {
        try {
            return v.normalize();
        } catch (ArithmeticException ex) {
            return new VecNd(v.size());
        }
    }
}
