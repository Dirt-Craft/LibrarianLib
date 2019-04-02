package com.flowpowered.math.vector;

import org.jetbrains.annotations.NotNull;

public interface VecN {
    @NotNull
    VecNd toDouble();
    double getNd(int i);

    @NotNull
    VecNf toFloat();
    float getNf(int i);

    @NotNull
    VecNl toLong();
    long getNl(int i);

    @NotNull
    VecNi toInt();
    int getNi(int i);

    @NotNull
    Number getNn(int i);
}
