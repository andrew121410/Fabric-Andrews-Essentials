package com.andrew121410.fabric.managers;

import CCUtils.Storage.ISQL;
import com.andrew121410.fabric.Main;
import com.andrew121410.lackAPI.math.Vector3;
import com.andrew121410.lackAPI.player.Location;
import com.andrew121410.lackAPI.player.lackPlayer;
import net.minecraft.world.dimension.DimensionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HomeManager {

    private Map<UUID, Map<String, Location>> rawHomesMap;

    private Main plugin;
    private ISQL isql;

    public HomeManager(Main plugin, ISQL isql) {
        this.plugin = plugin;
        this.isql = isql;

        this.rawHomesMap = this.plugin.getSetListMap().getHomesMap();

        isql.Connect();
        isql.ExecuteCommand("CREATE TABLE IF NOT EXISTS `Homes` (" +
                "`UUID` TEXT," +
                "`Date` TEXT," +
                "`PlayerName` TEXT," +
                "`HomeName` TEXT," +
                "`X` TEXT," +
                "`Y` TEXT," +
                "`Z` TEXT," +
                "`YAW` TEXT," +
                "`PITCH` TEXT," +
                "`World` TEXT" +
                ");");
        isql.Disconnect();
    }

    public void getAllHomesFromISQL(lackPlayer player) {
        rawHomesMap.putIfAbsent(player.getUUID(), new HashMap<>());

        isql.Connect();

        ResultSet rs = isql.GetResult("SELECT * FROM Homes WHERE (UUID='" + player.getUUID().toString() + "');");
        try {
            while (rs.next()) {
                String UUID = rs.getString("UUID");
                String Date = rs.getString("Date");
                String PlayerName = rs.getString("PlayerName");
                String HomeName = rs.getString("HomeName");
                String X = rs.getString("X");
                String Y = rs.getString("Y");
                String Z = rs.getString("Z");
                String YAW = rs.getString("YAW");
                String PITCH = rs.getString("PITCH");
                String World = rs.getString("World");

                Location location = new Location(new Vector3(Double.parseDouble(X), Double.parseDouble(Y), Double.parseDouble(Z)), player.getPlayerEntity().getServer().getWorld(DimensionType.byRawId(Integer.parseInt(World))));
                rawHomesMap.get(player.getUUID()).put(HomeName, location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            isql.Disconnect();
        }
    }

    public Location getHome(lackPlayer player, String HomeName) {
        return rawHomesMap.get(player.getUUID()).get(HomeName);
    }

    public Location getHome(UUID uuid, String HomeName) {
        return rawHomesMap.get(uuid).get(HomeName);
    }

    public void deleteHome(ISQL isql, lackPlayer player, String HomeName) {
        rawHomesMap.get(player.getUUID()).remove(HomeName.toLowerCase());

        deleteHomeFromISQL(isql, player, HomeName);
    }

    private void deleteHomeFromISQL(ISQL isql, lackPlayer player, String HomeName) {
        isql.Connect();
        isql.ExecuteCommand("DELETE FROM Homes WHERE UUID='" + player.getUUID() + "' AND HomeName='" + HomeName.toLowerCase() + "'");
        isql.Disconnect();
    }

    public void deleteAllHomesFromISQL(ISQL isql, lackPlayer player) {
        isql.Connect();
        isql.ExecuteCommand("DELETE FROM Homes WHERE UUID='" + player.getUUID() + "'");
        isql.Disconnect();
        rawHomesMap.remove(player.getUUID());
    }

    public String listHomesInMap(lackPlayer player) {
        Set<String> homeSet = rawHomesMap.get(player.getUUID()).keySet();
        String[] homeString = homeSet.toArray(new String[0]);
        Arrays.sort(homeString);
        String str = String.join(", ", homeString);
        String homeListPrefix = "Homes:";
        return homeListPrefix + " " + str;
    }

    public void setHome(ISQL isql, lackPlayer player, String HomeName) {
        rawHomesMap.get(player.getUUID()).put(HomeName.toLowerCase(), player.getLocation());

        setHomeToISQL(isql, player.getUUID(), player.getPlayerEntity().getDisplayName().getString(), HomeName, player.getLocation());
    }

    public void setHome(ISQL isql, UUID uuid, String PlayerName, String HomeName, Location location) {
        rawHomesMap.get(uuid).put(HomeName.toLowerCase(), location);

        setHomeToISQL(isql, uuid, PlayerName, HomeName, location);
    }

    private void setHomeToISQL(ISQL isql, UUID uuid, String PlayerName, String HomeName, Location location) {
        isql.Connect();
        PreparedStatement preparedStatement = isql.ExecuteCommandPreparedStatement("INSERT INTO Homes (UUID,Date,PlayerName,HomeName,X,Y,Z,YAW,PITCH,World) VALUES (?,?,?,?,?,?,?,?,?,?);");
        try {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, "0");
            preparedStatement.setString(3, PlayerName);
            preparedStatement.setString(4, HomeName.toLowerCase());
            preparedStatement.setString(5, String.valueOf(location.getVector3().getX()));
            preparedStatement.setString(6, String.valueOf(location.getVector3().getY()));
            preparedStatement.setString(7, String.valueOf(location.getVector3().getZ()));
            preparedStatement.setString(8, String.valueOf(0));
            preparedStatement.setString(9, String.valueOf(0));
            preparedStatement.setString(10, String.valueOf(location.getWorld().getDimension().getType().getRawId()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            isql.Disconnect();
        }

    }

    public void unloadPlayerHomes(lackPlayer player) {
        rawHomesMap.remove(player.getUUID());
    }
}
