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
package com.admincmd.sponge;

import com.admincmd.api.entity.player.Player;
import com.admincmd.api.util.logger.DebugLogger;
import com.admincmd.api.world.World;
import com.admincmd.sponge.entity.player.SpongePlayer;
import com.admincmd.sponge.world.SpongeWorld;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;

import java.util.*;

public class SpongeServer implements com.admincmd.api.Server {

    private SpongeModule module;
    private Server sponge;

    private Map<UUID, Player> players = new HashMap<>();
    private Map<UUID, World> worlds = new HashMap<>();

    public SpongeServer(SpongeModule module) {
        this.module = module;
        this.sponge = Sponge.getServer();
    }

    @Override
    public Collection<Player> getOnlinePlayers() {
        players.clear();
        for (org.spongepowered.api.entity.living.player.Player p : sponge.getOnlinePlayers()) {
            if (!players.containsKey(p.getUniqueId())) {
                players.put(p.getUniqueId(), new SpongePlayer(p));
            }
        }
        return players.values();
    }

    @Override
    public Player getPlayer(UUID uuid) {
        Player player = null;
        Optional<org.spongepowered.api.entity.living.player.Player> p = sponge.getPlayer(uuid);
        if (p.isPresent()) {
            if (!players.containsKey(p.get().getUniqueId())) {
                players.put(p.get().getUniqueId(), new SpongePlayer(p.get()));
            }
            player = players.get(p.get().getUniqueId());
        }
        return player;
    }

    @Override
    public Player getPlayer(String name) {
        Player player = null;
        Optional<org.spongepowered.api.entity.living.player.Player> p = sponge.getPlayer(name);
        if (p.isPresent()) {
            if (!players.containsKey(p.get().getUniqueId())) {
                players.put(p.get().getUniqueId(), new SpongePlayer(p.get()));
            }
            player = players.get(p.get().getUniqueId());
        }
        return player;
    }

    @Override
    public Collection<World> getWorlds() {
        worlds.clear();
        for (org.spongepowered.api.world.World w : sponge.getWorlds()) {
            if (!worlds.containsKey(w.getUniqueId())) {
                worlds.put(w.getUniqueId(), new SpongeWorld(w));
            }
        }
        return worlds.values();
    }

    @Override
    public World getWorld(UUID uuid) {
        World world = null;
        Optional<org.spongepowered.api.world.World> w = sponge.getWorld(uuid);
        if (w.isPresent()) {
            if (!worlds.containsKey(w.get().getUniqueId())) {
                worlds.put(w.get().getUniqueId(), new SpongeWorld(w.get()));
            }
            world = worlds.get(w.get().getUniqueId());
        }
        return world;
    }

    @Override
    public World getWorld(String name) {
        World world = null;
        Optional<org.spongepowered.api.world.World> w = sponge.getWorld(name);
        if (w.isPresent()) {
            if (!worlds.containsKey(w.get().getUniqueId())) {
                worlds.put(w.get().getUniqueId(), new SpongeWorld(w.get()));
            }
            world = worlds.get(w.get().getUniqueId());
        }
        return world;
    }

    @Override
    public void runSyncTask(Runnable task) {
        Sponge.getScheduler().createTaskBuilder().execute(task).submit(module);
        DebugLogger.debug("Created new synchronous task " + task.getClass());
    }

    @Override
    public void scheduleSyncRepeatingTask(Runnable task, long delay, long interval) {
        Sponge.getScheduler().createTaskBuilder().delayTicks(delay).intervalTicks(interval).execute(task).submit(module);
        DebugLogger.debug("Created new repeating synchronous task " + task.getClass());
    }

    @Override
    public void runAsyncTask(Runnable task) {
        Sponge.getScheduler().createTaskBuilder().async().execute(task).submit(module);
        DebugLogger.debug("Created new asynchronous task " + task.getClass());
    }

}
