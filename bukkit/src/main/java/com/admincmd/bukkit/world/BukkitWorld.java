/*
 * This file is part of AdminCMD
 * Copyright (C) 2015 AdminCMD Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*/
package com.admincmd.bukkit.world;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.database.Database;
import com.admincmd.api.util.logger.DebugLogger;
import com.admincmd.api.world.Location;
import com.admincmd.api.world.Weather;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BukkitWorld implements com.admincmd.api.world.World {

    private final World world;

    private final Database database;

    private String spawnLoc = "";
    private boolean wPaused = false;
    private Weather wPMoment = Weather.CLEAR;
    private boolean tPaused = false;
    private long tPMoment = 0;

    private int id;

    public BukkitWorld(World world) {
        this.world = world;

        database = AdminCMD.getDatabaseManager().getDatabase();
        try {
            PreparedStatement ps = database.getPreparedStatement("SELECT * FROM `ac_worlds` WHERE `uuid` = ?;");
            ps.setString(1, world.getUID().toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String spawnLoc = rs.getString("spawnLoc");
                boolean wPaused = rs.getBoolean("wPaused");
                String wPMoment = rs.getString("wPMoment");
                boolean tPaused = rs.getBoolean("tPaused");
                long tPMoment = rs.getLong("tPMoment");
                // TODO Get information
                int id = rs.getInt("id");

                this.spawnLoc = spawnLoc;
                this.wPaused = wPaused;
                this.wPMoment = Weather.get(wPMoment);
                this.tPaused = tPaused;
                this.tPMoment = tPMoment;
                // TODO Set information
                this.id = id;
            } else {
                createWorld();
            }
            database.closeResultSet(rs);
            database.closeStatement(ps);
        } catch (SQLException e) {

        }
    }

    private void createWorld() {
        try {
            PreparedStatement ps = database.getPreparedStatement("INSERT INTO `ac_worlds` (`uuid`, `name`, `spawnLoc`, `wPaused`, `wPMoment`, `tPaused`, `tPMoment`) VALUES (?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, world.getUID().toString());
            ps.setString(2, world.getName());
            ps.setString(3, this.spawnLoc);
            ps.setBoolean(4, this.wPaused);
            ps.setString(5, this.wPMoment.getName());
            ps.setBoolean(6, this.tPaused);
            ps.setLong(7, this.tPMoment);
            ps.executeUpdate();
            database.closeStatement(ps);
        } catch (SQLException e) {
            DebugLogger.severe("Database world could not be created: " + world.getUID(), e);
        }
    }

    public void update() {
        try {
            PreparedStatement ps = database.getPreparedStatement("UPDATE `ac_worlds` SET `spawnLoc` = ?, `wPaused` = ?, `wPMoment` = ?, `tPaused` = ?, `tPMoment` = ? WHERE `id` = ?;");
            ps.setString(1, this.spawnLoc);
            ps.setBoolean(2, this.wPaused);
            ps.setString(3, this.wPMoment.getName());
            ps.setBoolean(4, this.tPaused);
            ps.setLong(5, this.tPMoment);
            // TODO Set information
            ps.setInt(6, this.id);
            ps.executeUpdate();
            database.closeStatement(ps);
        } catch (SQLException e) {
            DebugLogger.severe("Database world could not be accessed: " + world.getUID(), e);
        }
    }

    @Override
    public UUID getUUID() {
        return world.getUID();
    }

    @Override
    public String getName() {
        return world.getName();
    }

    @Override
    public boolean isRaining() {
        return world.hasStorm();
    }

    @Override
    public void setRaining(boolean raining) {
        world.setStorm(raining);
    }

    @Override
    public boolean isThundering() {
        return world.isThundering();
    }

    @Override
    public void setThundering(boolean thundering) {
        world.setThundering(thundering);
    }

    @Override
    public int getRainTime() {
        return world.getWeatherDuration();
    }

    @Override
    public void setRainTime(int seconds) {
        world.setWeatherDuration(seconds);
    }

    @Override
    public int getThunderTime() {
        return world.getThunderDuration();
    }

    @Override
    public void setThunderTime(int seconds) {
        world.setThunderDuration(seconds);
    }

    @Override
    public long getTime() {
        return world.getTime();
    }

    @Override
    public void setTime(long time) {
        world.setTime(time);
    }

    @Override
    public Location getSpawnLocation() {
        return new BukkitLocation(world.getSpawnLocation());
    }

    @Override
    public void setSpawnLocation(Location loc) {
        world.setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    @Override
    public Weather getWeather() {
        if (isRaining() && isThundering()) {
            return Weather.STORM;
        } else if (isRaining() && !isThundering()) {
            return Weather.RAIN;
        } else {
            return Weather.CLEAR;
        }
    }

    @Override
    public void setWeather(Weather weather) {
        if (weather == Weather.CLEAR) {
            setRaining(false);
            setThundering(false);
        } else if (weather == Weather.RAIN) {
            setRaining(true);
            setThundering(false);
        } else {
            setRaining(true);
            setThundering(true);
        }
    }

    @Override
    public boolean isWeatherPaused() {
        return wPaused;
    }

    @Override
    public Weather getWeatherPaused() {
        return wPMoment;
    }

    @Override
    public void setWeatherPaused(boolean paused) {
        wPaused = paused;
        if (wPaused) {
            wPMoment = getWeather();
        }
    }

    @Override
    public boolean isTimePaused() {
        return tPaused;
    }

    @Override
    public long getTimePaused() {
        return tPMoment;
    }

    @Override
    public void setTimePaused(boolean paused) {
        tPaused = paused;
        if (tPaused) {
            tPMoment = getTime();
        }
    }

    public static World toBukkitWorld(com.admincmd.api.world.World world) {
        return Bukkit.getWorld(world.getUUID());
    }

}
