package com.ht_dq.rotp_kingcrimson.client.render.entity.renderer.stand;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.client.render.entity.model.stand.KingCrimsonModel;
import com.ht_dq.rotp_kingcrimson.entity.stand.stands.KingCrimsonEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class KingCrimsonRenderer extends StandEntityRenderer<KingCrimsonEntity, KingCrimsonModel> {
    
    public KingCrimsonRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "kingcrimson"), KingCrimsonModel::new),
                new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "textures/entity/stand/kingcrimson.png"), 0);
    }
}
