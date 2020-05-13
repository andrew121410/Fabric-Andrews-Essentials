package com.andrew121410.mc.essfabric.managers;

import com.andrew121410.mc.lackAPI.player.LackPlayer;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeathChestManager {

    public DeathChestManager() {
    }

    public void run(ServerPlayerEntity playerEntity, List<DefaultedList<ItemStack>> inv) {
        LackPlayer lackPlayer = new LackPlayer(playerEntity);

        int x = (int) playerEntity.getX();
        int y = (int) playerEntity.getY();
        int z = (int) playerEntity.getZ();

        int a = 0;

        BlockPos blockPos = new BlockPos(x, y, z);
        ChestBlockEntity blockEntity = new ChestBlockEntity();
        Iterator<DefaultedList<ItemStack>> iterator = inv.iterator();

        List<ItemStack> itemStacks = new ArrayList<>();

        //Filters out the air
        while (iterator.hasNext()) {
            List<ItemStack> list = (List) iterator.next();
            for (int i = 0; i < list.size(); ++i) {
                ItemStack itemStack = (ItemStack) list.get(i);
                if (!itemStack.isEmpty()) {
                    itemStacks.add(itemStack);
                    list.set(i, ItemStack.EMPTY);
                }
            }
        }

        //Sets the ItemStacks in the chest 27 MAX
        for (ItemStack itemStack : itemStacks) {
            if (a <= 27) {
                blockEntity.setStack(a, itemStack);
                a++;
            }//Throw it back on the ground because the chest is too damn full.....
            else {
                playerEntity.dropItem(itemStack, true, false);
            }
        }

        blockEntity.setCustomName(new LiteralText(lackPlayer.getDisplayName() + " Chest!!! V:01"));
        playerEntity.getServerWorld().setBlockEntity(blockPos, blockEntity);
    }
}