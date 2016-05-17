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

import com.admincmd.api.command.parsing.Arguments;
import com.admincmd.api.util.logger.DebugLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Command {

    private List<String> aliasList;
    private String aliases;
    private String permission;
    private String description;
    private String help;
    private String usage;

    private Method method;

    public Command(String aliases, String permission, String description, String help, String usage) {
        this(aliases, permission, description, help, usage, null);
    }

    public Command(String aliases, String permission, String description, String help, String usage, Method method) {
        this.aliases = aliases;
        this.aliasList = Arrays.asList(aliases.split(" "));
        this.permission = permission;
        this.description = description;
        this.help = help;
        this.usage = usage;
        this.method = method;
    }

    public CommandResult execute(CommandSource source, Arguments args) {
        CommandResult result = null;
        if (method != null) {
            try {
                Object o = method.getDeclaringClass().newInstance();
                result = (CommandResult) method.invoke(o, source, args);
            } catch (IllegalAccessException e) {
                DebugLogger.severe("Command failed to be accessed: " + getPrimaryAlias(), e);
            } catch (InstantiationException e) {
                DebugLogger.severe("Command failed to be instantiated: " + getPrimaryAlias(), e);
            } catch (InvocationTargetException e) {
                DebugLogger.severe("Command failed to be invoked: " + getPrimaryAlias(), e);
            }
        }
        return result;
    }

    public String getAliases() {
        return aliases;
    }

    public List<String> getAliasList() {
        return aliasList;
    }

    public String getPrimaryAlias() {
        List<String> aliases = getAliasList();
        return aliases.get(0);
    }

    public List<String> getSecondaryAliases() {
        List<String> aliases = getAliasList();
        return aliases.subList(1, aliases.size() - 1);
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public String getHelp() {
        return help;
    }

    public String getUsage() {
        return usage;
    }

}
