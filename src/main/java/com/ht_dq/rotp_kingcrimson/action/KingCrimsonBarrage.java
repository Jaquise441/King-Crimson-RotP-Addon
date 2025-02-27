package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.config.KCConfig;
import com.ht_dq.rotp_kingcrimson.init.InitStands;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class KingCrimsonBarrage extends StandEntityMeleeBarrage {

    public KingCrimsonBarrage(Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        LivingEntity user = power.getUser();
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            if (!player.isCreative() && power.getResolveLevel() >= 3) {
                if (player.getHealth() / player.getMaxHealth() > KCConfig.BARRAGE_HEALTH_THRESHOLD.get()) {
                    return conditionMessage("barrage_health");
                }
            }
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        if (!(power.isActive() && power.getStandManifestation() instanceof StandEntity)) {
            return InitStands.KINGCRIMSON_DOPPIO_BARRAGE.get();
        }
        return super.replaceAction(power, target);
    }

    @Override
    public StandAction[] getExtraUnlockable() {
        return new StandAction[] { InitStands.KINGCRIMSON_DOPPIO_BARRAGE.get() };
    }
}