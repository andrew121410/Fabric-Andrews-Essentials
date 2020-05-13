package com.andrew121410.mc.essfabric.commands.utils;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.math.Vector3;
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

public class GetRelativeCMD {

    private Main main;

    public GetRelativeCMD(Main main) {
        this.main = main;
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("getRelative")
                .then(CommandManager.argument("x", StringArgumentType.string()))
                .then(CommandManager.argument("y", StringArgumentType.string()))
                .then(CommandManager.argument("z", StringArgumentType.string()))
                .then(CommandManager.argument("x1", StringArgumentType.string()))
                .then(CommandManager.argument("y1", StringArgumentType.string()))
                .then(CommandManager.argument("z1", StringArgumentType.string()))
                .executes(this::go));
    }

    public int go(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        LackPlayer lackPlayer = new LackPlayer(player);
        int x = Integer.parseInt(StringArgumentType.getString(ctx, "x"));
        int y = Integer.parseInt(StringArgumentType.getString(ctx, "y"));
        int z = Integer.parseInt(StringArgumentType.getString(ctx, "z"));
        int x1 = Integer.parseInt(StringArgumentType.getString(ctx, "x1"));
        int y1 = Integer.parseInt(StringArgumentType.getString(ctx, "y1"));
        int z1 = Integer.parseInt(StringArgumentType.getString(ctx, "z1"));

        Vector3 vector3 = new Vector3(x, y, z);
        Vector3 vector31 = new Vector3(x1, y1, z1);
        Vector3 vector32 = vector31.subtract(vector3);

        String format = "~" + vector32.getX() + " ~" + vector32.getY() + " ~" + vector32.getZ();
        Text text = new LiteralText("").append(TextFormat.stringToFormattedText("&6Click me to copy.")).styled(it -> it.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, format)));
        player.sendMessage(text, MessageType.CHAT);
        return 1;
    }
}
