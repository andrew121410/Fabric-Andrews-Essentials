package com.andrew121410.mc.essfabric.commands;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.andrew121410.mc.lackAPI.player.Location;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.UUID;

public class BackCMD {

    private Map<UUID, Location> backMap;

    private Main main;

    public BackCMD(Main plugin) {
        this.main = plugin;
        this.backMap = this.main.getSetListMap().getBackMap();
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("back")
                .executes(this::go));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        Location location = this.backMap.get(lackPlayer.getUUID());
        if (location != null) {
            lackPlayer.teleport(location);
            lackPlayer.sendColorMessage("&6Teleporting...");
            return 1;
        }
        lackPlayer.sendColorMessage("&cNo back data has been found for you.");
        return 1;
    }

    public int goPlayerOP(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);
        ServerPlayerEntity playerTarget = EntityArgumentType.getPlayer(ctx, "player");
        LackPlayer targetLackPlayer = new LackPlayer(playerTarget);

        Location location = this.backMap.get(targetLackPlayer.getUUID());
        if (location != null) {
            targetLackPlayer.teleport(location);
            lackPlayer.sendColorMessage("&6Teleporting...");
            lackPlayer.sendColorMessage("&6You sent: " + targetLackPlayer.getDisplayName() + " to their back location.");
            return 1;
        }
        lackPlayer.sendColorMessage("&cThere is no data for: " + targetLackPlayer.getDisplayName());
        targetLackPlayer.sendColorMessage("&cNo back data has been found for you.");
        return 1;
    }
}
