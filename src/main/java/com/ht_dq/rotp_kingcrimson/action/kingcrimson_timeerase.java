package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SDestroyEntitiesPacket;
import net.minecraft.network.play.server.SEntityMetadataPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.network.play.server.SStopSoundPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class kingcrimson_timeerase extends StandEntityAction {

    private static final int MAX_DURATION = 200;
    private static final double RADIUS = 192.0;

    private final Map<Entity, Vector3d> entityPositions = new HashMap<>();
    private final Set<ArmorStandEntity> markers = new HashSet<>();
    private boolean timeEraseActive = false;

    public kingcrimson_timeerase(Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            ServerPlayerEntity player = (ServerPlayerEntity) standEntity.getUser();
            if (player != null) {
                applyEffects(player, standEntity, true);
                saveEntityPositions(player);
                makeMobsNonAggressive(player);
                MinecraftForge.EVENT_BUS.register(new TimeEraseHandler(player.getUUID(), standEntity, userPower, task));
                timeEraseActive = true;
                playSound(player, InitSounds.TIME_ERASE_START.get(), true);
                TimeSkipHandler.startVFX(player, false);


            }
        }
    }

    @Override
    protected void onTaskStopped(World world, StandEntity standEntity, IStandPower standPower, StandEntityTask task, StandEntityAction newAction) {
        PlayerEntity player = (PlayerEntity) standEntity.getUser();
        if (player != null) {
            applyEffects(player, standEntity, false);
            restoreEntityPositions((ServerWorld) world);
            removeMarkers((ServerWorld) world);
            if (timeEraseActive) {
                playSound(player, InitSounds.TIME_ERASE_END.get(), false);
                stopSound(player, InitSounds.TIME_ERASE_START.get());
                timeEraseActive = false;
                DelayedTaskScheduler.stopRepeating();
                TimeSkipHandler.startVFX(player, true);


            }
        }
    }

    //Todo:
    // Fix the sound to only be for 192 blocks.
    // Fix the effect so every player can see it.
    // Fix the effect to be only played for 192 blocks.
    // Fix the first effect so the only the King Crimson user can see that one. [DONE]
    // Make a chestplate for the markers that will have a cube model to make the markers more visible.
    // Add a skybox thingy that is only visible for KC user during time erase, probably with another armor stand + chestplate. [SORT-OF DONE, NEEDS FIXING]



    private void applyInvulnerability(PlayerEntity player) {
        if (!player.isCreative()) {
            player.abilities.invulnerable = true;
        }
    }

    private void removeInvulnerability(PlayerEntity player) {
        if (!player.isCreative()) {
            player.abilities.invulnerable = false;
        }
    }

    private void applyEffects(PlayerEntity player, StandEntity standEntity, boolean start) {
        if (start) {
            player.addEffect(new EffectInstance(Effects.INVISIBILITY, MAX_DURATION, 0, false, false));
            player.addEffect(new EffectInstance(ModStatusEffects.FULL_INVISIBILITY.get(), MAX_DURATION, 999999, false, false));
            player.addEffect(new EffectInstance(Effects.WEAKNESS, MAX_DURATION, 999999, false, false));
            player.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, MAX_DURATION, 999999, false, false));
            player.addEffect(new EffectInstance(Effects.WATER_BREATHING, MAX_DURATION, 0, false, false));

            standEntity.addEffect(new EffectInstance(Effects.INVISIBILITY, MAX_DURATION, 0, false, false));

            applyInvulnerability(player);
        } else {
            player.removeEffect(Effects.INVISIBILITY);
            player.removeEffect(ModStatusEffects.FULL_INVISIBILITY.get());
            player.removeEffect(Effects.DAMAGE_RESISTANCE);
            player.removeEffect(Effects.WEAKNESS);
            player.removeEffect(Effects.DIG_SLOWDOWN);
            player.removeEffect(Effects.FIRE_RESISTANCE);
            player.removeEffect(Effects.WATER_BREATHING);

            standEntity.removeEffect(Effects.INVISIBILITY);

            removeInvulnerability(player);
        }
    }

    private void saveEntityPositions(ServerPlayerEntity player) {
        entityPositions.clear();
        markers.clear();
        ServerWorld world = (ServerWorld) player.level;
        world.getEntities(player, player.getBoundingBox().inflate(RADIUS), entity -> {
            if (entity instanceof StandEntity) {
                StandEntity standEntity = (StandEntity) entity;
                return !standEntity.getUser().getUUID().equals(player.getUUID());
            }
            return entity instanceof LivingEntity && entity != player;
        }).forEach(entity -> {
            entityPositions.put(entity, entity.position());
            createMarker(world, entity.position(), player);
        });
    }

    private void restoreEntityPositions(ServerWorld world) {
        entityPositions.forEach((entity, position) -> {
            if (entity.isAlive()) {
                teleportEntityWithRetry(world, entity, position, 5); // Attempt to teleport up to 5 times if needed
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20, 2, false, false));
                }
            }
        });
        entityPositions.clear();
    }

    private void teleportEntityWithRetry(ServerWorld world, Entity entity, Vector3d position, int retries) {
        boolean success = false;
        for (int i = 0; i < retries; i++) {
            try {
                entity.teleportTo(position.x, position.y, position.z);
                success = true;
                break;
            } catch (Exception e) {
                System.err.println("Failed to teleport entity " + entity.getUUID() + " on attempt " + (i + 1) + ": " + e.getMessage());
            }
        }
        if (!success) {
            System.err.println("Failed to teleport entity " + entity.getUUID() + " after " + retries + " attempts");
        }
    }

    private void makeMobsNonAggressive(PlayerEntity player) {
        ServerWorld world = (ServerWorld) player.level;
        world.getEntities(player, player.getBoundingBox().inflate(RADIUS), entity -> entity instanceof MobEntity).forEach(entity -> {
            MobEntity mob = (MobEntity) entity;
            mob.setTarget(null);
        });
    }

    private void createMarker(ServerWorld world, Vector3d position, ServerPlayerEntity kingCrimsonUser) {
        ArmorStandEntity marker = new ArmorStandEntity(world, position.x, position.y, position.z);
        marker.setCustomNameVisible(true);
        marker.setCustomName(new StringTextComponent("|||||||||").withStyle(TextFormatting.RED));
        marker.setInvisible(true);
        marker.setNoGravity(true);
        marker.setSilent(true);

        markers.add(marker);

        IPacket<?> spawnPacket = marker.getAddEntityPacket();
        kingCrimsonUser.connection.send(spawnPacket);

        SEntityMetadataPacket metadataPacket = new SEntityMetadataPacket(marker.getId(), marker.getEntityData(), true);
        kingCrimsonUser.connection.send(metadataPacket);
    }

    private void removeMarkers(ServerWorld world) {
        markers.forEach(marker -> {
            if (marker.isAlive()) {
                IPacket<?> removePacket = new SDestroyEntitiesPacket(marker.getId());
                world.getPlayers(player -> true).forEach(player -> ((ServerPlayerEntity) player).connection.send(removePacket));
            }
        });
        markers.clear();
    }

    private static void playSound(PlayerEntity player, SoundEvent sound, boolean forKingCrimsonUserOnly) {
        if (player instanceof ServerPlayerEntity) {
            ServerWorld world = (ServerWorld) player.level;
            Vector3d position = player.position();

            if (forKingCrimsonUserOnly) {
                ((ServerPlayerEntity) player).connection.send(new SPlaySoundEffectPacket(sound, SoundCategory.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F));
            } else {
                world.getPlayers(p -> p.position().distanceTo(position) <= RADIUS).forEach(p -> {
                    ((ServerPlayerEntity) p).connection.send(new SPlaySoundEffectPacket(sound, SoundCategory.PLAYERS, position.x, position.y, position.z, 1.0F, 1.0F));
                });
            }
        }
    }

    private static void stopSound(PlayerEntity player, SoundEvent sound) {
        if (player instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) player).connection.send(new SStopSoundPacket(sound.getLocation(), SoundCategory.PLAYERS));
        }
    }

    public static class TimeEraseHandler {
        private final UUID playerUUID;
        private final StandEntity standEntity;
        private final IStandPower userPower;
        private final StandEntityTask task;
        private boolean isUsingItem = true;

        public TimeEraseHandler(UUID playerUUID, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
            this.playerUUID = playerUUID;
            this.standEntity = standEntity;
            this.userPower = userPower;
            this.task = task;
        }

        @SubscribeEvent
        public void onLivingAttack(LivingAttackEvent event) {
            if (event.getEntityLiving().getUUID().equals(playerUUID)) {
                stopTimeErase((PlayerEntity) event.getEntityLiving());
            }
        }

        @SubscribeEvent
        public void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.player.getUUID().equals(playerUUID) && event.phase == TickEvent.Phase.END) {
                if (!event.player.isUsingItem()) {
                    if (isUsingItem) {
                        isUsingItem = false;
                        stopTimeErase(event.player);
                    }
                } else {
                    isUsingItem = true;
                }
                checkStamina(event.player);
            }
        }

        private void checkStamina(PlayerEntity player) {
            if (userPower.getStamina() <= 0) {
                stopTimeErase(player);
            }
        }

        private void stopTimeErase(PlayerEntity player) {
            MinecraftForge.EVENT_BUS.unregister(this);
            DelayedTaskScheduler.stopRepeating();
            if (player instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) player).closeContainer();
            }
        }
    }

    public static class DelayedTaskScheduler {
        private static final Map<Integer, ConcurrentLinkedQueue<Runnable>> tasks = new HashMap<>();
        private static int tickCounter = 0;
        private static final ConcurrentLinkedQueue<Runnable> repeatingTasks = new ConcurrentLinkedQueue<>();

        static {
            MinecraftForge.EVENT_BUS.register(DelayedTaskScheduler.class);
        }

        public static void schedule(Runnable task, int delay) {
            int executionTick = tickCounter + delay;
            tasks.computeIfAbsent(executionTick, k -> new ConcurrentLinkedQueue<>()).add(task);
        }

        public static void scheduleRepeating(Runnable task, int initialDelay, int interval) {
            Runnable repeatingTask = new Runnable() {
                @Override
                public void run() {
                    task.run();
                    schedule(this, interval);
                }
            };
            schedule(repeatingTask, initialDelay);
            repeatingTasks.add(repeatingTask);
        }

        public static void stopRepeating() {
            repeatingTasks.clear();
        }

        @SubscribeEvent
        public static void onTick(TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                tickCounter++;
                ConcurrentLinkedQueue<Runnable> tasksToRun = tasks.remove(tickCounter);
                if (tasksToRun != null) {
                    tasksToRun.forEach(Runnable::run);
                }
            }
        }
    }
}
