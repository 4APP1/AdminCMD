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
package com.admincmd.bukkit;

import com.admincmd.api.entity.player.Player;
import com.admincmd.api.util.logger.DebugLogger;
import com.admincmd.api.world.World;
import com.admincmd.bukkit.entity.player.BukkitPlayer;
import com.admincmd.bukkit.world.BukkitWorld;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.util.*;

public class BukkitServer implements com.admincmd.api.Server {

    private BukkitModule module;
    private Server bukkit;

    private Map<UUID, Player> players = new HashMap<>();
    private Map<UUID, World> worlds = new HashMap<>();

    public BukkitServer(BukkitModule module) {
        this.module = module;
        this.bukkit = Bukkit.getServer();
    }

    @Override
    public Collection<Player> getOnlinePlayers() {
        players.clear();
        for (org.bukkit.entity.Player p : bukkit.getOnlinePlayers()) {
            if (!players.containsKey(p.getUniqueId())) {
                players.put(p.getUniqueId(), new BukkitPlayer(p));
            }
        }
        return players.values();
    }

    @Override
    public Player getPlayer(UUID uuid) {
        Player player = null;
        org.bukkit.entity.Player p = bukkit.getPlayer(uuid);
        if (p != null) {
            if (!players.containsKey(p.getUniqueId())) {
                players.put(p.getUniqueId(), new BukkitPlayer(p));
            }
            player = players.get(p.getUniqueId());
        }
        return player;
    }

    @Override
    public Player getPlayer(String name) {
        Player player = null;
        org.bukkit.entity.Player p = bukkit.getPlayer(name);
        if (p != null) {
            if (!players.containsKey(p.getUniqueId())) {
                players.put(p.getUniqueId(), new BukkitPlayer(p));
            }
            player = players.get(p.getUniqueId());
        }
        return player;
    }

    @Override
    public Collection<World> getWorlds() {
        worlds.clear();
        for (org.bukkit.World w : bukkit.getWorlds()) {
            if (!worlds.containsKey(w.getUID())) {
                worlds.put(w.getUID(), new BukkitWorld(w));
            }
        }
        return worlds.values();
    }

    @Override
    public World getWorld(UUID uuid) {
        World world = null;
        org.bukkit.World w = bukkit.getWorld(uuid);
        if (w != null) {
            if (!worlds.containsKey(w.getUID())) {
                worlds.put(w.getUID(), new BukkitWorld(w));
            }
            world = worlds.get(w.getUID());
        }
        return world;
    }

    @Override
    public World getWorld(String name) {
        World world = null;
        org.bukkit.World w = bukkit.getWorld(name);
        if (w != null) {
            if (!worlds.containsKey(w.getUID())) {
                worlds.put(w.getUID(), new BukkitWorld(w));
            }
            world = worlds.get(w.getUID());
        }
        return world;
    }

    @Override
    public void runSyncTask(Runnable task) {
        Bukkit.getServer().getScheduler().runTask(module, task);
        DebugLogger.debug("Created new synchronous task " + task.getClass());
    }

    @Override
    public void scheduleSyncRepeatingTask(Runnable task, long delay, long interval) {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(module, task, delay, interval);
        DebugLogger.debug("Created new repeating synchronous task " + task.getClass());
    }

    @Override
    public void runAsyncTask(Runnable task) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(module, task);
        DebugLogger.debug("Created new asynchronous task " + task.getClass());
    }

}
