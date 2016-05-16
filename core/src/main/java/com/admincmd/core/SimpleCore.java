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

import com.admincmd.api.*;
import com.admincmd.api.command.CommandManager;
import com.admincmd.api.event.EventManager;
import com.admincmd.core.database.DatabaseManager;

import java.io.File;

public class SimpleCore implements Core {

    private static SimpleCore INSTANCE;

    private final Plugin plugin;
    private final Server server;
    private final Registry registry;
    private final File folder;

    private final CommandManager command;
    private final EventManager event;

    private final DatabaseManager databaseManager;

    private SimpleCore(Plugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getPluginServer();
        this.registry = plugin.getPluginRegistry();
        this.folder = plugin.getPluginFolder();

        this.command = new CommandManager(this);
        this.event = new EventManager(this);

        this.databaseManager = new DatabaseManager(this);
    }

    public static void enable(Plugin plugin) {
        if (INSTANCE == null) {
            INSTANCE = new SimpleCore(plugin);

            // Initializes all API components
            AdminCMD.initialize(INSTANCE);

            // TODO AdminCMD features, commands, listeners
        }
    }

    public static void disable() {
        if (INSTANCE != null) {

        }
    }

    public static SimpleCore getCore() {
        return INSTANCE;
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
    public File getDataFolder() {
        return folder;
    }

    @Override
    public CommandManager getCommandManager() {
        return command;
    }

    @Override
    public EventManager getEventManager() {
        return event;
    }

    public static DatabaseManager getDatabaseManager() {
        return getCore().databaseManager;
    }

}
