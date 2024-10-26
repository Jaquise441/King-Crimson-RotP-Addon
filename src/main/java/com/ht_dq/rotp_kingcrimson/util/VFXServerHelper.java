package com.ht_dq.rotp_kingcrimson.util;

import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import com.ht_dq.rotp_kingcrimson.network.AddonPackets;
import com.ht_dq.rotp_kingcrimson.network.server.TimerPutPacket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class VFXServerHelper {
    
    public static void startVFX(PlayerEntity player, boolean forAll) {
        long currentTime = System.currentTimeMillis();
        if (forAll) {
            double radius = 192 * 192;
            for (PlayerEntity p : player.level.players()) {
                if (player.distanceToSqr(p) <= radius) {
                    startTimeSkipEffect(player, currentTime);
                }
            }
        } else {
            startTimeSkipEffect(player, currentTime);
        }
    }
    
    private static void startTimeSkipEffect(PlayerEntity player, long currentTime) {
        if (player.level.isClientSide()) {
            TimeSkipHandler.startTimeMap.put(player, currentTime);
        }
        else if (player instanceof ServerPlayerEntity) {
            AddonPackets.sendToClient(new TimerPutPacket(player.getId()), (ServerPlayerEntity) player);
        }
    }
}
