package com.ht_dq.rotp_kingcrimson.effect;

import com.github.standobyte.jojo.init.ModParticles;
import com.github.standobyte.jojo.potion.UncurableEffect;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.init.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class BleedingEffect extends UncurableEffect {
    public BleedingEffect(int color) {
        super(EffectType.HARMFUL, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide()) {
            if (amplifier == 0) amplifier = 1;
            if (entity.tickCount % amplifier == 0)
                if (entity.isAlive())
                    entity.hurt(DamageSource.MAGIC, (float) amplifier / 2);
        }
    }

    public static void addParticlesAround(Entity entity) {
        if (entity.level.isClientSide()) {
            int particlesCount = Math.max(MathHelper.ceil(entity.getBbWidth() * (entity.getBbHeight() * 2 * entity.getBbHeight())), 1);
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
        public static void onLivingHeal(LivingHealEvent event) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (entity.hasEffect(InitEffects.BLEEDING.get()))
                event.setCanceled(true);
        }
    }
}
