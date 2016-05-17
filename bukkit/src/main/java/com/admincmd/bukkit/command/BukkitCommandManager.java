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
import com.admincmd.bukkit.BukkitModule;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BukkitCommandManager extends CommandManager {

    private BukkitModule plugin;

    private CommandMap commandMap;
    private Map<String, BukkitCommand> registeredCommands = new HashMap<>();

    public BukkitCommandManager(BukkitModule plugin) {
        this.plugin = plugin;

        CommandMap map = null;
        try {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            map = (CommandMap) f.get(Bukkit.getServer());
        } catch (NoSuchFieldException e) {
            // TODO ACLogger message
        } catch (IllegalAccessException e) {
            // TODO ACLogger message
        }
        commandMap = map;
    }

    @Override
    public void registerPluginCommand(Command command) {
        if (commandMap.getCommand(command.getPrimaryAlias()) == null) {
            BukkitCommand cmd = new BukkitCommand(command);
            commandMap.register(plugin.getName().toLowerCase(), cmd);
            registeredCommands.put(command.getPrimaryAlias(), cmd);
        } else {
            // TODO ACLogger message
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
                // TODO ACLogger message
            }
        } else {
            // TODO ACLogger message
        }
    }

}
