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
            	PlayerEntity player = (PlayerEntity) entity;
                if (TimeSkipHandler.timer.containsKey(entity)){
                	Integer timerValue = TimeSkipHandler.timer.get(entity);
                    if (timerValue <= 0) {
                    	TimeSkipHandler.timer.remove(entity);
                    } else {
                        TimeSkipHandler.timer.put(player, timerValue - 1);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
