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
package com.admincmd.bukkit.entity.player;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.world.Location;
import com.admincmd.api.world.World;
import com.admincmd.core.entity.player.ACPlayer;
import org.bukkit.entity.Player;

public class BukkitPlayer extends ACPlayer {

    private Player player;

    public BukkitPlayer(Player player) {
        super(player.getUniqueId(), player.getName());
        this.player = player;
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public World getWorld() {
        return AdminCMD.getServer().getWorld(player.getWorld().getUID());
    }

    @Override
    public Location getLocation() {
        World world = getWorld();
        org.bukkit.Location loc = player.getLocation();
        return new Location(world, loc.getX(), loc.getY(), loc.getZ());
    }

}
