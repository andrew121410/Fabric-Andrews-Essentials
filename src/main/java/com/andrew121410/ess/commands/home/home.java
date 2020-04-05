package com.andrew121410.ess.commands.home;

import com.andrew121410.ess.Main;
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

import java.util.Map;
import java.util.UUID;

public class home {

    private Map<UUID, Map<String, Location>> homesMap;

    private Main main;

    public home(Main main) {
        this.main = main;
        this.homesMap = this.main.getSetListMap().getHomesMap();
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("home")
                .then(CommandManager.argument("home", StringArgumentType.string())
                        .suggests(new HomeArgumentType(this.main)::suggest)
                        .executes(this::go))
                .executes(this::no));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        String home = StringArgumentType.getString(ctx, "home");
        Location homeLoc = this.homesMap.get(lackPlayer.getUUID()).get(home);
        if (home != null && homeLoc != null) {
            lackPlayer.teleport(homeLoc);
            lackPlayer.sendColorMessage("Teleporting...", Formatting.GOLD);
        }
        return 1;
    }

    public int no(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("Please check your command and try again.", Formatting.RED);
        return 1;
    }
}
