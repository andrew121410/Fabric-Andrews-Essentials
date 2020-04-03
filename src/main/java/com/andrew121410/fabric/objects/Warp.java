package com.andrew121410.fabric.objects;

import com.andrew121410.lackAPI.player.Location;


public class Warp {
    private String name;
    private String ownerDisplayName;
    private Location location;

    public Warp(String name, String ownerDisplayName, Location location) {
        this.name = name;
        this.ownerDisplayName = ownerDisplayName;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getOwnerDisplayName() {
        return ownerDisplayName;
    }

    public Location getLocation() {
        return location;
    }
}
