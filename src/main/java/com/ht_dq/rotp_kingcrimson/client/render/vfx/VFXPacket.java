package com.ht_dq.rotp_kingcrimson.client.render.vfx;

import com.ht_dq.rotp_kingcrimson.init.InitVFX;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class VFXPacket {
    private final UUID playerUUID;
    private final boolean start;

    public VFXPacket(UUID playerUUID, boolean start) {
        this.playerUUID = playerUUID;
        this.start = start;
    }

    public static void encode(VFXPacket msg, PacketBuffer buffer) {
        buffer.writeUUID(msg.playerUUID);
        buffer.writeBoolean(msg.start);
    }

    public static VFXPacket decode(PacketBuffer buffer) {
        return new VFXPacket(buffer.readUUID(), buffer.readBoolean());
    }

    public static void handle(VFXPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (msg.start) {
                InitVFX.startVFXClient(msg.playerUUID);
            }
//            } else {
//                InitVFX.stopVFXClient(msg.playerUUID);
//            }
        });
        context.setPacketHandled(true);
    }
}
