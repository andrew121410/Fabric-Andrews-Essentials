package com.andrew121410.mc.essfabric.commands;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class TpsCMD {

    private Main main;

    public TpsCMD(Main plugin) {
        this.main = plugin;
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("tps")
                .executes(this::go));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        double tps = main.getTpsHelper().getAverageTPS();
        String color;
        if (tps >= 18.0) {
            color = "&a";
        } else if (tps >= 15.0) {
            color = "&e";
        } else color = "&c";

        long maxMemory = (Runtime.getRuntime().maxMemory() / 1024 / 1024);
        long allocatedMemory = (Runtime.getRuntime().totalMemory() / 1024 / 1024);
        long freeMemory = (Runtime.getRuntime().freeMemory() / 1024 / 1024);
        long usedMemory = allocatedMemory - freeMemory;

        lackPlayer.sendColorMessage("&6TPS: " + color + main.getTpsHelper().getFancyTPS());
        lackPlayer.sendColorMessage("&6Maximum memory: &c" + maxMemory + " MB.");
        lackPlayer.sendColorMessage("&6Allocated memory: &c" + allocatedMemory + " MB.");
        lackPlayer.sendColorMessage("&6Free memory: &c" + freeMemory + " MB.");
        lackPlayer.sendColorMessage("&6Used memory: &c" + usedMemory + " MB.");
        return 1;
    }
}
