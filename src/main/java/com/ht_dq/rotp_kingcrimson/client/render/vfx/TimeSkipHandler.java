package com.ht_dq.rotp_kingcrimson.client.render.vfx;


import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.network.AddonPackets;
import com.ht_dq.rotp_kingcrimson.network.server.TimerPutPacket;
import com.ht_dq.rotp_kingcrimson.network.server.VFXPlayerTick;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID)
public class TimeSkipHandler {
    public static final Map<PlayerEntity, Integer> timer = new HashMap<>();
    private static final Map<PlayerEntity, Integer> tickCounter = new HashMap<>();
    private static final int MAX_TIME = 15;
    private static final int TICK_INTERVAL = 10;

    public static void startVFX(PlayerEntity player, boolean forAll){
        if (forAll) {
            for (PlayerEntity p : player.level.players()) {
                timer.put(p, MAX_TIME);
                tickCounter.put(p, 0);
                if(p instanceof ServerPlayerEntity){
                    AddonPackets.sendToClient(new TimerPutPacket(p.getId(), MAX_TIME), (ServerPlayerEntity) p);
                }
            }
        } else {
            timer.put(player, MAX_TIME);
            tickCounter.put(player, 0);
            if(player instanceof ServerPlayerEntity){
                AddonPackets.sendToClient(new TimerPutPacket(player.getId(), MAX_TIME), (ServerPlayerEntity) player);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;

        if(timer.containsKey(player)){
            int currentTicks = tickCounter.getOrDefault(player, 0);
            if(currentTicks >= TICK_INTERVAL) {
                timer.replace(player, timer.get(player) - 1);
                tickCounter.replace(player, 0);
                if(timer.get(player) <= 0) {
                    timer.remove(player);
                    tickCounter.remove(player);
                }
            } else {
                tickCounter.replace(player, currentTicks + 1);
            }

            if(player instanceof ServerPlayerEntity){
                AddonPackets.sendToClient(new VFXPlayerTick(player.getId()), (ServerPlayerEntity) player);
            }
        }
    }

    public static boolean isEffectActive(PlayerEntity player) {
        return timer.containsKey(player);
    }

    public static int getRemainingTime(PlayerEntity player) {
        return timer.getOrDefault(player, 0);
    }
}
