package com.andrew121410.mc.lackAPI.world;

import com.andrew121410.CCUtils.storage.easy.SQLDataStore;
import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.math.Vector3;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.world.World;

import java.util.Objects;

public class Location extends Vector3 {

    private World world;
    private float yaw;
    private float pitch;

    public Location(double x, double y, double z, float yaw, float pitch, World world) {
        super(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    public static Location from(PlayerEntity playerEntity) {
        return new Location(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), playerEntity.yaw, playerEntity.pitch, playerEntity.getEntityWorld());
    }

    public static Location from(SQLDataStore sqlDataStore) {
        String X = sqlDataStore.getMap().get("X");
        String Y = sqlDataStore.getMap().get("Y");
        String Z = sqlDataStore.getMap().get("Z");
        String YAW = sqlDataStore.getMap().get("YAW");
        String PITCH = sqlDataStore.getMap().get("PITCH");
        String WORLD = sqlDataStore.getMap().get("WORLD");

        MinecraftDedicatedServer minecraftDedicatedServer = Main.getInstance().getMinecraftDedicatedServer();
        return new Location(Double.parseDouble(X), Double.parseDouble(Y), Double.parseDouble(Z), Float.parseFloat(YAW), Float.parseFloat(PITCH), minecraftDedicatedServer.getWorld(LackDimension.byRawID(Integer.parseInt(WORLD))));
    }

    public Vector3 getVector3() {
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Float.compare(location.yaw, yaw) == 0 &&
                Float.compare(location.pitch, pitch) == 0 &&
                Objects.equals(world, location.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, yaw, pitch);
    }

    @Override
    public String toString() {
        return "Location{" +
                "world=" + world +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                '}';
    }
}
