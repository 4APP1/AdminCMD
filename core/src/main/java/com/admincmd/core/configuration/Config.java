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
package com.admincmd.core.configuration;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.configuration.YAMLConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public enum Config {

    LOCALES_LANGUAGE("locales.language", "en_US", "The file containing all AdminCMD messages"),
    DB_USE("database.use", "sqlite", "The database type to use (Options: sqlite, mysql)"),
    DB_MYSQL_HOST("database.mysql.host", "127.0.0.1", "The MySQL host address"),
    DB_MYSQL_PORT("database.mysql.port", 3306, "The MySQL host port"),
    DB_MYSQL_NAME("database.mysql.name", "admincmd", "The MySQL database name"),
    DB_MYSQL_USER("database.mysql.user", "admin", "The MySQL database username"),
    DB_MYSQL_PASS("database.mysql.pass", "password", "The MySQL database password");

    private String path;
    private Object value;
    private String comment;

    private Config(String path, Object value, String message) {
        this.path = path;
        this.value = value;
        this.comment = message;
    }

    public String getPath() {
        return path;
    }

    public String getString() {
        return config.getString(path);
    }

    public List<String> getStringList() {
        return config.getStringList(path);
    }

    public int getInteger() {
        return config.getInteger(path);
    }

    public Double getDouble() {
        return config.getDouble(path);
    }

    public boolean getBoolean() {
        return config.getBoolean(path);
    }

    public Object getDefaultValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    public void set(Object value, boolean save) {
        config.set(path, value);
        if (save) {
            try {
                config.save();
            } catch (IOException e) {
                // TODO ACLogger message
            }
            reload(false);
        }
    }

    private static final File file = new File(AdminCMD.getDataFolder(), "config.yml");
    private static final YAMLConfiguration config = new YAMLConfiguration(file);

    public static void load() {
        file.mkdirs();
        reload(false);

        String header = "";
        for (Config c : values()) {
            header += c.getPath() + ": " + c.getComment() + System.lineSeparator();
            if (!config.contains(c.getPath())) {
                c.set(c.getDefaultValue(), false);
            }
        }
        config.getOptions().setHeader(header);

        try {
            config.save();
        } catch (IOException e) {
            // TODO ACLogger message
        }
    }

    public static void reload(boolean complete) {
        if (!complete) {
            try {
                config.load();
            } catch (IOException e) {
                // TODO ACLogger message
            }
            return;
        }
        load();
    }

}
