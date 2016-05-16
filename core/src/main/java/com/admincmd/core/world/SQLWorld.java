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
package com.admincmd.core.world;

import com.admincmd.api.database.Database;
import com.admincmd.core.SimpleCore;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLWorld {

    private final Database database;

    private final UUID uuid;
    private final String name;

    protected String spawnLoc;
    protected boolean weatherPaused;
    protected String weatherPausedMoment = "";
    protected boolean timePaused;
    protected long timePausedMoment = 0;

    private int id;

    public SQLWorld(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;

        database = SimpleCore.getDatabaseManager().getDatabase();
        try {
            PreparedStatement ps = database.getPreparedStatement("SELECT * FROM 'ac_worlds' WHERE 'uuid' = ?;");
            ps.setString(1, uuid.toString());
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
                this.weatherPaused = wPaused;
                this.weatherPausedMoment = wPMoment;
                this.timePaused = tPaused;
                this.timePausedMoment = tPMoment;
                // TODO Set information
                this.id = id;
            } else {
                createWorld();
            }
            database.closeResultSet(rs);
            database.closeStatement(ps);
        } catch (SQLException e) {
            // TODO ACLogger message
        }
    }

    private void createWorld() {
        try {
            PreparedStatement ps = database.getPreparedStatement("INSERT INTO 'ac_worlds' ('uuid', 'name', 'spawnLoc', 'wPaused', 'wPMoment', 'tPaused', 'tPMoment') VALUES (?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ps.setString(3, this.spawnLoc);
            ps.setBoolean(4, this.weatherPaused);
            ps.setString(5, this.weatherPausedMoment);
            ps.setBoolean(6, this.timePaused);
            ps.setLong(7, this.timePausedMoment);
            ps.executeUpdate();
            database.closeStatement(ps);
        } catch (SQLException e) {
            // TODO ACLogger message
        }
    }

    public void update() {
        try {
            PreparedStatement ps = database.getPreparedStatement("UPDATE 'ac_worlds' SET 'spawnLoc' = ?, 'wPaused' = ?, 'wPMoment' = ?, 'tPaused' = ?, 'tPMoment' = ? WHERE 'id' = ?;");
            ps.setString(1, this.spawnLoc);
            ps.setBoolean(2, this.weatherPaused);
            ps.setString(3, this.weatherPausedMoment);
            ps.setBoolean(4, this.timePaused);
            ps.setLong(5, this.timePausedMoment);
            // TODO Set information
            ps.setInt(6, this.id);
            ps.executeUpdate();
            database.closeStatement(ps);
        } catch (SQLException e) {
            // TODO ACLogger message
        }
    }

    public boolean isWeatherPaused() {
        return weatherPaused;
    }

    public String getWeatherPaused() {
        return weatherPausedMoment;
    }

    public boolean isTimePaused() {
        return timePaused;
    }

    public long getTimePaused() {
        return timePausedMoment;
    }

}
