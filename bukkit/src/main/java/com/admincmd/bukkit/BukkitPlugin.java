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

import com.admincmd.api.Plugin;
import com.admincmd.api.Registry;
import com.admincmd.api.Server;
import com.admincmd.core.SimpleCore;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BukkitPlugin extends JavaPlugin implements Plugin {

    private BukkitServer server;
    private BukkitRegistry registry;

    @Override
    public void onEnable() {
        server = new BukkitServer(this);
        registry = new BukkitRegistry(this);

        SimpleCore.enable(this);
    }

    @Override
    public void onDisable() {
        SimpleCore.disable();

    }

    @Override
    public Server getPluginServer() {
        return server;
    }

    @Override
    public Registry getPluginRegistry() {
        return registry;
    }

    @Override
    public File getPluginFolder() {
        return null;
    }

}
