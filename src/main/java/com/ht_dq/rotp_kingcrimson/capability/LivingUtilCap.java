package com.ht_dq.rotp_kingcrimson.capability;

import com.ht_dq.rotp_kingcrimson.network.AddonPackets;
import com.ht_dq.rotp_kingcrimson.network.server.TrLivingDataPacket;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class LivingUtilCap {
    private final LivingEntity livingEntity;

    private boolean isConcealIdentityActive = false;
    
    public LivingUtilCap(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }
    
    public void setConcealIdentify(boolean set) {
        this.isConcealIdentityActive = set;
        if (!livingEntity.level.isClientSide()) {
            AddonPackets.sendToClientsTrackingAndSelf(new TrLivingDataPacket(livingEntity.getId(), set), livingEntity);
        }
    }

    public boolean isConcealIdentityActive() {
        return this.isConcealIdentityActive;
    }

    public void syncWithAnyPlayer(ServerPlayerEntity player) {
        AddonPackets.sendToClient(new TrLivingDataPacket(livingEntity.getId(), isConcealIdentityActive), player);
    }

    public void onClone(LivingUtilCap old, boolean wasDeath) {
        this.isConcealIdentityActive = old.isConcealIdentityActive;
    }

    public void onTracking(ServerPlayerEntity tracking) {
        AddonPackets.sendToClient(new TrLivingDataPacket(livingEntity.getId(), isConcealIdentityActive), tracking);
    }

    public void syncWithClient() {
        if (livingEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) livingEntity;
            AddonPackets.sendToClient(new TrLivingDataPacket(livingEntity.getId(), isConcealIdentityActive), player);
        }
    }

    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("isConcealIdentityActive", isConcealIdentityActive);
        return nbt;
    }

    public void fromNBT(CompoundNBT nbt) {
        this.isConcealIdentityActive = nbt.getBoolean("isConcealIdentityActive");
    }
}
