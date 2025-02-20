package com.ht_dq.rotp_kingcrimson.util;

import java.lang.reflect.Field;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;

import com.ht_dq.rotp_kingcrimson.mixin.EntityRendererMixin;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AddonReflection {

    private static final Field PLAYER_ENTITY_DATA_PLAYER_MODE_CUSTOMISATION_FIELD = ObfuscationReflectionHelper.findField(PlayerEntity.class, "field_184827_bp");
    private static DataParameter<Byte> PLAYER_ENTITY_DATA_PLAYER_MODE_CUSTOMISATION_CACHE;

    public static final Field ENTITY_RENDERER_MIXIN_ISRED_FIELD = ObfuscationReflectionHelper.findField(EntityRenderer.class, "isRed");
    public static boolean ENTITY_RENDERER_ISRED;
// the shittest code in my life...
//    @SubscribeEvent
//    public void onEntityRenderPre(RenderLivingEvent.Pre event){
//        RotpKingCrimsonAddon.getLogger().info(ENTITY_RENDERER_ISRED);
//        try {
//            ENTITY_RENDERER_ISRED = ENTITY_RENDERER_MIXIN_ISRED_FIELD.getBoolean(null);
//        } catch (IllegalAccessException e) {
//            RotpKingCrimsonAddon.getLogger().error(e);
//            throw new RuntimeException(e);
//        }
//    }

    @SuppressWarnings("unchecked")
    public static DataParameter<Byte> getPlayerSkinLayerDataParameter() {
        if (PLAYER_ENTITY_DATA_PLAYER_MODE_CUSTOMISATION_CACHE == null) {
            try {
                PLAYER_ENTITY_DATA_PLAYER_MODE_CUSTOMISATION_CACHE = (DataParameter<Byte>) PLAYER_ENTITY_DATA_PLAYER_MODE_CUSTOMISATION_FIELD.get(null);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                RotpKingCrimsonAddon.getLogger().error(e);
                throw new RuntimeException(e);
            }
        }
        return PLAYER_ENTITY_DATA_PLAYER_MODE_CUSTOMISATION_CACHE;
    }
}
