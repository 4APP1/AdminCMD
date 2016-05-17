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
package com.admincmd.bukkit.event;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.event.command.CommandProcessEvent;
import com.admincmd.bukkit.BukkitModule;
import com.admincmd.bukkit.event.command.BukkitPlayerCommandProcessEvent;
import com.admincmd.bukkit.event.command.BukkitServerCommandProcessEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class BukkitEventListener extends BukkitListener {

    private BukkitModule plugin;
    private BukkitEventManager manager;

    public BukkitEventListener(BukkitModule plugin, BukkitEventManager manager) {
        super(plugin, manager);
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerCommandProcess(PlayerCommandPreprocessEvent event) {
        if (manager.isEventRegistered(CommandProcessEvent.class)) {
            AdminCMD.getEventManager().callEvent(new BukkitPlayerCommandProcessEvent(event));
        }
    }

    @EventHandler
    public void onServerCommandProcess(ServerCommandEvent event) {
        if (manager.isEventRegistered(CommandProcessEvent.class)) {
            AdminCMD.getEventManager().callEvent(new BukkitServerCommandProcessEvent(event));
        }
    }

}
