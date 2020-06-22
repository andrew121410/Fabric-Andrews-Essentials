package com.andrew121410.mc.essfabric.mixin;

import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DimensionType.class)
public interface AccessorDimensionType {

    @Accessor("OVERWORLD")
    DimensionType getOverworld();

    @Accessor("THE_NETHER")
    DimensionType getNether();

    @Accessor("THE_END")
    DimensionType getEnd();
}
