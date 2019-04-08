package com.teamwizardry.librarianlib.math.mixin;

import com.teamwizardry.librarianlib.math.vector.Vec3d;
import com.teamwizardry.librarianlib.math.vector.Vec3i;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockPos.class)
public abstract class BlockPosMixin implements Vec3i {
}
