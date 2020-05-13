package com.andrew121410.mc.essfabric.commands;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;
import java.util.Set;

public class EchestCMD {

    private Main main;

    public EchestCMD(Main plugin) {
        this.main = plugin;
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("echest")
                .executes(this::go));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        Set<String> strings = player.getScoreboardTags();
        Optional<String> tag = strings.stream().filter(s -> s.contains("echest")).findFirst();

        if (tag.isPresent() || lackPlayer.isOp()) {
            lackPlayer.getLackPlayerInventory().openEnderChest();
            lackPlayer.sendColorMessage("&aOpening...");
        } else {
            lackPlayer.sendColorMessage("&cYou don't have the tag: &eechest");
        }
        return 1;
    }
}
