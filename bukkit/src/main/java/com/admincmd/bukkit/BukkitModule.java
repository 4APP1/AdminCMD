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
package com.admincmd.bukkit;

import com.admincmd.api.*;
import com.admincmd.api.addon.AddonManager;
import com.admincmd.api.command.CommandManager;
import com.admincmd.api.database.DatabaseManager;
import com.admincmd.api.event.EventManager;
import com.admincmd.api.file.FileManager;
import com.admincmd.bukkit.command.BukkitCommandManager;
import com.admincmd.bukkit.event.BukkitEventManager;
import com.admincmd.core.ACModule;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BukkitModule extends JavaPlugin implements Module {

    private File dataFolder = getDataFolder();

    private ACModule coreModule;
    private CommandManager commandManager;
    private EventManager eventManager;
    private Registry pluginRegistry;
    private Server pluginServer;

    @Override
    public void onEnable() {
        dataFolder.mkdirs();
        coreModule = new ACModule(getClass().getClassLoader(), dataFolder);

        commandManager = new BukkitCommandManager(this);
        eventManager = new BukkitEventManager(this);
        pluginRegistry = new BukkitRegistry(this);
        pluginServer = new BukkitServer(this);

        AdminCMD.initialize(this);

        coreModule.onEnable();
    }

    @Override
    public void onDisable() {
        coreModule.onDisable();


    }

    @Override
    public AddonManager getAddonManager() {
        return coreModule.getAddonManager();
    }

    @Override
    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return coreModule.getDatabaseManager();
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public FileManager getFileManager() {
        return coreModule.getFileManager();
    }

    @Override
    public Registry getPluginRegistry() {
        return pluginRegistry;
    }

    @Override
    public Server getPluginServer() {
        return pluginServer;
    }

}
