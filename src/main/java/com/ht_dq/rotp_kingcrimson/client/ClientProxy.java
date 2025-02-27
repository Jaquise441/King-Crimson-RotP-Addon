package com.ht_dq.rotp_kingcrimson.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.EntityTickableSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class ClientProxy {
    public static void playSound(SoundEvent sound, SoundCategory category, float volume, float pitch, Entity entity) {
        Minecraft.getInstance().getSoundManager().play(new EntityTickableSound(sound, category, volume, pitch, entity));
    }
}
