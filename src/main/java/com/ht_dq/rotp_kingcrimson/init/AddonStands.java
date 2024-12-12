package com.ht_dq.rotp_kingcrimson.init;

import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject.EntityStandSupplier;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.ht_dq.rotp_kingcrimson.entity.stand.stands.KingCrimsonEntity;

public class AddonStands {

    public static final EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<KingCrimsonEntity>> KINGCRIMSON = new EntityStandSupplier<>(InitStands.STAND_KINGCRIMSON);
}
