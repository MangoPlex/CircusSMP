package top.circussmp.mod.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockUtils {
    public static boolean isSaveForPlayerToStand(World world, BlockPos pos) {
        return world.getBlockState(pos).isSolidBlock(world, pos);
    }
}
