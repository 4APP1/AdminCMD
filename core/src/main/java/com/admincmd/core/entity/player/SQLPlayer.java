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
package com.admincmd.core.entity.player;

import com.admincmd.api.database.Database;
import com.admincmd.core.ACPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLPlayer {

    private final Database database;

    private final UUID uuid;
    private final String name;

    private boolean hidden = false;
    private boolean fly = false;
    private boolean god = false;
    private String nickname = "";

    private int id;

    public SQLPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;

        database = ACPlugin.getDatabaseManager().getDatabase(); // TODO AdminCMD.getDatabaseManager()
        try {
            PreparedStatement ps = database.getPreparedStatement("SELECT * FROM 'ac_players' WHERE 'uuid' = ?;");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolean hidden = rs.getBoolean("hidden");
                boolean fly = rs.getBoolean("fly");
                boolean god = rs.getBoolean("god");
                String nickname = rs.getString("nickname");
                // TODO Get information
                int id = rs.getInt("id");

                this.hidden = hidden;
                this.fly = fly;
                this.god = god;
                this.nickname = nickname;
                // TODO Set information
                this.id = id;
            } else {
                createPlayer();
            }
            database.closeResultSet(rs);
            database.closeStatement(ps);
        } catch (SQLException e) {
            // TODO ACLogger message
        }
    }

    private void createPlayer() {
        try {
            PreparedStatement ps = database.getPreparedStatement("INSERT INTO 'ac_players' ('uuid', 'name', 'hidden', 'fly', 'god', 'nickname') VALUES (?, ?, ?, ?, ?, ?);");
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ps.setBoolean(3, this.hidden);
            ps.setBoolean(4, this.fly);
            ps.setBoolean(5, this.god);
            ps.setString(6, this.nickname);
            ps.executeUpdate();
            database.closeStatement(ps);
        } catch (SQLException e) {
            // TODO ACLogger message
        }
    }

    public void update() {
        try {
            PreparedStatement ps = database.getPreparedStatement("UPDATE 'ac_players' SET 'hidden' = ?, 'fly' = ?, 'god' = ?, 'nickname' = ? WHERE 'id' = ?;");
            ps.setBoolean(1, this.hidden);
            ps.setBoolean(2, this.fly);
            ps.setBoolean(3, this.god);
            ps.setString(4, this.nickname);
            // TODO Set information
            ps.setInt(4, this.id);
            ps.executeUpdate();
            database.closeStatement(ps);
        } catch (SQLException e) {
            // TODO ACLogger message
        }
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isFly() {
        return fly;
    }

    public void setFly(boolean fly) {
        this.fly = fly;
    }

    public boolean isGod() {
        return god;
    }

    public void setGod(boolean god) {
        this.god = god;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
