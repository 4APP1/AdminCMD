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

import com.admincmd.api.AdminCMD;
import com.admincmd.api.Identifiable;
import com.admincmd.api.addon.AddonManager;
import com.admincmd.core.addon.ACAddonManager;
import com.admincmd.core.commands.WorldCommands;
import com.admincmd.core.configuration.Config;
import com.admincmd.core.configuration.Locale;
import com.admincmd.core.database.DatabaseManager;

public class ACPlugin implements Identifiable {

    private static ACPlugin INSTANCE;

    private final AddonManager addonManager;
    private final DatabaseManager databaseManager;

    private ACPlugin() {
        Config.load();
        Locale.load();

        addonManager = new ACAddonManager(this);
        databaseManager = new DatabaseManager(this);
    }

    public static ACPlugin getInstance() {
        return INSTANCE;
    }

    public static void enable() {
        if (INSTANCE == null) {
            INSTANCE = new ACPlugin();

            ACServer server = (ACServer) AdminCMD.getServer();
            server.preparePlayers(getDatabaseManager().getDatabase());
            server.prepareWorlds(getDatabaseManager().getDatabase());

            AdminCMD.getCommandManager().registerClass(WorldCommands.class, getInstance());
        }
    }

    public static void disable() {
        if (INSTANCE != null) {

        }
    }

    public static DatabaseManager getDatabaseManager() {
        return getInstance().databaseManager;
    }

    @Override
    public String getModuleId() {
        return AdminCMD.getCore().getModuleId();
    }

    @Override
    public String getModuleName() {
        return AdminCMD.getCore().getModuleName();
    }

}
