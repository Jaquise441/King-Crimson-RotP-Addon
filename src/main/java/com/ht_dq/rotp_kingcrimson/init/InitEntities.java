package com.ht_dq.rotp_kingcrimson.init;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.entity.KCAfterimageEntity;
import com.ht_dq.rotp_kingcrimson.entity.TimeEraseDecoyEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RotpKingCrimsonAddon.MOD_ID);

    public static final RegistryObject<EntityType<KCAfterimageEntity>> KC_AFTERIMAGE = ENTITIES.register("kc_afterimage",
            () -> EntityType.Builder.<KCAfterimageEntity>of(KCAfterimageEntity::new, EntityClassification.MISC).sized(0.6F, 1.8F).noSummon().setUpdateInterval(Integer.MAX_VALUE).setShouldReceiveVelocityUpdates(false)
                    .build(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "kc_afterimage").toString()));

    public static final RegistryObject<EntityType<TimeEraseDecoyEntity>> TIME_ERASE_DECOY = ENTITIES.register("time_erase_decoy",
            () -> EntityType.Builder.<TimeEraseDecoyEntity>of(TimeEraseDecoyEntity::new, EntityClassification.MISC).sized(0.6F, 1.8F).noSummon()
                    .build(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "time_erase_decoy").toString()));
    

    @SubscribeEvent
    public static void addEntityAttribues(EntityAttributeCreationEvent event){
        event.put(TIME_ERASE_DECOY.get(), PlayerEntity.createAttributes().add(Attributes.FOLLOW_RANGE, 16.0D).build());
    }
}
