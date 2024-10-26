package com.ht_dq.rotp_kingcrimson.client.render.vfx;

import java.util.HashMap;
import java.util.Map;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID, value = Dist.CLIENT)
public class TimeSkipHandler {
    public static final Map<PlayerEntity, Long> startTimeMap = new HashMap<>();
    public static final int EFFECT_DURATION = 375;
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (startTimeMap.containsKey(player)) {
            long currentTime = System.currentTimeMillis();
            long startTime = startTimeMap.get(player);
            long elapsedTime = currentTime - startTime;

            if (elapsedTime >= EFFECT_DURATION) {
                startTimeMap.remove(player);
            }
        }
    }

    public static boolean isEffectActive(PlayerEntity player) {
        return startTimeMap.containsKey(player);
    }

    public static long getRemainingTime(PlayerEntity player) {
        if (!startTimeMap.containsKey(player)) return 0;
        long currentTime = System.currentTimeMillis();
        long startTime = startTimeMap.get(player);
        long elapsedTime = currentTime - startTime;
        return Math.max(0, EFFECT_DURATION - elapsedTime);
    }
    
}
