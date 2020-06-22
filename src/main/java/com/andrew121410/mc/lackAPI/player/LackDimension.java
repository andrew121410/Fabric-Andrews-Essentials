package com.andrew121410.mc.lackAPI.player;

import com.andrew121410.mc.essfabric.mixin.AccessorDimensionType;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class LackDimension {

    @Deprecated
    public static RegistryKey<World> byRawID(int id) {
        switch (id) {
            case 1:
                return World.END;
            case 0:
                return World.OVERWORLD;
            case -1:
                return World.NETHER;
        }
        return null;
    }

    @Deprecated
    public static int toRawID(DimensionType dimensionType) {
        AccessorDimensionType customDim = (AccessorDimensionType) dimensionType;

        if (dimensionType == customDim.getEnd()) {
            return 1;
        } else if (dimensionType == customDim.getOverworld()) {
            return 0;
        } else if (dimensionType == customDim.getNether()) {
            return -1;
        }
        return 0;
    }
}
