package com.andrew121410.fabric.commands.tpa;

import com.andrew121410.fabric.Main;
import com.andrew121410.lackAPI.player.LackPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.Map;
import java.util.UUID;

public class tpaccept {

    private Map<UUID, LackPlayer> tpaMap;

    private Main main;

    public tpaccept(Main main) {
        this.main = main;

        this.tpaMap = this.main.getSetListMap().getTpaMap();
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("tpaccept")
                .executes(this::go));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);

        LackPlayer target = this.tpaMap.get(lackPlayer.getUUID());
        if (target != null) {
            target.teleport(lackPlayer.getLocation());
            this.tpaMap.remove(lackPlayer.getUUID());
            target.sendColorMessage(lackPlayer.getDisplayName() + " has accepted your tpa request.", Formatting.GREEN);
        } else {
            lackPlayer.sendColorMessage("Looks like you don't have any tpa request.", Formatting.RED);
        }
        return 1;
    }
}
