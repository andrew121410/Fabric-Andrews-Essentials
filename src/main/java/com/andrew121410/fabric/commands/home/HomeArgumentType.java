package com.andrew121410.fabric.commands.home;

import com.andrew121410.fabric.Main;
import com.andrew121410.lackAPI.player.Location;
import com.andrew121410.lackAPI.player.lackPlayer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class HomeArgumentType implements ArgumentType<String> {

    private Map<UUID, Map<String, Location>> homeMap;

    private Main main;

    public HomeArgumentType(Main main) {
        this.main = main;

        this.homeMap = this.main.getSetListMap().getHomesMap();
    }

    @Override
    public String parse(StringReader reader) {
        final String text = reader.getRemaining();
        reader.setCursor(reader.getTotalLength());
        return text;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) context.getSource();
        lackPlayer lackPlayer = new lackPlayer(serverPlayerEntity);

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
