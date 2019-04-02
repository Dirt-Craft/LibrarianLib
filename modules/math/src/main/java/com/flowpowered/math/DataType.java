package com.flowpowered.math;

public enum DataType {
    INT(int.class, Integer.class),
    FLOAT(float.class, Float.class),
    LONG(long.class, Long.class),
    DOUBLE(double.class, Double.class);

    public final Class<?> primitive, wrapper;

    DataType(Class<?> primitive, Class<?> wrapper) {
        this.primitive = primitive;
        this.wrapper = wrapper;
    }
}
