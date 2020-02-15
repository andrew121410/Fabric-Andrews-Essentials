package com.andrew121410.fabric.utils;

import CCUtils.Storage.ISQL;
import CCUtils.Storage.SQLite;
import com.andrew121410.fabric.Main;
import com.andrew121410.fabric.managers.HomeManager;
import com.andrew121410.lackAPI.player.lackPlayer;

import java.io.File;

public class PlayerInitializer {

    private Main main;

    private HomeManager homeManager;

    private ISQL isqlHomes;

    public PlayerInitializer(Main main) {
        this.main = main;

        this.isqlHomes = new SQLite(new File("Andrews-Config/"), "Homes");
        this.homeManager = new HomeManager(this.main, this.isqlHomes);
    }

    public void load(lackPlayer lackPlayer) {
        this.homeManager.getAllHomesFromISQL(lackPlayer);
    }

    public void unload(lackPlayer lackPlayer) {
        this.main.getSetListMap().unloadPlayer(lackPlayer);
    }
}
