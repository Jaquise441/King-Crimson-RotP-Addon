package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.StandStatFormulas;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.ht_dq.rotp_kingcrimson.init.InitEffects;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class KingCrimsonImpale extends StandEntityLightAttack {
    public static final StandPose IMPALE = new StandPose("kingcrimson_impale");

    public KingCrimsonImpale(Builder builder) {
        super(builder);
    }


    @Override
    public int getStandWindupTicks(IStandPower standPower, StandEntity standEntity) {
        return StandStatFormulas.getHeavyAttackWindup(standEntity.getAttackSpeed(), standEntity.getFinisherMeter());
    }

    @Override
    public int getStandRecoveryTicks(IStandPower standPower, StandEntity standEntity) {
        return StandStatFormulas.getHeavyAttackRecovery(standEntity.getAttackSpeed(), standEntity.getLastHeavyFinisherValue());
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        return new KingcrimsonImpaleInstance(stand, target, dmgSource)
                .copyProperties(super.punchEntity(stand, target, dmgSource))
                .armorPiercing((float) stand.getAttackDamage() * 0.01F)
                .disableBlocking((float) stand.getProximityRatio(target) - 0.25F)
                .impactSound(InitSounds.KINGCRIMSON_IMPALE)
                .damage((float) stand.getAttackDamage() * 1.0F)
                .reduceKnockback(15.0F);

    }

    public static class KingcrimsonImpaleInstance extends LightPunchInstance {

        public KingcrimsonImpaleInstance(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
            super(stand, target, dmgSource);
        }

        @Override
        protected void afterAttack(StandEntity stand, Entity target, StandEntityDamageSource dmgSource, StandEntityTask task, boolean hurt, boolean killed) {
            super.afterAttack(stand, target, dmgSource, task, hurt, killed);

            if (hurt && target instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity) target;

                livingTarget.addEffect(new EffectInstance(ModStatusEffects.BLEEDING.get(), 140, 1, false, false));
                livingTarget.addEffect(new EffectInstance(InitEffects.BLEEDING.get(), 140, 1, false, false));
                livingTarget.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 140, 3, false, false));
                livingTarget.addEffect(new EffectInstance(Effects.WEAKNESS, 140, 3, false, false));
                livingTarget.addEffect(new EffectInstance(InitEffects.GAPING_WOUND.get(), 140, 1, false, false));
            }
        }
    }
}

