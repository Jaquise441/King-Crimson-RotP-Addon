package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.punch.StandBlockPunch;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.ht_dq.rotp_kingcrimson.client.ClientProxy;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.EntityTickableSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import java.util.List;

public class KingCrimsonGroundSlam extends StandEntityLightAttack {
    public static final StandPose SLAM = new StandPose("kingcrimson_slam");

    public KingCrimsonGroundSlam(Builder builder) {
        super(builder);
    }

    @Override
    public int getStandWindupTicks(IStandPower standPower, StandEntity standEntity) {
        return 5;
    }

    @Override
    public int getStandRecoveryTicks(IStandPower standPower, StandEntity standEntity) {
        return 4;
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        StandEntityPunch punch = super.punchEntity(stand, target, dmgSource);
        punch.impactSound(InitSounds.KINGCRIMSON_GROUNDPUNCH);

        if (stand.level.isClientSide()) {
            LivingEntity user = stand.getUser();
            if (user != null) {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                        ClientProxy.playSound(
                                InitSounds.DOPPIO_TAKE_THIS.get(),
                                SoundCategory.PLAYERS,
                                1.0F,
                                1.0F,
                                user
                        )
                );
            }
        }



        punch.addKnockback(0.75F)
                .damage((float) stand.getAttackDamage() * 1.075F)
                .disableBlocking(0.75F);

        applyAoEKnockback(stand, target.position(), dmgSource, target);
        return punch;
    }

    @Override
    public StandBlockPunch punchBlock(StandEntity stand, BlockPos pos, BlockState state, Direction face) {
        StandBlockPunch punch = super.punchBlock(stand, pos, state, face);
        applyAoEKnockback(stand, Vector3d.atCenterOf(pos), null, null);
        return punch;
    }

    private void applyAoEKnockback(StandEntity stand, Vector3d position, StandEntityDamageSource dmgSource, Entity directHitEntity) {
        double aoeRange = 2.0;

        AxisAlignedBB aoeBox = new AxisAlignedBB(
                position.x - aoeRange, position.y - aoeRange, position.z - aoeRange,
                position.x + aoeRange, position.y + aoeRange, position.z + aoeRange
        );

        Entity user = stand.getUser();

        if (user == null) {
            return;
        }

        List<Entity> nearbyEntities = stand.level.getEntities(stand, aoeBox);

        for (Entity entity : nearbyEntities) {
            if (entity.isAlive() && entity != stand && entity != user && entity != directHitEntity) {

                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    double distance = position.distanceTo(entity.position());

                    float damage = (float) (stand.getAttackDamage() * (0.5 - (distance / aoeRange)));

                    damage = Math.max(0.25F, damage);

                    livingEntity.hurt(DamageSource.indirectMobAttack(stand, (LivingEntity) user), damage);
                }

                double knockbackStrengthX = (entity.getX() - position.x) * 0.4;
                double knockbackStrengthZ = (entity.getZ() - position.z) * 0.4;
                double knockbackStrengthY = 0.2;

                entity.push(knockbackStrengthX, knockbackStrengthY, knockbackStrengthZ);
            }
        }
    }
}