package com.ht_dq.rotp_kingcrimson.mixin;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.ht_dq.rotp_kingcrimson.capability.LivingUtilCapProvider;
import com.mojang.blaze3d.matrix.MatrixStack;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

    @Inject(method = "renderNameTag", at = @At("HEAD"), cancellable = true)
    public void onRenderNameTag(AbstractClientPlayerEntity p_225629_1_, ITextComponent p_225629_2_, MatrixStack p_225629_3_, IRenderTypeBuffer p_225629_4_, int p_225629_5_, CallbackInfo ci) {
        p_225629_1_.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(cap -> {
            if (cap.isConcealIdentityActive()) {
                ci.cancel();
            }
        });
    }
}
