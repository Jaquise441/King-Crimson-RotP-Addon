package com.ht_dq.rotp_kingcrimson.init;

import com.github.standobyte.jojo.JojoMod;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.entity.AfterimageEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.util.ResourceLocation;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RotpKingCrimsonAddon.MOD_ID);

    public static final RegistryObject<EntityType<AfterimageEntity>> AFTERIMAGE = ENTITIES.register("afterimage",
            () -> EntityType.Builder.<AfterimageEntity>of(AfterimageEntity::new, EntityClassification.MISC).sized(0.6F, 1.8F).noSummon().setUpdateInterval(Integer.MAX_VALUE).setShouldReceiveVelocityUpdates(false)
                    .build(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "afterimage").toString()));
}
