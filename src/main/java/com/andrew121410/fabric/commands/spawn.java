package com.andrew121410.fabric.commands;

import com.andrew121410.fabric.Main;
import com.andrew121410.lackAPI.player.LackPlayer;
import com.andrew121410.lackAPI.player.Location;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
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

        BlockPos blockPos = player.getServer().getWorld(DimensionType.OVERWORLD).getSpawnPos();
        lackPlayer.teleport(Location.from(blockPos, player.getServer().getWorld(DimensionType.OVERWORLD)));
        lackPlayer.sendColorMessage("Teleporting...", Formatting.GOLD);
        return 1;
    }
}
