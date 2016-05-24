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
package com.admincmd.api.command;

import com.admincmd.api.Identifiable;
import com.admincmd.api.command.parsing.Arguments;
import com.admincmd.api.util.message.Messager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandManager {

    private final Map<Identifiable, List<Command>> commandMap = new HashMap<>();

    public void registerClass(Class<?> clazz, Identifiable identifiable) {
        if (identifiable == null || clazz == null) {
            return;
        }

        if (!commandMap.containsKey(identifiable)) {
            commandMap.put(identifiable, new ArrayList<>());
        }

        List<Command> commands = commandMap.get(identifiable);
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(CommandHandler.class)) {
                CommandHandler handler = m.getAnnotation(CommandHandler.class);

                String aliases = handler.aliases();
                String permission = handler.permission();
                String description = handler.description();
                String help = handler.help();
                String usage = handler.usage();
                Command command = new Command(aliases, permission, description, help, usage, m);

                if (!commands.contains(command)) {
                    registerPluginCommand(command);
                    commands.add(command);
                }
            }
        }

        commandMap.put(identifiable, commands);
    }

    public void registerCommand(Command command, Identifiable identifiable) {
        if (identifiable == null || command == null) {
            return;
        }

        if (!commandMap.containsKey(identifiable)) {
            commandMap.put(identifiable, new ArrayList<>());
        }

        List<Command> commands = commandMap.get(identifiable);
        if (!commands.contains(command)) {
            registerPluginCommand(command);
            commands.add(command);
        }

        commandMap.put(identifiable, commands);
    }

    public void unregisterAll(Identifiable identifiable) {
        if (identifiable == null) {
            return;
        }

        if (commandMap.containsKey(identifiable)) {
            List<Command> commands = commandMap.get(identifiable);
            for (Command c : commands) {
                unregisterPluginCommand(c);
            }

            commandMap.remove(identifiable);
        }
    }

    public abstract void registerPluginCommand(Command command);

    public abstract void unregisterPluginCommand(Command command);

    public void callCommand(Command command, CommandSource source, Arguments args) {
        CommandResult result = null;

        if (!source.hasPermission(command.getPermission())) {
            result = CommandResult.NO_PERMISSIONS;
        } else {
            // TODO Check help argument
            result = command.execute(source, args);
        }

        if (result != null && result.getMessage() != null) {
            Messager.sendMessage(result.getMessage(), source);
        }
    }

}
