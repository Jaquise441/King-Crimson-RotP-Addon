package com.ht_dq.rotp_kingcrimson.mixin;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.action.KingCrimsonTimeErase;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.ClientEventHandler;
import com.ht_dq.rotp_kingcrimson.entity.KCAfterimageEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    @Inject(method = "render", at = @At(value = "HEAD"))
    private void injectRenderer(T entity, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_, CallbackInfo ci){
        List<KCAfterimageEntity> entities = entity.level.getEntitiesOfClass(KCAfterimageEntity.class, entity.getBoundingBox().inflate(KingCrimsonTimeErase.RADIUS), entity1 -> {
            return !(entity instanceof KCAfterimageEntity) && entity1.getOriginEntity() == entity && entity1.isStationary();
        });
        if (!entities.isEmpty()){
            isRed = true;
        }
        else {
            isRed = false;
        }
    }

    @Unique
    private static boolean isRed;
}
