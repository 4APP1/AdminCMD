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
package com.admincmd.core;

import com.admincmd.api.Server;
import com.admincmd.api.database.Database;
import com.admincmd.api.entity.player.Player;
import com.admincmd.api.world.World;
import com.admincmd.core.entity.player.ACPlayer;
import com.admincmd.core.entity.player.SQLPlayer;
import com.admincmd.core.world.ACWorld;
import com.admincmd.core.world.SQLWorld;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class ACServer implements Server {

    protected Database database;

    private final Map<UUID, SQLPlayer> sqlPlayers = new HashMap<>();
    private final Map<UUID, SQLWorld> sqlWorlds = new HashMap<>();

    public ACServer() {

    }

    public abstract void initialize();

    protected void preparePlayers() {
        sqlPlayers.clear();
        try {
            PreparedStatement ps = database.getPreparedStatement("SELECT * FROM 'ac_players'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String name = rs.getString("name");
                SQLPlayer player = new SQLPlayer(uuid, name);
                sqlPlayers.put(uuid, player);
            }
        } catch (SQLException e) {
            // TODO ACLogger message
        }
    }

    protected void prepareWorlds() {
        sqlWorlds.clear();
        try {
            PreparedStatement ps = database.getPreparedStatement("SELECT * FROM 'ac_worlds'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String name = rs.getString("name");
                SQLWorld world = new SQLWorld(uuid, name);
                sqlWorlds.put(uuid, world);
            }
        } catch (SQLException e) {
            // TODO ACLogger message
        }
    }

    public void insertPlayer(UUID uuid, ACPlayer player) {
        if (!sqlPlayers.containsKey(uuid)) {
            sqlPlayers.put(uuid, player);
            player.update();
        }
    }

    public void insertWorld(UUID uuid, ACWorld world) {
        if (!sqlWorlds.containsKey(uuid)) {
            sqlWorlds.put(uuid, world);
            world.update();
        }
    }

    @Override
    public Player getPlayer(UUID uuid) {
        ACPlayer player = null;
        if (sqlPlayers.containsKey(uuid)) {
            SQLPlayer p = sqlPlayers.get(uuid);
            if (p instanceof ACPlayer) {
                player = (ACPlayer) p;
            }
        }
        return player;
    }

    @Override
    public World getWorld(UUID uuid) {
        ACWorld world = null;
        if (sqlWorlds.containsKey(uuid)) {
            SQLWorld w = sqlWorlds.get(uuid);
            if (w instanceof ACWorld) {
                world = (ACWorld) w;
            }
        }
        return world;
    }

}
