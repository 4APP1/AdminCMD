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
import com.admincmd.api.world.Location;
import com.admincmd.core.world.ACWorld;
import org.bukkit.World;

public class BukkitWorld extends ACWorld {

    private World world;

    public BukkitWorld(World world) {
        super(world.getUID(), world.getName());
        this.world = world;
    }

    @Override
    public boolean isRaining() {
        return world.hasStorm();
    }

    @Override
    public void setRaining(boolean raining) {
        world.setStorm(raining);
    }

    @Override
    public boolean isThundering() {
        return world.isThundering();
    }

    @Override
    public void setThundering(boolean thundering) {
        world.setThundering(thundering);
    }

    @Override
    public int getWeatherDuration() {
        return world.getWeatherDuration();
    }

    @Override
    public void setWeatherDuration(int seconds) {
        world.setWeatherDuration(seconds);
    }

    @Override
    public long getTime() {
        return world.getTime();
    }

    @Override
    public void setTime(long time) {
        world.setTime(time);
    }

    @Override
    public Location getSpawnLocation() {
        return new Location(AdminCMD.getServer().getWorld(world.getUID()), world.getSpawnLocation().getX(), world.getSpawnLocation().getY(), world.getSpawnLocation().getZ());
    }

    @Override
    public void setSpawnLocation(Location location) {
        world.setSpawnLocation((int) location.getX(), (int) location.getY(), (int) location.getZ());
    }
}
