package com.andrew121410.mc.essfabric.mixin;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.world.Location;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class OnPlayerDeathEvent {

    @Inject(method = "onDeath", at = @At(value = "HEAD"))
    public void onDeath(final DamageSource source, CallbackInfo callbackInfo) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        Main.getInstance().getSetListMap().getBackMap().remove(playerEntity.getUuid());
        Main.getInstance().getSetListMap().getBackMap().put(playerEntity.getUuid(), Location.from(playerEntity));
    }
}
