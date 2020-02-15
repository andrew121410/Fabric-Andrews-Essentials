package com.andrew121410.fabric.commands.tpa;

import com.andrew121410.fabric.Main;
import com.andrew121410.lackAPI.player.lackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.Map;

public class tpa {

    private Map<lackPlayer, lackPlayer> tpaMap;

    private Main main;

    public tpa(Main main) {
        this.main = main;

        this.tpaMap = this.main.getSetListMap().getTpaMap();
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("tpa")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(this::go))
                .executes(this::no));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        lackPlayer lackPlayer = new lackPlayer(player);
        ServerPlayerEntity playerTarget = EntityArgumentType.getPlayer(ctx, "player");
        lackPlayer targetLackPlayer = new lackPlayer(playerTarget);

        tpaMap.put(targetLackPlayer, lackPlayer);
        lackPlayer.sendColorMessage("You sent a tpa request to " + playerTarget.getDisplayName(), Formatting.YELLOW);
        targetLackPlayer.sendColorMessage(player.getDisplayName() + " has sent a tpa request to you.", Formatting.GREEN);
        targetLackPlayer.sendColorMessage("/tpaccept OR /tpdeny", Formatting.LIGHT_PURPLE);
        return 1;
    }

    public int no(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        lackPlayer lackPlayer = new lackPlayer(player);

        lackPlayer.sendColorMessage("Please check your command and try again.", Formatting.RED);
        return 1;
    }

}
