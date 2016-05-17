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

public enum Locale {

    RESULT_NOTFOUND_PLAYER("commandResult.notFound.player", "No such player: %player%"),
    RESULT_NOTFOUND_WORLD("commandResult.notFound.world", "No such world: %world%"),
    RESULT_NOTFOUND_ITEM("commandResult.notFound.item", "No such item: %item%"),
    RESULT_NOTFOUND_KIT("commandResult.notFound.kit", "No such kit: %kit%");

    private String path;
    private String message;

    private Locale(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return (config.contains(path) ? config.getString(path) : message);
    }

    public String getDefaultMessage() {
        return message;
    }

    public void set(String value, boolean save) {
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

    private static final File file = new File(AdminCMD.getDataFolder(), "locales" + File.separator + Config.LOCALES_LANGUAGE.getString() + ".yml");
    private static final YAMLConfiguration config = new YAMLConfiguration(file);

    public static void load() {
        file.getParentFile().mkdirs();
        reload(false);

        for (Locale l : values()) {
            if (!config.contains(l.getPath())) {
                l.set(l.getDefaultMessage(), false);
            }
        }

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
