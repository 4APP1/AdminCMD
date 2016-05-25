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
package com.admincmd.sponge.entity;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.world.Location;
import com.admincmd.api.world.World;
import com.admincmd.sponge.world.SpongeLocation;
import org.spongepowered.api.entity.Entity;

import java.util.UUID;

public class SpongeEntity implements com.admincmd.api.entity.Entity {

    private Entity entity;

    public SpongeEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public UUID getUUID() {
        return entity.getUniqueId();
    }

    @Override
    public World getWorld() {
        return AdminCMD.getServer().getWorld(entity.getWorld().getUniqueId());
    }

    @Override
    public Location getLocation() {
        return new SpongeLocation(entity.getLocation());
    }

    @Override
    public void teleport(Location location) {
        entity.setLocationAndRotation(SpongeLocation.toSpongeLocation(location),
                SpongeLocation.toSpongeRotation(location));
    }

    @Override
    public void teleport(com.admincmd.api.entity.Entity entity) {
        this.entity.setLocationAndRotation(SpongeLocation.toSpongeLocation(entity.getLocation()),
                SpongeLocation.toSpongeRotation(entity.getLocation()));
    }

}
