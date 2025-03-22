package com.ht_dq.rotp_kingcrimson.client.render.vfx;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID, value = Dist.CLIENT)
public class EpitaphVFX {
    private static final Minecraft mc = Minecraft.getInstance();
    private static boolean isShaderLoaded = false;
    private static PointOfView oldF5Mode;
    private static boolean enabled = false;
    @Nullable
    private static ResourceLocation shaderTexture;

    public static void enableShader(ResourceLocation shaderTextureToRender) {
        shaderTexture = shaderTextureToRender;
        setEnabled(true);
    }

    public static void shutdownShader() {
        mc.gameRenderer.shutdownEffect();
        isShaderLoaded = false;
        setEnabled(false);
        shaderTexture = null;
    }

    public static void onShaderTick() {
        if (!enabled) {
            shutdownShader();
            return;
        }
        if (oldF5Mode != mc.options.getCameraType()) {
            setIsShaderLoaded(false);
        }
        oldF5Mode = mc.options.getCameraType();
        renderShaders();
    }

    private static void renderShaders() {
        if (!isShaderLoaded && shaderTexture != null) {
            RenderSystem.recordRenderCall(() -> mc.gameRenderer.loadEffect(shaderTexture));
            setIsShaderLoaded(true);
        }
    }

    public static void setEnabled(boolean set) {
        enabled = set;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setIsShaderLoaded(boolean set) {
        isShaderLoaded = set;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.level != null) {
            onShaderTick();
        }
    }
}