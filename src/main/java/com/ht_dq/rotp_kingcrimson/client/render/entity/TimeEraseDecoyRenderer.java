package com.ht_dq.rotp_kingcrimson.client.render.entity;

import com.ht_dq.rotp_kingcrimson.entity.TimeEraseDecoyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

// TODO use the stand user's model
// TODO render layers
// TODO render name tag
//public class TimeEraseDecoyRenderer extends EntityRenderer<TimeEraseDecoyEntity> {
public class TimeEraseDecoyRenderer extends BipedRenderer<TimeEraseDecoyEntity, PlayerModel<TimeEraseDecoyEntity>> {

    public TimeEraseDecoyRenderer(EntityRendererManager renderManager) {
//        super(renderManager);
        super(renderManager, new PlayerModel<>(0, false), 0.5F);
    }

    private static final ResourceLocation DEFAULT_LOCATION = new ResourceLocation("textures/entity/steve.png");
    @Override
    public ResourceLocation getTextureLocation(TimeEraseDecoyEntity p_110775_1_) {
//        return null;
        return DEFAULT_LOCATION;
    }

    @Override
    public void render(TimeEraseDecoyEntity decoyEntity, float yRotation, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        super.render(decoyEntity, yRotation, partialTick, matrixStack, buffer, packedLight);
    }

}
