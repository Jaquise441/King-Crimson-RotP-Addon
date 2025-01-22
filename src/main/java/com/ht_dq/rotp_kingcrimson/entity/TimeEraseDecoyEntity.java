package com.ht_dq.rotp_kingcrimson.entity;

import com.github.standobyte.jojo.entity.mob.IMobStandUser;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.network.PacketManager;
import com.github.standobyte.jojo.network.packets.fromserver.TrSetStandEntityPacket;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandPower;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.ht_dq.rotp_kingcrimson.init.InitEntities;
import com.ht_dq.rotp_kingcrimson.init.InitStands;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

// FIXME update the rotation of the decoy entity and its stand entity immediately
public class TimeEraseDecoyEntity extends MobEntity implements IMobStandUser, IEntityAdditionalSpawnData {
    private IStandPower decoyStandPower = new StandPower(this);
    private LivingEntity kcUser;

    public TimeEraseDecoyEntity(World world) {
        super(InitEntities.TIME_ERASE_DECOY.get(), world);
    }

    public TimeEraseDecoyEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }
    
    public void setKCUser(LivingEntity user) {
        this.kcUser = user;
        IStandPower.getStandPowerOptional(kcUser).ifPresent(power -> {
            decoyStandPower.readNBT((CompoundNBT) power.writeNBT());
        });
    }
    
    public LivingEntity getKCUser() {
        return kcUser;
    }
    
    public void silentSummonStand() {
        StandType<?> KCType = decoyStandPower.getType();
        if (KCType instanceof EntityStandType) {
            StandEntity standEntity = ((EntityStandType<?>) KCType).getEntityType().create(level);
            standEntity.copyPosition(this);
            decoyStandPower.setStandManifestation(standEntity);
            level.addFreshEntity(standEntity);
            PacketManager.sendToClientsTrackingAndSelf(new TrSetStandEntityPacket(this.getId(), standEntity.getId()), this);
        }
    }
    
    private void makeStandEntityLessSus() {
        if (level.isClientSide() && decoyStandPower.getStandManifestation() instanceof StandEntity) {
            StandEntity decoyStandEntity = (StandEntity) decoyStandPower.getStandManifestation();
            if (decoyStandEntity.getStandPose() == StandPose.SUMMON) {
                decoyStandEntity.setStandPose(StandPose.IDLE);
            }
            decoyStandEntity.overlayTickCount = 999;
//            if (decoyStandEntity.tickCount == 0) {
//                Vector3d pos = decoyStandEntity.position();
//                decoyStandEntity.setPosAndOldPos(pos.x, pos.y, pos.z);
//                decoyStandEntity.tickCount = 999;
//            }
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide()) {
            if (!isValid()) {
                remove();
                return;
            }
            if (tickCount == 1) {
                silentSummonStand();
            }
        }
        makeStandEntityLessSus();
    }
    
    private boolean isValid() {
        return kcUser != null && IStandPower.getStandPowerOptional(kcUser).resolve().map(
                power -> power.getHeldAction() == InitStands.KINGCRIMSON_TIMEERASE.get()).orElse(false);
    }
    
    @Override
    public IStandPower getStandPower() {
        return decoyStandPower;
    }
    
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeBoolean(kcUser != null);
        if (kcUser != null) {
            buffer.writeInt(kcUser.getId());
        }
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        if (additionalData.readBoolean()) {
            Entity entity = level.getEntity(additionalData.readInt());
            if (entity instanceof LivingEntity) {
                setKCUser((LivingEntity) entity);
            }
        }
    }

}
