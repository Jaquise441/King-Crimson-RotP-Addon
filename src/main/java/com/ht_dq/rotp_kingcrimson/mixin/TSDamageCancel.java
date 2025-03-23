package com.ht_dq.rotp_kingcrimson.mixin;

import com.ht_dq.rotp_kingcrimson.action.KingCrimsonTimeSkip;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class TSDamageCancel {

    @Inject(
            method = "hurt",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            if (KingCrimsonTimeSkip.getActiveTimeSkips().containsKey(player.getUUID())) {
                cir.setReturnValue(false);
            }
        }
    }
}