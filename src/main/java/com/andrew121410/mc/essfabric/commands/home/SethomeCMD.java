package com.andrew121410.mc.essfabric.commands.home;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.andrew121410.mc.lackAPI.world.Location;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class SethomeCMD {

    private Map<UUID, Map<String, Location>> homesMap;

    private Main main;

    public SethomeCMD(Main main) {
        this.main = main;
        this.homesMap = this.main.getSetListMap().getHomesMap();
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean isDedicated) {
        commandDispatcher.register(CommandManager.literal("sethome")
                .then(CommandManager.argument("home", StringArgumentType.string())
                        .executes(this::go))
                .executes(this::no));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        Integer integer = null;
        Set<String> strings = player.getScoreboardTags();
        Optional<String> a = strings.stream().filter(s -> s.contains("home")).findFirst();
        if (a.isPresent()) {
            String[] c = a.get().split("\\.");
            integer = Integer.valueOf(c[1]);
        }

        Map<String, Location> homes = this.homesMap.get(lackPlayer.getUUID());
        if (homes.size() == this.main.getModConfig().getHomeLimit() && !lackPlayer.isOp() && integer == null) {
            lackPlayer.sendColorMessage("&eYou can only have " + this.main.getModConfig().getHomeLimit() + " homes.");
            lackPlayer.sendColorMessage("&cIf you want to override the home please delete it and try again.");
            return 1;
        } else if (integer != null && homes.size() == integer && !lackPlayer.isOp()) {
            lackPlayer.sendColorMessage("&eYou have expanded homes but you have reached the limit of: " + integer);
            lackPlayer.sendColorMessage("&cIf you want to override the home please delete it and try again.");
            return 1;
        }

        String home = StringArgumentType.getString(ctx, "home");
        this.main.getHomeManager().save(lackPlayer, home, lackPlayer.getLocation());
        lackPlayer.sendColorMessage("&aYour home has been set.");
        return 1;
    }

    public int no(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("&cHOME NOT SET -> Usage: /sethome (HomeName)");
        return 1;
    }
}
