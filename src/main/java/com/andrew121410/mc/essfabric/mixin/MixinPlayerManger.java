package com.andrew121410.mc.essfabric.mixin;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class MixinPlayerManger {

    @Inject(method = "onPlayerConnect", at = @At(value = "RETURN"))
    public void playerJoin(ClientConnection clientConnection, ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        Main.getMain().getPlayerInitializer().load(new LackPlayer(playerEntity));
    }

    @Inject(method = "remove", at = @At(value = "RETURN"))
    public void playerLeave(final ServerPlayerEntity serverPlayerEntity, CallbackInfo callbackInfo) {
        Main.getMain().getPlayerInitializer().unload(new LackPlayer(serverPlayerEntity));
    }
}
