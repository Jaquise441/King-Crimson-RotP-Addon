package com.ht_dq.rotp_kingcrimson.action;

import java.util.Optional;
import javax.annotation.Nullable;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.effect.StandEffectInstance;
import com.github.standobyte.jojo.action.stand.effect.StandEffectType;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandEffectsTracker;
import com.ht_dq.rotp_kingcrimson.client.ClientProxy;
import com.ht_dq.rotp_kingcrimson.config.KCConfig;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.ht_dq.rotp_kingcrimson.init.InitStandEffects;
import com.ht_dq.rotp_kingcrimson.init.InitStands;
import com.ht_dq.rotp_kingcrimson.network.AddonPackets;
import com.ht_dq.rotp_kingcrimson.network.server.ActivateEpitaphShader;
import com.ht_dq.rotp_kingcrimson.network.server.RemoveEpitaphShader;
import com.ht_dq.rotp_kingcrimson.util.VFXServerHelper;

import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class KingCrimsonEpitaph extends StandAction {
    private static final int EFFECT_DURATION = KCConfig.EPITAPH_DURATION.get();
    private static final int SLOWNESS_LEVEL = 1;
    private static final double TELEPORT_DISTANCE = 3.0;
    private static boolean soundPlayed = false;

    public KingCrimsonEpitaph(Builder builder) {
        super(builder);
    }

    @Override
    protected void holdTick(World world, LivingEntity user, IStandPower userPower, int ticksHeld, ActionTarget target, boolean requirementsFulfilled) {
        if (requirementsFulfilled) {
            if (!world.isClientSide) {
                if (user != null) {
                    applyEffects(user, true);
                    userPower.getContinuousEffects().addEffect(new EpitaphEffect());
                }
            }

            if (!soundPlayed) {
                if (world.isClientSide()) {
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                            ClientProxy.playSound(
                                    InitSounds.KINGCRIMSON_EPITAPH.get(),
                                    user.getSoundSource(),
                                    1.0F,
                                    1.0F,
                                    user
                            )
                    );
                }
                soundPlayed = true;
            }
        }
    }

    public static void stopHolding(World world, LivingEntity user, IStandPower power) {
        if (!world.isClientSide) {
            applyEffects(user, false);
            StandEffectsTracker.getEffectOfType(user, InitStandEffects.EPITAPH.get()).ifPresent(StandEffectInstance::remove);
            power.setCooldownTimer(InitStands.KINGCRIMSON_EPITAPH.get(), KCConfig.EPITAPH_COOLDOWN.get());
            soundPlayed = false;
        }
    }

    private static void applyEffects(LivingEntity player, boolean start) {
        if (start) {
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, EFFECT_DURATION, SLOWNESS_LEVEL, false, false));
        } else {
            player.removeEffect(Effects.MOVEMENT_SLOWDOWN);
        }
    }
    
    
    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onLivingAttack(LivingAttackEvent event) {
            Optional<EpitaphEffect> epitaphEffect = StandEffectsTracker.getEffectOfType(event.getEntityLiving(), InitStandEffects.EPITAPH.get());
            epitaphEffect.ifPresent(epitaph -> epitaph.onLivingAttack(event));
        }
    }
    
    public static class EpitaphEffect extends StandEffectInstance {

        public EpitaphEffect() {
            this(InitStandEffects.EPITAPH.get());
        }

        public EpitaphEffect(StandEffectType<?> effectType) {
            super(effectType);
        }

        @Override
        protected void start() {}

        @Override
        protected void tick() {
            if (!user.level.isClientSide() && user instanceof ServerPlayerEntity) {
                AddonPackets.sendToClient(new ActivateEpitaphShader(user.getId()), (ServerPlayerEntity) user);
            }
            IStandPower power = this.getUserPower();
            if (power.getHeldAction() != InitStands.KINGCRIMSON_EPITAPH.get()) {
                this.removeAllShit();
            }
        }

        @Override
        protected void stop() {}

        @Override
        protected boolean needsTarget() {
            return false;
        }

        private static void applyEpitaphTimeSkip(LivingEntity player, LivingEntity attacker) {
            World world = player.level;
            List<Entity> entities = world.getEntities(player, player.getBoundingBox().inflate(32), entity ->
                    entity instanceof LivingEntity &&
                            !entity.getUUID().equals(player.getUUID()) &&
                            !entity.getUUID().equals(attacker.getUUID()));

            applyInvulnerability(player);

            for (int i = 0; i < 40; i++) {
                for (Entity entity : entities) {
                    entity.tick();
                }
            }

            MinecraftServer server = player.level.getServer();
            if (server != null){
                server.execute(() -> removeInvulnerability(player));
            }
        }

        private static void applyInvulnerability(LivingEntity living) {
            if(living instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity) living;
                if (!player.isCreative()) {
                    player.abilities.invulnerable = true;
                }
            }
            else {
                living.setInvulnerable(true);
            }
        }

        private static void removeInvulnerability(LivingEntity living) {
            if(living instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity) living;
                if (!player.isCreative()) {
                    player.abilities.invulnerable = false;
                }
            }else {
                living.setInvulnerable(false);
            }
        }

        private static void handleDashBackward(LivingEntity livingEntity, @Nullable Entity attacker) {
            World world = livingEntity.level;
            Vector3d currentPosition = livingEntity.position();
            Vector3d backwardDirection = livingEntity.getLookAngle().scale(-1).normalize();

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
                Vector3d sidewaysDirection = getSidewaysDashDirection(livingEntity, attacker);
                Vector3d sidewaysPosition = currentPosition.add(sidewaysDirection.scale(2));
                BlockPos sidewaysBlockPos = new BlockPos(sidewaysPosition);

                if (isPositionClear(world, sidewaysBlockPos)) {
                    lastValidPosition = sidewaysPosition;
                }
            }

            livingEntity.teleportTo(lastValidPosition.x, lastValidPosition.y, lastValidPosition.z);

            livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 10, 1, false, false));
            playSound(livingEntity, InitSounds.DIAVOLO_DASH.get());
            playSound(livingEntity, InitSounds.KINGCRIMSON_DASH.get());
        }

        private static Vector3d getSidewaysDashDirection(LivingEntity livingEntity, Entity attacker) {
            Vector3d attackerPosition = attacker.position();
            Vector3d position = livingEntity.position();

            Vector3d directionToAttacker = attackerPosition.subtract(position).normalize();
            Vector3d lookDirection = livingEntity.getLookAngle().normalize();

            Vector3d rightDirection = lookDirection.cross(new Vector3d(0, 1, 0));
            Vector3d leftDirection = rightDirection.scale(-1);

            if (rightDirection.dot(directionToAttacker) > 0) {
                return leftDirection;
            } else {
                return rightDirection;
            }
        }

        private static void handleEpitaphTeleport(LivingEntity livingEntity, @Nullable Entity attacker) {
            if (attacker instanceof LivingEntity) {
                LivingEntity livingAttacker = (LivingEntity) attacker;

                VFXServerHelper.startVFX(livingEntity, true);

                double distanceToAttacker = livingEntity.position().distanceTo(livingAttacker.position());

                if (distanceToAttacker > 25.0) {
                    Vector3d direction = livingAttacker.position().subtract(livingEntity.position()).normalize();
                    Vector3d teleportPosition = livingEntity.position().add(direction.scale(25.0));

                    if (!isPositionClear(livingEntity.level, new BlockPos(teleportPosition))) {
                        teleportPosition = findClearPositionAround(livingEntity.level, teleportPosition);
                    }
                    livingEntity.teleportTo(teleportPosition.x, teleportPosition.y, teleportPosition.z);
                } else {
                    teleportAround(livingEntity, livingAttacker);
                }
                applyEpitaphTimeSkip(livingEntity, livingAttacker);

                applyAfterEpitaphEffect(livingAttacker);
                playSound(livingEntity, InitSounds.EPITAPH_TIMESKIP.get());
            }
        }

        private static void handleEpitaphTeleportBehind(LivingEntity livingEntity, @Nullable Entity standUser) {
            if (standUser instanceof LivingEntity) {
                LivingEntity livingUser = (LivingEntity) standUser;
                teleportAround(livingEntity, livingUser);
            }
        }

        private static void playSound(LivingEntity livingEntity, SoundEvent sound) {
            livingEntity.level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }

        private static void teleportAround(LivingEntity player, LivingEntity target) {
            Vector3d lookDirection = target.getLookAngle().normalize();

            Vector3d behindTarget = target.position().subtract(lookDirection.scale(TELEPORT_DISTANCE));

            if (!isPositionClear(player.level, new BlockPos(behindTarget))) {
                behindTarget = searchForClearSpot(player.level, target.position(), lookDirection);
            }

            player.teleportTo(behindTarget.x, behindTarget.y, behindTarget.z);
            player.lookAt(EntityAnchorArgument.Type.FEET, target.position());

        }

        private static Vector3d searchForClearSpot(World world, Vector3d targetPosition, Vector3d lookDirection) {
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

        private static Vector3d findClearPositionAround(World world, Vector3d targetPosition) {
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

        private static boolean isBlockSolid(World world, BlockPos pos) {
            return world.getBlockState(pos).isSolidRender(world, pos);
        }

        private static boolean isPositionClear(World world, BlockPos pos) {
            if (pos.getY() < 0 || pos.getY() >= world.getMaxBuildHeight()) {
                return false;
            }

            return !isBlockSolid(world, pos) && !isBlockSolid(world, pos.above()) && !isBlockSolid(world, pos.above(2));
        }

        public void onLivingAttack(LivingAttackEvent event) {
            DamageSource source = event.getSource();
            Entity attacker = source.getEntity();
            boolean isResolveLevel3OrHigher = userPower.getResolveLevel() >= 3;

            if (isResolveLevel3OrHigher) {
                if (source == DamageSource.FALL || source == DamageSource.LIGHTNING_BOLT) {
                    event.setCanceled(true);
                    VFXServerHelper.startVFX(user, true);
                    playSound(user, InitSounds.EPITAPH_TIMESKIP.get());
                    userPower.stopHeldAction(true);
                    this.removeAllShit();
                    return;
                }

                event.setCanceled(true);

                if (userPower.consumeStamina(150)) {
                    if (attacker instanceof StandEntity && ((StandEntity) attacker).getUser() != null) {
                        handleEpitaphTeleportBehind(user, ((StandEntity) attacker).getUser());
                    } else {
                        handleEpitaphTeleport(user, attacker);
                    }

                    if (attacker instanceof LivingEntity) {
                        applyAfterEpitaphEffect((LivingEntity) attacker);
                    }
                } else {
                    handleDashBackward(user, attacker);
                    IStandPower.getPlayerStandPower((PlayerEntity) user).stopHeldAction(true);
                    this.removeAllShit();
                    return;
                }
            } else {
                if (source == DamageSource.FALL || source == DamageSource.LIGHTNING_BOLT) {
                    return;
                }

                if (source.isExplosion() || (attacker instanceof MobEntity && attacker.getType().getRegistryName().toString().contains("creeper"))) {
                    return;
                }

                event.setCanceled(true);
                handleDashBackward(user, attacker);
            }

            if (isResolveLevel3OrHigher || userPower.getResolveLevel() < 3) {
                userPower.stopHeldAction(true);
                this.removeAllShit();
            }
        }

        private static void applyAfterEpitaphEffect(LivingEntity target) {
            target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20, 2, false, false));
        }

        public void removeAllShit() {
            this.remove();

            stopHolding(user.level, user, this.getUserPower());
            if (!user.level.isClientSide() && user instanceof ServerPlayerEntity) {
                AddonPackets.sendToClient(new RemoveEpitaphShader(user.getId()), (ServerPlayerEntity) user);
            }
        }
    }
}