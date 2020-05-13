package com.andrew121410.mc.essfabric.commands.tpa;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.UUID;

public class TpdenyCMD {

    private Map<UUID, LackPlayer> tpaMap;

    private Main main;

    public TpdenyCMD(Main main) {
        this.main = main;

        this.tpaMap = this.main.getSetListMap().getTpaMap();
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("tpdeny")
                .executes(this::go));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        LackPlayer target = tpaMap.get(lackPlayer.getUUID());
        if (target != null) {
            tpaMap.remove(lackPlayer.getUUID());
            lackPlayer.sendColorMessage("&cYou denied the tpa request.");
            target.sendColorMessage("&c" + lackPlayer.getDisplayName() + " has denied your tpa request.");
        } else {
            lackPlayer.sendColorMessage("&cLooks like you don't have any tpa request.");
        }
        return 1;
    }
}