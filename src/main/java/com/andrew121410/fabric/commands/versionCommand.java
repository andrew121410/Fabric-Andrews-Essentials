package com.andrew121410.fabric.commands;

import com.andrew121410.fabric.Main;
import com.andrew121410.lackAPI.player.lackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

public class versionCommand {

    private Main plugin;

    public versionCommand(Main plugin) {
        this.plugin = plugin;
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("version")
                .executes(this::go));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        lackPlayer lackPlayer = new lackPlayer(player);

        lackPlayer.sendColorMessage("This server is running Andrew's custom server software with Andrew's lack api.", Formatting.GOLD);
        return 1;
    }
}
