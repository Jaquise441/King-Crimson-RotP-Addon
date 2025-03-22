package com.ht_dq.rotp_kingcrimson.util;

import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import net.minecraft.util.ResourceLocation;

public class KingCrimsonUtil {
    private static final ResourceLocation harvestShader = new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "shaders/post/epitaph.json");

    public static ResourceLocation getShader(IStandPower power) {
        if (power == null || !power.getStandInstance().isPresent()) return harvestShader;
        return StandSkinsManager.getInstance().getRemappedResPath(manager -> manager.getStandSkin(power.getStandInstance().get()), harvestShader);
    }
}
