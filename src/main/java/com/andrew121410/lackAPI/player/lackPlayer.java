package com.andrew121410.lackAPI.player;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class lackPlayer {

    private ServerPlayerEntity playerEntity;

    public lackPlayer(ServerPlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void sendRawMessage(String msg) {
        for (String part : msg.split("\n")) {
            this.playerEntity.sendMessage(new LiteralText(part));
        }
    }

    public void sendColorMessage(String msg, Formatting formatting) {
        for (String part : msg.split("\n")) {
            LiteralText component = new LiteralText(part);
            component.getStyle().setColor(formatting);
            this.playerEntity.sendMessage(component);
        }
    }

    public Location getLocation() {
        return Location.from(playerEntity);
    }
}
