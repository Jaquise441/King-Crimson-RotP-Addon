package com.ht_dq.rotp_kingcrimson.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.ht_dq.rotp_kingcrimson.client.render.entity.AfterimageRenderer;

import net.minecraft.client.renderer.model.ModelRenderer;

@Mixin(ModelRenderer.class)
public class ModelPartMixin {

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IIFFFF)V", 
            at = @At("HEAD"), argsOnly = true, ordinal = 0)
    public float kcAfterimageColor0(float red) {
        return AfterimageRenderer.isRenderingKCAfterimage ? red * 0.5f : red;
    }

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IIFFFF)V", 
            at = @At("HEAD"), argsOnly = true, ordinal = 1)
    public float kcAfterimageColor1(float green) {
        return AfterimageRenderer.isRenderingKCAfterimage ? 0 : green;
    }
    
    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IIFFFF)V", 
            at = @At("HEAD"), argsOnly = true, ordinal = 2)
    public float kcAfterimageColor2(float blue) {
        return AfterimageRenderer.isRenderingKCAfterimage ? 0 : blue;
    }
}
