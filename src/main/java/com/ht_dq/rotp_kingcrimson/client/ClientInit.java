package com.ht_dq.rotp_kingcrimson.client;

import com.ht_dq.rotp_kingcrimson.client.render.vfx.ClientEventHandler2;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TemporaryDimensionEffects;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = Minecraft.getInstance();
        event.enqueueWork(() -> {
            ClientEventHandler2.init(mc);
            TemporaryDimensionEffects.init();
        });
    }
}