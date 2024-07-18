package top.circussmp.mod.mixin.voidtotem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.circussmp.mod.util.BlockUtils;
import top.circussmp.mod.util.LivingEntityUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Unique
    private boolean usedTotem;

    @Unique
    private DimensionType lastSafeDimension;
    @Unique
    private BlockPos lastSafePos;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "tryUseTotem", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private DamageSource handleVoidDamage(DamageSource source) {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            LivingEntityUtils.randomTeleport((LivingEntity) (Object) this, this.lastSafePos);

            this.usedTotem = true;

            return this.getWorld().getDamageSources().fall();
        }

        return source;
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void cancelVoidDamageAfterTotem(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (this.usedTotem) {
            this.usedTotem = false;
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        var world = this.getWorld();
        var dimension = world.getDimension();
        var pos = this.getBlockPos().down();

        if ((this.lastSafeDimension != dimension || this.lastSafePos != pos) && BlockUtils.isSaveForPlayerToStand(world, pos)) {
            this.lastSafeDimension = dimension;
            this.lastSafePos = pos;
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.usedTotem = nbt.getBoolean("used_totem");
        this.lastSafePos = BlockPos.fromLong(nbt.getLong("last_safe_pos"));
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (this.lastSafePos != null) {
            nbt.putBoolean("used_totem", this.usedTotem);
            nbt.putLong("last_safe_pos", this.lastSafePos.asLong());
        }
    }
}
