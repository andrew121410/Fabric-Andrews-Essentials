package com.andrew121410.mc.essfabric.managers;

import com.andrew121410.CCUtils.storage.ISQL;
import com.andrew121410.CCUtils.storage.SQLite;
import com.andrew121410.CCUtils.storage.easy.EasySQL;
import com.andrew121410.CCUtils.storage.easy.SQLDataStore;
import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.essfabric.objects.WarpObject;
import com.andrew121410.mc.lackAPI.world.LackDimension;
import com.andrew121410.mc.lackAPI.world.Location;
import com.google.common.collect.Multimap;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpManager {

    private Map<String, WarpObject> warpsMap;

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
        columns.add("WORLD");
        columns.add("X");
        columns.add("Y");
        columns.add("Z");
        columns.add("YAW");
        columns.add("PITCH");
        columns.add("Owner");

        this.easySQL.create(columns, false);
    }

    public void load() {
        try {
            Multimap<String, SQLDataStore> everythingMap = easySQL.getEverything();
            for (Map.Entry<String, SQLDataStore> entry : everythingMap.entries())
                this.warpsMap.put(entry.getKey(), toWarp(entry.getValue()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(WarpObject warpObject) {
        this.warpsMap.putIfAbsent(warpObject.getName(), warpObject);
        try {
            easySQL.save(toSQLDataStore(warpObject));
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

    private WarpObject toWarp(SQLDataStore sqlDataStore) {
        String name = sqlDataStore.getMap().get("Name");
        String owner = sqlDataStore.getMap().get("Owner");
        return new WarpObject(name, owner, Location.from(sqlDataStore));
    }

    private SQLDataStore toSQLDataStore(WarpObject warpObject) {
        SQLDataStore sqlDataStore = new SQLDataStore();
        Location location = warpObject.getLocation();
        String worldID = String.valueOf(LackDimension.toRawID(location.getWorld().getDimension()));
        sqlDataStore.getMap().put("Name", warpObject.getName().toLowerCase());
        sqlDataStore.getMap().put("WORLD", worldID);
        sqlDataStore.getMap().put("X", String.valueOf(location.getVector3().getX()));
        sqlDataStore.getMap().put("Y", String.valueOf(location.getVector3().getY()));
        sqlDataStore.getMap().put("Z", String.valueOf(location.getVector3().getZ()));
        sqlDataStore.getMap().put("YAW", String.valueOf(location.getYaw()));
        sqlDataStore.getMap().put("PITCH", String.valueOf(location.getPitch()));
        sqlDataStore.getMap().put("Owner", warpObject.getOwner());
        return sqlDataStore;
    }
}
