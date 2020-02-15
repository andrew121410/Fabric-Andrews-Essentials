package com.andrew121410.fabric.mixin;

import com.andrew121410.fabric.Main;
import com.andrew121410.lackAPI.player.lackPlayer;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class OnPlayerJoinEvent {

    @Inject(method = "onPlayerConnect", at = @At(value = "RETURN"))
    public void onPlayerConnect(ClientConnection clientConnection, ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        Main.getMain().getPlayerInitializer().load(new lackPlayer(playerEntity));
    }
}
