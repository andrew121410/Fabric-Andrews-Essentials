package com.andrew121410.mc.essfabric.commands.home;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.andrew121410.mc.lackAPI.world.Location;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class HomeArgumentType {

    private Map<UUID, Map<String, Location>> homeMap;

    private Main main;

    public HomeArgumentType(Main main) {
        this.main = main;
        this.homeMap = this.main.getSetListMap().getHomesMap();
    }

    public CompletableFuture<Suggestions> suggest(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayerEntity = context.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(serverPlayerEntity);

        if (homeMap.get(lackPlayer.getUUID()).isEmpty()) {
            return builder.buildFuture();
        }

        Set<String> homeSet = homeMap.get(lackPlayer.getUUID()).keySet();
        String[] homeString = homeSet.toArray(new String[0]);
        for (String s : homeString) {
            builder.suggest(s);
        }
        return builder.buildFuture();
    }

    public static String getString(final CommandContext<?> context, final String name) {
        return context.getArgument(name, String.class);
    }
}
