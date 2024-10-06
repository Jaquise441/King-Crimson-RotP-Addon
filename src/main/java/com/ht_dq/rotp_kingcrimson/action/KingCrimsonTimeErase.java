package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.ht_dq.rotp_kingcrimson.network.AddonPackets;
import com.ht_dq.rotp_kingcrimson.network.server.KingCrimsonDimensionChangeHandler;
import com.ht_dq.rotp_kingcrimson.network.server.PlayerTimerActivePacket;
import com.ht_dq.rotp_kingcrimson.network.server.RemoveTimerActivePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import com.github.standobyte.jojo.entity.AfterimageEntity;
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
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;

public class KingCrimsonTimeErase extends StandEntityAction {

    private static final int MAX_DURATION = 200;
    private static final double RADIUS = 192.0;

    public static final Map<UUID, Boolean> playerTimeEraseActive = new HashMap<>();

    private final Map<UUID, KingCrimsonDimensionChangeHandler> dimensionChangeHandlers = new HashMap<>();

    private final Map<Entity, Vector3d> entityPositions = new HashMap<>();
    private final Set<ArmorStandEntity> markers = new HashSet<>();
    private boolean timeEraseActive = false;
    private static boolean isTimeEraseActive = false;

    private final Map<Entity, AfterimageEntity> afterimages = new HashMap<>();
    private final Map<Entity, AfterimageEntity> stationaryAfterimages = new HashMap<>();
    private KingCrimsonDimensionChangeHandler dimensionChangeHandler;

    private final Map<Entity, Boolean> originalPiglinAggression = new HashMap<>();

