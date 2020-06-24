package com.andrew121410.mc.essfabric.commands.home;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.andrew121410.mc.lackAPI.world.Location;
import com.andrew121410.mc.lackAPI.utils.SimpleScheduler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;
import java.util.UUID;

public class HomeCMD {

    private Map<UUID, Map<String, Location>> homesMap;

    private Main main;

    public HomeCMD(Main main) {
        this.main = main;
        this.homesMap = this.main.getSetListMap().getHomesMap();
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean isDedicated) {
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

        if (home != null && homeLoc != null && !lackPlayer.isOp()) {
            ServerWorld serverWorld = (ServerWorld) homeLoc.getWorld();
            int x = (int) homeLoc.getX();
            int z = (int) homeLoc.getZ();
            serverWorld.setChunkForced(x, z, true);
            lackPlayer.sendColorMessage("&c[HomeManager] &4&eLoading your home chunks.");
            SimpleScheduler.schedule(20, () -> {
                lackPlayer.teleport(homeLoc);
                lackPlayer.sendColorMessage("&6Teleporting...");
                serverWorld.setChunkForced(x, z, false);
            });
        } else if (lackPlayer.isOp() && home != null && homeLoc != null) {
            lackPlayer.teleport(homeLoc);
            lackPlayer.sendColorMessage("&6Teleporting...");
        }
        return 1;
    }

    public int no(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("&cPlease check your command and try again.");
        return 1;
    }
}
