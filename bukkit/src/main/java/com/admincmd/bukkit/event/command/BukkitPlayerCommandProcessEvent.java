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
package com.admincmd.bukkit.event.command;

import com.admincmd.api.command.CommandSource;
import com.admincmd.api.event.command.CommandProcessEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BukkitPlayerCommandProcessEvent implements CommandProcessEvent {

    private PlayerCommandPreprocessEvent event;

    public BukkitPlayerCommandProcessEvent(PlayerCommandPreprocessEvent event) {
        this.event = event;
    }

    @Override
    public CommandSource getSource() {
        return null;
    }

    @Override
    public String getCommand() {
        return getCommand(event.getMessage());
    }

    @Override
    public void setCommand(String command) {
        event.setMessage(command + " " + getArguments());
    }

    @Override
    public String getArguments() {
        return getArguments(event.getMessage());
    }

    @Override
    public void setArguments(String arguments) {
        event.setMessage(getCommand() + " " + arguments);
    }

    @Override
    public boolean isCancelled() {
        return event.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        event.setCancelled(cancelled);
    }

    private String getCommand(String message) {
        String command = null;
        if (message.startsWith("/")) {
            command = message.replaceFirst("/", "");
        }

        if (command != null && command.indexOf(" ") != 0) {
            int pos = command.indexOf(" ");
            command = command.substring(0, pos);
        } else {
            command = null;
        }

        return command;
    }

    private String getArguments(String message) {
        String arguments = message;
        if (message.startsWith("/")) {
            arguments = message.replaceFirst("/", "");
        }

        if (arguments != null && arguments.indexOf(" ") != 0) {
            int pos = arguments.indexOf(" ");
            arguments = arguments.substring(pos + 1);
        } else {
            arguments = null;
        }

        return arguments;
    }

}
