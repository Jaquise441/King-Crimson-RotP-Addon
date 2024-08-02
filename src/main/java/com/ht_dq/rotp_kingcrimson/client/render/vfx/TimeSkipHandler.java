package com.ht_dq.rotp_kingcrimson.client.render.vfx;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimeSkipHandler {

    public static final Map<UUID, Integer> timer = new HashMap<>();
    private static final int MAX_TIME = 15;


    public static void startEffect(PlayerEntity player, boolean forAll) {
        UUID playerUUID = player.getUUID();
        if (forAll) {
            for (PlayerEntity p : player.level.players()) {
                timer.put(p.getUUID(), MAX_TIME);
            }
        } else {
            timer.put(playerUUID, MAX_TIME);
        }
    }


    public static void tick() {
        timer.entrySet().removeIf(entry -> entry.getValue() <= 0);
        timer.replaceAll((uuid, time) -> time - 1);
    }


    public static boolean isEffectActive(PlayerEntity player) {
        return timer.containsKey(player.getUUID());
    }


    public static int getRemainingTime(PlayerEntity player) {
        return timer.getOrDefault(player.getUUID(), 0);
    }


    public static ResourceLocation getTextureForTime(int time) {
        return new ResourceLocation("rotp_kingcrimson", "textures/timeskip/time_skip" + time + ".png");
    }
}
