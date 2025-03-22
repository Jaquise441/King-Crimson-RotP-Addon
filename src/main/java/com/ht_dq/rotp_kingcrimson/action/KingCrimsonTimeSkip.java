package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.ht_dq.rotp_kingcrimson.client.ClientProxy;
import com.ht_dq.rotp_kingcrimson.config.KCConfig;
import com.ht_dq.rotp_kingcrimson.init.InitSounds;
import com.ht_dq.rotp_kingcrimson.util.VFXServerHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

public class KingCrimsonTimeSkip extends StandAction {
    private static final Random RANDOM = new Random();

    public KingCrimsonTimeSkip(Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        return ActionConditionResult.noMessage(KingCrimsonTimeErase.playerTimeEraseActive.isEmpty());
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower userPower, ActionTarget target) {
        if (user == null) return;

        SoundEvent soundToPlay = RANDOM.nextBoolean()
                ? InitSounds.KINGCRIMSON_TIMESKIP.get()
                : InitSounds.KINGCRIMSON_TIMESKIP2.get();

        if (world.isClientSide()) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    ClientProxy.playSound(
                            soundToPlay,
                            user.getSoundSource(),
                            1.0F,
                            1.0F,
                            user
                    )
            );
        }

        VFXServerHelper.startVFX(user, true);
        List<Entity> entities = MCUtil.entitiesAround(LivingEntity.class, user, 32, true, null);

        int timeSkipDuration = KCConfig.TIME_SKIP_DURATION.get();
        user.getTags().add("KingCrimsonTimeSkip");

        for (int i = 0; i < timeSkipDuration; i++) {
            for (Entity entity : entities) {
                entity.tick();
            }
        }

        user.getTags().remove("KingCrimsonTimeSkip");

        userPower.setCooldownTimer(this, KCConfig.TIME_SKIP_COOLDOWN.get());
    }

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onLivingAttack(LivingHurtEvent event) {
            LivingEntity target = event.getEntityLiving();
            if (target instanceof PlayerEntity && !target.level.isClientSide) {
                PlayerEntity player = (PlayerEntity) target;

                if (player.getTags().contains("KingCrimsonTimeSkip")) {
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void onLivingFall(LivingFallEvent event) {
            LivingEntity target = event.getEntityLiving();
            if (target instanceof PlayerEntity && !target.level.isClientSide) {
                PlayerEntity player = (PlayerEntity) target;
                if (player.getTags().contains("KingCrimsonTimeSkip")) {
                    event.setCanceled(true);
                }
            }
        }
    }
}