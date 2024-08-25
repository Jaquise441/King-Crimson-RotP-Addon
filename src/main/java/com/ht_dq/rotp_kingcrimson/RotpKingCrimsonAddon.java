package com.ht_dq.rotp_kingcrimson;

import com.ht_dq.rotp_kingcrimson.network.AddonPackets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ht_dq.rotp_kingcrimson.init.InitEntities;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.ht_dq.rotp_kingcrimson.init.InitStands;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RotpKingCrimsonAddon.MOD_ID)
public class RotpKingCrimsonAddon {
    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "rotp_kingcrimson";
    private static final Logger LOGGER = LogManager.getLogger();

    public RotpKingCrimsonAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);

        AddonPackets.init();
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
