package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.config.KCConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class KingCrimsonDoppioBarrage extends StandEntityMeleeBarrage {

    public KingCrimsonDoppioBarrage(Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        LivingEntity user = power.getUser();
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            if (!player.isCreative() && power.getResolveLevel() >= 3) {
                if (player.getHealth() / player.getMaxHealth() > KCConfig.BARRAGE_HEALTH_THRESHOLD_DOPPIO.get()) {
                    return conditionMessage("barrage_health_doppio");
                }
            }
        }
        return ActionConditionResult.POSITIVE;
    }
}