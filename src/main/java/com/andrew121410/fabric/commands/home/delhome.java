package com.andrew121410.fabric.commands.home;

import com.andrew121410.fabric.Main;
import com.andrew121410.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

public class delhome {

    private Main main;

    public delhome(Main main) {
        this.main = main;
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("delhome")
                .then(CommandManager.argument("home", StringArgumentType.string())
                        .suggests(new HomeArgumentType(this.main)::suggest)
                        .executes(this::go))
                .executes(this::no));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        String home = StringArgumentType.getString(ctx, "home");

        this.main.getHomeManager().delete(lackPlayer.getUUID(), home);
        lackPlayer.sendColorMessage("The home has been deleted.", Formatting.YELLOW);
        return 1;
    }

    public int no(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("Please check your command and try again.", Formatting.RED);
        return 1;
    }
}
