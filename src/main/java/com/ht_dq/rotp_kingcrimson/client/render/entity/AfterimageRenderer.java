package com.ht_dq.rotp_kingcrimson.client.render.entity;

import com.ht_dq.rotp_kingcrimson.entity.KCAfterimageEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class AfterimageRenderer<T extends KCAfterimageEntity> extends EntityRenderer<T> {
    // it just works
    public static boolean isRenderingKCAfterimage = false;
    public static boolean renderKCAfterimageRed = false;
    public static boolean renderLivingEntityRed = false;

    public AfterimageRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return null;
    }

    @Override
    public void render(T afterimageEntity, float yRotation, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        if (!afterimageEntity.isInvisibleTo(Minecraft.getInstance().player)) {
            Entity originEntity = afterimageEntity.getOriginEntity();
            if (originEntity != null) {
                isRenderingKCAfterimage = true;
                renderKCAfterimageRed = afterimageEntity.isRedOnly();
                renderAfterimage(originEntity, afterimageEntity,
                        yRotation, partialTick, matrixStack, buffer, packedLight);
                renderKCAfterimageRed = false;
                isRenderingKCAfterimage = false;
            }
            super.render(afterimageEntity, yRotation, partialTick, matrixStack, buffer, packedLight);
        }
    }

    private <E extends Entity, M extends EntityModel<E>> void renderAfterimage(E entity, KCAfterimageEntity afterimageEntity,
                                                                               float yRotation, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
//        LivingRenderer<E, M> renderer = (LivingRenderer<E, M>) entityRenderDispatcher.getRenderer(entity);
        EntityRenderer<E> renderer = (EntityRenderer<E>) entityRenderDispatcher.getRenderer(entity);
        renderer.render(entity, yRotation, partialTick, matrixStack, buffer, packedLight);
        /*if (MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Pre<E, M>(entity, renderer, partialTick, matrixStack, buffer, packedLight))) return;

        M model = renderer.getModel();
        matrixStack.pushPose();
        model.attackTime = 0;

        model.riding = false;
        model.young = entity.isBaby();
        float yHeadRotation = MathHelper.rotLerp(partialTick, afterimageEntity.getOriginEntity().yHeadRotO, afterimageEntity.getOriginEntity().yHeadRot);
        float yBodyRotation = MathHelper.rotLerp(partialTick, afterimageEntity.getOriginEntity().yBodyRotO, afterimageEntity.getOriginEntity().yBodyRot);
        float f2 = yHeadRotation - yBodyRotation;

        float xRotation = MathHelper.lerp(partialTick, afterimageEntity.xRotO, afterimageEntity.xRot);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - yBodyRotation));

        float ticks = entity.tickCount + partialTick;
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.translate(0.0D, (double)-1.501F, 0.0D);

        model.prepareMobModel(entity, 0, 0, partialTick);
        model.setupAnim(entity, 0, 0, ticks, f2, xRotation);
        RenderType rendertype = RenderType.itemEntityTranslucentCull(renderer.getTextureLocation(entity));
        if (rendertype != null) {
            IVertexBuilder ivertexbuilder = buffer.getBuffer(rendertype);
            int i = OverlayTexture.pack(OverlayTexture.u(0.5F), OverlayTexture.v(false));
            float alpha = 50;
            model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, i, 1.0F, 1.0F, 0.0F, alpha);
        }

        matrixStack.popPose();
        MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post<E, M>(entity, renderer, partialTick, matrixStack, buffer, packedLight));*/
    }
}