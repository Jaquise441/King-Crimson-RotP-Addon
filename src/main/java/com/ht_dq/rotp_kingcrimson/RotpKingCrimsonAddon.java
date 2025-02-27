package com.ht_dq.rotp_kingcrimson;

import com.ht_dq.rotp_kingcrimson.config.KCConfig;
import com.ht_dq.rotp_kingcrimson.init.*;
import com.ht_dq.rotp_kingcrimson.network.AddonPackets;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        InitStandEffects.STAND_EFFECTS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, KCConfig.SPEC, "rotp_kingcrimson.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        AddonPackets.init();
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
