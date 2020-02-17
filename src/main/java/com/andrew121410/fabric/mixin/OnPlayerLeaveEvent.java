package com.andrew121410.fabric.mixin;

import com.andrew121410.fabric.Main;
import com.andrew121410.lackAPI.player.LackPlayer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class OnPlayerLeaveEvent {

    @Inject(method = "remove", at = @At(value = "RETURN"))
    public void remove(final ServerPlayerEntity serverPlayerEntity, CallbackInfo callbackInfo) {
        Main.getMain().getPlayerInitializer().unload(new LackPlayer(serverPlayerEntity));
    }
}
