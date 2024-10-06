package com.ht_dq.rotp_kingcrimson.init;

import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InitVFX {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            if (mc.player != null && TimeSkipHandler.isEffectActive(mc.player)) {
                long remainingTime = TimeSkipHandler.getRemainingTime(mc.player);
                ResourceLocation texture = getTextureForRemainingTime(remainingTime);
                int screenWidth = event.getWindow().getGuiScaledWidth();
                int screenHeight = event.getWindow().getGuiScaledHeight();

                TextureManager textureManager = mc.getTextureManager();
                textureManager.bind(texture);

                MatrixStack matrixStack = event.getMatrixStack();
                AbstractGui.blit(matrixStack, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
            }
        }
    }

    public static ResourceLocation getTextureForRemainingTime(long remainingTime) {
        int frameDuration = 23;
        int maxFrames = 15;
        int imageIndex = Math.min(maxFrames, Math.max(0, (int) (remainingTime / frameDuration)));
        return new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "textures/timeskip/time_skip" + imageIndex + ".png");
    }
    public static ResourceLocation getTextureForEptiaphTime() {
        return new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "textures/entity/epitaph_vfx" + ".png");
}
    }