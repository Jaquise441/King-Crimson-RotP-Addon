package com.ht_dq.rotp_kingcrimson.client;

import com.github.standobyte.jojo.client.playeranim.PlayerAnimationHandler;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.client.render.entity.AfterimageRenderer;
import com.ht_dq.rotp_kingcrimson.client.render.entity.TimeEraseDecoyRenderer;
import com.ht_dq.rotp_kingcrimson.client.render.entity.layerrenderer.HalfBlindnessLeftLayer;
import com.ht_dq.rotp_kingcrimson.client.render.entity.layerrenderer.HalfBlindnessRightLayer;
import com.ht_dq.rotp_kingcrimson.client.render.entity.renderer.stand.KingCrimsonRenderer;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.ClientEventHandler;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TemporaryDimensionEffects;
import com.ht_dq.rotp_kingcrimson.init.AddonStands;
import com.ht_dq.rotp_kingcrimson.init.InitEntities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = Minecraft.getInstance();
        RenderingRegistry.registerEntityRenderingHandler(AddonStands.KINGCRIMSON.getEntityType(), KingCrimsonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.KC_AFTERIMAGE.get(), AfterimageRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.TIME_ERASE_DECOY.get(), TimeEraseDecoyRenderer::new);
        event.enqueueWork(() -> {
            ClientEventHandler.init(mc);
            TemporaryDimensionEffects.init();
        });
        event.enqueueWork(() -> {
            ClientEventHandler.init(mc);
            Map<String, PlayerRenderer> skinMap = mc.getEntityRenderDispatcher().getSkinMap();
            addLayers(skinMap.get("default"), false);
            addLayers(skinMap.get("slim"), true);
            mc.getEntityRenderDispatcher().renderers.values().forEach(ClientInit::addLayersToEntities);

            PlayerAnimationHandler.initAnimator();
        });
    }
    private static void addLayers(PlayerRenderer renderer, boolean slim) {
        addLivingLayers(renderer);
        addBipedLayers(renderer);
    }

    private static <T extends LivingEntity, M extends BipedModel<T>> void addLayersToEntities(EntityRenderer<?> renderer) {
        if (renderer instanceof LivingRenderer<?, ?>) {
            addLivingLayers((LivingRenderer<T, ?>) renderer);
            if (((LivingRenderer<?, ?>) renderer).getModel() instanceof BipedModel<?>) {
                addBipedLayers((LivingRenderer<T, M>) renderer);
            }
        }
    }

    private static <T extends LivingEntity, M extends EntityModel<T>> void addLivingLayers(LivingRenderer<T, M> renderer) {
        renderer.addLayer(new HalfBlindnessLeftLayer<>(renderer));
        renderer.addLayer(new HalfBlindnessRightLayer<>(renderer));
    }

    private static <T extends LivingEntity, M extends BipedModel<T>> void addBipedLayers(LivingRenderer<T, M> renderer) {
    }
}