    public KingCrimsonTimeErase(Builder builder) {
        super(builder);
    }


    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        AtomicBoolean activation = new AtomicBoolean(true);
        playerTimeEraseActive.keySet().forEach(entry ->{
            if (entry != user.getUUID()){
                activation.set(false);
            }
        });
        return activation.get() ?ActionConditionResult.POSITIVE:ActionConditionResult.NEGATIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            ServerPlayerEntity player = (ServerPlayerEntity) standEntity.getUser();
            if (player != null) {
                dimensionChangeHandler = new KingCrimsonDimensionChangeHandler(player);
                isTimeEraseActive = true;
                UUID playerId = player.getUUID();
                playerTimeEraseActive.put(playerId, true);
                if(userPower.getUser() instanceof ServerPlayerEntity){
                    AddonPackets.sendToClientsTrackingAndSelf(new PlayerTimerActivePacket(playerId,true),player);
                }
                dimensionChangeHandlers.put(playerId, new KingCrimsonDimensionChangeHandler(player));
                applyEffects(player, standEntity, true);
                saveEntityPositions(player);
                createAfterimages(player);
                disablePiglinAggression(player);
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
            UUID playerId = player.getUUID();
            if(player instanceof ServerPlayerEntity){
                if (Boolean.TRUE.equals(playerTimeEraseActive.get(playerId))) {
                    applyEffects(player, standEntity, false);
                    restoreEntityPositions((ServerWorld) world);
                    removeAfterimages((ServerPlayerEntity) player);
                    removeStationaryAfterimages((ServerPlayerEntity) player);
                    restorePiglinAggression((ServerWorld) world);
                    isTimeEraseActive = false;
                    playSound(player, InitSounds.TIME_ERASE_END.get(), false);
                    stopSound(player, InitSounds.TIME_ERASE_START.get());

                    playerTimeEraseActive.remove(playerId);
                    if(standPower.getUser() instanceof ServerPlayerEntity){
                        AddonPackets.sendToClientsTrackingAndSelf(new RemoveTimerActivePacket(playerId),player);
                    }
                    dimensionChangeHandlers.remove(playerId);

                    DelayedTaskScheduler.stopRepeating();
                    TimeSkipHandler.startVFX(player, true);

                    List<LivingEntity> list = MCUtil.entitiesAround(LivingEntity.class, standEntity,standEntity.getMaxEffectiveRange()*4,false, EntityPredicates.ENTITY_STILL_ALIVE);
                    if(!list.isEmpty()){
                        list.forEach(livingEntity -> {
                            IStandPower.getStandPowerOptional(livingEntity).ifPresent(power -> power.setLeapCooldown(40));
                        });
                    }
                }
            }
        }
    }

    public static boolean isTimeEraseActive() {
        return isTimeEraseActive;
    }

    private void postTimeEraseCleanup(ServerWorld world, PlayerEntity player, StandEntity standEntity) {
        if (player.abilities.invulnerable) {
            removeInvulnerability(player);
        }

//        if (!markers.isEmpty()) {
//            removeMarkers(world);
//        }

        if (!entityPositions.isEmpty()) {
            restoreEntityPositions(world);
        }
    }

    //Todo:
    // Balls


    private void createAfterimages(ServerPlayerEntity player) {
        ServerWorld world = (ServerWorld) player.level;
        Scoreboard scoreboard = world.getScoreboard();

        ScorePlayerTeam redTeam = scoreboard.getPlayerTeam("KC_RedGlow");
        if (redTeam == null) {
            redTeam = scoreboard.addPlayerTeam("KC_RedGlow");
            redTeam.setColor(TextFormatting.RED);
        }

        final ScorePlayerTeam finalRedTeam = redTeam;

        world.getEntities(player, player.getBoundingBox().inflate(RADIUS), entity -> entity instanceof LivingEntity && entity != player)
                .forEach(entity -> {
                    AfterimageEntity movingAfterimage = new AfterimageEntity(world, (LivingEntity) entity, 10);
                    movingAfterimage.setLifeSpan(MAX_DURATION);
                    afterimages.put(entity, movingAfterimage);
                    sendAfterimageToPlayer(player, movingAfterimage);

                    Vector3d startPos = entity.position();
                    AfterimageEntity stationaryAfterimage = new AfterimageEntity(world, (LivingEntity) entity, 10000);
                    stationaryAfterimage.setLifeSpan(MAX_DURATION);
                    stationaryAfterimage.setPos(startPos.x, startPos.y, startPos.z);
                    stationaryAfterimage.setGlowing(true);

                    scoreboard.addPlayerToTeam(stationaryAfterimage.getStringUUID(), finalRedTeam);

                    stationaryAfterimages.put(entity, stationaryAfterimage);
                    sendAfterimageWithGlowToPlayer(player, stationaryAfterimage);
                });
    }

    private void sendAfterimageWithGlowToPlayer(ServerPlayerEntity kingCrimsonUser, AfterimageEntity afterimage) {
        IPacket<?> spawnPacket = afterimage.getAddEntityPacket();
        kingCrimsonUser.connection.send(spawnPacket);

        SEntityMetadataPacket metadataPacket = new SEntityMetadataPacket(afterimage.getId(), afterimage.getEntityData(), true);
        kingCrimsonUser.connection.send(metadataPacket);

    }

    private void removeAfterimages(ServerPlayerEntity kingCrimsonUser) {
        afterimages.values().forEach(afterimage -> {
            if (afterimage.isAlive()) {
                IPacket<?> removePacket = new SDestroyEntitiesPacket(afterimage.getId());
                kingCrimsonUser.connection.send(removePacket);
                afterimage.remove();
            }
        });
        afterimages.clear();
    }

    private void removeStationaryAfterimages(ServerPlayerEntity kingCrimsonUser) {
        stationaryAfterimages.values().forEach(afterimage -> {
            if (afterimage.isAlive()) {
                IPacket<?> removePacket = new SDestroyEntitiesPacket(afterimage.getId());
                kingCrimsonUser.connection.send(removePacket);
                afterimage.remove();
            }
        });
        stationaryAfterimages.clear();
    }


    private void sendAfterimageToPlayer(ServerPlayerEntity kingCrimsonUser, AfterimageEntity afterimage) {
        IPacket<?> spawnPacket = afterimage.getAddEntityPacket();
        kingCrimsonUser.connection.send(spawnPacket);

        SEntityMetadataPacket metadataPacket = new SEntityMetadataPacket(afterimage.getId(), afterimage.getEntityData(), true);
        kingCrimsonUser.connection.send(metadataPacket);
    }


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
            player.addEffect(new EffectInstance(Effects.LUCK, MAX_DURATION, 0, false, false));
            player.addEffect(new EffectInstance(ModStatusEffects.FULL_INVISIBILITY.get(), MAX_DURATION, 999999, false, false));
            player.addEffect(new EffectInstance(Effects.WEAKNESS, MAX_DURATION, 999999, false, false));
            player.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, MAX_DURATION, 999999, false, false));
            player.addEffect(new EffectInstance(Effects.WATER_BREATHING, MAX_DURATION, 0, false, false));
            standEntity.addEffect(new EffectInstance(Effects.INVISIBILITY, MAX_DURATION, 0, false, false));
            applyInvulnerability(player);
        } else {
            player.removeEffect(Effects.LUCK);
            player.removeEffect(Effects.INVISIBILITY);
            player.removeEffect(ModStatusEffects.FULL_INVISIBILITY.get());
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
            //createMarker(world, entity.position(), player);
        });
    }

    private void restoreEntityPositions(ServerWorld world) {
        entityPositions.forEach((entity, position) -> {
            if (entity.isAlive()) {
                teleportEntityWithRetry(world, entity, position, 5);
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


//    private void createMarker(ServerWorld world, Vector3d position, ServerPlayerEntity kingCrimsonUser) {
//        ArmorStandEntity marker = new ArmorStandEntity(world, position.x, position.y, position.z);
//        marker.setCustomNameVisible(true);
//        marker.setCustomName(new StringTextComponent("|||||||||").withStyle(TextFormatting.RED));
//        marker.setInvisible(true);
//        marker.setNoGravity(true);
//        marker.setSilent(true);
//
//        marker.addTag("KC_MARKER");
//
//        markers.add(marker);
//
//        IPacket<?> spawnPacket = marker.getAddEntityPacket();
//        kingCrimsonUser.connection.send(spawnPacket);
//
//        SEntityMetadataPacket metadataPacket = new SEntityMetadataPacket(marker.getId(), marker.getEntityData(), true);
//        kingCrimsonUser.connection.send(metadataPacket);
//    }

//    private void removeMarkers(ServerWorld world) {
//        for (ArmorStandEntity marker : markers) {
//            if (marker.isAlive()) {
//                IPacket<?> removePacket = new SDestroyEntitiesPacket(marker.getId());
//                for (ServerPlayerEntity player : world.getPlayers(player -> true)) {
//                    player.connection.send(removePacket);
//                }
//                marker.remove();
//            }
//        }
//        markers.clear();
//    }

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

    private void disablePiglinAggression(ServerPlayerEntity player) {
        ServerWorld world = (ServerWorld) player.level;
        world.getEntities(player, player.getBoundingBox().inflate(RADIUS), entity -> entity instanceof PiglinEntity || entity instanceof PiglinBruteEntity)
                .forEach(entity -> {
                    if (entity instanceof PiglinEntity) {
                        PiglinEntity piglin = (PiglinEntity) entity;
                        originalPiglinAggression.put(piglin, piglin.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
                        piglin.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                        piglin.getBrain().setMemory(MemoryModuleType.ADMIRING_ITEM, true);
                    } else if (entity instanceof PiglinBruteEntity) {
                        PiglinBruteEntity piglinBrute = (PiglinBruteEntity) entity;
                        originalPiglinAggression.put(piglinBrute, piglinBrute.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
                        piglinBrute.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                    }
                });
    }

    private void restorePiglinAggression(ServerWorld world) {
        originalPiglinAggression.forEach((entity, wasAggressive) -> {
            if (entity.isAlive()) {
                if (entity instanceof PiglinEntity) {
                    PiglinEntity piglin = (PiglinEntity) entity;
                    piglin.getBrain().eraseMemory(MemoryModuleType.ADMIRING_ITEM);
                    if (wasAggressive) {
                        piglin.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, piglin.getTarget());
                    }
                } else if (entity instanceof PiglinBruteEntity) {
                    PiglinBruteEntity piglinBrute = (PiglinBruteEntity) entity;
                    if (wasAggressive) {
                        piglinBrute.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, piglinBrute.getTarget());
                    }
                }
            }
        });
        originalPiglinAggression.clear();
    }
}