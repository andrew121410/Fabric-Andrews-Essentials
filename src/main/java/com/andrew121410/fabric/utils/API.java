package com.andrew121410.fabric.utils;

import CCUtils.Storage.CustomYmlManager;
import com.andrew121410.lackAPI.player.Location;

public class API {

    public static String VERSION = "1.0";

    public Location getLocationFromFile(CustomYmlManager configinstance, String path) {
        if (configinstance == null || path == null) {
            return null;
        }

        return (Location) configinstance.getConfig().get(path);
    }

    public void setLocationToFile(CustomYmlManager configinstance, String path, Location location) {
        if (configinstance == null || path == null || location == null) {
            return;
        }

        configinstance.getConfig().set(path, location);
        configinstance.saveConfigSilent();
    }
}
