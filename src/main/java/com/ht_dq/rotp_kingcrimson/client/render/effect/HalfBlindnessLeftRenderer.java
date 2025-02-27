package com.ht_dq.rotp_kingcrimson.client.render.effect;

import com.ht_dq.rotp_kingcrimson.init.InitEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rotp_kingcrimson")
public class HalfBlindnessLeftRenderer {

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();

        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            if (mc.player != null && mc.player.hasEffect(InitEffects.HALF_BLINDNESS_LEFT.get())) {
                RenderSystem.disableDepthTest();
                RenderSystem.color4f(0.0F, 0.0F, 0.0F, 1.0F);

                int screenWidth = mc.getWindow().getGuiScaledWidth();
                int screenHeight = mc.getWindow().getGuiScaledHeight();

                AbstractGui.fill(event.getMatrixStack(), 0, 0, screenWidth / 2, screenHeight, 0xFF000000);

                RenderSystem.enableDepthTest();
            }
        }
    }
}