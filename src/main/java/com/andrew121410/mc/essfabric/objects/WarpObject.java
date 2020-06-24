package com.andrew121410.mc.essfabric.objects;

import com.andrew121410.mc.lackAPI.world.Location;
import org.jetbrains.annotations.NotNull;

public class WarpObject {

    private String name;
    private String owner;
    private Location location;

    public WarpObject(String name, String owner, @NotNull Location location) {
        this.name = name;
        this.owner = owner;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }
}
