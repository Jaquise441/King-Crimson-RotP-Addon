package com.ht_dq.rotp_kingcrimson.init;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InitVFX {


    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && TimeSkipHandler.isEffectActive(mc.player)) {
                int time = TimeSkipHandler.getRemainingTime(mc.player);
                ResourceLocation texture = TimeSkipHandler.getTextureForTime(time);
                int screenWidth = event.getWindow().getGuiScaledWidth();
                int screenHeight = event.getWindow().getGuiScaledHeight();

                TextureManager textureManager = mc.getTextureManager();
                textureManager.bind(texture);

                MatrixStack matrixStack = event.getMatrixStack();
                AbstractGui.blit(matrixStack, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
            }
        }
    }


    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            TimeSkipHandler.tick();
        }
    }
}
