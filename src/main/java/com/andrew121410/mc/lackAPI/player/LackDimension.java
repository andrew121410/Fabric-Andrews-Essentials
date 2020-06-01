package com.andrew121410.mc.lackAPI.player;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class LackDimension {

    @Deprecated
    public static RegistryKey<World> byRawID(int id) {
        switch (id) {
            case 1:
                return World.field_25181;
            case 0:
                return World.field_25179;
            case -1:
                return World.field_25180;
        }
        return null;
    }

    @Deprecated
    public static int toRawID(DimensionType dimensionType) {
        if (dimensionType.isEnd()) {
            return 1;
        } else if (dimensionType.isOverworld()) {
            return 0;
        } else if (dimensionType.isNether()) {
            return -1;
        }
        return 0;
    }
}