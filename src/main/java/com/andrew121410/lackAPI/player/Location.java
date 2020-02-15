package com.andrew121410.lackAPI.player;

import com.andrew121410.lackAPI.math.Vector3;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class Location {

    private Vector3 vector3;
    private World world;

    public Location(Vector3 vector3, World world) {
        this.vector3 = vector3;
        this.world = world;
    }

    public static Location from(PlayerEntity playerEntity) {
        return new Location(new Vector3(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ()), playerEntity.getEntityWorld());
    }

    public Vector3 getVector3() {
        return vector3;
    }

    public World getWorld() {
        return world;
    }
}
