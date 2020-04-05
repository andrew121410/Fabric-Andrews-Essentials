package com.andrew121410.ess.commands;

import com.andrew121410.ess.Main;
import com.andrew121410.lackAPI.player.LackPlayer;
import com.andrew121410.lackAPI.player.Location;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.Map;
import java.util.UUID;

public class back {

    private Map<UUID, Location> backMap;

    private Main main;

    public back(Main plugin) {
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
            lackPlayer.sendColorMessage("Teleporting...", Formatting.GOLD);
            return 1;
        }
        lackPlayer.sendColorMessage("No back data has been found for you.", Formatting.RED);
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
            targetLackPlayer.sendColorMessage("Teleporting...", Formatting.GOLD);
            lackPlayer.sendColorMessage("You sent: " + targetLackPlayer.getDisplayName() + " to their back location.", Formatting.GOLD);
            return 1;
        }
        lackPlayer.sendColorMessage("Their is no data for: " + targetLackPlayer.getDisplayName(), Formatting.RED);
        targetLackPlayer.sendColorMessage("No back data has been found for you.", Formatting.RED);
        return 1;
    }
}
