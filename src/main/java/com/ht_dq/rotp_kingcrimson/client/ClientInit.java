package com.ht_dq.rotp_kingcrimson.client;

import java.util.Map;

import com.github.standobyte.jojo.client.ui.standstats.StandStatsRenderer;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.client.render.entity.AfterimageRenderer;
import com.ht_dq.rotp_kingcrimson.client.render.entity.TimeEraseDecoyRenderer;
import com.ht_dq.rotp_kingcrimson.client.render.entity.layerrenderer.EpitaphLayer;
import com.ht_dq.rotp_kingcrimson.client.render.entity.layerrenderer.HalfBlindnessLeftLayer;
import com.ht_dq.rotp_kingcrimson.client.render.entity.layerrenderer.HalfBlindnessRightLayer;
import com.ht_dq.rotp_kingcrimson.client.render.entity.renderer.stand.KingCrimsonRenderer;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.ClientEventHandler;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.TemporaryDimensionEffects;
import com.ht_dq.rotp_kingcrimson.init.AddonStands;
import com.ht_dq.rotp_kingcrimson.init.InitEntities;

import com.ht_dq.rotp_kingcrimson.init.InitStands;
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
        });
        StandStatsRenderer.overrideCosmeticStats(
                InitStands.STAND_KINGCRIMSON.getStandType().getRegistryName(),
                new StandStatsRenderer.ICosmeticStandStats() {
                    @Override public double statConvertedValue(StandStatsRenderer.StandStat stat, IStandPower standData, StandStats stats, float statLeveling) {
                        switch (stat) {
                            case RANGE:
                            case DURABILITY:
                                return 1;
                            case PRECISION:
                            case DEV_POTENTIAL:
                                return 0;
                            default:
                                return StandStatsRenderer.ICosmeticStandStats.super.statConvertedValue(stat, standData, stats, statLeveling);
                        }
                    }
                    @Override public String statRankLetter(StandStatsRenderer.StandStat stat, IStandPower standData, double statConvertedValue) {
                        switch (stat) {
                            case PRECISION:
                            case DEV_POTENTIAL:
                                return "?";
                            default:
                                return StandStatsRenderer.getRankFromConvertedValue(statConvertedValue);
                        }
                    }
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
        renderer.addLayer(new EpitaphLayer<>(renderer));
    }

    private static <T extends LivingEntity, M extends BipedModel<T>> void addBipedLayers(LivingRenderer<T, M> renderer) {
    }
}
