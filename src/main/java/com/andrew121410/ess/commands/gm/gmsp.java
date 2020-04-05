package com.andrew121410.ess.commands.gm;

import com.andrew121410.ess.Main;
import com.andrew121410.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;

public class gmsp {

    private Main main;

    public gmsp(Main plugin) {
        this.main = plugin;
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("gmsp")
                .requires(it -> it.hasPermissionLevel(2))
                .executes(this::go));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.getPlayerEntity().setGameMode(GameMode.SPECTATOR);
        lackPlayer.sendColorMessage("Your gamemode has been changed to SPECTATOR", Formatting.GOLD);
        return 1;
    }
}
