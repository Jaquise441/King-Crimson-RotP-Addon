package com.ht_dq.rotp_kingcrimson.client.render.vfx;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.github.standobyte.jojo.init.ModStatusEffects;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.Pair;

import com.ht_dq.rotp_kingcrimson.client.render.vfx.TemporaryDimensionEffects.DimensionEffect;

import com.github.standobyte.jojo.init.ModStatusEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler2 {
    private static ClientEventHandler2 instance = null;

    private final Minecraft mc;
    
    private ClientEventHandler2(Minecraft mc) {
        this.mc = mc;
    }

    public static void init(Minecraft mc) {
        if (instance == null) {
            instance = new ClientEventHandler2(mc);
            MinecraftForge.EVENT_BUS.register(instance);
        }
    }
    
    
    private boolean prevTickErasingTime = false;
    
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        if (mc.level != null) {
            switch (event.phase) {
            case START:
                break;
            case END:
                boolean erasingTime = isErasingTime();
                if (!prevTickErasingTime && erasingTime) {
                    DimensionEffect timeEraseSkyEffect = new DimensionEffect() {
                        @Override
                        public boolean isActive() {
                            return isErasingTime();
                        }
                    }
                    .withSkyRenderer(new TimeEraseSkyRenderHandler());
                    TemporaryDimensionEffects.getInstance().getEffectsStack(mc.level).addEffect(mc.level, timeEraseSkyEffect);
                    timeEraseStartEffects();
                }
                prevTickErasingTime = erasingTime;
                break;
            }
        }
        else {
            prevTickErasingTime = false;
        }
    }

    private static final Random RANDOM = new Random();
    private static final double MAX_BLOCK_RENDER_RANGE = 4;
    private static final int MAX_BLOCKS_BREAKING_EFFECT = 128; // TODO consider using mc.options.graphicsMode and/or mc.options.particles
    @SuppressWarnings("deprecation")
    private void timeEraseStartEffects() {
        List<Pair<BlockPos, BlockState>> blocks = new ArrayList<>();
        BlockPos center = mc.player.blockPosition();
        for (int y = -8; y <= 8; y++) {
            for (int x = -8; x <= 8; x++) {
                for (int z = -8; z <= 8; z++) {
                    int xAbs = Math.abs(x);
                    int zAbs = Math.abs(z);
                    if (xAbs * xAbs + zAbs * zAbs > MAX_BLOCK_RENDER_RANGE * MAX_BLOCK_RENDER_RANGE) {
                        BlockPos blockPos = center.offset(x, y, z);
                        BlockState blockState = mc.level.getBlockState(blockPos);
                        if (!blockState.isAir(mc.level, blockPos)) {
                            blocks.add(Pair.of(blockPos, blockState));
                        }
                    }
                }
            }
        }
        MutableInt blocksBroken = new MutableInt(0);
        blocks.stream()
        .sorted(Comparator.comparingInt(block -> block.getLeft().distManhattan(center)))
        .allMatch(block -> {
            if (blocksBroken.intValue() >= MAX_BLOCKS_BREAKING_EFFECT) return false;
            if (blocks.size() <= MAX_BLOCKS_BREAKING_EFFECT || RANDOM.nextInt(blocks.size()) <= MAX_BLOCKS_BREAKING_EFFECT * 2) {
                mc.level.levelEvent(2001, block.getLeft(), Block.getId(block.getRight()));
                blocksBroken.increment();
            }
            return true;
        });
    }
    
    
    public static boolean isErasingTime() {
        Minecraft mc = Minecraft.getInstance();
        return mc.player != null && mc.player.hasEffect(Effects.LUCK) /*for testing purposes*/;
    }
    
}
