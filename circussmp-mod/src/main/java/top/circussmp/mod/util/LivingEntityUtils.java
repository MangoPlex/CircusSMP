package top.circussmp.mod.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public final class LivingEntityUtils {
    public static void randomTeleport(LivingEntity entity, BlockPos targetPos) {
        var success = false;

        for (int i = 0; i < 16; i++) {
            var pos = BlockPosUtils.getRandomAround(entity, targetPos);

            if (entity.teleport(pos.getX(), pos.getY(), pos.getZ(), false)) {
                success = true;
                break;
            }
        }

        if (!success) {
            entity.requestTeleport(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ());
        }
    }
}
