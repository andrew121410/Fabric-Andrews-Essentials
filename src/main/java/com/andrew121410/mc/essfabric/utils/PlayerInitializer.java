package com.andrew121410.mc.essfabric.utils;

import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;

public class PlayerInitializer {

    private Main main;

    public PlayerInitializer(Main main) {
        this.main = main;
    }

    public void load(LackPlayer lackPlayer) {
        this.main.getHomeManager().load(lackPlayer);
    }

    public void unload(LackPlayer lackPlayer) {
        this.main.getSetListMap().unloadPlayer(lackPlayer);
    }
}
