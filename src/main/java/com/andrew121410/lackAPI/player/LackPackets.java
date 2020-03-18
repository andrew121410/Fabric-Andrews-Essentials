package com.andrew121410.lackAPI.player;

import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;

public class LackPackets {

    private LackPlayer lackPlayer;

    public LackPackets(LackPlayer lackPlayer) {
        this.lackPlayer = lackPlayer;
    }

    public void removePlayerFromTablist() {
        PlayerListS2CPacket packet = new PlayerListS2CPacket(PlayerListS2CPacket.Action.REMOVE_PLAYER, new ServerPlayerEntity[]{this.lackPlayer.getPlayerEntity()});
        lackPlayer.getPlayerEntity().getServer().getPlayerManager().sendToAll(packet);
    }

    public void addPlayerFromTablist() {
        PlayerListS2CPacket packet = new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, new ServerPlayerEntity[]{this.lackPlayer.getPlayerEntity()});
        lackPlayer.getPlayerEntity().getServer().getPlayerManager().sendToAll(packet);
    }
}
