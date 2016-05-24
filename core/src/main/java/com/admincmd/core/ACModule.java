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
import com.admincmd.api.addon.Addon;
import com.admincmd.api.addon.AddonManager;
import com.admincmd.api.database.DatabaseManager;
import com.admincmd.api.file.FileManager;
import com.admincmd.core.addon.ACAddonManager;
import com.admincmd.core.commands.WorldCommands;
import com.admincmd.core.database.ACDatabaseManager;
import com.admincmd.core.file.ACFileManager;

import java.io.File;

public class ACModule implements Addon {

    private final ClassLoader parent;
    private final File dataFolder;

    private final AddonManager addonManager;
    private final DatabaseManager databaseManager;
    private final FileManager fileManager;

    public ACModule(ClassLoader parent, File dataFolder) {
        this.parent = parent;
        this.dataFolder = dataFolder;

        fileManager = new ACFileManager(this);
        databaseManager = new ACDatabaseManager(this);
        addonManager = new ACAddonManager(this);
    }

    @Override
    public void onEnable() {
        AdminCMD.getCommandManager().registerClass(WorldCommands.class, this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getModId() {
        return ACVersion.PLUGIN_ID;
    }

    @Override
    public String getModName() {
        return ACVersion.PLUGIN_NAME;
    }

    @Override
    public String getModVersion() {
        return ACVersion.PLUGIN_VERSION;
    }

    public AddonManager getAddonManager() {
        return addonManager;
    }

    public ClassLoader getClassLoader() {
        return parent;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

}
