package com.flowpowered.math.vector;

import com.flowpowered.math.AllocationTracker;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class MCVec3dPool {
    private static final FlowPool<Vec3d> pool = new FlowPool<Vec3d>(3, FlowPool.BITS_3D) {
        @NotNull
        protected Vec3d hit(Vec3d value) {
            AllocationTracker.cacheHitsMCVec3d++;
            return value;
        }

        @NotNull
        protected Vec3d create(double x, double y, double z, double w) {
            AllocationTracker.instancesMCVec3d++;
            return new Vec3d(x, y, z);
        }
    };

    public static Vec3d createPooled(double x, double y, double z) {
        return pool.getPooled(x, y, z, 0);
    }
}
