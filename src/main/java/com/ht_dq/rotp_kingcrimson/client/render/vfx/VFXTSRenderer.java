package com.ht_dq.rotp_kingcrimson.client.render.vfx;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.gui.AbstractGui;

@OnlyIn(Dist.CLIENT)
public class VFXTSRenderer {
    private static final String TEXTURE_PATH = "rotp_kingcrimson:textures/timeskip/time_skip";
    private final VFXTSHandler handler;
    private final TextureManager textureManager;

    public VFXTSRenderer(VFXTSHandler handler) {
        this.handler = handler;
        this.textureManager = Minecraft.getInstance().getTextureManager();
    }

    public void render(MatrixStack matrixStack, float partialTicks) {
        if (handler.isActive()) {
            int tick = handler.getTickCounter();
            ResourceLocation texture = new ResourceLocation(TEXTURE_PATH + (tick + 1) + ".png");
            textureManager.bind(texture);
            int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            AbstractGui.blit(matrixStack, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
        }
    }
}
