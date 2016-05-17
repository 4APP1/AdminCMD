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
package com.admincmd.sponge.command;

import com.admincmd.api.command.Command;
import com.admincmd.api.command.CommandManager;
import com.admincmd.sponge.SpongeModule;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SpongeCommandManager extends CommandManager {

    private SpongeModule plugin;

    private Map<String, CommandMapping> commandMappings = new HashMap<>();

    public SpongeCommandManager(SpongeModule plugin) {
        this.plugin = plugin;
    }

    @Override
    public void registerPluginCommand(Command command) {
        SpongeCommand cmd = new SpongeCommand(command);
        Optional<CommandMapping> mapping = Sponge.getCommandManager().register(plugin, cmd, command.getAliasList());
        if (mapping.isPresent()) {
            commandMappings.put(command.getPrimaryAlias(), mapping.get());
        }
    }

    @Override
    public void unregisterPluginCommand(Command command) {
        if (commandMappings.containsKey(command.getPrimaryAlias())) {
            Sponge.getCommandManager().removeMapping(commandMappings.get(command.getPrimaryAlias()));
        }
    }

}
