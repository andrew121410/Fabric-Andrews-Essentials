package com.andrew121410.fabric.utils;

import com.andrew121410.lackAPI.player.LackPlayer;
import com.andrew121410.lackAPI.player.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SetListMap {

    // 0 TO CLEAR AFTER THE PLAYER LEAVES
    // 1 TO ONLY CLEAR WHEN THE SERVER SHUTS DOWN

    private Map<UUID, LackPlayer> tpaMap; //0
    private Map<UUID, Map<String, Location>> homesMap; //0
    private Map<UUID, Location> backMap; //0

    public SetListMap() {
        this.tpaMap = new HashMap<>();
        this.homesMap = new HashMap<>();
        this.backMap = new HashMap<>();
    }

    public void unloadPlayer(LackPlayer lackPlayer) {
        tpaMap.remove(lackPlayer.getUUID());
        homesMap.remove(lackPlayer.getPlayerEntity().getUuid());
        backMap.remove(lackPlayer.getPlayerEntity().getUuid());
    }

    //GETTERS

    public Map<UUID, LackPlayer> getTpaMap() {
        return tpaMap;
    }

    public Map<UUID, Map<String, Location>> getHomesMap() {
        return homesMap;
    }

    public Map<UUID, Location> getBackMap() {
        return backMap;
    }
}
