package com.ht_dq.rotp_kingcrimson.mixin;

import com.ht_dq.rotp_kingcrimson.util.VFXServerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.ht_dq.rotp_kingcrimson.client.render.entity.AfterimageRenderer;

import net.minecraft.client.renderer.model.ModelRenderer;

import static com.ht_dq.rotp_kingcrimson.util.AddonReflection.ENTITY_RENDERER_ISRED;

@Mixin(ModelRenderer.class)
public class ModelPartMixin {

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IIFFFF)V", 
            at = @At("HEAD"), argsOnly = true, ordinal = 0)
    public float kcAfterimageColor0(float red) {
        return AfterimageRenderer.isRenderingKCAfterimage || ENTITY_RENDERER_ISRED ? red * 0.5f : red;
    }

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IIFFFF)V", 
            at = @At("HEAD"), argsOnly = true, ordinal = 1)
    public float kcAfterimageColor1(float green) {
        return AfterimageRenderer.isRenderingKCAfterimage || ENTITY_RENDERER_ISRED ? 0 : green;
    }
    
    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IIFFFF)V", 
            at = @At("HEAD"), argsOnly = true, ordinal = 2)
    public float kcAfterimageColor2(float blue) {
        return AfterimageRenderer.isRenderingKCAfterimage || ENTITY_RENDERER_ISRED ? 0 : blue;
    }
}
