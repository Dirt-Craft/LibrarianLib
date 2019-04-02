package com.flowpowered.math.vector;

import org.jetbrains.annotations.NotNull;

public interface Vec3 {
    @NotNull
    Vec3d toDouble();
    double getXd();
    double getYd();
    double getZd();

    @NotNull
    net.minecraft.util.math.Vec3d toMC();

    @NotNull
    Vec3f toFloat();
    float getXf();
    float getYf();
    float getZf();

    @NotNull
    Vec3l toLong();
    long getXl();
    long getYl();
    long getZl();

    @NotNull
    Vec3i toInt();
    int getXi();
    int getYi();
    int getZi();

    @NotNull
    Number getXn();
    @NotNull
    Number getYn();
    @NotNull
    Number getZn();
}
