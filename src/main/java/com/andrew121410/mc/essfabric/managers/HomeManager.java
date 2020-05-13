package com.andrew121410.mc.essfabric.managers;

import com.andrew121410.CCUtils.storage.ISQL;
import com.andrew121410.CCUtils.storage.SQLite;
import com.andrew121410.CCUtils.storage.easy.EasySQL;
import com.andrew121410.CCUtils.storage.easy.SQLDataStore;
import com.andrew121410.mc.essfabric.Main;
import com.andrew121410.mc.lackAPI.player.LackPlayer;
import com.andrew121410.mc.lackAPI.player.Location;
import com.google.common.collect.Multimap;
import net.minecraft.world.dimension.DimensionType;

import java.sql.SQLException;
import java.util.*;

public class HomeManager {

    private Map<UUID, Map<String, Location>> homesMap;

    private Main main;

    private EasySQL easySQL;
    private ISQL isql;

    public HomeManager(Main main) {
        this.main = main;
        this.homesMap = this.main.getSetListMap().getHomesMap();

        this.isql = new SQLite(main.getModConfigFolder(), "Homes");
        this.easySQL = new EasySQL(isql, "Homes");

        List<String> columns = new ArrayList<>();
        columns.add("UUID");
        columns.add("Date");
        columns.add("PlayerName");
        columns.add("HomeName");
        columns.add("X");
        columns.add("Y");
        columns.add("Z");
        columns.add("YAW");
        columns.add("PITCH");
        columns.add("World");
        easySQL.create(columns, false);
    }

    public void load(LackPlayer player) {
        Map<String, String> toGet = new HashMap<>();
        toGet.put("UUID", String.valueOf(player.getUUID()));
        Multimap<String, SQLDataStore> convert = easySQL.get(toGet);
        this.homesMap.putIfAbsent(player.getUUID(), new HashMap<>());
        convert.forEach((k, v) -> load(player, v));
    }

    public void save(LackPlayer player, String homeName, Location location) {
        this.homesMap.get(player.getUUID()).put(homeName.toLowerCase(), location);

        SQLDataStore sqlDataStore = new SQLDataStore();
        sqlDataStore.getMap().put("UUID", String.valueOf(player.getUUID()));
        sqlDataStore.getMap().put("Date", "0");
        sqlDataStore.getMap().put("PlayerName", player.getDisplayName());
        sqlDataStore.getMap().put("HomeName", homeName.toLowerCase());
        sqlDataStore.getMap().put("X", String.valueOf(location.getVector3().getX()));
        sqlDataStore.getMap().put("Y", String.valueOf(location.getVector3().getY()));
        sqlDataStore.getMap().put("Z", String.valueOf(location.getVector3().getZ()));
        sqlDataStore.getMap().put("YAW", String.valueOf(location.getYaw()));
        sqlDataStore.getMap().put("PITCH", String.valueOf(location.getPitch()));
        sqlDataStore.getMap().put("World", String.valueOf(location.getWorld().getDimension().getType().getRawId()));
        try {
            easySQL.save(sqlDataStore);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(UUID uuid, String homeName) {
        this.homesMap.get(uuid).remove(homeName.toLowerCase());
        Map<String, String> toDelete = new HashMap<>();
        toDelete.put("UUID", String.valueOf(uuid));
        toDelete.put("HomeName", homeName.toLowerCase());
        easySQL.delete(toDelete);
    }

    private void load(LackPlayer player, SQLDataStore sqlDataStore) {
        String UUID = sqlDataStore.getMap().get("UUID");
        String Date = sqlDataStore.getMap().get("Date");
        String PlayerName = sqlDataStore.getMap().get("PlayerName");
        String HomeName = sqlDataStore.getMap().get("HomeName");
        String X = sqlDataStore.getMap().get("X");
        String Y = sqlDataStore.getMap().get("Y");
        String Z = sqlDataStore.getMap().get("Z");
        String YAW = sqlDataStore.getMap().get("YAW");
        String PITCH = sqlDataStore.getMap().get("PITCH");
        String World = sqlDataStore.getMap().get("World");

        Location location = new Location(Double.parseDouble(X), Double.parseDouble(Y), Double.parseDouble(Z), Float.parseFloat(YAW), Float.parseFloat(PITCH), player.getPlayerEntity().getServer().getWorld(DimensionType.byRawId(Integer.parseInt(World))));
        this.homesMap.get(player.getUUID()).put(HomeName, location);
    }
}