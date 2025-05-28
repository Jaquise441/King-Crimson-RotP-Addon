package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.player.ContinuousActionInstance;
import com.github.standobyte.jojo.action.player.IPlayerAction;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.capability.entity.PlayerUtilCap;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
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
import net.minecraftforge.fml.DistExecutor;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class KingCrimsonTimeSkip extends StandAction implements IPlayerAction<KingCrimsonTimeSkip.Instance, IStandPower> {
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
        setPlayerAction(user, userPower);
        List<Entity> entities = world.getEntities(user, user.getBoundingBox().inflate(32))
                .stream()
                .filter(entity -> entity instanceof Entity)
                .collect(Collectors.toList());
        entities.add(user);

        List<Entity> players = entities.stream().filter(e -> e instanceof PlayerEntity).collect(Collectors.toList());

        System.out.println("Client: " + world.isClientSide() + ", Entities: " + entities.size() + ", Players: " + players.size());

        int timeSkipDuration = KCConfig.TIME_SKIP_DURATION.get();

        for (int i = 0; i < timeSkipDuration; i++) {
            for (Entity entity : entities) {
                entity.tick();
            }
        }

        userPower.setCooldownTimer(this, KCConfig.TIME_SKIP_COOLDOWN.get());
    }

    @Override
    public Instance createContinuousActionInstance(LivingEntity user, PlayerUtilCap userCap, IStandPower power) {
        return new Instance(user, userCap, power, this);
    }

    public static class Instance extends ContinuousActionInstance<KingCrimsonTimeSkip, IStandPower> {

        public Instance(LivingEntity user, PlayerUtilCap userCap,
                        IStandPower playerPower, KingCrimsonTimeSkip action) {
            super(user, userCap, playerPower, action);
        }

        private int invTicks = 10;

        @Override
        public boolean cancelIncomingDamage(DamageSource dmgSource, float dmgAmount) {
            return true;
        }
        @Override
        public void playerTick() {
            LivingEntity user = getUser();
            if (!user.level.isClientSide()) {
                invTicks--;
                if (invTicks <= 0){
                    stopAction();
                }
            }
        }

    }
}