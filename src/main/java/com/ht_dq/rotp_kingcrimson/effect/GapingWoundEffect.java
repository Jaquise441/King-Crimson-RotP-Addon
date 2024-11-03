package com.ht_dq.rotp_kingcrimson.effect;

import com.github.standobyte.jojo.init.ModParticles;
import com.github.standobyte.jojo.potion.UncurableEffect;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.init.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class GapingWoundEffect extends UncurableEffect {
    private static final int MAX_AMPLIFIER = 2;

    public GapingWoundEffect(int color) {
        super(EffectType.HARMFUL, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        int limitedAmplifier = Math.min(amplifier, MAX_AMPLIFIER);
    }

    public static void addGapingWoundParticles(Entity entity) {
        if (entity.level.isClientSide()) {
            int particlesCount = Math.max(MathHelper.ceil(entity.getBbWidth() * (entity.getBbHeight() * 1.5)), 1);
            for (int i = 0; i < particlesCount; i++) {
                entity.level.addParticle(ModParticles.BLOOD.get(), entity.getRandomX(1), entity.getRandomY(), entity.getRandomZ(1), 0, 0, 0);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID)
    public static class Events {
        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (entity.hasEffect(InitEffects.GAPING_WOUND.get())) {
                int amplifier = entity.getEffect(InitEffects.GAPING_WOUND.get()).getAmplifier();
                amplifier = Math.min(amplifier, MAX_AMPLIFIER);

                float armorReduction = 0.2F * (amplifier + 1); // 20% per level, temporary for now ig, will probably change later
                float reducedDamage = event.getAmount() * (1 + armorReduction);

                event.setAmount(reducedDamage);

                addGapingWoundParticles(entity);
            }
        }
    }
}
