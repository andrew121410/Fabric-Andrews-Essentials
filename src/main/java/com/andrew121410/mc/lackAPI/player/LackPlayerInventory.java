package com.andrew121410.mc.lackAPI.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class LackPlayerInventory {

    private LackPlayer lackPlayer;

    public LackPlayerInventory(LackPlayer lackPlayer) {
        this.lackPlayer = lackPlayer;
    }

    public void openEnderChest() {
        this.lackPlayer.getPlayerEntity().openHandledScreen(containerFactoryEnderChest(this.lackPlayer.getPlayerEntity()));
    }

    public NamedScreenHandlerFactory containerFactoryEnderChest(ServerPlayerEntity player) {
        player.incrementStat(Stats.OPEN_ENDERCHEST);
        return new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return new LiteralText("Ender-Chest");
            }

            @Override
            public ScreenHandler createMenu(int syncId, net.minecraft.entity.player.PlayerInventory inv, PlayerEntity player1) {
                return GenericContainerScreenHandler.createGeneric9x3(syncId, inv, player1.getEnderChestInventory());
            }
        };
    }
}
