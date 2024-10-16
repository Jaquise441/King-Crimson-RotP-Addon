package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class KingCrimsonEpitaph extends StandEntityAction {

    private static final int EFFECT_DURATION = 60;
    private static final int SLOWNESS_LEVEL = 1;
    private static final double TELEPORT_DISTANCE = 3.0;
    private static final double DASH_BACKWARD_DISTANCE = 2.0;

    private static final ResourceLocation EPITAPH_SHADER = new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "shaders/post/epitaph.json");

    private EpitaphHandler epitaphHandler;

    public KingCrimsonEpitaph(AbstractBuilder<?> builder) {
        super(builder);
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide){
            if(userPower.getStamina() <= 0){
                userPower.stopHeldAction(false);
            }
        }
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            PlayerEntity player = (PlayerEntity) standEntity.getUser();
            if (player != null) {
                applyEffects(player, true);
                epitaphHandler = new EpitaphHandler(player.getUUID(), standEntity, userPower, task);
                MinecraftForge.EVENT_BUS.register(epitaphHandler);
            }
        }
    }

    @Override
    protected void onTaskStopped(World world, StandEntity standEntity, IStandPower standPower, StandEntityTask task, @Nullable StandEntityAction newAction) {
        PlayerEntity player = (PlayerEntity) standEntity.getUser();
        if (player != null) {
            applyEffects(player, false);
            if (epitaphHandler != null) {
                MinecraftForge.EVENT_BUS.unregister(epitaphHandler);
                epitaphHandler = null;
            }
            if (world.isClientSide()) {
                Minecraft mc = Minecraft.getInstance();
                ((GameRenderer)(Object)mc.gameRenderer).shutdownEffect();
            }
        }
    }

    private static void applyEffects(PlayerEntity player, boolean start) {
        if (start) {
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, EFFECT_DURATION, SLOWNESS_LEVEL, false, false));
        } else {
            player.removeEffect(Effects.MOVEMENT_SLOWDOWN);
        }
    }

    public static class EpitaphHandler {
        private final UUID playerUUID;
        private final StandEntity standEntity;
        private final IStandPower userPower;
        private final StandEntityTask task;
        private boolean shaderLoaded = false;

        public EpitaphHandler(UUID playerUUID, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
            this.playerUUID = playerUUID;
            this.standEntity = standEntity;
            this.userPower = userPower;
            this.task = task;
        }

        @SubscribeEvent
        public void onLivingAttack(LivingAttackEvent event) {
            if (event.getEntityLiving().getUUID().equals(playerUUID)) {
                PlayerEntity player = (PlayerEntity) event.getEntityLiving();
                DamageSource source = event.getSource();
                Entity attacker = source.getEntity();

                if (!(source == DamageSource.FALL)) {
                    event.setCanceled(true);

                    if (userPower.getResolveLevel() >= 3) {
                        if (attacker instanceof StandEntity) {
                            handleEpitaphTeleportBehind(player, ((StandEntity) attacker).getUser());
                        } else {
                            handleEpitaphTeleport(player, attacker);
                        }

                        if (attacker instanceof LivingEntity) {
                            applyAfterEpitaphEffect((LivingEntity) attacker);
                        }
                    } else {
                        handleDashBackward(player, attacker);
                    }

                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            }
        }

        private void handleAttack(PlayerEntity player, Entity attacker, DamageSource source) {
            if (userPower.getResolveLevel() < 3) {
                if (source.isExplosion() || (attacker instanceof MobEntity && attacker.getType().getRegistryName().toString().contains("creeper"))) {
                    return;
                }
            }

            if (!(source == DamageSource.FALL)) {
                if (userPower.getResolveLevel() >= 3) {
                    if (attacker instanceof StandEntity) {
                        handleEpitaphTeleportBehind(player, ((StandEntity) attacker).getUser());
                    } else {
                        handleEpitaphTeleport(player, attacker);
                    }

                    if (attacker instanceof LivingEntity) {
                        applyAfterEpitaphEffect((LivingEntity) attacker);
                    }
                } else {
                    handleDashBackward(player, attacker);
                }
            }
        }

        private void applyAfterEpitaphEffect(LivingEntity target) {
            target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20, 1, false, false));
        }

        private void handleDashBackward(PlayerEntity player, @Nullable Entity attacker) {
            World world = player.level;
            Vector3d currentPosition = player.position();
            Vector3d backwardDirection = player.getLookAngle().scale(-1).normalize();

            Vector3d lastValidPosition = currentPosition;
            boolean obstacleDetected = false;

            for (int i = 1; i <= 2; i++) {
                Vector3d checkPosition = currentPosition.add(backwardDirection.scale(i));
                BlockPos checkBlockPos = new BlockPos(checkPosition);

                if (isPositionClear(world, checkBlockPos)) {
                    lastValidPosition = checkPosition;
                }
                else if (isBlockSolid(world, checkBlockPos) && isPositionClear(world, checkBlockPos.above())) {
                    lastValidPosition = checkPosition.add(0, 1, 0);
                    break;
                }
                else if (isBlockSolid(world, checkBlockPos) && isBlockSolid(world, checkBlockPos.above())) {
                    obstacleDetected = true;
                    break;
                }
            }

            if (obstacleDetected && attacker != null) {
                Vector3d sidewaysDirection = getSidewaysDashDirection(player, attacker);
                Vector3d sidewaysPosition = currentPosition.add(sidewaysDirection.scale(2));
                BlockPos sidewaysBlockPos = new BlockPos(sidewaysPosition);

                if (isPositionClear(world, sidewaysBlockPos)) {
                    lastValidPosition = sidewaysPosition;
                }
            }

            player.teleportTo(lastValidPosition.x, lastValidPosition.y, lastValidPosition.z);

            player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 10, 1, false, false));
            playSound(player, InitSounds.DIAVOLO_DASH.get());
            playSound(player, InitSounds.KINGCRIMSON_DASH.get());
        }

        private Vector3d getSidewaysDashDirection(PlayerEntity player, Entity attacker) {
            Vector3d attackerPosition = attacker.position();
            Vector3d playerPosition = player.position();

            Vector3d directionToAttacker = attackerPosition.subtract(playerPosition).normalize();
            Vector3d lookDirection = player.getLookAngle().normalize();

            Vector3d rightDirection = lookDirection.cross(new Vector3d(0, 1, 0));
            Vector3d leftDirection = rightDirection.scale(-1);

            if (rightDirection.dot(directionToAttacker) > 0) {
                return leftDirection;
            } else {
                return rightDirection;
            }
        }


        private void handleEpitaphTeleport(PlayerEntity player, @Nullable Entity attacker) {
            if (attacker instanceof LivingEntity) {
                LivingEntity livingAttacker = (LivingEntity) attacker;

                TimeSkipHandler.startVFX(player, true);

                double distanceToAttacker = player.position().distanceTo(livingAttacker.position());

                if (distanceToAttacker > 25.0) {
                    Vector3d direction = livingAttacker.position().subtract(player.position()).normalize();
                    Vector3d teleportPosition = player.position().add(direction.scale(25.0));

                    if (!isPositionClear(player.level, new BlockPos(teleportPosition))) {
                        teleportPosition = findClearPositionAround(player.level, teleportPosition);
                    }
                    player.teleportTo(teleportPosition.x, teleportPosition.y, teleportPosition.z);
                } else {
                    teleportAround(player, livingAttacker);
                }

                applyAfterEpitaphEffect(livingAttacker);
                playSound(player, InitSounds.EPITAPH_TIMESKIP.get());
            }
        }

        private void handleEpitaphTeleportBehind(PlayerEntity player, @Nullable Entity standUser) {
            if (standUser instanceof LivingEntity) {
                LivingEntity livingUser = (LivingEntity) standUser;
                teleportAround(player, livingUser);
            }
        }

        private void teleportAround(PlayerEntity player, LivingEntity target) {
            Vector3d lookDirection = target.getLookAngle().normalize();

            Vector3d behindTarget = target.position().subtract(lookDirection.scale(TELEPORT_DISTANCE));

            if (!isPositionClear(player.level, new BlockPos(behindTarget))) {
                behindTarget = searchForClearSpot(player.level, target.position(), lookDirection);
            }

            player.teleportTo(behindTarget.x, behindTarget.y, behindTarget.z);
            player.lookAt(EntityAnchorArgument.Type.FEET, target.position());

        }

        private Vector3d searchForClearSpot(World world, Vector3d targetPosition, Vector3d lookDirection) {
            Random rand = new Random();

            Vector3d left = targetPosition.add(lookDirection.cross(new Vector3d(0, 1, 0)).scale(TELEPORT_DISTANCE));
            Vector3d right = targetPosition.add(lookDirection.cross(new Vector3d(0, 1, 0)).scale(-TELEPORT_DISTANCE));

            Vector3d[] potentialPositions = new Vector3d[]{
                    targetPosition.subtract(lookDirection.scale(TELEPORT_DISTANCE)),
                    left,
                    right
            };

            for (int i = 0; i < potentialPositions.length; i++) {
                Vector3d pos = potentialPositions[i];
                if (isPositionClear(world, new BlockPos(pos))) {
                    return pos;
                }
            }

            return findClearPositionAround(world, targetPosition);
        }

        private Vector3d findClearPositionAround(World world, Vector3d targetPosition) {
            BlockPos basePos = new BlockPos(targetPosition);

            for (int x = -3; x <= 3; x++) {
                for (int z = -3; z <= 3; z++) {
                    BlockPos searchPos = basePos.offset(x, 0, z);
                    for (int y = -1; y <= 1; y++) {
                        BlockPos checkPos = searchPos.above(y);
                        if (isPositionClear(world, checkPos)) {
                            return new Vector3d(checkPos.getX() + 0.5, checkPos.getY(), checkPos.getZ() + 0.5);
                        }
                    }
                }
            }

            return targetPosition;
        }

        private boolean isBlockSolid(World world, BlockPos pos) {
            return world.getBlockState(pos).isSolidRender(world, pos);
        }

        private boolean isPositionClear(World world, BlockPos pos) {
            if (pos.getY() < 0 || pos.getY() >= world.getMaxBuildHeight()) {
                return false;
            }

            return !isBlockSolid(world, pos) && !isBlockSolid(world, pos.above()) && !isBlockSolid(world, pos.above(2));
        }

        private void playSound(PlayerEntity player, SoundEvent sound) {
            player.level.playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }

        @SubscribeEvent
        public void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.player.getUUID().equals(playerUUID)) {
                if (event.player.isAlive()) {
                    if (event.player.level.isClientSide()) {
                        Minecraft mc = Minecraft.getInstance();
                        if (!shaderLoaded) {
                            RenderSystem.recordRenderCall(() -> {
                                ((GameRenderer)(Object)mc.gameRenderer).loadEffect(EPITAPH_SHADER);
                            });
                            shaderLoaded = true;
                        }
                    }
                }
            }
        }
    }
}