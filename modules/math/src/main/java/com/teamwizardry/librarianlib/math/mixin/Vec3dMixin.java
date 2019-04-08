package com.teamwizardry.librarianlib.math.mixin;

import com.teamwizardry.librarianlib.math.vector.Vec3d;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.util.math.Vec3d.class)
public abstract class Vec3dMixin implements Vec3d {
    @Shadow @Final public double x;
    @Shadow @Final public double y;
    @Shadow @Final public double z;

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @NotNull
    @Override
    public net.minecraft.util.math.Vec3d toMC() {
        return (net.minecraft.util.math.Vec3d) (Object) this;
    }
}
