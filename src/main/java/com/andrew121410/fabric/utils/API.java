package com.andrew121410.fabric.utils;

import CCUtils.Storage.CustomYmlManager;
import com.andrew121410.lackAPI.player.Location;

public class API {

    public static int HOME_LIMIT = 1;
    public static String VERSION = "1.3";

    public Location getLocationFromFile(CustomYmlManager configinstance, String path) {


        return (Location) configinstance.getConfig().get(path);
    }

    public void setLocationToFile(CustomYmlManager configinstance, String path, Location location) {

        configinstance.getConfig().set(path, location);
        configinstance.saveConfigSilent();
    }
}
