package com.andrew121410.fabric.managers;

import com.andrew121410.CCUtils.storage.ISQL;
import com.andrew121410.CCUtils.storage.SQLite;
import com.andrew121410.CCUtils.storage.easy.EasySQL;
import com.andrew121410.CCUtils.storage.easy.SQLDataStore;
import com.andrew121410.fabric.Main;
import com.andrew121410.fabric.objects.Warp;
import com.andrew121410.lackAPI.player.Location;
import com.google.common.collect.Multimap;
import net.minecraft.world.dimension.DimensionType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpManager {

    private Map<String, Warp> warpsMap;

    private Main main;

    private ISQL isql;
    private EasySQL easySQL;

    public WarpManager(Main main) {
        this.main = main;
        this.warpsMap = this.main.getSetListMap().getWarpsMap();

        this.isql = new SQLite(this.main.getModConfigFolder(), "Warps");
        this.easySQL = new EasySQL(this.isql, "Warps");

        List<String> columns = new ArrayList<>();
        columns.add("Name");
        columns.add("World");
        columns.add("X");
        columns.add("Y");
        columns.add("Z");
        columns.add("YAW");
        columns.add("PITCH");
        columns.add("Owner");

        this.easySQL.create(columns, true);
    }

    public void load() {
        try {
            Multimap<String, SQLDataStore> multimap = easySQL.getEverything();
            multimap.forEach((k, v) -> this.warpsMap.putIfAbsent(k, toWarp(v)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Warp warp) {
        this.warpsMap.putIfAbsent(warp.getName(), warp);
        try {
            easySQL.save(toSQLDataStore(warp));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String warpName) {
        this.warpsMap.remove(warpName);
        Map<String, String> toDelete = new HashMap<>();
        toDelete.put("Name", warpName);
        easySQL.delete(toDelete);
    }

    private Warp toWarp(SQLDataStore sqlDataStore) {
        String name = sqlDataStore.getMap().get("Name");
        String worldID = sqlDataStore.getMap().get("World");
        String x = sqlDataStore.getMap().get("X");
        String y = sqlDataStore.getMap().get("Y");
        String z = sqlDataStore.getMap().get("Z");
        String yaw = sqlDataStore.getMap().get("YAW");
        String pitch = sqlDataStore.getMap().get("PITCH");
        String owner = sqlDataStore.getMap().get("Owner");
        return new Warp(name, owner, new Location(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z), Float.parseFloat(yaw), Float.parseFloat(pitch), main.getMinecraftDedicatedServer().getWorld(DimensionType.byRawId(Integer.parseInt(worldID)))));
    }

    private SQLDataStore toSQLDataStore(Warp warp) {
        SQLDataStore sqlDataStore = new SQLDataStore();
        Location location = warp.getLocation();
        String worldID = String.valueOf(location.getWorld().getDimension().getType().getRawId());
        sqlDataStore.getMap().put("Name", warp.getName().toLowerCase());
        sqlDataStore.getMap().put("World", worldID);
        sqlDataStore.getMap().put("X", String.valueOf(location.getVector3().getX()));
        sqlDataStore.getMap().put("Y", String.valueOf(location.getVector3().getY()));
        sqlDataStore.getMap().put("Z", String.valueOf(location.getVector3().getZ()));
        sqlDataStore.getMap().put("YAW", String.valueOf(location.getYaw()));
        sqlDataStore.getMap().put("PITCH", String.valueOf(location.getPitch()));
        sqlDataStore.getMap().put("Owner", warp.getOwnerDisplayName());
        return sqlDataStore;
    }
}
