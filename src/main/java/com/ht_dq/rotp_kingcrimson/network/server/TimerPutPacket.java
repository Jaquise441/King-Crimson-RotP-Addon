package com.ht_dq.rotp_kingcrimson.network.server;

import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TimerPutPacket {
    private final int entityId;
    private final long MAX_TIME;

    public TimerPutPacket(int entityId, long MAX_TIME){
        this.entityId = entityId;
        this.MAX_TIME = MAX_TIME;
    }

    public static void encode(TimerPutPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeLong(msg.MAX_TIME);
    }

    public static TimerPutPacket decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        long max_time = buf.readLong();
        return new TimerPutPacket(entityId, max_time);
    }

    public static void handle(TimerPutPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.entityId);
            if (entity instanceof PlayerEntity) {
                TimeSkipHandler.timer.put((PlayerEntity) entity, (int)msg.MAX_TIME);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
