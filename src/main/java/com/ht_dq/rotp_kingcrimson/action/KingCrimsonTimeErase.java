package com.ht_dq.rotp_kingcrimson.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.capability.world.TimeStopHandler;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.ht_dq.rotp_kingcrimson.entity.KCAfterimageEntity;
import com.ht_dq.rotp_kingcrimson.entity.TimeEraseDecoyEntity;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.ht_dq.rotp_kingcrimson.network.AddonPackets;
import com.ht_dq.rotp_kingcrimson.network.server.KingCrimsonDimensionChangeHandler;
import com.ht_dq.rotp_kingcrimson.network.server.PlayerTimerActivePacket;
import com.ht_dq.rotp_kingcrimson.network.server.RemoveTimerActivePacket;
import com.ht_dq.rotp_kingcrimson.util.VFXServerHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KingCrimsonTimeErase extends StandEntityAction {

    private static final int MAX_DURATION = 200;
    private static final double RADIUS = 192.0;
    public static final Map<UUID, Boolean> playerTimeEraseActive = new HashMap<>();
    private final Map<UUID, KingCrimsonDimensionChangeHandler> dimensionChangeHandlers = new HashMap<>();
    private static boolean isTimeEraseActive = false;
    private final Map<Entity, KCAfterimageEntity> afterimages = new HashMap<>();
    private final Map<Entity, KCAfterimageEntity> stationaryAfterimages = new HashMap<>();
    private final Map<Entity, Boolean> originalPiglinAggression = new HashMap<>();
    private final int delay = 60;
    private final Map<Entity, ArrayList<Vector3d>> POSITIONS = new HashMap<>();
    private final Map<Entity, ArrayList<Float>> YROT = new HashMap<>();
    private final Map<Entity,ArrayList<Float>> XROT = new HashMap<>();
    public KingCrimsonTimeErase(Builder builder) {
        super(builder);
    }


    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        AtomicBoolean activation = new AtomicBoolean(true);
        playerTimeEraseActive.keySet().forEach(entry ->{
            if (!entry.equals(user.getUUID())){
                activation.set(false);
            }
        });
        return ActionConditionResult.noMessage(activation.get());
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            if (userPower.getStamina() <= 0) {
                userPower.stopHeldAction(false);
            }

            stationaryAfterimages.forEach((entity, afterimage) -> {
                POSITIONS.putIfAbsent(entity, new ArrayList<>());
                POSITIONS.get(entity).add(entity.position());

                YROT.putIfAbsent(entity, new ArrayList<>());
                YROT.get(entity).add(entity.yRot);

                XROT.putIfAbsent(entity, new ArrayList<>());
                XROT.get(entity).add(entity.xRot);

            });

        }
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        LivingEntity user = userPower.getUser();
        if (!world.isClientSide() && user instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) userPower.getUser();

            new KingCrimsonDimensionChangeHandler(player);
            isTimeEraseActive = true;
            UUID playerId = player.getUUID();
            playerTimeEraseActive.put(playerId, true);

            AddonPackets.sendToClientsTrackingAndSelf(new PlayerTimerActivePacket(playerId, true), player);
            dimensionChangeHandlers.put(playerId, new KingCrimsonDimensionChangeHandler(player));
            applyEffects(player, standEntity, true);
            createAfterimages(player);
            disablePiglinAggression(player);
            MinecraftForge.EVENT_BUS.register(new TimeEraseHandler(player.getUUID(), standEntity, userPower, task));
            playSound(player, InitSounds.TIME_ERASE_START.get(), true);
            VFXServerHelper.startVFX(player, false);
            
            TimeEraseDecoyEntity userDecoy = new TimeEraseDecoyEntity(world);
            userDecoy.copyPosition(user);
            userDecoy.setKCUser(user);
            world.addFreshEntity(userDecoy);
        }
    }

    @Override
    protected void onTaskStopped(World world, StandEntity standEntity, IStandPower standPower, StandEntityTask task, StandEntityAction newAction) {
        LivingEntity user = standPower.getUser();
        if (user instanceof PlayerEntity && !world.isClientSide()) {
            PlayerEntity player = (PlayerEntity) user;
            UUID playerId = player.getUUID();
            if (player instanceof ServerPlayerEntity) {
                if (Boolean.TRUE.equals(playerTimeEraseActive.get(playerId))) {
                    applyEffects(player, standEntity, false);
                    restorePiglinAggression((ServerWorld) world);
                    isTimeEraseActive = false;
                    playSound(player, InitSounds.TIME_ERASE_END.get(), false);
                    stopSound(player, InitSounds.TIME_ERASE_START.get());

                    removeAfterimages((ServerPlayerEntity) player);
                    stationaryAfterimages.forEach((entity, afterimage) -> {
                        if (entity.isAlive()) {
                            Vector3d finalPos = POSITIONS.get(entity).get(Math.max(0,POSITIONS.get( entity).size()-delay));
                            float rotY = YROT.get(entity).get(Math.max(0,YROT.get( entity).size()-delay));
                            float rotX = XROT.get(entity).get(Math.max(0,XROT.get( entity).size()-delay));
                            MCUtil.runCommand((LivingEntity) entity,"tp @s "+finalPos.x+" "+finalPos.y+" "+finalPos.z+" "+rotY+" "+rotX);
                            POSITIONS.remove(entity);
                            XROT.remove(entity);
                            YROT.remove(entity);
                        }
                    });
                    removeStationaryAfterimages((ServerPlayerEntity) player);

                    playerTimeEraseActive.remove(playerId);
                    if (standPower.getUser() instanceof ServerPlayerEntity) {
                        AddonPackets.sendToClientsTrackingAndSelf(new RemoveTimerActivePacket(playerId), player);
                    }
                    dimensionChangeHandlers.remove(playerId);
                    DelayedTaskScheduler.stopRepeating();
                    VFXServerHelper.startVFX(player, true);
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
    }

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
                    KCAfterimageEntity movingAfterimage = new KCAfterimageEntity(world, (LivingEntity) entity, 10);
                    movingAfterimage.setLifeSpan(MAX_DURATION);
                    afterimages.put(entity, movingAfterimage);
                    sendAfterimageToPlayer(player, movingAfterimage);

                    KCAfterimageEntity stationaryAfterimage = new KCAfterimageEntity(world, (LivingEntity) entity, delay);
                    stationaryAfterimage.setLifeSpan(MAX_DURATION);
                    stationaryAfterimage.setGlowing(true);
                    scoreboard.addPlayerToTeam(stationaryAfterimage.getStringUUID(), finalRedTeam);
                    stationaryAfterimages.put(entity, stationaryAfterimage);
                    sendAfterimageWithGlowToPlayer(player, stationaryAfterimage);
                });
    }

    private void sendAfterimageWithGlowToPlayer(ServerPlayerEntity kingCrimsonUser, KCAfterimageEntity afterimage) {
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

    private void sendAfterimageToPlayer(ServerPlayerEntity kingCrimsonUser, KCAfterimageEntity afterimage) {
        IPacket<?> spawnPacket = afterimage.getAddEntityPacket();
        kingCrimsonUser.connection.send(spawnPacket);

        SEntityMetadataPacket metadataPacket = new SEntityMetadataPacket(afterimage.getId(), afterimage.getEntityData(), true);
        kingCrimsonUser.connection.send(metadataPacket);
    }

    private void applyInvulnerability(LivingEntity user) {
        setInvulnerability(user, true);
    }

    private void removeInvulnerability(LivingEntity user) {
        setInvulnerability(user, false);
    }
    
    private void setInvulnerability(LivingEntity user, boolean invulnerable) {
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            if (!player.isCreative()) {
                player.abilities.invulnerable = invulnerable;
            }
        }
        else {
            user.setInvulnerable(invulnerable);
        }
    }

    private void applyEffects(LivingEntity player, StandEntity standEntity, boolean start) {
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

    private static void playSound(LivingEntity player, SoundEvent sound, boolean forKingCrimsonUserOnly) {
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

    private static void stopSound(LivingEntity player, SoundEvent sound) {
        if (player instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) player).connection.send(new SStopSoundPacket(sound.getRegistryName(), SoundCategory.PLAYERS));
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

        @SubscribeEvent(priority = EventPriority.HIGH)
        public void onItemPickup(EntityItemPickupEvent event) {
            PlayerEntity player = event.getPlayer();
            if (playerTimeEraseActive.containsKey(player.getUUID()) && Boolean.TRUE.equals(playerTimeEraseActive.get(player.getUUID()))) {
                event.setCanceled(true);
            }
        }

        @SubscribeEvent(priority = EventPriority.HIGH)
        public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
            PlayerEntity player = event.getPlayer();
            if (playerTimeEraseActive.containsKey(player.getUUID()) && Boolean.TRUE.equals(playerTimeEraseActive.get(player.getUUID()))) {
                stopTimeErase(player);
            }
        }

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void onRenderWorldLast(RenderWorldLastEvent event) {
            Minecraft mc = Minecraft.getInstance();
            ClientPlayerEntity player = mc.player;

            if (player != null && playerTimeEraseActive.containsKey(player.getUUID())
                    && Boolean.TRUE.equals(playerTimeEraseActive.get(player.getUUID()))
                    && player.isSprinting()) {
                player.clearFire();
            }
        }

        @SubscribeEvent
        public void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (!TimeStopHandler.isTimeStopped(event.player.level, event.player.blockPosition())) {
                if (event.player.getUUID().equals(playerUUID) && event.phase == TickEvent.Phase.END) {
                    if (event.player.isUsingItem()) {
                        stopTimeErase(event.player);
                    }
                }
            }
        }

        @SubscribeEvent
        public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
            if (event.getPlayer().getUUID().equals(playerUUID)) {
                stopTimeErase(event.getPlayer());
            }
        }

        @SubscribeEvent
        public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
            if (event.getPlayer().getUUID().equals(playerUUID)) {
                stopTimeErase(event.getPlayer());
            }
        }

        @SubscribeEvent
        public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
            if (event.getPlayer().getUUID().equals(playerUUID)) {
                stopTimeErase(event.getPlayer());
            }
        }

        @SubscribeEvent
        public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
            if (event.getPlayer().getUUID().equals(playerUUID)) {
                stopTimeErase(event.getPlayer());
            }
        }

        @SubscribeEvent
        public void onAttackEntity(AttackEntityEvent event) {
            PlayerEntity player = event.getPlayer();
            if (player.getUUID().equals(playerUUID)) {
                stopTimeErase(player);
            }
        }

        @SubscribeEvent
        public void onLeftClickEntitySpecific(PlayerInteractEvent.EntityInteractSpecific event) {
            PlayerEntity player = event.getPlayer();
            if (player.getUUID().equals(playerUUID)) {
                stopTimeErase(player);
            }
        }

        private void stopTimeErase(PlayerEntity player) {
            MinecraftForge.EVENT_BUS.unregister(this);
            DelayedTaskScheduler.stopRepeating();
            if (player instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) player).closeContainer();
            }
            userPower.stopHeldAction(false);
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
