package com.andrew121410.lackAPI.player;

import com.andrew121410.lackAPI.utils.TextFormat;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.util.UUID;

public class LackPlayer {

    private ServerPlayerEntity playerEntity;

    public LackPlayer(ServerPlayerEntity playerEntity) {
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

    public void sendColorMessage(String msg) {
        this.playerEntity.sendMessage(TextFormat.stringToFormattedText(msg));
    }

    public void teleport(Location location) {
        playerEntity.teleport((ServerWorld) location.getWorld(), location.getVector3().getX(), location.getVector3().getY(), location.getVector3().getZ(), location.getYaw(), location.getPitch());
    }

    public Location getLocation() {
        return Location.from(playerEntity);
    }

    public UUID getUUID() {
        return this.playerEntity.getUuid();
    }

    public String getDisplayName() {
        return playerEntity.getEntityName();
    }

    public boolean isOp() {
        return playerEntity.getServer().getPlayerManager().isOperator(playerEntity.getGameProfile());
    }

    public LackPackets getLackPackets() {
        return new LackPackets(this);
    }

    //VERY RAW,
    public ServerPlayerEntity getPlayerEntity() {
        return playerEntity;
    }
}
