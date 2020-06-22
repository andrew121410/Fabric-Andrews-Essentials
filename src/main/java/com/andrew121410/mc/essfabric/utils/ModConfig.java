package com.andrew121410.mc.essfabric.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModConfig {

    public int homeLimit;

    public ModConfig(Map<String, Object> map) {
        this.homeLimit = (int) map.get("home-limit");
    }

    public int getHomeLimit() {
        return homeLimit;
    }

    public static Map<String, Object> createDefaults() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("home-limit", 1);
        return map;
    }
}
