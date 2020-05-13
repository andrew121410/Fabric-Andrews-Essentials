package com.andrew121410.mc.essfabric.commands.tpa;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.UUID;

public class TpaCMD {

    private Map<UUID, LackPlayer> tpaMap;

    private Main main;

    public TpaCMD(Main main) {
        this.main = main;
        this.tpaMap = this.main.getSetListMap().getTpaMap();
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("tpa")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(this::go))
                .executes(this::no));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);
        ServerPlayerEntity playerTarget = EntityArgumentType.getPlayer(ctx, "player");
        LackPlayer targetLackPlayer = new LackPlayer(playerTarget);

        this.tpaMap.remove(targetLackPlayer.getUUID());
        this.tpaMap.put(targetLackPlayer.getUUID(), lackPlayer);
        lackPlayer.sendColorMessage("&eYou sent a tpa request to " + targetLackPlayer.getDisplayName());
        targetLackPlayer.sendColorMessage("&a" + lackPlayer.getDisplayName() + " has sent a tpa request to you.");
        targetLackPlayer.sendColorMessage("&5/tpaccept OR /tpdeny");
        return 1;
    }

    public int no(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        lackPlayer.sendColorMessage("&cPlease check your command and try again.");
        return 1;
    }

}
