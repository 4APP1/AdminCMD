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
import com.admincmd.api.command.CommandManager;
import com.admincmd.api.event.EventManager;
import com.admincmd.bukkit.command.BukkitCommandManager;
import com.admincmd.bukkit.event.BukkitEventManager;
import com.admincmd.core.ACPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BukkitModule extends JavaPlugin implements Identifiable {

    private File folder;
    private Server server;
    private Registry registry;
    private CommandManager commandManager;
    private EventManager eventManager;

    private Core core;

    @Override
    public void onEnable() {
        folder = this.getDataFolder();
        folder.mkdirs();

        server = new BukkitServer(this);
        registry = new BukkitRegistry(this);
        commandManager = new BukkitCommandManager(this);
        eventManager = new BukkitEventManager(this);

        core = new BukkitCore(this, folder, server, registry, commandManager, eventManager);
        AdminCMD.initialize(core);

        ACPlugin.enable();
    }

    @Override
    public void onDisable() {
        ACPlugin.disable();


    }

    @Override
    public String getModuleId() {
        return getDescription().getName();
    }

    @Override
    public String getModuleName() {
        return getDescription().getFullName();
    }

}
