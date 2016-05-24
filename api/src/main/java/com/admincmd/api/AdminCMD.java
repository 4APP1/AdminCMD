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
package com.admincmd.api;

import com.admincmd.api.addon.AddonManager;
import com.admincmd.api.command.CommandManager;
import com.admincmd.api.database.DatabaseManager;
import com.admincmd.api.event.EventManager;
import com.admincmd.api.file.FileManager;

public class AdminCMD {

    private static Module INSTANCE;

    public static void initialize(Module module) {
        if (INSTANCE == null) {
            INSTANCE = module;
        }
    }

    private static Module getModule() {
        return INSTANCE;
    }

    public static AddonManager getAddonManager() {
        return getModule().getAddonManager();
    }

    public static CommandManager getCommandManager() {
        return getModule().getCommandManager();
    }

    public static DatabaseManager getDatabaseManager() {
        return getModule().getDatabaseManager();
    }

    public static EventManager getEventManager() {
        return getModule().getEventManager();
    }

    public static FileManager getFileManager() {
        return getModule().getFileManager();
    }

    public static Registry getRegistry() {
        return getModule().getPluginRegistry();
    }

    public static Server getServer() {
        return getModule().getPluginServer();
    }

}
