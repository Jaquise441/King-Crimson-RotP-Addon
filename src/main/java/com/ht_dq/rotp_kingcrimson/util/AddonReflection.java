package com.ht_dq.rotp_kingcrimson.util;

import java.lang.reflect.Field;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class AddonReflection {

    private static final Field PLAYER_ENTITY_DATA_PLAYER_MODE_CUSTOMISATION_FIELD = ObfuscationReflectionHelper.findField(PlayerEntity.class, "field_184827_bp");
    private static DataParameter<Byte> PLAYER_ENTITY_DATA_PLAYER_MODE_CUSTOMISATION_CACHE;
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
