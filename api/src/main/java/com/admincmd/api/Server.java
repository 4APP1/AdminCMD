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
package com.admincmd.api;

import com.admincmd.api.entity.player.Player;
import com.admincmd.api.world.World;

import java.util.Collection;
import java.util.UUID;

public interface Server {

    public Collection<Player> getOnlinePlayers();

    public Player getPlayer(UUID uuid);

    public Player getPlayer(String name);

    public Collection<World> getWorlds();

    public World getWorld(UUID uuid);

    public World getWorld(String name);

    public void runSyncTask(Runnable task);

    public void scheduleSyncRepeatingTask(Runnable task, long delay, long interval);

    public void runAsyncTask(Runnable task);

}
