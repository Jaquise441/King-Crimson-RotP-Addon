package com.ht_dq.rotp_kingcrimson.client;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.client.render.entity.AfterimageRenderer;
import com.ht_dq.rotp_kingcrimson.client.render.entity.TimeEraseDecoyRenderer;
import com.ht_dq.rotp_kingcrimson.client.render.entity.renderer.stand.KingCrimsonRenderer;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.ClientEventHandler;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TemporaryDimensionEffects;
import com.ht_dq.rotp_kingcrimson.init.AddonStands;
import com.ht_dq.rotp_kingcrimson.init.InitEntities;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = Minecraft.getInstance();
        RenderingRegistry.registerEntityRenderingHandler(AddonStands.KINGCRIMSON.getEntityType(), KingCrimsonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.KC_AFTERIMAGE.get(), AfterimageRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.TIME_ERASE_DECOY.get(), TimeEraseDecoyRenderer::new);
        event.enqueueWork(() -> {
            ClientEventHandler.init(mc);
            TemporaryDimensionEffects.init();
        });
    }
}
