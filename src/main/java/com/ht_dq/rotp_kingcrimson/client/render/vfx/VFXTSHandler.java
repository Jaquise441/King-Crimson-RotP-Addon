package com.ht_dq.rotp_kingcrimson.client.render.vfx;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VFXTSHandler {
    private static final int DURATION = 15;
    private int tickCounter = 0;
    private boolean active = false;

    public void start() {
        this.tickCounter = 0;
        this.active = true;
    }

    public void stop() {
        this.active = false;
    }

    public void update() {
        if (active) {
            tickCounter++;
            if (tickCounter >= DURATION) {
                active = false;
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public int getTickCounter() {
        return tickCounter;
    }


}
