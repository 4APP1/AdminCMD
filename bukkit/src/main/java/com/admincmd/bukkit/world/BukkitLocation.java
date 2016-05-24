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
package com.admincmd.bukkit.world;

import com.admincmd.api.AdminCMD;
import org.bukkit.Location;

public class BukkitLocation extends com.admincmd.api.world.Location {

    private Location location;

    public BukkitLocation(Location location) {
        super(AdminCMD.getServer().getWorld(location.getWorld().getUID()),
                location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.location = location;
    }

}
