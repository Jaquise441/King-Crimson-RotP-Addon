package com.ht_dq.rotp_kingcrimson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ht_dq.rotp_kingcrimson.capability.CapabilityHandler;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import com.ht_dq.rotp_kingcrimson.init.InitEffects;
import com.ht_dq.rotp_kingcrimson.init.InitEntities;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.ht_dq.rotp_kingcrimson.init.InitStands;
import com.ht_dq.rotp_kingcrimson.network.AddonPackets;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RotpKingCrimsonAddon.MOD_ID)
public class RotpKingCrimsonAddon {
    public static final String MOD_ID = "rotp_kingcrimson";
    private static final Logger LOGGER = LogManager.getLogger();

    public RotpKingCrimsonAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitEntities.ENTITIES.register(modEventBus);
        InitEffects.EFFECTS.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);

        AddonPackets.init();

        modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        TimeSkipHandler.registerClientSideEvents();
        CapabilityHandler.registerCapabilities();
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
