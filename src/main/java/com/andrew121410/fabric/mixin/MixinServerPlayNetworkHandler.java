package com.andrew121410.fabric.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayNetworkHandler {

    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onGameMessage", at = @At(value = "RETURN"))
    public void onMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        String string = packet.getChatMessage();
        string = StringUtils.normalizeSpace(string);
        for (int i = 0; i < string.length(); ++i) {
            if (SharedConstants.isValidChar(string.charAt(i))) continue;
            return;
        }
        if (!string.startsWith("/")) {
//            Main.getMain().getDiscordAddon().sendPlayerMessage(new LackPlayer(this.player), string);
        }
    }
}
