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
package com.admincmd.core.database;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.database.Database;
import com.admincmd.api.database.type.MySQL;
import com.admincmd.api.database.type.SQLite;
import com.admincmd.api.util.logger.DebugLogger;
import com.admincmd.core.ACPlugin;
import com.admincmd.core.configuration.Config;

import java.io.File;
import java.sql.SQLException;

public class DatabaseManager {

    private ACPlugin plugin;

    private final Database database;

    public DatabaseManager(ACPlugin plugin) {
        this.plugin = plugin;

        if (Config.DB_USE.getString().equalsIgnoreCase("mysql")) {
            database = new MySQL(Config.DB_MYSQL_HOST.getString(), Config.DB_MYSQL_PORT.getInteger(),
                    Config.DB_MYSQL_NAME.getString(), Config.DB_MYSQL_USER.getString(), Config.DB_MYSQL_PASS.getString());
        } else {
            database = new SQLite(new File(AdminCMD.getDataFolder(), "admincmd.db"));
        }

        if (database.testConnection()) {
            buildTables();
        } else {
            DebugLogger.severe("Unable to connect to the database");
        }
    }

    public Database getDatabase() {
        return database;
    }

    private void buildTables() {
        String PLAYER_TABLE;
        String WORLD_TABLE;
        String HOMES_TABLE;
        String WARPS_TABLE;
        if (database.getType() == Database.Type.MYSQL) {
            PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS `ac_player` ("
                    + "`id` INTEGER PRIMARY KEY AUTO_INCREMENT,"
                    + "`uuid` varchar(64) NOT NULL,"
                    + "`name` varchar(64) NOT NULL,"
                    + "`hidden` BOOLEAN,"
                    + "`fly` BOOLEAN,"
                    + "`god` BOOLEAN,"
                    + "`nickname` varchar(64) DEFAULT 'none'"
                    + ");";
            WORLD_TABLE = "CREATE TABLE IF NOT EXISTS `ac_worlds` ("
                    + "`uuid` varchar(64) PRIMARY KEY NOT NULL,"
                    + "`name` varchar(64) NOT NULL,"
                    + "`spawnLoc` varchar(64) NOT NULL,"
                    + "`wPaused` BOOLEAN,"
                    + "`wPMoment` INTEGER NOT NULL,"
                    + "`tPaused` BOOLEAN,"
                    + "`tPMoment` INTEGER NOT NULL"
                    + ");";
            HOMES_TABLE = "CREATE TABLE IF NOT EXISTS `ac_homes` ("
                    + "`id` INTEGER PRIMARY KEY AUTO_INCREMENT,"
                    + "`name` varchar(64) NOT NULL,"
                    + "`uuid` varchar(64) NOT NULL,"
                    + "`location` TEXT NOT NULL"
                    + ");";
            WARPS_TABLE = "CREATE TABLE IF NOT EXISTS `ac_warps` ("
                    + "`id` INTEGER PRIMARY KEY AUTO_INCREMENT,"
                    + "`name` varchar(64) NOT NULL,"
                    + "`uuid` varchar(64) NOT NULL,"
                    + "`location` TEXT NOT NULL"
                    + ");";
        } else {
            PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS `ac_players` ("
                    + "`id` INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "`uuid` varchar(64) NOT NULL,"
                    + "`name` varchar(64) NOT NULL,"
                    + "`hidden` BOOLEAN,"
                    + "`fly` BOOLEAN,"
                    + "`god` BOOLEAN,"
                    + "`nickname` varchar(64) DEFAULT 'none'"
                    + ");";
            WORLD_TABLE = "CREATE TABLE IF NOT EXISTS `ac_worlds` ("
                    + "`id` INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "`uuid` varchar(64) NOT NULL,"
                    + "`name` varchar(64) NOT NULL,"
                    + "`spawnLoc` varchar(64) NOT NULL,"
                    + "`wPaused` BOOLEAN,"
                    + "`wPMoment` INTEGER NOT NULL,"
                    + "`tPaused` BOOLEAN,"
                    + "`tPMoment` INTEGER NOT NULL"
                    + ");";
            HOMES_TABLE = "CREATE TABLE IF NOT EXISTS `ac_homes` ("
                    + "`id` INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "`name` varchar(64) NOT NULL,"
                    + "`uuid` varchar(64) NOT NULL,"
                    + "`location` TEXT NOT NULL"
                    + ");";
            WARPS_TABLE = "CREATE TABLE IF NOT EXISTS `ac_warps` ("
                    + "`id` INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "`name` varchar(64) NOT NULL,"
                    + "`uuid` varchar(64) NOT NULL,"
                    + "`location` TEXT NOT NULL"
                    + ");";
        }

        try {
            database.executeStatement(PLAYER_TABLE);
            database.executeStatement(WORLD_TABLE);
            database.executeStatement(HOMES_TABLE);
            database.executeStatement(WARPS_TABLE);
        } catch (SQLException e) {
            DebugLogger.severe("Failed to create database tables", e);
        }
    }

}
