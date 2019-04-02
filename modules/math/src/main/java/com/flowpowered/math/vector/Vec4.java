package com.flowpowered.math.vector;

import org.jetbrains.annotations.NotNull;

public interface Vec4 {
    @NotNull
    Vec4d toDouble();
    double getXd();
    double getYd();
    double getZd();
    double getWd();

    @NotNull
    Vec4f toFloat();
    float getXf();
    float getYf();
    float getZf();
    float getWf();

    @NotNull
    Vec4l toLong();
    long getXl();
    long getYl();
    long getZl();
    long getWl();

    @NotNull
    Vec4i toInt();
    int getXi();
    int getYi();
    int getZi();
    int getWi();

    @NotNull
    Number getXn();
    @NotNull
    Number getYn();
    @NotNull
    Number getZn();
    @NotNull
    Number getWn();
}
