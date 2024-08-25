package com.ht_dq.rotp_kingcrimson.mixin;

import com.ht_dq.rotp_kingcrimson.client.render.vfx.ClientEventHandler2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.WorldRenderer;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "renderChunkLayer", at = @At("HEAD"), cancellable = true)
    private void jojoTimeEraseCancelBlocksRender(CallbackInfo ci) {
        if (ClientEventHandler2.isErasingTime()) {
            ci.cancel();
        }
    }

}
