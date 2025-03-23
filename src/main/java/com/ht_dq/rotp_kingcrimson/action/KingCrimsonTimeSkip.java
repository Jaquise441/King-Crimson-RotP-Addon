package com.ht_dq.rotp_kingcrimson.action;

import java.util.*;
import java.util.stream.Collectors;

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
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

public class KingCrimsonTimeSkip extends StandAction {
    private static final Random RANDOM = new Random();
    private static final Map<UUID, Integer> activeTimeSkips = new HashMap<>();

    public static Map<UUID, Integer> getActiveTimeSkips() {
        return activeTimeSkips;
    }

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

        activeTimeSkips.put(user.getUUID(), timeSkipDuration);

        for (int i = 0; i < timeSkipDuration; i++) {
            for (Entity entity : entities) {
                entity.tick();
            }
        }

        activeTimeSkips.remove(user.getUUID());

        userPower.setCooldownTimer(this, KCConfig.TIME_SKIP_COOLDOWN.get());
    }
}
