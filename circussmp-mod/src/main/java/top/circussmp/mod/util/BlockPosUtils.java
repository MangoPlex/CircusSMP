package top.circussmp.mod.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public final class BlockPosUtils {
    public static @NotNull BlockPos getRandomAround(@NotNull LivingEntity entity, final @NotNull BlockPos pos) {
        var rand = entity.getRandom();
        var world = entity.getWorld();

        var x = pos.getX() + (rand.nextDouble() - 0.5D) * 16.0D;
        var y = MathHelper.clamp(pos.getY() + (double) (rand.nextInt(16) - 8), world.getBottomY(), world.getTopY() - 1);
        var z = pos.getZ() + (world.getRandom().nextDouble() - 0.5D) * 16.0D;

        return new BlockPos((int) x, (int) y, (int) z);
    }
}
