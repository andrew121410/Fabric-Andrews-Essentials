package com.andrew121410.fabric.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    /**
     * @author andrew121410
     */

    @Overwrite
    public String getServerModName() {
        return "Andrew's custom software";
    }
}
