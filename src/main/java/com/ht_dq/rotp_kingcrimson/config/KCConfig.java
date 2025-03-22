package com.ht_dq.rotp_kingcrimson.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class KCConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue BARRAGE_HEALTH_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue BARRAGE_HEALTH_THRESHOLD_DOPPIO;
    public static final ForgeConfigSpec.DoubleValue DESPERATE_EYEJAB_HEALTH_THRESHOLD;
    public static final ForgeConfigSpec.IntValue DESPERATE_EYEJAB_COOLDOWN;
    public static final ForgeConfigSpec.IntValue TIME_SKIP_DURATION;
    public static final ForgeConfigSpec.IntValue TIME_ERASE_PRESENT_ENTITY_DELAY;
    public static final ForgeConfigSpec.IntValue TIME_ERASE_DURATION;
    public static final ForgeConfigSpec.IntValue TIME_ERASE_COOLDOWN;
    public static final ForgeConfigSpec.BooleanValue TIME_ERASE_DYNAMIC_COOLDOWN;
    public static final ForgeConfigSpec.DoubleValue TIME_ERASE_DYNAMIC_COOLDOWN_MULTIPLIER;
    public static final ForgeConfigSpec.IntValue TIME_ERASE_DYNAMIC_MINIMUM_COOLDOWN;
    public static final ForgeConfigSpec.IntValue CHOP_COOLDOWN;
    public static final ForgeConfigSpec.IntValue IMPALE_COOLDOWN;
    public static final ForgeConfigSpec.IntValue PROJECTILE_THROW_COOLDOWN;
    public static final ForgeConfigSpec.ConfigValue<String> ALLOWED_PROJECTILES;
    public static final ForgeConfigSpec.DoubleValue TIME_ERASE_STAMINA_TICK;
    public static final ForgeConfigSpec.IntValue TIME_SKIP_COOLDOWN;
    public static final ForgeConfigSpec.IntValue EPITAPH_DURATION;
    public static final ForgeConfigSpec.DoubleValue EPITAPH_STAMINA_COST_TICK;
    public static final ForgeConfigSpec.IntValue EPITAPH_COOLDOWN;
    public static final ForgeConfigSpec.IntValue IMPALE_GAPING_WOUND_LEVEL;

    static {
        BUILDER.push("King Crimson Barrage Config");
        BARRAGE_HEALTH_THRESHOLD = BUILDER
                .comment("The minimum health percentage required to use King Crimson's barrage (0.0 to 1.0 = 0% to 100%) (If set to 100% you can always use Barrage, if set to 0% you can never use Barrage)")
                .defineInRange("barrageHealthThreshold", 0.5, 0.0, 1.0);
        BARRAGE_HEALTH_THRESHOLD_DOPPIO = BUILDER
                .comment("The minimum health percentage required to use Doppio's barrage (Stand-Off mode) (0.0 to 1.0 = 0% to 100%) (If set to 100% you can always use Barrage, if set to 0% you can never use Barrage)")
                .defineInRange("barrageHealthThresholdDoppio", 0.5, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("King Crimson Desperate Eyejab Config");
        DESPERATE_EYEJAB_HEALTH_THRESHOLD = BUILDER
                .comment("The minimum health percentage required to have Ground Punch be replaced by Desperate Eyejab (0.0 to 1.0 = 0% to 100%) (If set to 100% you will always have Desperate Eyejab as the finisher, if set to 0% you can never use it as finisher)")
                .defineInRange("desperateEyejabHealthThreshold", 0.5, 0.0, 1.0);
        DESPERATE_EYEJAB_COOLDOWN = BUILDER
                .comment("The cooldown (in ticks) for King Crimson's Desperate Eyejab. (Default is 200 ticks = 10 seconds)")
                .defineInRange("desperateEyejabCooldown", 200, 0, 12000);
        BUILDER.pop();

        BUILDER.push("King Crimson Chop Config");
        CHOP_COOLDOWN = BUILDER
                .comment("The cooldown (in ticks) for King Crimson's Chop. (Default is 350 ticks = 17.5 seconds)")
                .defineInRange("chopCooldown", 350, 0, 12000);
        BUILDER.pop();

        BUILDER.push("King Crimson Impale Config");
        IMPALE_COOLDOWN = BUILDER
                .comment("The cooldown (in ticks) for King Crimson's Impale. (Default is 180 ticks = 9 seconds)")
                .defineInRange("impaleCooldown", 180, 0, 12000);
        IMPALE_GAPING_WOUND_LEVEL = BUILDER
                .comment("The level of Gaping Wound applied by Impale (0 = level 1, 1 = level 2, etc...) (Previous Version's Gaping Wound: 3 = level 4)")
                .defineInRange("impaleGapingWoundLevel", 0, -1, 9);
        BUILDER.pop();

        BUILDER.push("King Crimson Projectile Throw Config");
        PROJECTILE_THROW_COOLDOWN = BUILDER
                .comment("The cooldown (in ticks) for King Crimson's Projectile Throw. (Default is 95 ticks = 4.75 seconds)")
                .defineInRange("projectileThrowCooldown", 95, 0, 12000);
        ALLOWED_PROJECTILES = BUILDER
                .comment("A list of item IDs that can be thrown by King Crimson's Projectile Throw. (Default: arrow,egg,snowball,ender_pearl,firework_rocket,splash_potion,spectral_arrow,experience_bottle,fire_charge,jojo:knife,jojo:molotov)")
                .define("allowedProjectiles", "arrow,egg,snowball,ender_pearl,firework_rocket,splash_potion,spectral_arrow,experience_bottle,fire_charge,jojo:knife,jojo:molotov");
        BUILDER.pop();

        BUILDER.push("King Crimson Epitaph Config");
        EPITAPH_DURATION = BUILDER
                .comment("The duration (in ticks) of King Crimson's Epitaph. (Default is 60 ticks = 3 seconds)")
                .defineInRange("epitaphDuration", 60, 0, 12000);
        EPITAPH_STAMINA_COST_TICK = BUILDER
                .comment("The stamina cost per tick for King Crimson's Epitaph. (Default is 2 stamina per tick)")
                .defineInRange("epitaphStaminaCostTick", 2.0, 0.0, 100.0);
        EPITAPH_COOLDOWN = BUILDER
                .comment("The cooldown (in ticks) for King Crimson's Epitaph. (Default is 35 ticks = 2 seconds)")
                .defineInRange("epitaphCooldown", 35, 0, 12000);
        BUILDER.pop();

        BUILDER.push("King Crimson Time Erase Config");
        TIME_ERASE_PRESENT_ENTITY_DELAY = BUILDER
                .comment("The delay (in ticks) before the outlined entity starts following the path of the \"future\". (Default is 60 = 3 seconds) (Setting it to max value will make it never move)")
                .defineInRange("timeErasePresentEntityDelay", 60, 0, 16000);
        TIME_ERASE_DURATION = BUILDER
                .comment("The duration (in ticks) of King Crimson's Time Erase ability. (Default is 200 = 10 seconds) (KEEP IN MIND THAT I HAVE NOT EXTENDED THE TIME ERASE SOUNDS YET. SOUNDS WILL CUT OFF AFTER A WHILE) ")
                .defineInRange("timeEraseDuration", 200, 0, 12000);
        TIME_ERASE_COOLDOWN = BUILDER
                .comment("The cooldown (in ticks) for Time Erase. (Default is 200 ticks = 10 seconds) (ONLY WORKS IF DYNAMIC COOLDOWN IS SET TO \"false\")")
                .defineInRange("timeEraseCooldown", 200, 0, 12000);
        TIME_ERASE_DYNAMIC_COOLDOWN = BUILDER
                .comment("Toggle for whether Time Erase should use a dynamic cooldown based on the duration of the ability used. (Default is true).")
                .define("timeEraseDynamicCooldown", true);
        TIME_ERASE_DYNAMIC_COOLDOWN_MULTIPLIER = BUILDER
                .comment("The multiplier used to calculate the dynamic cooldown for Time Erase. (Default is 2.0) (2.0 means that the cooldown will be double the amount of time you spent in Time Erase. For example: 6 seconds of Time Erase = 12 seconds of Cooldown)")
                .defineInRange("timeEraseDynamicCooldownMultiplier", 2.0, 0.0, 10.0);
        TIME_ERASE_DYNAMIC_MINIMUM_COOLDOWN = BUILDER
                .comment("The minimum cooldown (in ticks) for Time Erase when using Dynamic Cooldown. (Default is 120 ticks = 6 seconds) (This means even if you use the ability for 1 second, the minimum cooldown will be 6 seconds. This is to prevent abuse of the ability)")
                .defineInRange("timeEraseDynamicMinimumCooldown", 120, 0, 12000);
        TIME_ERASE_STAMINA_TICK = BUILDER
                .comment("The stamina cost per tick for King Crimson's Time Erase. (Default is 0.5 stamina per tick)")
                .defineInRange("timeEraseStaminaTick", 0.5, 0.0, 100.0);
        BUILDER.pop();

        BUILDER.push("King Crimson Time Skip Config");
        TIME_SKIP_DURATION = BUILDER
                .comment("The duration (in ticks) of King Crimson's Time Skip. (Default is 200 = 10 seconds) (KEEP IN MIND THAT THE HIGHER YOU PUT IT, THE MORE THE GAME WILL STUTTER. 1200 EQUALS TO 1 MINUTE. THE GAME WILL LAG IF YOU TRY TO TIME SKIP WITH THIS MANY TICKS)")
                .defineInRange("timeSkipDuration", 200, 0, 1200);
        TIME_SKIP_COOLDOWN = BUILDER
                .comment("The cooldown (in ticks) for King Crimson's Time Skip. (Default is 60 ticks = 3 seconds)")
                .defineInRange("timeSkipCooldown", 60, 0, 12000);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}