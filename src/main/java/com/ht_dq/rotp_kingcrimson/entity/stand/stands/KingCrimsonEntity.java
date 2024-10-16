package com.ht_dq.rotp_kingcrimson.entity.stand.stands;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import net.minecraft.world.World;

public class KingCrimsonEntity extends StandEntity {
    private static boolean concealIdentityActive = false;

    public static boolean isConcealIdentityActive() {
        return concealIdentityActive;
    }

    public static void setConcealIdentityActive(boolean active) {
        concealIdentityActive = active;
    }
    
    public KingCrimsonEntity(StandEntityType<KingCrimsonEntity> type, World world) {
        super(type, world);
    }
}
