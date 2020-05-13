package com.andrew121410.mc.essfabric.mixin;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.Location;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TeleportCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(TeleportCommand.class)
public class OnPlayerTeleportEventv2 {

    @Inject(method = "teleport", at = @At(value = "HEAD"))
    private static void teleport(ServerCommandSource source, Entity target, ServerWorld world, double x, double y, double z, Set<PlayerPositionLookS2CPacket.Flag> movementFlags, float yaw, float pitch, @Coerce Object facingLocation, CallbackInfo ci) {
        if (target instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) target;
            Main.getMain().getSetListMap().getBackMap().remove(serverPlayerEntity.getUuid());
            Main.getMain().getSetListMap().getBackMap().put(serverPlayerEntity.getUuid(), Location.from(serverPlayerEntity));
        }
    }
}