package com.ht_dq.rotp_kingcrimson.init;

import com.ht_dq.rotp_kingcrimson.RotpKingCrimsonAddon;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.VFXPacket;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.VFXTSHandler;
import com.ht_dq.rotp_kingcrimson.client.render.vfx.VFXTSRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = RotpKingCrimsonAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InitVFX {

    private static final Map<UUID, VFXTSHandler> handlers = new HashMap<>();
    private static final Map<UUID, VFXTSRenderer> renderers = new HashMap<>();

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(RotpKingCrimsonAddon.MOD_ID, "network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init(FMLClientSetupEvent event) {
        CHANNEL.registerMessage(0, VFXPacket.class, VFXPacket::encode, VFXPacket::decode, VFXPacket::handle);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            handlers.values().forEach(VFXTSHandler::update);
        }
    }

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft.getInstance().execute(() -> {
                Minecraft mc = Minecraft.getInstance();
                IngameGui ingameGui = mc.gui;
                MatrixStack matrixStack = new MatrixStack();
                renderers.values().forEach(renderer -> renderer.render(matrixStack, event.renderTickTime));
            });
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void startVFXClient(UUID playerUUID) {
        if (!handlers.containsKey(playerUUID)) {
            VFXTSHandler handler = new VFXTSHandler();
            VFXTSRenderer renderer = new VFXTSRenderer(handler);
            handlers.put(playerUUID, handler);
            renderers.put(playerUUID, renderer);
        }
        handlers.get(playerUUID).start();
    }
}