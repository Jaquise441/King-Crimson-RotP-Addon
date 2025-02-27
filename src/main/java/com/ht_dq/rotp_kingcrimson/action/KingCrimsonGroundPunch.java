package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandBlockPunch;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.ht_dq.rotp_kingcrimson.config.KCConfig;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.ht_dq.rotp_kingcrimson.init.InitStands;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class KingCrimsonGroundPunch extends StandEntityHeavyAttack {

    public KingCrimsonGroundPunch(StandEntityHeavyAttack.Builder builder) {
        super(builder);
    }

    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        LivingEntity user = power.getUser();
        if (user != null && user.getHealth() / user.getMaxHealth() < KCConfig.DESPERATE_EYEJAB_HEALTH_THRESHOLD.get()) {
            return InitStands.KINGCRIMSON_DESPERATE_EYEJAB.get();
        }
        return super.replaceAction(power, target);
    }

    @Override
    public StandAction[] getExtraUnlockable() {
        return new StandAction[] { InitStands.KINGCRIMSON_DESPERATE_EYEJAB.get() };
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        StandEntityPunch punch = super.punchEntity(stand, target, dmgSource);
        punch.impactSound(InitSounds.KINGCRIMSON_GROUNDPUNCH);
        punch.addKnockback(1.5F)
                .disableBlocking(1.0F);

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
        double aoeRange = 3.0;

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
                double knockbackStrengthY = 0.4;

                entity.push(knockbackStrengthX, knockbackStrengthY, knockbackStrengthZ);
            }
        }
    }
}
