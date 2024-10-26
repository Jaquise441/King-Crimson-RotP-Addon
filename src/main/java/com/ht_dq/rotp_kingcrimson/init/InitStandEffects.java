package com.ht_dq.rotp_kingcrimson.init;

import com.github.standobyte.jojo.action.stand.effect.StandEffectType;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.action.KingCrimsonEpitaph;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStandEffects {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandEffectType<?>> STAND_EFFECTS = DeferredRegister.create(StandEffectType.class, RotpKingCrimsonAddon.MOD_ID);

    
    public static final RegistryObject<StandEffectType<KingCrimsonEpitaph.EpitaphEffect>> EPITAPH = STAND_EFFECTS.register(
            "epitaph", () -> new StandEffectType<>(KingCrimsonEpitaph.EpitaphEffect::new));
}
