package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.capability.LivingUtilCapProvider;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class KingCrimsonConcealIdentity extends StandEntityAction {

    public KingCrimsonConcealIdentity(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        PlayerEntity player = (PlayerEntity) userPower.getUser();

        if (!world.isClientSide()) {
            player.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(cap -> {
                cap.setConcealIdentify(!cap.isConcealIdentityActive());
                boolean isActive = cap.isConcealIdentityActive();
                System.out.println("ConcealIdentity: " + isActive);
            });
        }
    }

    @Override
    public boolean greenSelection(IStandPower power, ActionConditionResult conditionCheck) {
        return power.getUser().getCapability(LivingUtilCapProvider.CAPABILITY).map(cap -> cap.isConcealIdentityActive()).orElse(false);
    }
}
