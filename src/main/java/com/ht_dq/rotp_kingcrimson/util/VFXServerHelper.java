package com.ht_dq.rotp_kingcrimson.util;

import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import com.ht_dq.rotp_kingcrimson.network.AddonPackets;
import com.ht_dq.rotp_kingcrimson.network.server.TimerPutPacket;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class VFXServerHelper {
    
    public static void startVFX(LivingEntity user, boolean forAll) {
        long currentTime = System.currentTimeMillis();
        if (forAll) {
            double radius = 192 * 192;
            for (PlayerEntity p : user.level.players()) {
                if (user.distanceToSqr(p) <= radius) {
                    startTimeSkipEffect(p, currentTime);
                }
            }
        } else if (user instanceof PlayerEntity) {
            startTimeSkipEffect((PlayerEntity) user, currentTime);
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
