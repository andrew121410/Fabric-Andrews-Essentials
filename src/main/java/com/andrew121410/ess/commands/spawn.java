package com.andrew121410.ess.commands;

import com.andrew121410.ess.Main;
import com.andrew121410.lackAPI.player.LackPlayer;
import com.andrew121410.lackAPI.player.Location;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.world.dimension.DimensionType;

public class spawn {

    private Main main;

    public spawn(Main plugin) {
        this.main = plugin;
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("spawn")
                .executes(this::go));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        ServerWorld world = this.main.getMinecraftDedicatedServer().getWorld(DimensionType.OVERWORLD);
        lackPlayer.teleport(new Location(Double.parseDouble(String.valueOf(world.getLevelProperties().getSpawnX())), Double.parseDouble(String.valueOf(world.getLevelProperties().getSpawnY())), Double.parseDouble(String.valueOf(world.getLevelProperties().getSpawnZ())), 0, 0, world));
        lackPlayer.sendColorMessage("Teleporting...", Formatting.GOLD);
        return 1;
    }
}
