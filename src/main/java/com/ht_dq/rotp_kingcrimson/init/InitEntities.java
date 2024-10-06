package com.ht_dq.rotp_kingcrimson.init;

import com.github.standobyte.jojo.JojoMod;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.util.ResourceLocation;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RotpKingCrimsonAddon.MOD_ID);

}
