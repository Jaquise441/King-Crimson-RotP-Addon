package com.ht_dq.rotp_kingcrimson.entity;

import com.ht_dq.rotp_kingcrimson.init.InitEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class KCAfterimageEntity extends Entity implements IEntityAdditionalSpawnData {
    private Entity originEntity;
    private UUID originUuid;
    private int ticksDelayed;
    private int delay;
    private int lifeSpan;
    private double speedLowerLimit;
    private boolean isStationary = false;
    private Queue<PosData> originPosQueue = new LinkedList<PosData>();
    
    public KCAfterimageEntity(World world, Entity originEntity, int delay) {
        this(InitEntities.KC_AFTERIMAGE.get(), world);
        setOriginEntity(originEntity);
        this.delay = delay;
        this.lifeSpan = 1200;
    }

    public KCAfterimageEntity(EntityType<?> type, World world) {
        super(type, world);
        noPhysics = true;
    }
    
    private void setOriginEntity(Entity entity) {
        this.originEntity = entity;
        if (entity != null) {
            copyPosition(entity);
        }
    }
    
    public Entity getOriginEntity() {
        return originEntity;
    }
    
    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }
    
    public void setMinSpeed(double speed) {
        this.speedLowerLimit = speed;
    }
    
    public boolean shouldRender() {
        return originEntity != null;
    }
    
    public void setStationary(boolean isStationary) {
        this.isStationary = isStationary;
    }
    
    /** just in case you'd want only some of them to be red */
    public boolean isRedOnly() {
        return !isStationary;
    }
    
    @Override
    public void tick() {
        super.tick();
        ticksDelayed++;
        if (originEntity == null || !originEntity.isAlive() || !level.isClientSide() && tickCount > lifeSpan) {
            remove();
            return;
        }
        originPosQueue.add(new PosData(originEntity.position(), originEntity.xRot, originEntity.yRot));
        if (ticksDelayed > delay) {
            PosData posData = originPosQueue.remove();
            moveTo(posData.pos.x, posData.pos.y, posData.pos.z, posData.yRot, posData.xRot);
        }
    }

    @Override
    protected void defineSynchedData() {}
    
    @Override
    public boolean isInvisible() {
        return super.isInvisible() || originEntity != null && originEntity.isInvisible();
    }
    
    @Override
    public boolean isInvisibleTo(PlayerEntity player) {
        return super.isInvisibleTo(player) || originEntity != null && originEntity.isInvisibleTo(player);
    }
    
    @Override
    public boolean displayFireAnimation() {
        return originEntity != null && originEntity.displayFireAnimation();
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        this.delay = nbt.getInt("Delay");
        this.tickCount = nbt.getInt("Age");
        this.lifeSpan = nbt.getInt("LifeSpan");
        this.speedLowerLimit = nbt.getDouble("Speed");
        if (nbt.hasUUID("Origin")) {
            this.originUuid = nbt.getUUID("Origin");
        }
        this.isStationary = nbt.getBoolean("Stationary");
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        nbt.putInt("Delay", delay);
        nbt.putInt("Age", tickCount);
        nbt.putInt("LifeSpan", lifeSpan);
        nbt.putDouble("Speed", speedLowerLimit);
        if (originUuid != null) {
            nbt.putUUID("Origin", originEntity.getUUID());
        }
        nbt.putBoolean("Stationary", isStationary);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        if (originUuid != null) {
            Entity entity = ((ServerWorld) level).getEntity(originUuid);
            if (entity != null) {
                setOriginEntity(entity);
            }
        }
        buffer.writeInt(originEntity == null ? -1 : originEntity.getId());
        buffer.writeVarInt(delay);
        buffer.writeInt(lifeSpan);
        buffer.writeDouble(speedLowerLimit);
        buffer.writeBoolean(isStationary);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        Entity entity = level.getEntity(additionalData.readInt());
        if (entity != null) {
            setOriginEntity(entity);
        }
        delay = additionalData.readVarInt();
        lifeSpan = additionalData.readInt();
        speedLowerLimit = additionalData.readDouble();
        isStationary = additionalData.readBoolean();
    }

    private static class PosData {
        private final Vector3d pos;
        private final float xRot;
        private final float yRot;
        
        private PosData(Vector3d pos, float xRot, float yRot) {
            this.pos = pos;
            this.xRot = xRot;
            this.yRot = yRot;
        }
    }
}
