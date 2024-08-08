package com.ht_dq.rotp_kingcrimson.network;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.network.server.TimerPutPacket;
import com.ht_dq.rotp_kingcrimson.network.server.VFXPlayerTick;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class AddonPackets {
    private static final String PROTOCOL_VERSION = "1";
    private static SimpleChannel channel;
    public static void init() {
        channel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "main_channel"))
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .simpleChannel();

        int packetIndex = 0;
        channel.registerMessage(packetIndex++, TimerPutPacket.class,
                TimerPutPacket::encode,TimerPutPacket::decode,TimerPutPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, VFXPlayerTick.class,
                VFXPlayerTick::encode,VFXPlayerTick::decode,VFXPlayerTick::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static void sendToClient(Object msg, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer)) {
            channel.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }
    }

    public static void sendToClientsTracking(Object msg, Entity entity) {
        channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
    }

    public static void sendToClientsTrackingAndSelf(Object msg, Entity entity) {
        channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }
}
