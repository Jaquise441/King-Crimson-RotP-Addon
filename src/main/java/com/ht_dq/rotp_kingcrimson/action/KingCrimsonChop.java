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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class KingCrimsonChop extends StandEntityLightAttack {
    public static final StandPose CHOP = new StandPose("kingcrimson_chop");

    public KingCrimsonChop(Builder builder) {
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
        return new KingcrimsonChopInstance(stand, target, dmgSource)
                .copyProperties(super.punchEntity(stand, target, dmgSource))
                .armorPiercing((float) stand.getAttackDamage() * 0.01F)
                .disableBlocking((float) stand.getProximityRatio(target) - 0.25F)
                .impactSound(InitSounds.KINGCRIMSON_CHOP)
                .damage((float) stand.getAttackDamage() * 1.25F)
                .reduceKnockback(15.0F);

    }

    public static class KingcrimsonChopInstance extends LightPunchInstance {

        public KingcrimsonChopInstance(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
            super(stand, target, dmgSource);
        }

        @Override
        protected void afterAttack(StandEntity stand, Entity target, StandEntityDamageSource dmgSource, StandEntityTask task, boolean hurt, boolean killed) {
            super.afterAttack(stand, target, dmgSource, task, hurt, killed);

            if (hurt && target instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity) target;

                livingTarget.addEffect(new EffectInstance(ModStatusEffects.BLEEDING.get(), 80, 0, false, false));
                livingTarget.addEffect(new EffectInstance(InitEffects.BLEEDING.get(), 80, 1, false, false));
                livingTarget.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 80, 3, false, false));
                if (target instanceof PlayerEntity) {
                    livingTarget.addEffect(new EffectInstance(InitEffects.MANGLED_BODY.get(), 80, 1, false, false));
                }
            }
        }
    }
}
