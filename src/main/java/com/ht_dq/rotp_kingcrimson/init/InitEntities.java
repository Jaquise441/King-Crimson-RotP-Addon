package com.ht_dq.rotp_kingcrimson.init;

import com.github.standobyte.jojo.JojoMod;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.entity.KCAfterimageEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.util.ResourceLocation;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RotpKingCrimsonAddon.MOD_ID);

    public static final RegistryObject<EntityType<KCAfterimageEntity>> KC_AFTERIMAGE = ENTITIES.register("kc_afterimage",
            () -> EntityType.Builder.<KCAfterimageEntity>of(KCAfterimageEntity::new, EntityClassification.MISC).sized(0.6F, 1.8F).noSummon().setUpdateInterval(Integer.MAX_VALUE).setShouldReceiveVelocityUpdates(false)
                    .build(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "kc_afterimage").toString()));
}
