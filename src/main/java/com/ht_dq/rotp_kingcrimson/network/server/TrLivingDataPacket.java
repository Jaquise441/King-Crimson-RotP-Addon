package com.ht_dq.rotp_kingcrimson.network.server;

import com.github.standobyte.jojo.client.ClientUtil;
import com.ht_dq.rotp_kingcrimson.capability.LivingUtilCapProvider;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TrLivingDataPacket {
    private final int entityId;
    private final boolean set;

    public TrLivingDataPacket(int entityId, boolean set) {
        this.entityId = entityId;
        this.set = set;
    }

    public static void encode(TrLivingDataPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeBoolean(msg.set);
    }

    public static TrLivingDataPacket decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        boolean set = buf.readBoolean();
        return new TrLivingDataPacket(entityId, set);
    }

    public static void handle(TrLivingDataPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Entity entity = ClientUtil.getEntityById(msg.entityId);
        if (entity != null) {
            entity.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setConcealIdentify(msg.set));
        }
        ctx.get().setPacketHandled(true);
    }
}