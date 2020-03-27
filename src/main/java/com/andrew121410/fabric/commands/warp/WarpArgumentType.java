package com.andrew121410.fabric.commands.warp;

import com.andrew121410.fabric.Main;
import com.andrew121410.fabric.objects.Warp;
import com.andrew121410.lackAPI.player.LackPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WarpArgumentType {

    private Map<String, Warp> warpMap;

    private Main main;

    public WarpArgumentType(Main main) {
        this.main = main;
        this.warpMap = this.main.getSetListMap().getWarpsMap();
    }

    public CompletableFuture<Suggestions> suggest(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        if (warpMap.isEmpty()) return builder.buildFuture();
        ServerPlayerEntity serverPlayerEntity = context.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(serverPlayerEntity);

        Set<String> warpSet = warpMap.keySet();
        String[] warpStrings = warpSet.toArray(new String[0]);
        for (String s : warpStrings) builder.suggest(s);
        return builder.buildFuture();
    }

    public static String getString(final CommandContext<?> context, final String name) {
        return context.getArgument(name, String.class);
    }
}