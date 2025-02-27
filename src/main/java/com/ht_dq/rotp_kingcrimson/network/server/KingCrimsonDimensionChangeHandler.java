package com.ht_dq.rotp_kingcrimson.network.server;

import com.github.standobyte.jojo.init.ModStatusEffects;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.ht_dq.rotp_kingcrimson.util.VFXServerHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KingCrimsonDimensionChangeHandler {

    private PlayerEntity player;
    private boolean timeEraseActive = false;
    private int lastKnownDimensionId;

    public KingCrimsonDimensionChangeHandler(PlayerEntity player) {
        this.player = player;
        this.lastKnownDimensionId = player.level.dimension().location().hashCode();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player != null && isKingCrimsonUser(player)) {
            postTimeEraseCleanup((ServerWorld) player.level, player);
            applyTimeEraseEffects(player);
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (timeEraseActive && player != null) {
            int currentDimensionId = player.level.dimension().location().hashCode();

            if (currentDimensionId != lastKnownDimensionId && isKingCrimsonUser(player)) {
                postTimeEraseCleanup((ServerWorld) player.level, player);
                applyTimeEraseEffects(player);
                lastKnownDimensionId = currentDimensionId;
            }
        }
    }

    public void startTimeErase() {
        timeEraseActive = true;
        lastKnownDimensionId = player.level.dimension().location().hashCode();
        applyTimeEraseEffects(player);
    }

    public void stopTimeErase() {
        timeEraseActive = false;
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    private boolean isKingCrimsonUser(PlayerEntity player) {
        return player != null &&
                player.hasEffect(Effects.LUCK) &&
                player.hasEffect(Effects.INVISIBILITY) &&
                player.hasEffect(ModStatusEffects.FULL_INVISIBILITY.get()) &&
                player.hasEffect(Effects.DIG_SLOWDOWN);
    }

    private void postTimeEraseCleanup(ServerWorld world, PlayerEntity player) {
        if (player.abilities.invulnerable) {
            player.abilities.invulnerable = false;
        }

        player.removeEffect(ModStatusEffects.FULL_INVISIBILITY.get());
        player.removeEffect(Effects.LUCK);
        player.removeEffect(Effects.INVISIBILITY);
        player.removeEffect(Effects.DIG_SLOWDOWN);
        removeMarkers(world);
        restoreEntityPositions(world);
    }

    private void applyTimeEraseEffects(PlayerEntity player) {
        playSound(player, InitSounds.TIME_ERASE_END.get(), false);
        stopSound(player, InitSounds.TIME_ERASE_START.get());

        VFXServerHelper.startVFX(player, true);
    }

    private void removeMarkers(ServerWorld world) {
    }

    private void restoreEntityPositions(ServerWorld world) {
    }

    private void playSound(PlayerEntity player, SoundEvent sound, boolean loop) {
        player.level.playSound(null, player.blockPosition(), sound, player.getSoundSource(), 1.0F, 1.0F);
    }

    private void stopSound(PlayerEntity player, SoundEvent sound) {
    }
}
