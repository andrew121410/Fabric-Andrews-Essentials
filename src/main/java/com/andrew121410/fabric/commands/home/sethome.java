package com.andrew121410.fabric.commands.home;

import CCUtils.Storage.ISQL;
import CCUtils.Storage.SQLite;
import com.andrew121410.fabric.Main;
import com.andrew121410.fabric.managers.HomeManager;
import com.andrew121410.fabric.utils.API;
import com.andrew121410.lackAPI.player.LackPlayer;
import com.andrew121410.lackAPI.player.Location;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class sethome {

    private Map<UUID, Map<String, Location>> homesMap;

    private Main main;

    private ISQL sqLite;
    private HomeManager homeManager;

    public sethome(Main main) {
        this.main = main;

        this.homesMap = this.main.getSetListMap().getHomesMap();

        this.sqLite = new SQLite(new File("Andrews-Config/"), "Homes");
        this.homeManager = new HomeManager(this.main, this.sqLite);
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
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
        if (homes.size() == API.HOME_LIMIT && !lackPlayer.isOp() && integer == null) {
            lackPlayer.sendColorMessage("You can only have " + API.HOME_LIMIT + " homes.", Formatting.YELLOW);
            lackPlayer.sendColorMessage("If you want to override the home please delete it and try again.", Formatting.RED);
            return 1;
        } else if (integer != null && homes.size() == integer && !lackPlayer.isOp()) {
            lackPlayer.sendColorMessage("You have expanded homes but you have reached the limit of: " + integer, Formatting.YELLOW);
            lackPlayer.sendColorMessage("If you want to override the home please delete it and try again.", Formatting.RED);
            return 1;
        }

        String home = StringArgumentType.getString(ctx, "home");
        homeManager.setHome(sqLite, lackPlayer, home);
        lackPlayer.sendColorMessage("Your home has been set.", Formatting.GREEN);
        return 1;
    }

    public int no(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("HOME NOT SET -> Usage: /sethome (HomeName)", Formatting.RED);
        return 1;
    }
}
