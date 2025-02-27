package com.ht_dq.rotp_kingcrimson.network.server;

import com.ht_dq.rotp_kingcrimson.action.KingCrimsonTimeErase;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class RemoveTimerActivePacket {
    private final UUID entityId;


    public RemoveTimerActivePacket(UUID entityId){
        this.entityId = entityId;
    }

    public static void encode(RemoveTimerActivePacket msg, PacketBuffer buf) {
        buf.writeUUID(msg.entityId);

    }

    public static RemoveTimerActivePacket decode(PacketBuffer buf) {
        UUID entityId = buf.readUUID();
        return new RemoveTimerActivePacket(entityId);
    }

    public static void handle(RemoveTimerActivePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            KingCrimsonTimeErase.playerTimeEraseActive.remove(msg.entityId);
        });
        ctx.get().setPacketHandled(true);
    }
}
