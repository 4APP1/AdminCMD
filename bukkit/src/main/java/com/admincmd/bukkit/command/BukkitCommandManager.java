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
package com.admincmd.bukkit.command;

import com.admincmd.api.command.Command;
import com.admincmd.api.command.CommandManager;
import com.admincmd.api.util.logger.DebugLogger;
import com.admincmd.bukkit.BukkitModule;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BukkitCommandManager extends CommandManager {

    private BukkitModule module;

    private CommandMap commandMap;
    private Map<String, BukkitCommand> registeredCommands = new HashMap<>();

    public BukkitCommandManager(BukkitModule module) {
        this.module = module;

        CommandMap map = null;
        try {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            map = (CommandMap) f.get(Bukkit.getServer());
        } catch (NoSuchFieldException e) {
            DebugLogger.severe("CommandMap could not be found", e);
        } catch (IllegalAccessException e) {
            DebugLogger.severe("CommandMap could not be access", e);
        }
        commandMap = map;
    }

    @Override
    public void registerPluginCommand(Command command) {
        if (commandMap.getCommand(command.getPrimaryAlias()) == null) {
            BukkitCommand cmd = new BukkitCommand(command);
            commandMap.register(module.getName().toLowerCase(), cmd);
            registeredCommands.put(command.getPrimaryAlias(), cmd);
        } else {
            DebugLogger.warn("Command is already registered: " + command.getPrimaryAlias() + "! Skipping it...");
        }
    }

    @Override
    public void unregisterPluginCommand(Command command) {
        if (commandMap.getCommand(command.getPrimaryAlias()) != null) {
            if (registeredCommands.containsKey(command.getPrimaryAlias())) {
                BukkitCommand cmd = registeredCommands.get(command.getPrimaryAlias());
                cmd.unregister(commandMap);
                registeredCommands.remove(command.getPrimaryAlias());
            } else {
                DebugLogger.warn("Command is not recognized: " + command.getPrimaryAlias() + "! Skipping it...");
            }
        } else {
            DebugLogger.warn("Command is not registered: " + command.getPrimaryAlias() + "! Skipping it...");
        }
    }

}
