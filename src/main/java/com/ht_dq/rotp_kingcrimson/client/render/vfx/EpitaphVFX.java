package com.ht_dq.rotp_kingcrimson.client.render.vfx;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.ResourceLocation;

public class EpitaphVFX {
    private static final ResourceLocation EPITAPH_SHADER = new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "shaders/post/epitaph.json");
    private static boolean shaderLoaded = false;
    
    public static void playerTick() {
        Minecraft mc = Minecraft.getInstance();
        if (!shaderLoaded) {
            RenderSystem.recordRenderCall(() -> {
                mc.gameRenderer.loadEffect(EPITAPH_SHADER);
            });
            shaderLoaded = true;
        }
    }
    
    public static void stopEffect() {
        Minecraft mc = Minecraft.getInstance();
        ((GameRenderer)(Object)mc.gameRenderer).shutdownEffect();
        shaderLoaded = false;
    }

}
