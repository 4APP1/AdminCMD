/*
 * This file is part of AdminCMD
 * Copyright (C) 2017 AdminCMD Team
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
package com.admincmd.warp;

import com.admincmd.utils.LocationSerialization;
import com.admincmd.world.ACWorld;
import com.admincmd.world.WorldManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warp {

    private Location loc;
    private final String name;
    private final int id;

    public Warp(Location loc, String name) {
        this.loc = loc;
        this.name = name;
        this.id = -1;
    }

    public Warp(String serializedLocation, String name, int id) {
        this.loc = LocationSerialization.deserialLocation(serializedLocation);
        this.name = name;
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public Location getLocation() {
        return loc;
    }

    public void updateLocation(Location loc) {
        this.loc = loc;
    }

    public void updateLocation(String serializedLocation) {
        this.loc = LocationSerialization.deserialLocation(serializedLocation);
    }

    public String getSerializedLocation() {
        return LocationSerialization.serialLocation(loc);
    }

    public String getName() {
        return name;
    }

    public ACWorld getWorld() {
        return WorldManager.getWorld(loc);
    }

    public void teleport(Player p) {
        if (p != null) {
            p.teleport(loc);
        }
    }

}
