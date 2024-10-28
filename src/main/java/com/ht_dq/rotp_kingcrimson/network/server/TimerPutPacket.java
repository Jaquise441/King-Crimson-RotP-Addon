package com.ht_dq.rotp_kingcrimson.network.server;

import java.util.function.Supplier;

import com.ht_dq.rotp_kingcrimson.util.VFXServerHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class TimerPutPacket {
    private final int entityId;

    public TimerPutPacket(int entityId){
        this.entityId = entityId;
    }

    public static void encode(TimerPutPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
    }

    public static TimerPutPacket decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        return new TimerPutPacket(entityId);
    }

    public static void handle(TimerPutPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.entityId);
            if (entity instanceof PlayerEntity) {
                VFXServerHelper.startVFX((PlayerEntity) entity, false);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
