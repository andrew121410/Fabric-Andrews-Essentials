package com.andrew121410.fabric.mixin;

import com.andrew121410.fabric.Main;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {

    /**
     * @author andrew121410
     */
    @Overwrite
    public String getServerModName() {
        return "Andrew's custom software";
    }

    @Inject(method = "shutdown", at = @At(value = "HEAD"))
    protected void shutdown(CallbackInfo callbackInfo) {
        Main.getMain().onShutdown();
    }
}