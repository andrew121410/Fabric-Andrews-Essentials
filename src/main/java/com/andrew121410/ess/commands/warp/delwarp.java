package com.andrew121410.ess.commands.warp;

import com.andrew121410.ess.Main;
import com.andrew121410.ess.objects.WarpObject;
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

public class delwarp {

    private Map<String, WarpObject> warpMap;
    private Main main;

    public delwarp(Main main) {
        this.main = main;
        this.warpMap = this.main.getSetListMap().getWarpsMap();
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("delwarp")
                .requires(it -> it.hasPermissionLevel(2))
                .then(CommandManager.argument("warp", StringArgumentType.string())
                        .executes(this::go))
                .executes(this::no));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        String warpName = StringArgumentType.getString(ctx, "warp");

        if (this.warpMap.get(warpName) == null) {
            lackPlayer.sendColorMessage("There's no warp by that name.", Formatting.RED);
        } else {
            this.main.getWarpManager().delete(warpName);
            lackPlayer.sendColorMessage("[&6Warp&r] &aWarp has been successfully deleted.");
        }
        return 1;
    }

    public int no(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("Warp has not been delete USAGE: /delwarp <Warp>", Formatting.RED);
        return 1;
    }
}