package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class KingCrimsonTimeSkip extends StandEntityAction {

    public KingCrimsonTimeSkip(AbstractBuilder<?> builder) {
        super(builder);
    }



    @Mod.EventBusSubscriber
    public static class KingCrimsonAbility extends StandEntityAction {

        public KingCrimsonAbility(StandEntityAction.Builder builder) {
            super(builder);
        }

        @Override
        protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
            return  KingCrimsonTimeErase.playerTimeEraseActive.isEmpty() ?ActionConditionResult.POSITIVE:ActionConditionResult.NEGATIVE;
        }

        @Override
        public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
            PlayerEntity player = (PlayerEntity) userPower.getUser();

            TimeSkipHandler.startVFX(player, true);

            List<Entity> entities = world.getEntities(standEntity, standEntity.getBoundingBox().inflate(32))
                    .stream()
                    .filter(entity -> entity instanceof PlayerEntity || entity instanceof LivingEntity)
                    .collect(Collectors.toList());

            for (Entity entity : entities) {
                for (int i = 0; i < 200; i++) {
                    entity.tick();
                }
            }

        }

    }
}
