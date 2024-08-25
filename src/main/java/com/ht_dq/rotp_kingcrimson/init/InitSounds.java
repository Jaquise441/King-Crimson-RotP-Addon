package com.ht_dq.rotp_kingcrimson.init;

import java.util.function.Supplier;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.util.mc.OstSoundList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RotpKingCrimsonAddon.MOD_ID);

    public static final Supplier<SoundEvent> DIAVOLO_KINGCRIMSON = SOUNDS.register("diavolo_kingcrimson",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "diavolo_kingcrimson")));

    public static final Supplier<SoundEvent> KING_CRIMSON_SUMMON = SOUNDS.register("king_crimson_summon",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "king_crimson_summon")));

    public static final Supplier<SoundEvent> KING_CRIMSON_UNSUMMON = SOUNDS.register("king_crimson_unsummon",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "king_crimson_unsummon")));

    public static final Supplier<SoundEvent> KINGCRIMSON_PUNCH = SOUNDS.register("kingcrimson_punch",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "kingcrimson_punch")));

    public static final Supplier<SoundEvent> DIAVOLO_COMBOFINISHER = SOUNDS.register("diavolo_combofinisher",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "diavolo_combofinisher")));

    public static final Supplier<SoundEvent> KING_CRIMSON_HEAVYPUNCH = SOUNDS.register("king_crimson_heavypunch",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "king_crimson_heavypunch")));

    public static final Supplier<SoundEvent> DIAVOLO_BARRAGE = SOUNDS.register("diavolo_barrage",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "diavolo_barrage")));

    public static final Supplier<SoundEvent> KINGCRIMSON_BARRAGE = SOUNDS.register("king_crimson_barragepunch",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "king_crimson_barragepunch")));

    public static final Supplier<SoundEvent> DIAVOLO_HEAVYPUNCH = SOUNDS.register("diavolo_heavypunch",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "diavolo_heavypunch")));

    public static final Supplier<SoundEvent> DIAVOLO_PUNCH = SOUNDS.register("diavolo_punch",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "diavolo_punch")));

    public static final Supplier<SoundEvent> DIAVOLO_CHOP = SOUNDS.register("diavolo_chop",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "diavolo_chop")));

    public static final Supplier<SoundEvent> KINGCRIMSON_CHOP = SOUNDS.register("kingcrimson_chop",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "kingcrimson_chop")));

    public static final Supplier<SoundEvent> KINGCRIMSON_IMPALE = SOUNDS.register("kingcrimson_impale",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "kingcrimson_impale")));

    public static final Supplier<SoundEvent> KINGCRIMSON_TIMESKIP = SOUNDS.register("kingcrimson_timeskip",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "kingcrimson_timeskip")));

    public static final Supplier<SoundEvent> EPITAPH_TIMESKIP = SOUNDS.register("a",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "epitaph_timeskip")));


    public static final Supplier<SoundEvent> TIME_ERASE_START = SOUNDS.register("time_erase_start",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "time_erase_start")));

    public static final Supplier<SoundEvent> TIME_ERASE_END = SOUNDS.register("time_erase_end",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "time_erase_end")));


    public static final Supplier<SoundEvent> KINGCRIMSON_EPITAPH = SOUNDS.register("kingcrimson_epitaph",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "kingcrimson_epitaph")));

    public static final Supplier<SoundEvent> DIAVOLO_EPITAPH = SOUNDS.register("diavolo_epitaph",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "diavolo_epitaph")));

    public static final Supplier<SoundEvent> KINGCRIMSON_TIMEERASE = SOUNDS.register("kingcrimson_timeerase",
            () -> new SoundEvent(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "kingcrimson_timeerase")));


    static final OstSoundList KINGCRIMSON_OST = new OstSoundList(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "king_crimson_ost"), SOUNDS);

}