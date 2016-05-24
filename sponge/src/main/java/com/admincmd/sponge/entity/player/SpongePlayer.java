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
package com.admincmd.sponge.entity.player;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.database.Database;
import com.admincmd.api.util.logger.DebugLogger;
import com.admincmd.api.world.Location;
import com.admincmd.api.world.World;
import com.admincmd.sponge.world.SpongeLocation;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SpongePlayer implements com.admincmd.api.entity.player.Player {

    private final Player player;

    private final Database database;

    private boolean hidden = false;
    private boolean fly = false;
    private boolean god = false;
    private String nickname = "";

    private int id;

    public SpongePlayer(Player player) {
        this.player = player;

        database = AdminCMD.getDatabaseManager().getDatabase();
        try {
            PreparedStatement ps = database.getPreparedStatement("SELECT * FROM `ac_players` WHERE `uuid` = ?;");
            ps.setString(1, player.getUniqueId().toString());
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
            DebugLogger.severe("Database player could not be loaded: " + getUUID(), e);
        }
    }

    private void createPlayer() {
        try {
            PreparedStatement ps = database.getPreparedStatement("INSERT INTO `ac_players` (`uuid`, `name`, `hidden`, `fly`, `god`, `nickname`) VALUES (?, ?, ?, ?, ?, ?);");
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, player.getName());
            ps.setBoolean(3, this.hidden);
            ps.setBoolean(4, this.fly);
            ps.setBoolean(5, this.god);
            ps.setString(6, this.nickname);
            ps.executeUpdate();
            database.closeStatement(ps);
        } catch (SQLException e) {
            DebugLogger.severe("Database player could not be created: " + getUUID(), e);
        }
    }

    public void update() {
        try {
            PreparedStatement ps = database.getPreparedStatement("UPDATE `ac_players` SET `hidden` = ?, `fly` = ?, `god` = ?, `nickname` = ? WHERE `id` = ?;");
            ps.setBoolean(1, this.hidden);
            ps.setBoolean(2, this.fly);
            ps.setBoolean(3, this.god);
            ps.setString(4, this.nickname);
            // TODO Set information
            ps.setInt(4, this.id);
            ps.executeUpdate();
            database.closeStatement(ps);
        } catch (SQLException e) {
            DebugLogger.severe("Database player could not be accessed: " + getUUID(), e);
        }
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        Text text = Text.of(message);
        player.sendMessage(text);
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public World getWorld() {
        return AdminCMD.getServer().getWorld(player.getWorld().getUniqueId());
    }

    @Override
    public Location getLocation() {
        return new SpongeLocation(player.getLocation());
    }

    @Override
    public String getName() {
        return player.getName();
    }

}
