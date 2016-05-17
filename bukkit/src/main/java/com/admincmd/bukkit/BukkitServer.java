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
import com.admincmd.api.world.World;
import com.admincmd.bukkit.entity.player.BukkitPlayer;
import com.admincmd.bukkit.world.BukkitWorld;
import com.admincmd.core.ACServer;
import com.admincmd.core.entity.player.ACPlayer;
import com.admincmd.core.world.ACWorld;
import org.bukkit.Bukkit;

import java.util.*;

public class BukkitServer extends ACServer {

    private BukkitModule plugin;

    // private Map<UUID, Player> players = new HashMap<>();
    // private Map<UUID, World> worlds = new HashMap<>();

    public BukkitServer(BukkitModule plugin) {
        this.plugin = plugin;
    }

    @Override
    public Collection<Player> getOnlinePlayers() {
        List<Player> list = new ArrayList<>();
        for (org.bukkit.entity.Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p != null) {
                Player player = super.getPlayer(p.getUniqueId()); // players.get(p.getUniqueId());
                if (player == null) {
                    player = new BukkitPlayer(p);
                    insertPlayer(p.getUniqueId(), (ACPlayer) player); // players.put(p.getUniqueId(), player);
                }
                list.add(player);
            }
        }
        return list;
    }

    @Override
    public Player getPlayer(UUID uuid) {
        Player p = null;
        org.bukkit.entity.Player player = Bukkit.getServer().getPlayer(uuid);
        if (player != null) {
            p = super.getPlayer(player.getUniqueId()); // players.get(player.getUniqueId());
            if (p == null) {
                p = new BukkitPlayer(player);
                insertPlayer(player.getUniqueId(), (ACPlayer) p); // players.put(player.getUniqueId(), p);
            }
        }
        return p;
    }

    @Override
    public Player getPlayer(String name) {
        Player p = null;
        org.bukkit.entity.Player player = Bukkit.getServer().getPlayer(name);
        if (player != null) {
            p = super.getPlayer(player.getUniqueId()); // players.get(player.getUniqueId());
            if (p == null) {
                p = new BukkitPlayer(player);
                insertPlayer(player.getUniqueId(), (ACPlayer) p); // players.put(player.getUniqueId(), p);
            }
        }
        return p;
    }

    @Override
    public Collection<World> getWorlds() {
        List<World> list = new ArrayList<>();
        for (org.bukkit.World w : Bukkit.getServer().getWorlds()) {
            if (w != null) {
                World world = super.getWorld(w.getUID()); // worlds.get(w.getUID());
                if (world == null) {
                    world = new BukkitWorld(w);
                    insertWorld(w.getUID(), (ACWorld) world); // worlds.put(w.getUID(), world);
                }
                list.add(world);
            }
        }
        return list;
    }

    @Override
    public World getWorld(UUID uuid) {
        World w  = null;
        org.bukkit.World world = Bukkit.getServer().getWorld(uuid);
        if (world != null) {
            w = super.getWorld(world.getUID()); // worlds.get(world.getUID());
            if (w == null) {
                w = new BukkitWorld(world);
                insertWorld(world.getUID(), (ACWorld) w); // worlds.put(world.getUID(), w);
            }
        }
        return w;
    }

    @Override
    public World getWorld(String name) {
        World w  = null;
        org.bukkit.World world = Bukkit.getServer().getWorld(name);
        if (world != null) {
            w = super.getWorld(world.getUID()); // worlds.get(world.getUID());
            if (w == null) {
                w = new BukkitWorld(world);
                insertWorld(world.getUID(), (ACWorld) w); // worlds.put(world.getUID(), w);
            }
        }
        return w;
    }

    @Override
    public void runSyncTask(Runnable task) {
        Bukkit.getServer().getScheduler().runTask(plugin, task);
    }

    @Override
    public void scheduleSyncRepeatingTask(Runnable task, long delay, long interval) {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, task, delay, interval);
    }

    @Override
    public void runAsyncTask(Runnable task) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, task);
    }

}
