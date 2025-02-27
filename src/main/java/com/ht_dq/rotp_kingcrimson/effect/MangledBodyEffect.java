package com.ht_dq.rotp_kingcrimson.effect;

import com.github.standobyte.jojo.potion.UncurableEffect;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.init.InitEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;

public class MangledBodyEffect extends UncurableEffect {
    public MangledBodyEffect(int color) {
        super(EffectType.HARMFUL, color);
    }

    private boolean cooldownApplied = false;

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            int duration = player.getEffect(InitEffects.MANGLED_BODY.get()).getDuration();
            int cooldownTime = duration > 0 ? duration : 4;

            if (!cooldownApplied) {
                applyCooldownToInventory(player, cooldownTime);
                cooldownApplied = true;
            }
        }
    }

    private void applyCooldownToInventory(PlayerEntity player, int cooldownTime) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.inventory.getItem(i);
            if (!stack.isEmpty()) {
                player.getCooldowns().addCooldown(stack.getItem(), cooldownTime);
            }
        }

        ItemStack mainHandStack = player.getMainHandItem();
        if (!mainHandStack.isEmpty()) {
            player.getCooldowns().addCooldown(mainHandStack.getItem(), cooldownTime);
        }

        ItemStack offHandStack = player.getOffhandItem();
        if (!offHandStack.isEmpty()) {
            player.getCooldowns().addCooldown(offHandStack.getItem(), cooldownTime);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID)
    public static class Events {
        @SubscribeEvent
        public static void onBlockBreak(PlayerEvent.BreakSpeed event) {
            PlayerEntity player = event.getPlayer();
            if (player.hasEffect(InitEffects.MANGLED_BODY.get())) {
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onItemPickup(EntityItemPickupEvent event) {
            PlayerEntity player = event.getPlayer();
            if (player.hasEffect(InitEffects.MANGLED_BODY.get())) {
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
            if (event.getEntity() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getEntity();
                if (player.hasEffect(InitEffects.MANGLED_BODY.get())) {
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void onEntityAttack(LivingAttackEvent event) {
            if (event.getSource().getEntity() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
                if (player.hasEffect(InitEffects.MANGLED_BODY.get())) {
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
            PlayerEntity player = event.getPlayer();
            if (player.hasEffect(InitEffects.MANGLED_BODY.get())) {
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
            PlayerEntity player = event.getPlayer();
            if (player.hasEffect(InitEffects.MANGLED_BODY.get())) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, net.minecraft.entity.ai.attributes.AttributeModifierManager attributeManager, int amplifier) {
        cooldownApplied = false;
        super.removeAttributeModifiers(entity, attributeManager, amplifier);
    }
}
