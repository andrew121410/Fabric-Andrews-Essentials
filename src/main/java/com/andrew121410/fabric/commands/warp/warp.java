package com.andrew121410.fabric.commands.warp;

import com.andrew121410.fabric.Main;
import com.andrew121410.fabric.objects.Warp;
import com.andrew121410.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.Map;

public class warp {

    private Map<String, Warp> warpMap;
    private Main main;

    public warp(Main main) {
        this.main = main;
        this.warpMap = this.main.getSetListMap().getWarpsMap();
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("warp")
                .then(CommandManager.argument("warp", StringArgumentType.string())
                        .suggests(new WarpArgumentType(this.main)::suggest)
                        .executes(this::go))
                .executes(this::no));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        String warpName = StringArgumentType.getString(ctx, "warp");
        Warp warp = this.warpMap.get(warpName);
        if (warp.getLocation() != null) {
            lackPlayer.teleport(warp.getLocation());
            lackPlayer.sendColorMessage("Teleporting...", Formatting.GOLD);
        }
        return 1;
    }

    public int no(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("Please check your command and try again.", Formatting.RED);
        return 1;
    }
}
