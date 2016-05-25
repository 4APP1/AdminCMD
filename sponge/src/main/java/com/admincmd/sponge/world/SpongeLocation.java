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
package com.admincmd.sponge.world;

import com.admincmd.api.AdminCMD;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class SpongeLocation extends com.admincmd.api.world.Location {

    private Location location;

    public SpongeLocation(Location location) {
        super(AdminCMD.getServer().getWorld(location.getExtent().getUniqueId()),
                location.getX(), location.getY(), location.getZ());
        this.location = location;
    }

    public static Location<World> toSpongeLocation(com.admincmd.api.world.Location location) {
        World world = SpongeWorld.toSpongeWorld(location.getWorld());
        return new Location<World>(world, location.getX(), location.getY(), location.getZ());
    }

    public static Vector3d toSpongeRotation(com.admincmd.api.world.Location location) {
        return new Vector3d(location.getYaw(), location.getPitch(), location.getRoll());
    }

}
