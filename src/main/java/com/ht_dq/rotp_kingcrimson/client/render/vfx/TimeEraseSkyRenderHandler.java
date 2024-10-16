package com.ht_dq.rotp_kingcrimson.client.render.vfx;

import java.sql.Time;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.EndPortalTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ISkyRenderHandler;

public class TimeEraseSkyRenderHandler implements ISkyRenderHandler {
    private static final Random RANDOM = new Random(31100L);
    private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16)
            .mapToObj(i -> TimeEraseSkyRenderType.endPortalNoFog(i + 1))
            .collect(ImmutableList.toImmutableList());
    
    
    @Override
    public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {
        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        
        IRenderTypeBuffer renderTypeBuffer = Minecraft.getInstance().renderBuffers().bufferSource();
        
        for (int i = 0; i < 6; ++i) {
            matrixStack.pushPose();
            switch (i) {
            case 0:                                                                 break;
            case 1:     matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));    break;
            case 2:     matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));   break;
            case 3:     matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));   break;
            case 4:     matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));    break;
            case 5:     matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));   break;
            }

            Matrix4f matrix = matrixStack.last().pose();
            RANDOM.setSeed(31100L);
            int passes = 13; // 15 is max, but the effect is too intense on 15

            renderSide(matrix, 0.15f, renderTypeBuffer.getBuffer(RENDER_TYPES.get(0)));
            for (int j = 0; j < passes; j++) {
                renderSide(matrix, 2.0F / (float)(18 - j), renderTypeBuffer.getBuffer(RENDER_TYPES.get(j)));
            }

            matrixStack.popPose();
        }
        
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
    }
    
    private void renderSide(Matrix4f matrix4f, float p_228883_3_, IVertexBuilder vertexBuilder) {
        float r = (RANDOM.nextFloat() * 0.5F + 0.4F) * p_228883_3_;
        float g = (RANDOM.nextFloat() * 0.5F + 0F) * p_228883_3_;
        float b = (RANDOM.nextFloat() * 0.5F + 0.2F) * p_228883_3_;
        float dist = 100f;
        vertexBuilder.vertex(matrix4f, -dist, -dist, -dist).color(r, g, b, 1).endVertex();
        vertexBuilder.vertex(matrix4f, -dist, -dist,  dist).color(r, g, b, 1).endVertex();
        vertexBuilder.vertex(matrix4f,  dist, -dist,  dist).color(r, g, b, 1).endVertex();
        vertexBuilder.vertex(matrix4f,  dist, -dist, -dist).color(r, g, b, 1).endVertex();
    }
    
    
    
    private static class TimeEraseSkyRenderType extends RenderType {
        
        private TimeEraseSkyRenderType(String name, VertexFormat format, int mode, int bufferSize,
                boolean affectCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
            super(name, format, mode, bufferSize, affectCrumbling, sortOnUpload, setupState, clearState);
        }

        public static RenderType endPortalNoFog(int i) {
            RenderState.TransparencyState transparencyState;
            RenderState.TextureState textureState;
            if (i <= 1) {
                transparencyState = RenderState.TRANSLUCENT_TRANSPARENCY;
                textureState = new RenderState.TextureState(TimeEraseSkyRenderer.ERASE_SKY_LOCATION, true, false);
            } else {
                transparencyState = RenderState.ADDITIVE_TRANSPARENCY;
                textureState = new RenderState.TextureState(TimeEraseSkyRenderer.ERASE_PORTAL_LOCATION, true, false);
            }

            return RenderType.create("jojo_time_erase", DefaultVertexFormats.POSITION_COLOR, 7, 256, false, true, RenderType.State.builder()
                    .setTransparencyState(transparencyState)
                    .setTextureState(textureState)
                    .setTexturingState(new RenderState.PortalTexturingState(i))
                    .setFogState(RenderState.NO_FOG)
                    .createCompositeState(false));
        }
    }
    
}
