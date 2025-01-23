package com.ht_dq.rotp_kingcrimson.client.render.entity;

import com.ht_dq.rotp_kingcrimson.entity.TimeEraseDecoyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class TimeEraseDecoyRenderer extends EntityRenderer<TimeEraseDecoyEntity> {

    public TimeEraseDecoyRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getTextureLocation(TimeEraseDecoyEntity p_110775_1_) {
        return null;
    }

    @Override
    public void render(TimeEraseDecoyEntity decoyEntity, float yRotation, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        renderDecoy(decoyEntity.getKCUserToRender(), decoyEntity, yRotation, partialTick, matrixStack, buffer, packedLight);
    }
    
    private <E extends LivingEntity, M extends EntityModel<E>> void renderDecoy(E realEntity, TimeEraseDecoyEntity decoyEntity, float yRotation, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        if (realEntity == null) return;
        LivingRenderer<E, M> renderer = (LivingRenderer<E, M>) entityRenderDispatcher.getRenderer(realEntity);
        renderer.render(realEntity, yRotation, partialTick, matrixStack, buffer, packedLight);
    }

}
