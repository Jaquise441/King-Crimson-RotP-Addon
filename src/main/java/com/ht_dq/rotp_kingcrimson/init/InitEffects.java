package com.ht_dq.rotp_kingcrimson.init;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.effect.*;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(
            ForgeRegistries.POTIONS,
            RotpKingCrimsonAddon.MOD_ID
    );

    public static final RegistryObject<Effect> BLEEDING = EFFECTS.register("bleeding",
            () -> new BleedingEffect(0xc7615a)
    );

    public static final RegistryObject<Effect> GAPING_WOUND = EFFECTS.register("gaping_wound",
            () -> new GapingWoundEffect(0xc7615a)
    );

    public static final RegistryObject<Effect> MANGLED_BODY = EFFECTS.register("mangled_body",
            () -> new MangledBodyEffect(0xc7615a)
    );

    public static final RegistryObject<Effect> HALF_BLINDNESS_LEFT = EFFECTS.register("half_blindness_left",
            () -> new HalfBlindnessLeftEffect(0xcFFFFF)
    );

    public static final RegistryObject<Effect> HALF_BLINDNESS_RIGHT = EFFECTS.register("half_blindness_right",
            () -> new HalfBlindnessRightEffect(0xcFFFFF)
    );
}
