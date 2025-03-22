package com.ht_dq.rotp_kingcrimson.network.server;

import com.github.standobyte.jojo.client.ClientUtil;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.EpitaphVFX;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveEpitaphShader {
    private final int entityId;

    public RemoveEpitaphShader(int entityId){
        this.entityId = entityId;
    }

    public static void encode(RemoveEpitaphShader msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
    }

    public static RemoveEpitaphShader decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        return new RemoveEpitaphShader(entityId);
    }

    public static void handle(RemoveEpitaphShader msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityId);
            if (entity instanceof PlayerEntity) {
                EpitaphVFX.shutdownShader();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
