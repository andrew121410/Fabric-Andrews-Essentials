package com.andrew121410.mc.essfabric.mixin;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.world.Location;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class OnPlayerTeleportEvent {

    @Inject(method = "teleport", at = @At(value = "HEAD"))
    public void teleport(final ServerWorld targetWorld, final double x, final double y, final double z, final float yaw, final float pitch, CallbackInfo callbackInfo) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        Main.getInstance().getSetListMap().getBackMap().remove(playerEntity.getUuid());
        Main.getInstance().getSetListMap().getBackMap().put(playerEntity.getUuid(), Location.from(playerEntity));
    }
}
