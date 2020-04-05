package com.andrew121410.ess.utils;

import com.andrew121410.ess.Main;
import com.andrew121410.lackAPI.player.LackPlayer;

public class PlayerInitializer {

    private Main main;

    public PlayerInitializer(Main main) {
        this.main = main;
    }

    public void load(LackPlayer lackPlayer) {
        this.main.getHomeManager().load(lackPlayer);
        main.getDiscordAddon().sendJoinMessage(lackPlayer);
    }

    public void unload(LackPlayer lackPlayer) {
        this.main.getSetListMap().unloadPlayer(lackPlayer);
        main.getDiscordAddon().sendLeaveMessage(lackPlayer);
    }
}
