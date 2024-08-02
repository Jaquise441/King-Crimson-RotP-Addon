package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.IHasStandPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.ht_dq.rotp_kingcrimson.init.InitStands;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.UUID;

public class kingcrimson_epitaph extends StandEntityAction {

    private static final int EFFECT_DURATION = 200;
    private static final int SLOWNESS_LEVEL = 2;
    private static final int RESISTANCE_LEVEL = 4;
    private static final double RADIUS = 192.0;
    private EpitaphHandler epitaphHandler;

    public kingcrimson_epitaph(AbstractBuilder<?> builder) {
        super(builder);
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
        }
    }

    private static void applyEffects(PlayerEntity player, boolean start) {
        if (start) {
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, EFFECT_DURATION, SLOWNESS_LEVEL, false, false));
            player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, EFFECT_DURATION, RESISTANCE_LEVEL, false, false));
        } else {
            player.removeEffect(Effects.MOVEMENT_SLOWDOWN);
            player.removeEffect(Effects.DAMAGE_RESISTANCE);
        }
    }

    public static class EpitaphHandler {
        private final UUID playerUUID;
        private final StandEntity standEntity;
        private final IStandPower userPower;
        private final StandEntityTask task;

        public EpitaphHandler(UUID playerUUID, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
            this.playerUUID = playerUUID;
            this.standEntity = standEntity;
            this.userPower = userPower;
            this.task = task;
        }

        @SubscribeEvent
        public void onLivingAttack(LivingAttackEvent event) {
            if (event.getEntityLiving().getUUID().equals(playerUUID)) {
                handleEpitaphCounter((PlayerEntity) event.getEntityLiving(), event.getSource().getEntity());
            }
        }

        private void handleEpitaphCounter(PlayerEntity player, @Nullable Entity attacker) {
            if (attacker instanceof StandEntity) {
                // Get the Stand User
                LivingEntity standUser = ((StandEntity) attacker).getUser();
                if (standUser instanceof PlayerEntity) {
                    // Teleport behind the Stand User
                    teleportAndCounter(player, standUser);
                }
            } else if (attacker instanceof LivingEntity) {
                // Teleport behind the attacker
                teleportAndCounter(player, (LivingEntity) attacker);
            }
        }

        private void teleportAndCounter(PlayerEntity player, LivingEntity target) {
            // Calculate the position one block behind the target
            Vector3d lookDirection = target.getLookAngle().normalize();
            Vector3d behindTarget = target.position().subtract(lookDirection.scale(1.0));

            // Teleport the player to the new position
            player.teleportTo(behindTarget.x, behindTarget.y, behindTarget.z);
            player.lookAt(EntityAnchorArgument.Type.FEET, target.position());
            playSound(player, InitSounds.EPITAPH_TIMESKIP.get());

            int resolveLevel = userPower.getResolveLevel();
            IHasStandPunch counterAction = resolveLevel >= 3 ? InitStands.KINGCRIMSON_IMPALE.get() : InitStands.KINGCRIMSON_HEAVY_PUNCH.get();

            ActionTarget actionTarget = new ActionTarget(target);
            standEntity.attackTarget(actionTarget, counterAction, task);

            applyEffects(player, true);

            World world = player.level;

            TimeSkipHandler.startEffect(player, true);

            MinecraftForge.EVENT_BUS.unregister(this);
        }

        private void playSound(PlayerEntity player, SoundEvent sound) {
            player.level.playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
    }
}
