package com.ht_dq.rotp_kingcrimson.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.ht_dq.rotp_kingcrimson.action.KingCrimsonTimeErase;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "addParticle(Lnet/minecraft/particles/IParticleData;DDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void jojoTimeEraseCancelParticlesRender(IParticleData p_195594_1_, double p_195594_2_, double p_195594_4_, double p_195594_6_, double p_195594_8_, double p_195594_10_, double p_195594_12_, CallbackInfo ci) {
        ClientWorld clientWorld = (ClientWorld) (Object) this;
        if (clientWorld.players().stream().anyMatch(KingCrimsonTimeErase::isErasingTime) && p_195594_1_.getType() == ParticleTypes.BLOCK) {
            ci.cancel();
        }
    }
}
