package com.andrew121410.mc.essfabric.commands;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class BottleCMD {

    private Main main;

    public BottleCMD(Main main) {
        this.main = main;
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean isDedicated) {
        commandDispatcher.register(CommandManager.literal("bottle")
                .then(CommandManager.literal("points")
                        .then(CommandManager.argument("int", IntegerArgumentType.integer())
                                .executes(this::points)))
                .then(CommandManager.literal("levels")
                        .then(CommandManager.argument("int", IntegerArgumentType.integer())
                                .executes(this::levels))));
    }

    public int points(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("Data sent: " + IntegerArgumentType.getInteger(ctx, "int"));
        lackPlayer.sendColorMessage("This is points::");

        return 1;
    }

    public int levels(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("Data sent: " + IntegerArgumentType.getInteger(ctx, "int"));
        lackPlayer.sendColorMessage("This is levels::");

        return 1;
    }

    public int no(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("&cPlease check your command and try again.");
        return 1;
    }
}
