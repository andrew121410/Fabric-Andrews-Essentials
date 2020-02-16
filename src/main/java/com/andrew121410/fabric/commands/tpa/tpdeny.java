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

public class tpdeny {

    private Map<UUID, LackPlayer> tpaMap;

    private Main main;

    public tpdeny(Main main) {
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
            lackPlayer.sendColorMessage("You denied the tpa request.", Formatting.RED);
            target.sendColorMessage(lackPlayer.getDisplayName() + " has denied your tpa request.", Formatting.RED);
        } else {
            lackPlayer.sendColorMessage("Looks like you don't have any tpa request.", Formatting.RED);
        }
        return 1;
    }
}