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
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

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
        if (kcUser != null) {
            fullCopyPos(user, this);
            IStandPower.getStandPowerOptional(kcUser).ifPresent(power -> {
                decoyStandPower.readNBT((CompoundNBT) power.writeNBT());
            });
        }
    }
    
    private static void fullCopyPos(LivingEntity src, LivingEntity dest) {
        dest.copyPosition(src);
        dest.yHeadRot = src.yHeadRot;
        dest.yHeadRotO = src.yHeadRotO;
        dest.yBodyRot = src.yBodyRot;
        dest.yBodyRotO = src.yBodyRotO;
        dest.xRot = src.xRot;
        dest.xRotO = src.xRotO;
        /* 
         * this scheisse does not work for player KC users, 
         * because the player's movement vector in only known on client side,
         * and for mobs it needs to be set on the server side to work correctly
         * (pain)
         */
//        if (!dest.level.isClientSide()) {
//            dest.setDeltaMovement(src.getDeltaMovement());
//            dest.hurtMarked = true;
//        }
    }
    
    public LivingEntity getKCUser() {
        return kcUser;
    }
    
    public void silentSummonStand() {
        StandType<?> KCType = decoyStandPower.getType();
        if (KCType instanceof EntityStandType) {
            StandEntity decoyStandEntity = ((EntityStandType<?>) KCType).getEntityType().create(level);
            
            if (kcUser != null) {
                IStandPower.getStandPowerOptional(kcUser).ifPresent(power -> {
                    if (power.getStandManifestation() instanceof StandEntity) {
                        fullCopyPos((StandEntity) power.getStandManifestation(), decoyStandEntity);
                    }
                });
            }
            decoyStandPower.setStandManifestation(decoyStandEntity);
            
            decoyStandEntity.summonLockTicks = 0;
            decoyStandEntity.gradualSummonWeaknessTicks = 0;
            
            level.addFreshEntity(decoyStandEntity);
            PacketManager.sendToClientsTrackingAndSelf(new TrSetStandEntityPacket(this.getId(), decoyStandEntity.getId()), this);
        }
    }
    
    private boolean makeStandEntityLessSus() {
        if (decoyStandPower.getStandManifestation() instanceof StandEntity) {
            StandEntity decoyStandEntity = (StandEntity) decoyStandPower.getStandManifestation();
            if (decoyStandEntity.tickCount < 999) {
                decoyStandEntity.tickCount = 999;
                
                decoyStandEntity.summonLockTicks = 0;
                decoyStandEntity.gradualSummonWeaknessTicks = 0;
                decoyStandEntity.overlayTickCount = 999;
                if (decoyStandEntity.getStandPose() == StandPose.SUMMON) {
                    decoyStandEntity.setStandPose(StandPose.IDLE);
                }
                
                return true;
            }
        }
        return false;
    }
    
    private boolean clientSusStandFlag = true;
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
        if (clientSusStandFlag && level.isClientSide()) clientSusStandFlag &= !makeStandEntityLessSus();
    }
    
    private boolean isValid() {
        return kcUser != null && IStandPower.getStandPowerOptional(kcUser).resolve().map(
                power -> power.getHeldAction() == InitStands.KINGCRIMSON_TIMEERASE.get()).orElse(false);
    }

    /* 
     * if this mob is gonna have AI in the future, 
     * these two anonymous classes will probably need some changes, 
     * it works fine for now
     */
    @Override
    protected void registerGoals() {
        this.lookControl = new LookController(this) {
            @Override
            public void tick() {}
        };
    }

    @Override
    protected BodyController createBodyControl() {
        return new BodyController(this) {
            @Override
            public void clientTick() {}
        };
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
