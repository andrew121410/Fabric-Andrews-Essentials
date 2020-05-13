package com.andrew121410.mc.essfabric.commands;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.andrew121410.mc.lackAPI.player.Location;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;

public class SpawnCMD {

    private Main main;

    public SpawnCMD(Main plugin) {
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
        lackPlayer.sendColorMessage("&6Teleporting...");
        return 1;
    }
}
