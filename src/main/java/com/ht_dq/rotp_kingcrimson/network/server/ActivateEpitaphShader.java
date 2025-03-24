package com.ht_dq.rotp_kingcrimson.network.server;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.EpitaphVFX;
import com.ht_dq.rotp_kingcrimson.util.KingCrimsonUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ActivateEpitaphShader {
    private final int entityId;

    public ActivateEpitaphShader(int entityId){
        this.entityId = entityId;
    }

    public static void encode(ActivateEpitaphShader msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
    }

    public static ActivateEpitaphShader decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        return new ActivateEpitaphShader(entityId);
    }

    public static void handle(ActivateEpitaphShader msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityId);
            if (entity instanceof PlayerEntity) {
                IStandPower userPower = IStandPower.getPlayerStandPower((PlayerEntity) entity);
                if (!EpitaphVFX.isEnabled()) {
                    EpitaphVFX.enableShader(KingCrimsonUtil.getShader(userPower));
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
