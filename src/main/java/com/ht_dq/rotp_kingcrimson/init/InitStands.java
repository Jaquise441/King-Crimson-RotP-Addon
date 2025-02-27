package com.ht_dq.rotp_kingcrimson.init;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.*;
import com.github.standobyte.jojo.action.stand.StandEntityAction.Phase;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.github.standobyte.jojo.util.mod.StoryPart;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.action.*;
import com.ht_dq.rotp_kingcrimson.config.KCConfig;
import com.ht_dq.rotp_kingcrimson.entity.stand.stands.KingCrimsonEntity;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpKingCrimsonAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpKingCrimsonAddon.MOD_ID);

    // ======================================== King Crimson ========================================

    public static final RegistryObject<StandEntityAction> KINGCRIMSON_PUNCH = ACTIONS.register("kingcrimson_punch",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.KINGCRIMSON_PUNCH)
                    .standSound(Phase.WINDUP, InitSounds.DIAVOLO_PUNCH)));

    public static final RegistryObject<StandEntityAction> KINGCRIMSON_BARRAGE = ACTIONS.register("kingcrimson_barrage",
            () -> new KingCrimsonBarrage(new StandEntityMeleeBarrage.Builder()
                    .holdType(60)
                    .barrageHitSound(InitSounds.KINGCRIMSON_PUNCH)
                    .standSound(InitSounds.DIAVOLO_BARRAGE)));

    public static final RegistryObject<StandEntityAction> KINGCRIMSON_DOPPIO_BARRAGE = ACTIONS.register("kingcrimson_doppio_barrage",
            () -> new KingCrimsonDoppioBarrage(new StandEntityMeleeBarrage.Builder()
                    .holdType(60)
                    .barrageHitSound(InitSounds.KINGCRIMSON_PUNCH)
                    .standSound(InitSounds.DOPPIO_BARRAGE)));

    public static final RegistryObject<StandEntityHeavyAttack> KINGCRIMSON_GROUNDPUNCH = ACTIONS.register("kingcrimson_groundpunch",
            () -> new KingCrimsonGroundPunch(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.KINGCRIMSON_GROUNDPUNCH)
                    .standSound(Phase.WINDUP, InitSounds.DIAVOLO_COMBOFINISHER)
                    .resolveLevelToUnlock(1)
                    .partsRequired(StandPart.MAIN_BODY)));

    public static final RegistryObject<StandEntityHeavyAttack> KINGCRIMSON_HEAVY_PUNCH = ACTIONS.register("kingcrimson_heavy_punch",
            () -> new KingCrimsonHeavyPunch(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.KINGCRIMSON_HEAVYPUNCH)
                    .standSound(Phase.WINDUP, InitSounds.DIAVOLO_HEAVYPUNCH)
                    .partsRequired(StandPart.MAIN_BODY)
                    .setFinisherVariation(KINGCRIMSON_GROUNDPUNCH)
                    .shiftVariationOf(KINGCRIMSON_PUNCH).shiftVariationOf(KINGCRIMSON_BARRAGE).shiftVariationOf(KINGCRIMSON_DOPPIO_BARRAGE)));

    public static final RegistryObject<StandEntityLightAttack> KINGCRIMSON_CHOP = ACTIONS.register("kingcrimson_chop",
            () -> new KingCrimsonChop(new StandEntityLightAttack.Builder()
                    .staminaCost(200)
                    .standUserWalkSpeed(0.5F)
                    .resolveLevelToUnlock(3)
                    .standPose(KingCrimsonChop.CHOP)
                    .autoSummonStand()
                    .standOffsetFromUser(-0.15, 0.75, 0)
                    .punchSound(InitSounds.KINGCRIMSON_CHOP)
                    .shout(InitSounds.DIAVOLO_CHOP)
                    .partsRequired(StandPart.MAIN_BODY)));

    public static final RegistryObject<StandEntityLightAttack> KINGCRIMSON_IMPALE = ACTIONS.register("kingcrimson_impale",
            () -> new KingCrimsonImpale(new StandEntityLightAttack.Builder()
                    .staminaCost(150)
                    .resolveLevelToUnlock(4)
                    .standPose(KingCrimsonImpale.IMPALE)
                    .autoSummonStand()
                    .standOffsetFromUser(-0.15, 0.75, 0)
                    .punchSound(InitSounds.KINGCRIMSON_IMPALE)
                    .shout(InitSounds.DIAVOLO_IMPALE)
                    .shiftVariationOf(KINGCRIMSON_CHOP)
                    .partsRequired(StandPart.MAIN_BODY)));

    public static final RegistryObject<StandEntityLightAttack> KINGCRIMSON_GROUNDSLAM = ACTIONS.register("kingcrimson_groundslam",
            () -> new KingCrimsonGroundSlam(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.KINGCRIMSON_GROUNDPUNCH)
                    .standPose(KingCrimsonGroundSlam.SLAM)
                    .standAutoSummonMode(StandEntityAction.AutoSummonMode.ARMS)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityHeavyAttack> KINGCRIMSON_DESPERATE_EYEJAB = ACTIONS.register("kingcrimson_desperate_eyejab",
            () -> new KingCrimsonDesperateEyejab(new StandEntityHeavyAttack.Builder()
                    .cooldown(200)
                    .staminaCost(200)
                    .standPose(KingCrimsonDesperateEyejab.JAB)
                    .standUserWalkSpeed(0.5F)
                    .resolveLevelToUnlock(1)
                    .autoSummonStand()
                    .standOffsetFromUser(-0.15, 0.75, 0)
                    .punchSound(InitSounds.KINGCRIMSON_EYEJAB)
                    .shout(InitSounds.DIAVOLO_CHOP)
                    .partsRequired(StandPart.MAIN_BODY)));

//    public static final RegistryObject<KingCrimsonBloodThrow> KINGCRIMSON_BLOOD_THROW = ACTIONS.register("kingcrimson_blood_throw",
//            () -> new KingCrimsonBloodThrow(new KingCrimsonBloodThrow.Builder()
//                    .cooldown(150)
//                    .staminaCost(200)
//                    .resolveLevelToUnlock(1)
//                    .shout(InitSounds.KINGCRIMSON_BLOODTHROW)
//                    .partsRequired(StandPart.MAIN_BODY)));

    public static final RegistryObject<StandEntityAction> KINGCRIMSON_BLOCK = ACTIONS.register("kingcrimson_block",
            () -> new StandEntityBlock());


    public static final RegistryObject<StandEntityAction> KINGCRIMSON_TIMESKIP = ACTIONS.register("kingcrimson_timeskip",
            () -> new KingCrimsonTimeSkip(new StandEntityAction.Builder()
                    .staminaCost(200)
                    .resolveLevelToUnlock(3)));

    public static final RegistryObject<StandEntityAction> KINGCRIMSON_TIMEERASE = ACTIONS.register("kingcrimson_timeerase",
            () -> new KingCrimsonTimeErase(new StandEntityAction.Builder()
                    .holdType()
                    .standPerformDuration(KCConfig.TIME_ERASE_DURATION.get())
                    .standUserWalkSpeed(2F)
                    .staminaCost(150)
                    .shout(InitSounds.KINGCRIMSON_TIMEERASE)
                    .resolveLevelToUnlock(3)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> KINGCRIMSON_EPITAPH = ACTIONS.register("kingcrimson_epitaph",
            () -> new KingCrimsonEpitaph(new StandEntityAction.Builder()
                    .holdType()
                    .standPerformDuration(KCConfig.EPITAPH_DURATION.get())
                    .staminaCost(175)
                    .resolveLevelToUnlock(1)
                    .shout(InitSounds.DIAVOLO_EPITAPH)
                    .standSound(InitSounds.KINGCRIMSON_EPITAPH)));

    public static final RegistryObject<StandEntityLightAttack> KINGCRIMSON_PROJECTILE_THROW = ACTIONS.register("kingcrimson_projectile_throw",
            () -> new KingCrimsonProjectileThrow(new StandEntityLightAttack.Builder()
                    .standPose(KingCrimsonProjectileThrow.THROW)
                    .staminaCost(120)
                    .standWindupDuration(85)
                    .resolveLevelToUnlock(3)
                    .shout(InitSounds.DOPPIO_THERE_YOU_ARE)
                    .partsRequired(StandPart.ARMS)));

    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<KingCrimsonEntity>> STAND_KINGCRIMSON =
            new EntityStandRegistryObject<>("kingcrimson",
                    STANDS,
                    () -> new EntityStandType.Builder<>()
                            .color(0xD90F13)
                            .storyPartName(StoryPart.GOLDEN_WIND.getName())
                            .leftClickHotbar(
                                    KINGCRIMSON_PUNCH.get(),
                                    KINGCRIMSON_BARRAGE.get(),
                                    KINGCRIMSON_CHOP.get()
                            )
                            .rightClickHotbar(
                                    KINGCRIMSON_BLOCK.get(),
                                    KINGCRIMSON_EPITAPH.get(),
                                    KINGCRIMSON_TIMESKIP.get(),
                                    KINGCRIMSON_TIMEERASE.get()
                            )
                            .defaultKey(KINGCRIMSON_TIMEERASE.get(), "key.keyboard.c") // key V to time erase
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .power(18.0)
                                    .speed(14.0)
                                    .range(2, 3)
                                    .durability(9)
                                    .precision(14.0)
                                    .randomWeight(1)
                            )
                            .addSummonShout(InitSounds.DIAVOLO_KINGCRIMSON)
                            .addOst(InitSounds.KINGCRIMSON_OST)
                            .build(),

                    InitEntities.ENTITIES,
                    () -> new StandEntityType<>(KingCrimsonEntity::new, 0.65F, 1.95F)
                            .summonSound(InitSounds.KINGCRIMSON_SUMMON)
                            .unsummonSound(InitSounds.KINGCRIMSON_UNSUMMON))
                    .withDefaultStandAttributes();
}
