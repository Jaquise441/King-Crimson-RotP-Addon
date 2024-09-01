package com.ht_dq.rotp_kingcrimson.action;

import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.server.ServerWorld;

public class kingcrimson_chop extends StandEntityHeavyAttack {
    public static final StandPose initActionPosesChop = new StandPose("kingcrimson_chop");

    private static final EffectInstance BLEEDING_EFFECT = new EffectInstance(Effects.WEAKNESS, 200);

    public kingcrimson_chop(Builder builder) {
        super(builder);
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        return new KingcrimsonChopInstance(stand, target, dmgSource)
                .copyProperties(super.punchEntity(stand, target, dmgSource))
                .armorPiercing((float) stand.getAttackDamage() * 0.01F)
                .disableBlocking((float) stand.getProximityRatio(target) - 0.25F)
                .impactSound(InitSounds.KINGCRIMSON_CHOP);
    }

    public static class KingcrimsonChopInstance extends HeavyPunchInstance {

        public KingcrimsonChopInstance(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
            super(stand, target, dmgSource);
        }

        public static boolean isUndead(LivingEntity entity) {
            return entity.getType() == EntityType.ZOMBIE ||
                    entity.getType() == EntityType.SKELETON ||
                    entity.getType() == EntityType.SKELETON_HORSE ||
                    entity.getType() == EntityType.STRAY ||
                    entity.getType() == EntityType.HUSK ||
                    entity.getType() == EntityType.DROWNED ||
                    entity.getType() == EntityType.ZOMBIE_VILLAGER ||
                    entity.getType() == EntityType.ZOMBIE_HORSE ||
                    entity.getType() == EntityType.ZOMBIFIED_PIGLIN ||
                    entity.getType() == EntityType.ZOGLIN;
        }

        public static boolean isWitherResistant(LivingEntity entity) {
            return entity.getType() == EntityType.WITHER_SKELETON ||
                    entity.getType() == EntityType.WITHER;
        }

        @Override
        protected void afterAttack(StandEntity stand, Entity target, StandEntityDamageSource dmgSource, StandEntityTask task, boolean hurt, boolean killed) {
            super.afterAttack(stand, target, dmgSource, task, hurt, killed);

            if (hurt && target instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity) target;
                ServerWorld serverWorld = (ServerWorld) stand.level;

                livingTarget.addEffect(new EffectInstance(BLEEDING_EFFECT)); // makes a deep copy of the static EffectInstance object

                applyEffectsImmediately(livingTarget);
            }
        }

        private void applyEffectsImmediately(LivingEntity livingTarget) {
            if (livingTarget.isAlive()) {
                if (isUndead(livingTarget)) {
                    livingTarget.addEffect(new EffectInstance(Effects.HEAL, 1, 0, false, false));
                    livingTarget.addEffect(new EffectInstance(Effects.WITHER, 100, 2, false, false));
                } else if (isWitherResistant(livingTarget)) {
                    livingTarget.addEffect(new EffectInstance(Effects.HEAL, 1, 0, false, false));
                    livingTarget.addEffect(new EffectInstance(Effects.REGENERATION, 100, 2, false, false));
                } else {
                    livingTarget.addEffect(new EffectInstance(Effects.HARM, 1, 1, false, false));
                    livingTarget.addEffect(new EffectInstance(Effects.WITHER, 100, 2, false, false));
                }
            }
        }
    }
}