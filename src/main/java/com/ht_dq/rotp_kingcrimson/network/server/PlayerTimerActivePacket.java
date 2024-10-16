package com.ht_dq.rotp_kingcrimson.network.server;

import com.ht_dq.rotp_kingcrimson.action.KingCrimsonTimeErase;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PlayerTimerActivePacket {
    private final UUID entityId;
    private final boolean active;

    public PlayerTimerActivePacket(UUID entityId, boolean active){
        this.entityId = entityId;
        this.active = active;
    }

    public static void encode(PlayerTimerActivePacket msg, PacketBuffer buf) {
        buf.writeUUID(msg.entityId);
        buf.writeBoolean(msg.active);
    }

    public static PlayerTimerActivePacket decode(PacketBuffer buf) {
        UUID entityId = buf.readUUID();
        boolean active = buf.readBoolean();
        return new PlayerTimerActivePacket(entityId, active);
    }

    public static void handle(PlayerTimerActivePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            KingCrimsonTimeErase.playerTimeEraseActive.put(msg.entityId,msg.active);
        });
        ctx.get().setPacketHandled(true);
    }
}
