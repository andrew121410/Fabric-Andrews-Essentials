package com.andrew121410.fabric.commands;

import com.andrew121410.fabric.Main;
import com.andrew121410.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class tps {

    private Main main;

    private static DecimalFormat twoDPlaces = new DecimalFormat("#,###.##");

    public tps(Main plugin) {
        this.main = plugin;
        twoDPlaces.setRoundingMode(RoundingMode.HALF_UP);
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("tps")
                .executes(this::go));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        double tps = main.getTpsHelper().getAverageTPS();
        Formatting color;
        if (tps >= 18.0) {
            color = Formatting.GREEN;
        } else if (tps >= 15.0) {
            color = Formatting.YELLOW;
        } else color = Formatting.RED;

        lackPlayer.sendColorMessage("TPS: " + twoDPlaces.format(tps), color);
        lackPlayer.sendColorMessage("Maximum memory: " + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + " MB.", Formatting.GOLD);
        lackPlayer.sendColorMessage("Allocated memory: " + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + " MB.", Formatting.GOLD);
        lackPlayer.sendColorMessage("Free memory: " + (Runtime.getRuntime().freeMemory() / 1024 / 1024) + " MB.", Formatting.GOLD);
        return 1;
    }
}
