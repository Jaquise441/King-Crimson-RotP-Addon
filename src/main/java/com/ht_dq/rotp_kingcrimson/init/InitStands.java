package com.ht_dq.rotp_kingcrimson.init;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.action.kingcrimson_chop;
import com.ht_dq.rotp_kingcrimson.action.kingcrimson_epitaph;
import com.ht_dq.rotp_kingcrimson.action.kingcrimson_timeerase;
import com.ht_dq.rotp_kingcrimson.action.kingcrimson_timeskip.KingCrimsonAbility;
import com.ht_dq.rotp_kingcrimson.entity.stand.stands.KingCrimsonEntity;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.*;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.TimeResume;
import com.github.standobyte.jojo.action.stand.TimeStop;
import com.github.standobyte.jojo.power.impl.stand.stats.TimeStopperStandStats;
import com.github.standobyte.jojo.action.stand.TimeStopInstant;
import com.github.standobyte.jojo.action.stand.*;
import com.github.standobyte.jojo.action.stand.StandEntityAction.Phase;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import net.minecraft.util.ResourceLocation;

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
                    .barrageHitSound(InitSounds.KINGCRIMSON_PUNCH)
                    .shout(InitSounds.DIAVOLO_BARRAGE)));

    public static final RegistryObject<StandEntityHeavyAttack> KINGCRIMSON_COMBO_PUNCH = ACTIONS.register("kingcrimson_combo_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.KING_CRIMSON_HEAVYPUNCH)
                    .standSound(Phase.WINDUP, InitSounds.DIAVOLO_COMBOFINISHER)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityHeavyAttack> KINGCRIMSON_HEAVY_PUNCH = ACTIONS.register("kingcrimson_heavy_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.KINGCRIMSON_PUNCH)
                    .standSound(Phase.WINDUP, InitSounds.DIAVOLO_HEAVYPUNCH)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(KINGCRIMSON_COMBO_PUNCH)
                    .shiftVariationOf(KINGCRIMSON_PUNCH).shiftVariationOf(KINGCRIMSON_BARRAGE)));

    public static final RegistryObject<StandEntityHeavyAttack> KINGCRIMSON_IMPALE = ACTIONS.register("kingcrimson_impale",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .cooldown(180)
                    .staminaCost(200)
                    .resolveLevelToUnlock(3)
                    .standOffsetFromUser(-0.15, 0.75, 0)
                    .punchSound(InitSounds.KINGCRIMSON_IMPALE)
                    .standSound(Phase.WINDUP)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityHeavyAttack> KINGCRIMSON_CHOP = ACTIONS.register("kingcrimson_chop",
            () -> new kingcrimson_chop(new StandEntityHeavyAttack.Builder()
                    .cooldown(350)
                    .staminaCost(200)
                    .standUserWalkSpeed(0.5F)
                    .resolveLevelToUnlock(4)
                    .standOffsetFromUser(-0.15, 0.75, 0)
                    .punchSound(InitSounds.KINGCRIMSON_CHOP)
                    .shiftVariationOf(KINGCRIMSON_IMPALE)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> KINGCRIMSON_BLOCK = ACTIONS.register("kingcrimson_block",
            () -> new StandEntityBlock());


    public static final RegistryObject<StandAction> KINGCRIMSON_TIMESKIP = ACTIONS.register("kingcrimson_timeskip",
            () -> new KingCrimsonAbility(new StandEntityAction.Builder()
                    .holdType(5)
                    .standUserWalkSpeed(2F)
                    .staminaCost(200)
                    .cooldown(60)
                    .standSound(InitSounds.KINGCRIMSON_TIMESKIP)
                    .resolveLevelToUnlock(2)
                    .standAutoSummonMode(StandEntityAction.AutoSummonMode.OFF_ARM) // need to remove it :D
            ));


    public static final RegistryObject<StandEntityAction> KINGCRIMSON_TIMEERASE = ACTIONS.register("kingcrimson_timeerase",
            () -> new kingcrimson_timeerase(new StandEntityAction.Builder()
                    .holdType(200)
                    .standUserWalkSpeed(2F)
                    .staminaCost(100)
                    .staminaCostTick(0.5F)
                    .cooldown(200)
                    .shout(InitSounds.KINGCRIMSON_TIMEERASE)
                    .resolveLevelToUnlock(4)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandAction> KINGCRIMSON_EPITAPH = ACTIONS.register("kingcrimson_epitaph",
            () -> new kingcrimson_epitaph(new StandEntityAction.Builder()
                    .holdType(100)
                    .staminaCost(100)
                    .staminaCostTick(5)
                    .cooldown(200)
                    .resolveLevelToUnlock(1)
                    .shout(InitSounds.DIAVOLO_EPITAPH)
                    .standSound(InitSounds.KINGCRIMSON_EPITAPH)
                    .standAutoSummonMode(StandEntityAction.AutoSummonMode.ARMS))
    );
    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<KingCrimsonEntity>> STAND_KINGCRIMSON =
            new EntityStandRegistryObject<>("kingcrimson",
                    STANDS,
                    () -> new EntityStandType.Builder<>()
                            .color(0xCF2F50)
                            .storyPartName(ModStandsInit.PART_5_NAME)
                            .leftClickHotbar(
                                    KINGCRIMSON_PUNCH.get(),
                                    KINGCRIMSON_BARRAGE.get(),
                                    KINGCRIMSON_IMPALE.get()
                            )
                            .rightClickHotbar(
                                    KINGCRIMSON_BLOCK.get(),
                                    KINGCRIMSON_EPITAPH.get(),
                                    KINGCRIMSON_TIMESKIP.get(),
                                    KINGCRIMSON_TIMEERASE.get()

                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .power(14.0)
                                    .speed(14.0)
                                    .range(2, 3)
                                    .durability(2)
                                    .precision(14.0)
                                    .randomWeight(1)
                            )
                            .addSummonShout(InitSounds.DIAVOLO_KINGCRIMSON)
                            .addOst(InitSounds.KINGCRIMSON_OST)
                            .addAttackerResolveMultTier(1)
                            .build(),

                    InitEntities.ENTITIES,
                    () -> new StandEntityType<KingCrimsonEntity>(KingCrimsonEntity::new, 0.65F, 1.95F)
                            .summonSound(InitSounds.KING_CRIMSON_SUMMON)
                            .unsummonSound(InitSounds.KING_CRIMSON_UNSUMMON))
                    .withDefaultStandAttributes();
}
