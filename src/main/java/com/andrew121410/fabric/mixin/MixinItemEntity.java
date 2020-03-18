package com.andrew121410.fabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity {

    @Shadow
    public abstract ItemStack getStack();

    @Inject(method = "changeDimension", at = @At(value = "HEAD"))
    public void onCo(DimensionType newDimension, CallbackInfoReturnable<Entity> cir) {
        if (newDimension == DimensionType.THE_NETHER) {
            Random random = new Random();
            int randomNum = random.nextInt((20 - 1) + 1) + 1;

            if (randomNum == 12) {
                ItemStack itemStack = this.getStack();
                itemStack.setCount(itemStack.getCount() + 1);
            }
        }
    }
}
