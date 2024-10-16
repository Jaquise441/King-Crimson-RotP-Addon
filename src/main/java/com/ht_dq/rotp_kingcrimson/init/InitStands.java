package com.ht_dq.rotp_kingcrimson.init;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.action.*;
import com.ht_dq.rotp_kingcrimson.action.KingCrimsonTimeSkip.KingCrimsonAbility;
import com.ht_dq.rotp_kingcrimson.entity.stand.stands.KingCrimsonEntity;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.*;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction.Phase;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpKingCrimsonAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpKingCrimsonAddon.MOD_ID);

    // ======================================== KingCrimson ========================================

    public static final RegistryObject<StandEntityAction> KINGCRIMSON_PUNCH = ACTIONS.register("kingcrimson_punch",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.KINGCRIMSON_PUNCH)
                    .standSound(Phase.WINDUP, InitSounds.DIAVOLO_PUNCH)));

    public static final RegistryObject<StandEntityAction> KINGCRIMSON_BARRAGE = ACTIONS.register("kingcrimson_barrage",
            () -> new StandEntityMeleeBarrage(new StandEntityMeleeBarrage.Builder()
                    .holdType(65)
                    .barrageHitSound(InitSounds.KINGCRIMSON_PUNCH)
                    .standSound(InitSounds.DIAVOLO_BARRAGE)));

    public static final RegistryObject<StandEntityHeavyAttack> KINGCRIMSON_GROUNDPUNCH = ACTIONS.register("kingcrimson_groundpunch",
            () -> new KingCrimsonGroundPunch(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.KINGCRIMSON_GROUNDPUNCH)
                    .standSound(Phase.WINDUP, InitSounds.DIAVOLO_COMBOFINISHER)
                    .resolveLevelToUnlock(1)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityHeavyAttack> KINGCRIMSON_HEAVY_PUNCH = ACTIONS.register("kingcrimson_heavy_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.KINGCRIMSON_PUNCH)
                    .standSound(Phase.WINDUP, InitSounds.DIAVOLO_HEAVYPUNCH)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(KINGCRIMSON_GROUNDPUNCH)
                    .shiftVariationOf(KINGCRIMSON_PUNCH).shiftVariationOf(KINGCRIMSON_BARRAGE)));

    public static final RegistryObject<StandEntityHeavyAttack> KINGCRIMSON_CHOP = ACTIONS.register("kingcrimson_chop",
            () -> new KingCrimsonChop(new StandEntityHeavyAttack.Builder()
                    .cooldown(350)
                    .staminaCost(200)
                    .standUserWalkSpeed(0.5F)
                    .resolveLevelToUnlock(3)
                    .standPose(KingCrimsonChop.CHOP)
                    .standOffsetFromUser(-0.15, 0.75, 0)
                    .punchSound(InitSounds.KINGCRIMSON_CHOP)
                    .shout(InitSounds.DIAVOLO_CHOP)
                    .partsRequired(StandPart.ARMS)));


    public static final RegistryObject<StandEntityHeavyAttack> KINGCRIMSON_IMPALE = ACTIONS.register("kingcrimson_impale",
            () -> new KingCrimsonImpale(new StandEntityHeavyAttack.Builder()
                    .cooldown(180)
                    .staminaCost(150)
                    .resolveLevelToUnlock(4)
                    .standPose(KingCrimsonImpale.IMPALE)
                    .standOffsetFromUser(-0.15, 0.75, 0)
                    .punchSound(InitSounds.KINGCRIMSON_IMPALE)
                    .shout(InitSounds.DIAVOLO_IMPALE)
                    .shiftVariationOf(KINGCRIMSON_CHOP)
                    .partsRequired(StandPart.ARMS)));


    public static final RegistryObject<StandEntityAction> KINGCRIMSON_BLOCK = ACTIONS.register("kingcrimson_block",
            () -> new StandEntityBlock());


    public static final RegistryObject<StandAction> KINGCRIMSON_TIMESKIP = ACTIONS.register("kingcrimson_timeskip",
            () -> new KingCrimsonAbility(new StandEntityAction.Builder()
                    .staminaCost(200)
                    .cooldown(60)
                    .standSound(InitSounds.KINGCRIMSON_TIMESKIP)
                    .resolveLevelToUnlock(2)));


    public static final RegistryObject<StandEntityAction> KINGCRIMSON_TIMEERASE = ACTIONS.register("kingcrimson_timeerase",
            () -> new KingCrimsonTimeErase(new StandEntityAction.Builder()
                    .holdType(200)
                    .standUserWalkSpeed(2F)
                    .staminaCost(100)
                    .staminaCostTick(0.5F)
                    .cooldown(200)
                    .shout(InitSounds.KINGCRIMSON_TIMEERASE)
                    .resolveLevelToUnlock(3)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandAction> KINGCRIMSON_EPITAPH = ACTIONS.register("kingcrimson_epitaph",
            () -> new KingCrimsonEpitaph(new StandEntityAction.Builder()
                    .holdType(60)
                    .staminaCost(100)
                    .staminaCostTick(2)
                    .cooldown(35)
                    .resolveLevelToUnlock(1)
                    .shout(InitSounds.DIAVOLO_EPITAPH)
                    .standSound(InitSounds.KINGCRIMSON_EPITAPH)));


    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<KingCrimsonEntity>> STAND_KINGCRIMSON =
            new EntityStandRegistryObject<>("kingcrimson",
                    STANDS,
                    () -> new EntityStandType.Builder<>()
                            .color(0xCF2F50)
                            .storyPartName(ModStandsInit.PART_5_NAME)
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
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .tier(6)
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
                    () -> new StandEntityType<KingCrimsonEntity>(KingCrimsonEntity::new, 0.65F, 1.95F)
                            .summonSound(InitSounds.KINGCRIMSON_SUMMON)
                            .unsummonSound(InitSounds.KINGCRIMSON_UNSUMMON))
                    .withDefaultStandAttributes();
}
