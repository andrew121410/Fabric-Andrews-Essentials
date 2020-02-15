package com.andrew121410.fabric.commands.home;

import CCUtils.Storage.ISQL;
import CCUtils.Storage.SQLite;
import com.andrew121410.fabric.Main;
import com.andrew121410.fabric.managers.HomeManager;
import com.andrew121410.lackAPI.player.Location;
import com.andrew121410.lackAPI.player.lackPlayer;
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
import java.util.UUID;

public class delhome {


    private Map<UUID, Map<String, Location>> homesMap;

    private Main main;

    private ISQL sqLite;
    private HomeManager homeManager;

    public delhome(Main main) {
        this.main = main;

        this.homesMap = this.main.getSetListMap().getHomesMap();

        this.sqLite = new SQLite(new File("Andrews-Config/"), "Homes");
        this.homeManager = new HomeManager(this.main, this.sqLite);
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("delhome")
                .then(CommandManager.argument("home", new HomeArgumentType(main))
                        .executes(this::go)));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        lackPlayer lackPlayer = new lackPlayer(player);

        String home = StringArgumentType.getString(ctx, "home");

        homeManager.deleteHome(sqLite, lackPlayer, home);
        lackPlayer.sendColorMessage("The home has been deleted.", Formatting.YELLOW);
        return 1;
    }
}
