package xyz.mangostudio.smp.mod.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockUtils {
    public static boolean isSaveForPlayerToStand(World world, BlockPos pos) {
        return world.getBlockState(pos).isSolidBlock(world, pos);
    }
}
