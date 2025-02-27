package com.ht_dq.rotp_kingcrimson.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.damaging.projectile.MolotovEntity;
import com.github.standobyte.jojo.entity.itemprojectile.KnifeEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.StandStatFormulas;
import com.github.standobyte.jojo.init.ModItems;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.config.KCConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.item.ExperienceBottleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class KingCrimsonProjectileThrow extends StandEntityLightAttack {
    public static final StandPose THROW = new StandPose("kingcrimson_throw");

    public KingCrimsonProjectileThrow(Builder builder) {
        super(builder);
    }

    private static Map<ItemStack, Class<? extends ProjectileEntity>> getThrowableItems() {
        Map<ItemStack, Class<? extends ProjectileEntity>> throwableItems = new HashMap<>();
        String[] allowedProjectiles = KCConfig.ALLOWED_PROJECTILES.get().split(",");
        for (String projectile : allowedProjectiles) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(projectile.trim()));
            if (item != null) {
                throwableItems.put(new ItemStack(item), getProjectileClass(item));
            }
        }
        return throwableItems;
    }

    private static Class<? extends ProjectileEntity> getProjectileClass(Item item) {
        if (item == Items.ARROW) return ArrowEntity.class;
        if (item == Items.EGG) return EggEntity.class;
        if (item == Items.SNOWBALL) return SnowballEntity.class;
        if (item == Items.ENDER_PEARL) return EnderPearlEntity.class;
        if (item == Items.FIREWORK_ROCKET) return FireworkRocketEntity.class;
        if (item == Items.SPLASH_POTION) return PotionEntity.class;
        if (item == Items.SPECTRAL_ARROW) return SpectralArrowEntity.class;
        if (item == Items.EXPERIENCE_BOTTLE) return ExperienceBottleEntity.class;
        if (item == Items.FIRE_CHARGE) return FireballEntity.class;
        if (item == ModItems.KNIFE.get()) return KnifeEntity.class;
        if (item == ModItems.MOLOTOV.get()) return MolotovEntity.class;
        return null;
    }

    @Override
    public int getStandWindupTicks(IStandPower standPower, StandEntity standEntity) {
        return StandStatFormulas.getHeavyAttackWindup(standEntity.getAttackSpeed(), standEntity.getFinisherMeter());
    }

    @Override
    public int getStandRecoveryTicks(IStandPower standPower, StandEntity standEntity) {
        return StandStatFormulas.getHeavyAttackRecovery(standEntity.getAttackSpeed(), standEntity.getLastHeavyFinisherValue());
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        ItemStack mainHandItem = user.getMainHandItem();

        if (mainHandItem.isEmpty()) {
            return conditionMessage("no_throwable_item_in_mainhand");
        }

        if (mainHandItem.getItem() == ModItems.STAND_ARROW.get()) {
            return conditionMessage("nope");
        }

        if (mainHandItem.getItem() == ModItems.STAND_ARROW_BEETLE.get()) {
            return conditionMessage("nope");
        }

        if (mainHandItem.getItem() == ModItems.MOLOTOV.get()) {
            ItemStack offhandItem = user.getOffhandItem();
            if (!(offhandItem.getItem() instanceof FlintAndSteelItem)) {
                return conditionMessage("molotov_flint_and_steel");
            }
        }

        if (!isThrowableItem(mainHandItem)) {
            return conditionMessage("not_throwable");
        }

        return super.checkSpecificConditions(user, power, target);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        LivingEntity user = userPower.getUser();
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            ItemStack mainHandItem = player.getMainHandItem();

            if (!mainHandItem.isEmpty() && isThrowableItem(mainHandItem)) {
                int itemCount = mainHandItem.getCount();
                int projectilesToThrow = Math.min(itemCount, 3);

                if (mainHandItem.getItem() == Items.FIRE_CHARGE) {
                    projectilesToThrow = 1;
                }

                for (int i = 0; i < projectilesToThrow; i++) {
                    ProjectileEntity projectile = createProjectile(world, player, mainHandItem);
                    if (projectile != null) {
                        Vector3d direction = player.getLookAngle();
                        Vector3d offset = calculateOffset(i, projectilesToThrow, direction);
                        projectile.setPos(player.getX() + offset.x, player.getEyeY() + offset.y, player.getZ() + offset.z);

                        float speed = 3.0F;
                        projectile.shoot(direction.x, direction.y, direction.z, speed, 1.0F);

                        if (projectile instanceof PotionEntity) {
                            ((PotionEntity) projectile).setItem(mainHandItem);
                        }

                        world.addFreshEntity(projectile);
                    } else {
                        System.out.println("Skill issue: " + mainHandItem.getItem().getRegistryName());
                    }
                }

                if (!player.isCreative()) {
                    mainHandItem.shrink(projectilesToThrow);
                    if (mainHandItem.isEmpty()) {
                        player.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    @Override
    protected void onTaskStopped(World world, StandEntity standEntity, IStandPower standPower, StandEntityTask task, StandEntityAction newAction) {
        super.onTaskStopped(world, standEntity, standPower, task, newAction);

        standPower.setCooldownTimer(this, KCConfig.PROJECTILE_THROW_COOLDOWN.get());
    }

    private boolean isThrowableItem(ItemStack itemStack) {
        return getThrowableItems().keySet().stream().anyMatch(stack -> stack.getItem() == itemStack.getItem()) ||
                itemStack.getItem() instanceof ArrowItem;
    }

    private ProjectileEntity createProjectile(World world, PlayerEntity player, ItemStack itemStack) {
        for (Map.Entry<ItemStack, Class<? extends ProjectileEntity>> entry : getThrowableItems().entrySet()) {
            if (ItemStack.isSame(entry.getKey(), itemStack)) {
                Class<? extends ProjectileEntity> projectileClass = entry.getValue();
                try {
                    ProjectileEntity projectile = projectileClass.getConstructor(World.class, LivingEntity.class).newInstance(world, player);

                    if (projectile instanceof ArrowEntity && itemStack.getItem() instanceof ArrowItem) {
                        ((ArrowEntity) projectile).setEffectsFromItem(itemStack);
                    }

                    return projectile;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (itemStack.getItem() instanceof ArrowItem) {
            ArrowEntity arrow = new ArrowEntity(world, player);
            arrow.setEffectsFromItem(itemStack);
            return arrow;
        }

        if (itemStack.getItem() instanceof FireworkRocketItem) {
            FireworkRocketEntity firework = new FireworkRocketEntity(world, itemStack, player, player.getX(), player.getEyeY() - 0.1, player.getZ(), true);
            return firework;
        }

        if (itemStack.getItem() == Items.FIRE_CHARGE) {
            FireballEntity fireball = new FireballEntity(world, player, player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z);
            return fireball;
        }

        return null;
    }

    private Vector3d calculateOffset(int index, int totalProjectiles, Vector3d direction) {
        double spread = 0.2;
        double offsetX = 0;
        double offsetY = 0;
        double offsetZ = 0;

        if (totalProjectiles > 1) {
            if (index == 0) {
                offsetX = -spread;
                offsetZ = -spread;
            } else if (index == 1) {
            } else if (index == 2) {
                offsetX = spread;
                offsetZ = spread;
            }
        }

        return new Vector3d(offsetX, offsetY, offsetZ);
    }
}