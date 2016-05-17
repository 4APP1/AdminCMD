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

import com.admincmd.api.Core;
import com.admincmd.api.Registry;
import com.admincmd.api.Server;
import com.admincmd.api.addon.AddonManager;
import com.admincmd.api.command.CommandManager;
import com.admincmd.api.event.EventManager;

import java.io.File;

public class SpongeCore implements Core {

    private SpongeModule plugin;

    private final File folder;
    private final Server server;
    private final Registry registry;
    private final CommandManager commandManager;
    private final EventManager eventManager;

    public SpongeCore(SpongeModule plugin, File folder, Server server, Registry registry, CommandManager commandManager, EventManager eventManager) {
        this.plugin = plugin;
        this.folder = folder;
        this.server = server;
        this.registry = registry;
        this.commandManager = commandManager;
        this.eventManager = eventManager;
    }

    @Override
    public File getDataFolder() {
        return folder;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public Registry getRegistry() {
        return registry;
    }

    @Override
    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public String getModuleId() {
        return plugin.getModuleId();
    }

    @Override
    public String getModuleName() {
        return plugin.getModuleName();
    }

}
