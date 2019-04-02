package com.flowpowered.math.vector;

import org.jetbrains.annotations.NotNull;

public interface Vec2 {
    @NotNull
    Vec2 mul(double a);
    /** Operator for Kotlin */
    @NotNull
    Vec2 times(double a);
    @NotNull
    Vec2 div(double a);
    @NotNull
    Vec2 pow(double pow);

//region Unary operations
    @NotNull
    Vec2 ceil();
    @NotNull
    Vec2 floor();
    @NotNull
    Vec2 round();
    @NotNull
    Vec2 abs();
    @NotNull
    Vec2 negate();
    /** Operator for Kotlin */
    @NotNull
    default Vec2 unaryMinus() { return negate(); }

    double length();
    double lengthSquared();
    @NotNull
    Vec2 normalize();
//endregion

//region Casting
    @NotNull
    Vec2d toDouble();
    double getXd();
    double getYd();

    @NotNull
    Vec2f toFloat();
    float getXf();
    float getYf();

    @NotNull
    Vec2l toLong();
    long getXl();
    long getYl();

    @NotNull
    Vec2i toInt();
    int getXi();
    int getYi();

    @NotNull
    Number getXn();
    @NotNull
    Number getYn();
//endregion

    static Vec2d create(double x, double y) {
        return Vec2d.createPooled(x, y);
    }
    static Vec2f create(float x, float y) {
        return Vec2f.createPooled(x, y);
    }
    static Vec2i create(int x, int y) {
        return Vec2i.createPooled(x, y);
    }
    static Vec2l create(long x, long y) {
        return Vec2l.createPooled(x, y);
    }
}
