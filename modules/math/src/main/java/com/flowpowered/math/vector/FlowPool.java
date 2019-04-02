package com.flowpowered.math.vector;

import org.jetbrains.annotations.NotNull;

abstract class FlowPool<T> {
    static final int BITS_2D = 5, BITS_3D = 4, BITS_4D = 3;

    final int axes, bits,
            mask, min, max;
    @NotNull
    final T[] cache;

    public FlowPool(int axes, int bits) {
        this.axes = axes;
        this.bits = bits;
        this.mask = (1 << bits)-1;
        this.max = (1 << (bits-1))-1;
        this.min = -(1 << (bits-1));

        //noinspection unchecked
        this.cache = (T[]) new Object[1 << (bits * axes)];

        int xMin = axes < 1 ? 0 : min;
        int xMax = axes < 1 ? 0 : max;
        int yMin = axes < 2 ? 0 : min;
        int yMax = axes < 2 ? 0 : max;
        int zMin = axes < 3 ? 0 : min;
        int zMax = axes < 3 ? 0 : max;
        int wMin = axes < 4 ? 0 : min;
        int wMax = axes < 4 ? 0 : max;

        for(int x = xMin; x <= xMax; x++) {
            for(int y = yMin; y <= yMax; y++) {
                for(int z = zMin; z <= zMax; z++) {
                    for(int w = wMin; w <= wMax; w++) {
                        cache[index(x, y, z, w)] = create(x, y, z, w);
                    }
                }
            }
        }
    }

    private int index(int x, int y, int z, int w) {
        return  (axes < 1 ? 0 : (x-min & mask)            ) |
                (axes < 2 ? 0 : (y-min & mask) << bits    ) |
                (axes < 3 ? 0 : (z-min & mask) << bits * 2) |
                (axes < 4 ? 0 : (w-min & mask) << bits * 3);
    }

    private boolean isPooled(int x, int y, int z, int w) {
        return min <= x && x <= max &&
                (axes < 2 || min <= y && y <= max) &&
                (axes < 3 || min <= z && z <= max) &&
                (axes < 4 || min <= w && w <= max);
    }

    private boolean isPooled(long x, long y, long z, long w) {
        return (int) x == x && (int) y == y && (int) z == z && (int) w == w &&
                isPooled((int) x, (int) y, (int) z, (int) w);
    }

    private boolean isPooled(float x, float y, float z, float w) {
        return isPooled((double) x, (double) y, (double) z, (double) w);
    }

    private boolean isPooled(double x, double y, double z, double w) {
        return (int) x == x && (int) y == y && (int) z == z && (int) w == w &&
                isPooled((int) x, (int) y, (int) z, (int) w);
    }

    public T getPooled(int x, int y, int z, int w) {
        if(isPooled(x, y, z, w))
            return cache[index(x, y, z, w)];
        return create(x, y, z, w);
    }

    public T getPooled(long x, long y, long z, long w) {
        if(isPooled(x, y, z, w))
            return cache[index((int) x, (int) y, (int) z, (int) w)];
        return create(x, y, z, w);
    }

    public T getPooled(float x, float y, float z, float w) {
        if(isPooled(x, y, z, w))
            return cache[index((int) x, (int) y, (int) z, (int) w)];
        return create(x, y, z, w);
    }

    public T getPooled(double x, double y, double z, double w) {
        if(isPooled(x, y, z, w))
            return cache[index((int) x, (int) y, (int) z, (int) w)];
        return create(x, y, z, w);
    }

    @NotNull
    protected T create(int x, int y, int z, int w) {
        return create((long) x, (long) y, (long) z, (long) w);
    }

    @NotNull
    protected T create(long x, long y, long z, long w) {
        return create((float) x, (float) y, (float) z, (float) w);
    }

    @NotNull
    protected T create(float x, float y, float z, float w) {
        return create((double) x, (double) y, (double) z, (double) w);
    }

    @NotNull
    protected T create(double x, double y, double z, double w) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    protected T hit(T value) {
        return value;
    }
}
