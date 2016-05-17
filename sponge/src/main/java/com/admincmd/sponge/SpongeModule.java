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
import com.admincmd.api.command.CommandManager;
import com.admincmd.api.event.EventManager;
import com.admincmd.core.ACPlugin;
import com.admincmd.sponge.command.SpongeCommandManager;
import com.admincmd.sponge.event.SpongeEventManager;
import com.google.inject.Inject;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;

import java.io.File;

@org.spongepowered.api.plugin.Plugin(id = "admincmd", name = "AdminCMD")
public class SpongeModule implements Identifiable {

    @Inject
    @ConfigDir(sharedRoot = false)
    private File folder;
    private Server server;
    private Registry registry;
    private CommandManager commandManager;
    private EventManager eventManager;

    private Core core;

    @Listener
    public void onEnable(GameStartingServerEvent event) {
        folder.mkdirs();

        server = new SpongeServer(this);
        registry = new SpongeRegistry(this);
        commandManager = new SpongeCommandManager(this);
        eventManager = new SpongeEventManager(this);

        core = new SpongeCore(this, folder, server, registry, commandManager, eventManager);
        AdminCMD.initialize(core);

        ACPlugin.enable();
    }

    @Listener
    public void onDisable(GameStoppingServerEvent event) {
        ACPlugin.disable();


    }

    @Override
    public String getModuleId() {
        return "admincmd";
    }

    @Override
    public String getModuleName() {
        return "AdminCMD";
    }

}
