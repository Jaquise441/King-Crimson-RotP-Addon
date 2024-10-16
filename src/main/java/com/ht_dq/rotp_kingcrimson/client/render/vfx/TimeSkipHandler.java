package com.ht_dq.rotp_kingcrimson.client.render.vfx;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.network.AddonPackets;
import com.ht_dq.rotp_kingcrimson.network.server.TimerPutPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID, value = Dist.CLIENT)
public class TimeSkipHandler {
    public static final Map<PlayerEntity, Long> startTimeMap = new HashMap<>();
    public static final int EFFECT_DURATION = 375;

    public static void startVFX(PlayerEntity player, boolean forAll) {
        long currentTime = System.currentTimeMillis();
        if (forAll) {
            double radius = 192 * 192;
            for (PlayerEntity p : player.level.players()) {
                if (player.distanceToSqr(p) <= radius) {
                    startTimeMap.put(p, currentTime);
                    if (p instanceof ServerPlayerEntity) {
                        AddonPackets.sendToClient(new TimerPutPacket(p.getId(), EFFECT_DURATION), (ServerPlayerEntity) p);
                    }
                }
            }
        } else {
            startTimeMap.put(player, currentTime);
            if (player instanceof ServerPlayerEntity) {
                AddonPackets.sendToClient(new TimerPutPacket(player.getId(), EFFECT_DURATION), (ServerPlayerEntity) player);
            }
        }
    }

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

    public static void registerClientSideEvents() {
        MinecraftForge.EVENT_BUS.register(com.ht_dq.rotp_kingcrimson.init.InitVFX.class);
    }
}
