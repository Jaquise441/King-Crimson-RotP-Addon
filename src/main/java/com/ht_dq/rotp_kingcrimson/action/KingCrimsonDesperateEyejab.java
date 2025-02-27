package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.StandStatFormulas;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.ht_dq.rotp_kingcrimson.config.KCConfig;
import com.ht_dq.rotp_kingcrimson.init.InitEffects;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

import java.util.Random;

public class KingCrimsonDesperateEyejab extends StandEntityHeavyAttack {
    public static final StandPose JAB = new StandPose("kingcrimson_jab");

    public KingCrimsonDesperateEyejab(Builder builder) {
        super(builder);
    }

    @Override
    public int getStandWindupTicks(IStandPower standPower, StandEntity standEntity) {
        return 10;
    }

    @Override
    public int getStandRecoveryTicks(IStandPower standPower, StandEntity standEntity) {
        return StandStatFormulas.getHeavyAttackRecovery(standEntity.getAttackSpeed(), standEntity.getLastHeavyFinisherValue());
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        return new KingCrimsonDesperateEyejab.KingcrimsonEyejabInstance(stand, target, dmgSource)
                .copyProperties(super.punchEntity(stand, target, dmgSource))
                .armorPiercing((float) stand.getAttackDamage() * 0.01F)
                .disableBlocking((float) stand.getProximityRatio(target) - 0.25F)
                .impactSound(InitSounds.KINGCRIMSON_IMPALE)
                .damage((float) stand.getAttackDamage() * 0.90F)
                .reduceKnockback(15.0F);

    }

    @Override
    protected void onTaskStopped(World world, StandEntity standEntity, IStandPower standPower, StandEntityTask task, StandEntityAction newAction) {
        super.onTaskStopped(world, standEntity, standPower, task, newAction);

        standPower.setCooldownTimer(this, KCConfig.DESPERATE_EYEJAB_COOLDOWN.get());
    }

    public static class KingcrimsonEyejabInstance extends HeavyPunchInstance {

        public KingcrimsonEyejabInstance(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
            super(stand, target, dmgSource);
        }

        @Override
        protected void afterAttack(StandEntity stand, Entity target, StandEntityDamageSource dmgSource, StandEntityTask task, boolean hurt, boolean killed) {
            super.afterAttack(stand, target, dmgSource, task, hurt, killed);

            if (hurt && target instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity) target;

                Random random = new Random();
                if (random.nextBoolean()) {
                    livingTarget.addEffect(new EffectInstance(InitEffects.HALF_BLINDNESS_LEFT.get(), 60, 0, false, false));
                } else {
                    livingTarget.addEffect(new EffectInstance(InitEffects.HALF_BLINDNESS_RIGHT.get(), 60, 0, false, false));
                }

                livingTarget.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 80, 0, false, false));
                livingTarget.addEffect(new EffectInstance(Effects.BLINDNESS, 80, 9, false, false));
            }
        }
    }
}