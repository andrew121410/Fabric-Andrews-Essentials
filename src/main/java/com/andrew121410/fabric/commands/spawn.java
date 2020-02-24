package com.andrew121410.fabric.commands;

import com.andrew121410.fabric.Main;
import com.andrew121410.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

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

        ServerScoreboard serverScoreboard = player.getServer().getScoreboard();
        Scoreboard scoreboard = player.getScoreboard();
        ScoreboardObjective scoreboardObjective = serverScoreboard.getObjective("spawn");
        ScoreboardPlayerScore scoreboardPlayerScore = new ScoreboardPlayerScore(scoreboard, scoreboardObjective, lackPlayer.getDisplayName());
        scoreboardPlayerScore.setScore(1);
        serverScoreboard.updateScore(scoreboardPlayerScore);
        lackPlayer.sendColorMessage("Teleporting...", Formatting.GOLD);
        return 1;
    }
}
