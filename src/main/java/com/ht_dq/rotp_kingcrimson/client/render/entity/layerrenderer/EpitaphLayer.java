package com.ht_dq.rotp_kingcrimson.client.render.entity.layerrenderer;

import com.github.standobyte.jojo.client.render.entity.layerrenderer.IFirstPersonHandLayer;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.action.KingCrimsonEpitaph;
import com.ht_dq.rotp_kingcrimson.init.InitStands;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class EpitaphLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> implements IFirstPersonHandLayer {
    public static final ResourceLocation TEXTURE = new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "textures/entity/epitaph.png");

    public EpitaphLayer(IEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight,
                       T entity, float walkAnimPos, float walkAnimSpeed, float partialTick,
                       float ticks, float headYRotation, float headXRotation) {
        if (shouldRenderEpitaphLayer(entity)) {
            M model = getParentModel();
            IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.entityTranslucent(TEXTURE));
            model.renderToBuffer(matrixStack, vertexBuilder, packedLight, LivingRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private boolean shouldRenderEpitaphLayer(LivingEntity entity) {
        if (entity instanceof AbstractClientPlayerEntity) {
            IStandPower power = IStandPower.getPlayerStandPower((AbstractClientPlayerEntity) entity);
            return power.getHeldAction() == InitStands.KINGCRIMSON_EPITAPH.get() && !power.isActive();
        }
        return false;
    }

    @Override
    public void renderHandFirstPerson(HandSide side, MatrixStack matrixStack,
                                      IRenderTypeBuffer buffer, int light, AbstractClientPlayerEntity player,
                                      PlayerRenderer playerRenderer) {
        if (shouldRenderEpitaphLayer(player)) {
            PlayerModel<AbstractClientPlayerEntity> model = playerRenderer.getModel();
            IFirstPersonHandLayer.defaultRender(side, matrixStack, buffer, light, player, playerRenderer,
                    model, TEXTURE);
        }
    }
}