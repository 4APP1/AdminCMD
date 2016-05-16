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
import com.admincmd.api.world.World;
import com.admincmd.core.ACServer;
import com.admincmd.core.SimpleCore;
import com.admincmd.core.entity.player.ACPlayer;
import com.admincmd.core.world.ACWorld;
import com.admincmd.sponge.entity.player.SpongePlayer;
import com.admincmd.sponge.world.SpongeWorld;
import org.spongepowered.api.Sponge;

import java.util.*;

public class SpongeServer extends ACServer {

    private SpongePlugin plugin;

    public SpongeServer(SpongePlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        database = SimpleCore.getDatabaseManager().getDatabase();

        preparePlayers();
        prepareWorlds();
    }

    @Override
    public Collection<Player> getOnlinePlayers() {
        List<Player> list = new ArrayList<>();
        for (org.spongepowered.api.entity.living.player.Player p : Sponge.getServer().getOnlinePlayers()) {
            if (p != null) {
                Player player = super.getPlayer(p.getUniqueId());
                if (player == null) {
                    player = new SpongePlayer(p);
                    insertPlayer(p.getUniqueId(), (ACPlayer) player);
                }
                list.add(player);
            }
        }
        return list;
    }

    @Override
    public Player getPlayer(UUID uuid) {
        Player p = null;
        Optional<org.spongepowered.api.entity.living.player.Player> player = Sponge.getServer().getPlayer(uuid);
        if (player.isPresent()) {
            p = super.getPlayer(player.get().getUniqueId());
            if (p == null) {
                p = new SpongePlayer(player.get());
                insertPlayer(player.get().getUniqueId(), (ACPlayer) p);
            }
        }
        return p;
    }

    @Override
    public Player getPlayer(String name) {
        Player p = null;
        Optional<org.spongepowered.api.entity.living.player.Player> player = Sponge.getServer().getPlayer(name);
        if (player.isPresent()) {
            p = super.getPlayer(player.get().getUniqueId());
            if (p == null) {
                p = new SpongePlayer(player.get());
                insertPlayer(player.get().getUniqueId(), (ACPlayer) p);
            }
        }
        return p;
    }

    @Override
    public Collection<World> getWorlds() {
        List<World> list = new ArrayList<>();
        for (org.spongepowered.api.world.World w : Sponge.getServer().getWorlds()) {
            if (w != null) {
                World world = super.getWorld(w.getUniqueId());
                if (world == null) {
                    world = new SpongeWorld(w);
                    insertWorld(w.getUniqueId(), (ACWorld) world);
                }
                list.add(world);
            }
        }
        return list;
    }

    @Override
    public World getWorld(UUID uuid) {
        World w  = null;
        Optional<org.spongepowered.api.world.World> world = Sponge.getServer().getWorld(uuid);
        if (world.isPresent()) {
            w = super.getWorld(world.get().getUniqueId());
            if (w == null) {
                w = new SpongeWorld(world.get());
                insertWorld(world.get().getUniqueId(), (ACWorld) w);
            }
        }
        return w;
    }

    @Override
    public World getWorld(String name) {
        World w  = null;
        Optional<org.spongepowered.api.world.World> world = Sponge.getServer().getWorld(name);
        if (world.isPresent()) {
            w = super.getWorld(world.get().getUniqueId());
            if (w == null) {
                w = new SpongeWorld(world.get());
                insertWorld(world.get().getUniqueId(), (ACWorld) w);
            }
        }
        return w;
    }

    @Override
    public void runSyncTask(Runnable task) {
        Sponge.getScheduler().createTaskBuilder().execute(task).submit(plugin);
    }

    @Override
    public void scheduleSyncRepeatingTask(Runnable task, long delay, long interval) {
        Sponge.getScheduler().createTaskBuilder().delayTicks(delay).intervalTicks(interval).execute(task).submit(plugin);
    }

    @Override
    public void runAsyncTask(Runnable task) {
        Sponge.getScheduler().createTaskBuilder().async().execute(task).submit(plugin);
    }

}
