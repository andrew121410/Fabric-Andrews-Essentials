package com.andrew121410.lackAPI.player;

import com.andrew121410.lackAPI.math.Vector3;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class Location {

    private Vector3 vector3;
    private World world;
    private float yaw;
    private float pitch;

    public Location(double x, double y, double z, float yaw, float pitch, World world) {
        this.vector3 = new Vector3(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    public static Location from(PlayerEntity playerEntity) {
        return new Location(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), playerEntity.yaw, playerEntity.pitch, playerEntity.getEntityWorld());
    }

    public Vector3 getVector3() {
        return vector3;
    }

    public World getWorld() {
        return world;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
