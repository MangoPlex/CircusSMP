package xyz.mangostudio.smp.mixin.entity.boss.dragon;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

@Mixin(EnderDragonFight.class)
public class EnderDragonFightMixin {
    @Shadow @Final private ServerWorld world;
    @Unique
    private boolean secondPhrase;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void getSecondPhrase(ServerWorld world, long gatewaysSeed, NbtCompound nbt, CallbackInfo ci) {
        secondPhrase = nbt.getBoolean("secondPhrase");
    }

    @Inject(method = "toNbt", at = @At("RETURN"))
    private void getSecondPhrase(CallbackInfoReturnable<NbtCompound> cir) {
        NbtCompound compound = cir.getReturnValue();
        compound.putBoolean("secondPhrase", secondPhrase);
    }

    @Inject(method = "updateFight", at = @At("HEAD"))
    private void checkPhraseOnUpdate(EnderDragonEntity enderDragonEntity, CallbackInfo ci) {
        if (!secondPhrase & enderDragonEntity.getHealth() < 200) {
            enderDragonEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 100000, 255, false, false, false));
            enderDragonEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 36, 1, false, false, false));
            enderDragonEntity.setHealth(enderDragonEntity.getMaxHealth());
            secondPhrase = true;

            world.getEntitiesByClass(PlayerEntity.class, enderDragonEntity.getBoundingBox().expand(200), player -> true).forEach(player -> {
                LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                lightning.setPos(player.getX(), player.getY(), player.getZ());

                world.spawnEntity(lightning);
            });
        }

        if (secondPhrase && !world.isClient()) {
            world.spawnParticles(ParticleTypes.DRAGON_BREATH, enderDragonEntity.getX(), enderDragonEntity.getY() + 2, enderDragonEntity.getZ(), 100, 0, 0, 0, 0.1);
            world.spawnParticles(ParticleTypes.END_ROD, enderDragonEntity.getX(), enderDragonEntity.getY() + 2, enderDragonEntity.getZ(), 100, 0, 0, 0, 0.1);
        }
    }

    @Inject(method = "dragonKilled", at = @At("HEAD"))
    private void checkPhraseOnKilled(EnderDragonEntity enderDragonEntity, CallbackInfo ci) {
        secondPhrase = false;
    }
}
