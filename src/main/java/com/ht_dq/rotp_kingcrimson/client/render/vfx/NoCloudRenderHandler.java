package com.ht_dq.rotp_kingcrimson.client.render.vfx;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.client.ICloudRenderHandler;

public class NoCloudRenderHandler implements ICloudRenderHandler {
    @Override
    public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc, double cameraX, double cameraY, double cameraZ) {
    }
}

