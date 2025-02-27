package com.ht_dq.rotp_kingcrimson.mixin;


import com.ht_dq.rotp_kingcrimson.client.render.vfx.ClientEventHandler;
import net.minecraft.client.particle.TexturedParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TexturedParticle.class)
public class TexturedParticleMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void jojoTimeEraseCancelParticlesRender(CallbackInfo ci) {
        if (ClientEventHandler.isErasingTime()) {
            ci.cancel();
        }
    }
}
