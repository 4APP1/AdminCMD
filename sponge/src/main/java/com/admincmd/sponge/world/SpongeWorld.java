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
package com.admincmd.sponge.world;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.database.Database;
import com.admincmd.api.util.logger.DebugLogger;
import com.admincmd.api.world.Location;
import com.admincmd.api.world.Weather;
import com.admincmd.core.util.tasks.TimeReset;
import com.admincmd.core.util.tasks.WeatherReset;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.World;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SpongeWorld implements com.admincmd.api.world.World {

    private final World world;

    private final Database database;

    private String spawnLoc;
    private boolean wPaused;
    private Weather wPMoment;
    private boolean tPaused;
    private long tPMoment;

    private int id;

    public SpongeWorld(World world) {
        this.world = world;

        database = AdminCMD.getDatabaseManager().getDatabase();
        try {
            PreparedStatement ps = database.getPreparedStatement("SELECT * FROM `ac_worlds` WHERE `uuid` = ?;");
            ps.setString(1, world.getUniqueId().toString());
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

        AdminCMD.getServer().scheduleSyncRepeatingTask(new WeatherReset(this), 20 * 3, 20 * 3);
        AdminCMD.getServer().scheduleSyncRepeatingTask(new TimeReset(this), 20 * 3, 20 * 3);
    }

    private void createWorld() {
        try {
            PreparedStatement ps = database.getPreparedStatement("INSERT INTO `ac_worlds` (`uuid`, `name`, `spawnLoc`, `wPaused`, `wPMoment`, `tPaused`, `tPMoment`) VALUES (?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, world.getUniqueId().toString());
            ps.setString(2, world.getName());
            ps.setString(3, this.spawnLoc);
            ps.setBoolean(4, this.wPaused);
            ps.setString(5, this.wPMoment.getName());
            ps.setBoolean(6, this.tPaused);
            ps.setLong(7, this.tPMoment);
            ps.executeUpdate();
            database.closeStatement(ps);
        } catch (SQLException e) {
            DebugLogger.severe("Database world could not be created: " + world.getUniqueId(), e);
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
            DebugLogger.severe("Database world could not be accessed: " + world.getUniqueId(), e);
        }
    }

    @Override
    public UUID getUUID() {
        return world.getUniqueId();
    }

    @Override
    public String getName() {
        return world.getName();
    }

    @Override
    public boolean isRaining() {
        return world.getProperties().isRaining();
    }

    @Override
    public void setRaining(boolean raining) {
        world.getProperties().setRaining(raining);
    }

    @Override
    public boolean isThundering() {
        return world.getProperties().isThundering();
    }

    @Override
    public void setThundering(boolean thundering) {
        world.getProperties().setThundering(thundering);
    }

    @Override
    public int getRainTime() {
        return world.getProperties().getRainTime();
    }

    @Override
    public void setRainTime(int seconds) {
        world.getProperties().setRainTime(seconds);
    }

    @Override
    public int getThunderTime() {
        return world.getProperties().getThunderTime();
    }

    @Override
    public void setThunderTime(int seconds) {
        world.getProperties().setThunderTime(seconds);
    }

    @Override
    public long getTime() {
        return world.getProperties().getWorldTime();
    }

    @Override
    public void setTime(long time) {
        world.getProperties().setWorldTime(time);
    }

    @Override
    public Location getSpawnLocation() {
        return new Location(AdminCMD.getServer().getWorld(world.getUniqueId()), world.getSpawnLocation().getX(), world.getSpawnLocation().getY(), world.getSpawnLocation().getZ());
    }

    @Override
    public void setSpawnLocation(Location location) {
        world.getProperties().setSpawnPosition(new Vector3i(location.getX(), location.getY(), location.getZ()));
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

    public static World toSpongeWorld(com.admincmd.api.world.World world) {
        return Sponge.getServer().getWorld(world.getUUID()).get();
    }
}
