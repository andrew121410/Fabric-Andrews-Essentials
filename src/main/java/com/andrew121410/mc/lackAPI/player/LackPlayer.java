package com.andrew121410.mc.lackAPI.player;

import com.andrew121410.mc.lackAPI.utils.TextFormat;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;

import java.util.UUID;

public class LackPlayer {

    private ServerPlayerEntity playerEntity;

    public LackPlayer(ServerPlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void sendRawMessage(String msg) {
        for (String part : msg.split("\n")) {
            this.playerEntity.sendMessage(new LiteralText(part), MessageType.CHAT);
        }
    }

    public void sendColorMessage(String msg) {
        this.playerEntity.sendMessage(TextFormat.stringToFormattedText(msg), MessageType.CHAT);
    }

    public void teleport(Location location) {
        playerEntity.teleport((ServerWorld) location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
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

    public LackPlayerInventory getLackPlayerInventory() {
        return new LackPlayerInventory(this);
    }

    //VERY RAW,
    public ServerPlayerEntity getPlayerEntity() {
        return playerEntity;
    }
}
