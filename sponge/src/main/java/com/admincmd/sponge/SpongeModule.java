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
package com.admincmd.sponge;

import com.admincmd.api.*;
import com.admincmd.api.addon.AddonManager;
import com.admincmd.api.command.CommandManager;
import com.admincmd.api.database.DatabaseManager;
import com.admincmd.api.event.EventManager;
import com.admincmd.api.file.FileManager;
import com.admincmd.core.ACModule;
import com.admincmd.core.ACVersion;
import com.admincmd.sponge.command.SpongeCommandManager;
import com.admincmd.sponge.event.SpongeEventManager;
import com.google.inject.Inject;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;

@Plugin(id = ACVersion.PLUGIN_ID, name = ACVersion.PLUGIN_NAME, version = ACVersion.PLUGIN_VERSION)
public class SpongeModule implements Module {

    @Inject
    @ConfigDir(sharedRoot = false)
    private File dataFolder;

    private ACModule coreModule;
    private CommandManager commandManager;
    private EventManager eventManager;
    private Registry pluginRegistry;
    private Server pluginServer;

    @Listener
    public void onEnable(GameStartingServerEvent event) {
        dataFolder.mkdirs();
        coreModule = new ACModule(getClass().getClassLoader(), dataFolder);

        commandManager = new SpongeCommandManager(this);
        eventManager = new SpongeEventManager(this);
        pluginRegistry = new SpongeRegistry(this);
        pluginServer = new SpongeServer(this);

        AdminCMD.initialize(this);

        coreModule.onEnable();
    }

    @Listener
    public void onDisable(GameStoppingServerEvent event) {
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
