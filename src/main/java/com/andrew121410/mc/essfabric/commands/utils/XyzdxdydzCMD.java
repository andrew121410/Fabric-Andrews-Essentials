package com.andrew121410.mc.essfabric.commands.utils;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.andrew121410.mc.lackAPI.utils.TextFormat;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class XyzdxdydzCMD {

    private Main main;

    public XyzdxdydzCMD(Main main) {
        this.main = main;
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean isDedicated) {
        commandDispatcher.register(CommandManager.literal("xyzdxdydz")
                .then(CommandManager.argument("x", StringArgumentType.string()))
                .then(CommandManager.argument("y", StringArgumentType.string()))
                .then(CommandManager.argument("z", StringArgumentType.string()))
                .then(CommandManager.argument("dx", StringArgumentType.string()))
                .then(CommandManager.argument("dy", StringArgumentType.string()))
                .then(CommandManager.argument("dz", StringArgumentType.string()))
                .executes(this::go));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);
        String x = StringArgumentType.getString(ctx, "x");
        String y = StringArgumentType.getString(ctx, "y");
        String z = StringArgumentType.getString(ctx, "z");
        String dx = StringArgumentType.getString(ctx, "dx");
        String dy = StringArgumentType.getString(ctx, "dy");
        String dz = StringArgumentType.getString(ctx, "dz");

        if (dx.contains("~")) dx = dx.replace("~", "");
        if (dy.contains("~")) dy = dy.replace("~", "");
        if (dz.contains("~")) dz = dz.replace("~", "");

        String done = "[x=" + x + ",y=" + y + ",z=" + z + ",dx=" + dx + ",dy=" + dy + ",dz=" + dz + "]";
        lackPlayer.sendRawMessage(done);
        Text text = new LiteralText("").append(TextFormat.stringToFormattedText("&6Click me to copy.")).styled(it -> it.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, done)));
        player.sendMessage(text, MessageType.CHAT, lackPlayer.getUUID());
        return 1;
    }
}
