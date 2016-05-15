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
package com.admincmd.sponge.event.command;

import com.admincmd.api.command.CommandSource;
import com.admincmd.api.event.command.CommandProcessEvent;
import org.spongepowered.api.event.command.SendCommandEvent;

public class SpongeCommandProcessEvent implements CommandProcessEvent {

    private SendCommandEvent event;

    public SpongeCommandProcessEvent(SendCommandEvent event) {
        this.event = event;
    }

    @Override
    public CommandSource getSource() {
        return null;
    }

    @Override
    public String getCommand() {
        return event.getCommand();
    }

    @Override
    public void setCommand(String command) {
        event.setCommand(command);
    }

    @Override
    public String getArguments() {
        return event.getArguments();
    }

    @Override
    public void setArguments(String arguments) {
        event.setArguments(arguments);
    }

    @Override
    public boolean isCancelled() {
        return event.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        event.setCancelled(cancelled);
    }

}
