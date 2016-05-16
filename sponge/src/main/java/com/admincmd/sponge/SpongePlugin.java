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

import com.admincmd.api.Plugin;
import com.admincmd.api.Registry;
import com.admincmd.api.Server;
import com.admincmd.core.SimpleCore;
import com.admincmd.sponge.command.SpongeCommandManager;
import com.admincmd.sponge.event.SpongeEventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;

import java.io.File;

@org.spongepowered.api.plugin.Plugin(id = "admincmd", name = "AdminCMD")
public class SpongePlugin implements Plugin {

    private SpongeServer server;
    private SpongeRegistry registry;

    private SpongeCommandManager commandManager;
    private SpongeEventManager eventManager;

    @Listener
    public void onEnable(GameStartingServerEvent event) {
        server = new SpongeServer(this);
        registry = new SpongeRegistry(this);

        commandManager = new SpongeCommandManager(this);
        eventManager = new SpongeEventManager(this);

        SimpleCore.enable(this);
    }

    @Listener
    public void onDisable(GameStoppingServerEvent event) {
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

    public SpongeCommandManager getCommandManager() {
        return commandManager;
    }

    public SpongeEventManager getEventManager() {
        return eventManager;
    }

}
