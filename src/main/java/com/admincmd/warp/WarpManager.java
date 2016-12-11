/*
 * This file is part of AdminCMD
 * Copyright (C) 2017 AdminCMD Team
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
package com.admincmd.warp;

import com.admincmd.home.*;
import com.admincmd.database.DatabaseFactory;
import com.admincmd.player.BukkitPlayer;
import com.admincmd.player.PlayerManager;
import com.admincmd.utils.ACLogger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import org.bukkit.Location;

public class WarpManager {
    
    private static final HashMap<String, Warp> warps = new HashMap<>();
    
    public static Warp getWarp(String name) {
        if (!warps.containsKey(name)) {
            return null;
        }
        
        return warps.get(name);
    }
    
    public static void deleteWarp(Warp w) {
        if (warps.containsKey(w.getName())) {
            warps.remove(w.getName());
            warps.put(w.getName(), null);
        }
    }
    
    public static HashMap<String, Warp> getWarps() {
        return warps;
    }
    
    public static void createWarp(Location target, String name) {
        Warp w = new Warp(target, name);
        warps.put(name, w);
    }
    
    public static void init() {
        try {
            Statement s = DatabaseFactory.getDatabase().getStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `ac_warps`");
            int loaded = 0;
            
            while (rs.next()) {
                Warp w = new Warp(rs.getString("location"), rs.getString("name"), rs.getInt("id"));
                
                warps.put(w.getName(), w);
                loaded++;
            }
            
            DatabaseFactory.getDatabase().closeStatement(s);
            DatabaseFactory.getDatabase().closeResultSet(rs);
            ACLogger.info("Loaded " + loaded + " warps!");
        } catch (SQLException ex) {
            ACLogger.severe("Error loading the warps", ex);
        }
    }
    
    public static void save() {
        if (warps.isEmpty()) {
            return;
        }
        int saved = 0;
        for (String name : warps.keySet()) {
            saved++;
            Warp w = warps.get(name);
            if (w == null) {
                try {
                    PreparedStatement s = DatabaseFactory.getDatabase().getPreparedStatement("DELETE FROM `ac_warps` WHERE `name` = ?;");
                    s.setString(1, name);
                    s.executeUpdate();
                    DatabaseFactory.getDatabase().closeStatement(s);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                continue;
            }
            
            try {
                PreparedStatement s = DatabaseFactory.getDatabase().getPreparedStatement("SELECT `id` FROM `ac_warps` WHERE `id` = ? LIMIT 1;");
                s.setInt(1, w.getID());
                ResultSet rs = s.executeQuery();
                
                if (rs.next()) {
                    PreparedStatement sta = DatabaseFactory.getDatabase().getPreparedStatement("UPDATE `ac_warps` SET `location` = ? WHERE `id` = ?;");
                    sta.setString(1, w.getSerializedLocation());
                    sta.setInt(2, w.getID());
                    sta.executeUpdate();
                    DatabaseFactory.getDatabase().closeStatement(sta);
                } else {
                    PreparedStatement sta = DatabaseFactory.getDatabase().getPreparedStatement("INSERT INTO `ac_warps` (`location`, `name`) VALUES (?, ?);");
                    sta.setString(1, w.getSerializedLocation());
                    sta.setString(2, w.getName());
                    sta.executeUpdate();
                    DatabaseFactory.getDatabase().closeStatement(sta);
                }
                
                DatabaseFactory.getDatabase().closeResultSet(rs);
                DatabaseFactory.getDatabase().closeStatement(s);
            } catch (SQLException ex) {
                ACLogger.severe("Error saving warps!", ex);
            }
        }
        warps.clear();
        ACLogger.info("Saved " + saved + " homes!");
    }
    
}
