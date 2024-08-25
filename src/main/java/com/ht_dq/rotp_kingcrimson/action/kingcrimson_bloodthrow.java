package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class kingcrimson_bloodthrow extends StandEntityAction {

    public kingcrimson_bloodthrow(Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            PlayerEntity player = (PlayerEntity) standEntity.getUser();
            if (player != null) {
                Vector3d lookVec = player.getLookAngle();
                //gonna have to create projectile and stuff


                //world.playSound(null, player.getX(), player.getY(), player.getZ(), InitSounds.KINGCRIMSON_BLOODTHROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }
    }
}
