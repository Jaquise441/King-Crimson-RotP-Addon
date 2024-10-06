package com.ht_dq.rotp_kingcrimson.capability;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.power.IPower;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID)
public class CapabilityHandler {
    private static final ResourceLocation LIVING_UTIL_CAP = new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "living_util");

    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(
                LivingUtilCap.class,
                new LivingUtilCapStorage(),
                () -> new LivingUtilCap(null));
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            event.addCapability(LIVING_UTIL_CAP, new LivingUtilCapProvider(living));
        }
    }

    @SubscribeEvent
    public static void syncWithNewPlayer(PlayerEvent.StartTracking event) {
        syncAttachedData(event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        syncAttachedData(event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncAttachedData(event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        syncAttachedData(event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        PlayerEntity original = event.getOriginal();
        PlayerEntity player = event.getPlayer();
        cloneCap(IStandPower.getStandPowerOptional(original), IStandPower.getStandPowerOptional(player), event.isWasDeath(), "non-Stand capability");
        original.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent((oldCap) -> {
            player.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent((newCap) -> {
                newCap.onClone(oldCap, event.isWasDeath());
            });
        });
    }

    private static <T extends IPower<T, ?>> void cloneCap(LazyOptional<T> oldCap, LazyOptional<T> newCap, boolean wasDeath, String warning) {
        if (oldCap.isPresent() && newCap.isPresent()) {
            newCap.resolve().get().onClone(oldCap.resolve().get(), wasDeath);
        } else {
            JojoMod.getLogger().warn("Failed to copy data!");
        }

    }

    @SubscribeEvent
    public static void onEntityTracking(PlayerEvent.StartTracking event) {
        Entity entityTracked = event.getTarget();
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        if (entityTracked instanceof LivingEntity) {
            LivingEntity livingTracked = (LivingEntity) entityTracked;
            livingTracked.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(data -> {
                data.onTracking(player);
            });
        }
    }

    private static void syncAttachedData(PlayerEntity player) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        player.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(data -> {
            data.syncWithAnyPlayer(serverPlayer);
            data.syncWithClient();
        });
    }
}
