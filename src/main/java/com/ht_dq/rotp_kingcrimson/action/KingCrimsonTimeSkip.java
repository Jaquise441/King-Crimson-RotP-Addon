package com.ht_dq.rotp_kingcrimson.action;

import java.util.List;
import java.util.stream.Collectors;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.util.VFXServerHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class KingCrimsonTimeSkip extends StandEntityAction {

    public KingCrimsonTimeSkip(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        return ActionConditionResult.noMessage(KingCrimsonTimeErase.playerTimeEraseActive.isEmpty());
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        LivingEntity user = userPower.getUser();
        if (user == null) return;

        VFXServerHelper.startVFX(user, true);

        List<Entity> entities = world.getEntities(standEntity, standEntity.getBoundingBox().inflate(32))
                .stream()
                .filter(entity -> entity instanceof PlayerEntity || entity instanceof LivingEntity)
                .collect(Collectors.toList());

        for (int i = 0; i < 200; i++) {
            for (Entity entity : entities) {
                entity.tick();
            }
        }
    }
}
