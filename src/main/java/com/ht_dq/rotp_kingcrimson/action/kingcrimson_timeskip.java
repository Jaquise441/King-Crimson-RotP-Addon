package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TimeSkipHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class kingcrimson_timeskip extends StandEntityAction {

    public kingcrimson_timeskip(AbstractBuilder<?> builder) {
        super(builder);
    }

    @Mod.EventBusSubscriber
    public static class KingCrimsonAbility extends StandEntityAction {

        private static final float ORIGINAL_STEP_HEIGHT = 0.6F;
        private static final float NEW_STEP_HEIGHT = 1.1F;
        private static final int MAX_DURATION = 10;
        private static final int MIN_DURATION = 5;
        private static final double RADIUS = 192.0;

        public KingCrimsonAbility(StandEntityAction.Builder builder) {
            super(builder);
        }

        @Override
        public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
            if (!world.isClientSide()) {
                List<PlayerEntity> players = (List<PlayerEntity>) world.players();
                for (PlayerEntity player : players) {
                    player.addEffect(new EffectInstance(Effects.BLINDNESS, MAX_DURATION, 9, false, false));
                    player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, MAX_DURATION, 99, false, false));
                    player.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, MAX_DURATION, 19, false, false));
                    player.addEffect(new EffectInstance(Effects.WEAKNESS, MAX_DURATION, 9, false, false));


                    TimeSkipHandler.startVFX(player,true);
                }

                standEntity.getUser().addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, MAX_DURATION, 4, false, false));

                List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, standEntity.getBoundingBox().inflate(50));
                for (LivingEntity entity : entities) {
                    entity.addEffect(new EffectInstance(Effects.BLINDNESS, MAX_DURATION, 9, false, false));
                    entity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, MAX_DURATION, 69, false, false));
                    entity.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, MAX_DURATION, 19, false, false));
                    entity.addEffect(new EffectInstance(Effects.WEAKNESS, MAX_DURATION, 9, false, false));
                    entity.maxUpStep = NEW_STEP_HEIGHT;
                }

                world.playSound(null, standEntity.getX(), standEntity.getY(), standEntity.getZ(), InitSounds.KINGCRIMSON_TIMESKIP.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);

                MinecraftForge.EVENT_BUS.register(new Object() {
                    private int ticks = 0;

                    @SubscribeEvent
                    public void onWorldTick(TickEvent.WorldTickEvent event) {
                        if (event.world == world && event.phase == TickEvent.Phase.END) {
                            ticks++;
                            if (ticks >= MIN_DURATION && task.getPhase() != Phase.PERFORM || ticks >= MAX_DURATION) {
                                for (PlayerEntity player : players) {
                                    player.removeEffect(Effects.BLINDNESS);
                                    player.removeEffect(Effects.MOVEMENT_SPEED);
                                    player.removeEffect(Effects.DOLPHINS_GRACE);
                                    player.removeEffect(Effects.WEAKNESS);
                                }

                                standEntity.getUser().removeEffect(Effects.DAMAGE_RESISTANCE);

                                for (LivingEntity entity : entities) {
                                    entity.removeEffect(Effects.BLINDNESS);
                                    entity.removeEffect(Effects.MOVEMENT_SPEED);
                                    entity.removeEffect(Effects.DOLPHINS_GRACE);
                                    entity.removeEffect(Effects.WEAKNESS);
                                    entity.maxUpStep = ORIGINAL_STEP_HEIGHT;
                                }
                                MinecraftForge.EVENT_BUS.unregister(this);

                            }
                        }
                    }
                });
            }
        }

        @Override
        public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, Phase phase, StandEntityTask task, int ticks) {
        }

        @Override
        protected void onTaskStopped(World world, StandEntity standEntity, IStandPower standPower, StandEntityTask task, StandEntityAction newAction) {
            if (!world.isClientSide()) {
                List<PlayerEntity> players = (List<PlayerEntity>) world.players();
                for (PlayerEntity player : players) {
                    player.removeEffect(Effects.BLINDNESS);
                    player.removeEffect(Effects.MOVEMENT_SPEED);
                    player.removeEffect(Effects.DOLPHINS_GRACE);
                    player.removeEffect(Effects.WEAKNESS);
                }

                standEntity.getUser().removeEffect(Effects.DAMAGE_RESISTANCE);

                List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, standEntity.getBoundingBox().inflate(50));
                for (LivingEntity entity : entities) {
                    entity.removeEffect(Effects.BLINDNESS);
                    entity.removeEffect(Effects.MOVEMENT_SPEED);
                    entity.removeEffect(Effects.DOLPHINS_GRACE);
                    entity.removeEffect(Effects.WEAKNESS);
                    entity.maxUpStep = ORIGINAL_STEP_HEIGHT;
                }
            }
        }
    }
}
