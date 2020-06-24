package com.andrew121410.mc.essfabric.commands.warp;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.essfabric.objects.WarpObject;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;

public class WarpCMD {

    private Map<String, WarpObject> warpMap;
    private Main main;

    public WarpCMD(Main main) {
        this.main = main;
        this.warpMap = this.main.getSetListMap().getWarpsMap();
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean isDedicated) {
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
        WarpObject warpObject = this.warpMap.get(warpName);

        if (warpObject.getLocation() != null) {
            lackPlayer.teleport(warpObject.getLocation());
            lackPlayer.sendColorMessage("&6Teleporting...");
        }
        return 1;
    }

    public int no(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("&cPlease check your command and try again.");
        return 1;
    }
}
