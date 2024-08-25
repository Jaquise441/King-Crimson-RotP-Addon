package com.ht_dq.rotp_kingcrimson.network.server;

import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class VFXPlayerTick {
    private final int entityId;

    public VFXPlayerTick(int entityId){
        this.entityId = entityId;
    }

    public static void encode(VFXPlayerTick msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
    }

    public static VFXPlayerTick decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        return new VFXPlayerTick(entityId);
    }

    public static void handle(VFXPlayerTick msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.entityId);
            if (entity instanceof PlayerEntity) {
                if(TimeSkipHandler.timer.containsKey((PlayerEntity) entity)){
                    if(TimeSkipHandler.timer.get((PlayerEntity) entity) <=0) TimeSkipHandler.timer.remove((PlayerEntity) entity);
                    TimeSkipHandler.timer.replace((PlayerEntity) entity, TimeSkipHandler.timer.get((PlayerEntity) entity)-1);
                }

            }
        });
        ctx.get().setPacketHandled(true);
    }
}
