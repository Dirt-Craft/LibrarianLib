package com.flowpowered.math.vector;

import org.jetbrains.annotations.NotNull;

public class Vectors {
    @NotNull public static Vec2d vec2d(double x, double y) { return Vec2d.createPooled(x, y); }
    @NotNull public static Vec2d vec2d(float x, float y) { return Vec2d.createPooled(x, y); }
    @NotNull public static Vec2f vec2f(float x, float y) { return Vec2f.createPooled(x, y); }
    @NotNull public static Vec2i vec2i(int x, int y) { return Vec2i.createPooled(x, y); }
    @NotNull public static Vec2l vec2l(long x, long y) { return Vec2l.createPooled(x, y); }
    @NotNull public static Vec2l vec2l(int x, int y) { return Vec2l.createPooled(x, y); }

    @NotNull public static Vec3d vec3d(double x, double y, double z) { return Vec3d.createPooled(x, y, z); }
    @NotNull public static Vec3d vec3d(float x, float y, float z) { return Vec3d.createPooled(x, y, z); }
    @NotNull public static net.minecraft.util.math.Vec3d mcVec3d(double x, double y, double z) { return MCVec3dPool.createPooled(x, y, z); }
    @NotNull public static net.minecraft.util.math.Vec3d mcVec3d(float x, float y, float z) { return MCVec3dPool.createPooled(x, y, z); }
    @NotNull public static Vec3f vec3f(float x, float y, float z) { return Vec3f.createPooled(x, y, z); }
    @NotNull public static Vec3i vec3i(int x, int y, int z) { return Vec3i.createPooled(x, y, z); }
    @NotNull public static Vec3l vec3l(long x, long y, long z) { return Vec3l.createPooled(x, y, z); }
    @NotNull public static Vec3l vec3l(int x, int y, int z) { return Vec3l.createPooled(x, y, z); }

    @NotNull public static Vec4d vec4d(double x, double y, double z, double w) { return Vec4d.createPooled(x, y, z, w); }
    @NotNull public static Vec4d vec4d(float x, float y, float z, float w) { return Vec4d.createPooled(x, y, z, w); }
    @NotNull public static Vec4f vec4f(float x, float y, float z, float w) { return Vec4f.createPooled(x, y, z, w); }
    @NotNull public static Vec4i vec4i(int x, int y, int z, int w) { return Vec4i.createPooled(x, y, z, w); }
    @NotNull public static Vec4l vec4l(long x, long y, long z, long w) { return Vec4l.createPooled(x, y, z, w); }
    @NotNull public static Vec4l vec4l(int x, int y, int z, int w) { return Vec4l.createPooled(x, y, z, w); }

    @NotNull public static VecNd vecNd(double... components) { return new VecNd(components); }
    @NotNull public static VecNf vecNf(float... components) { return new VecNf(components); }
    @NotNull public static VecNi vecNi(int... components) { return new VecNi(components); }
    @NotNull public static VecNl vecNl(long... components) { return new VecNl(components); }
}
