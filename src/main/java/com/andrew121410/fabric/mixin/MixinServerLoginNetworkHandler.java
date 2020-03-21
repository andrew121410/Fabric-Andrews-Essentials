package com.andrew121410.fabric.mixin;

import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerLoginNetworkHandler.class)
public class MixinServerLoginNetworkHandler {

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 600))
    public int fix(int old) {
        return 1200;
    }
}
