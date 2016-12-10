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
package com.admincmd;

import com.admincmd.addon.AddonManager;
import com.admincmd.commandapi.CommandManager;
import com.admincmd.commands.MaintenanceCommands;
import com.admincmd.commands.MobCommands;
import com.admincmd.commands.PlayerCommands;
import com.admincmd.commands.ServerCommands;
import com.admincmd.commands.SpawnCommands;
import com.admincmd.commands.TeleportCommands;
import com.admincmd.commands.WorldCommands;
import com.admincmd.commands.home.DelhomeCommand;
import com.admincmd.commands.home.EdithomeCommand;
import com.admincmd.commands.home.HomeCommand;
import com.admincmd.commands.home.SethomeCommand;
import com.admincmd.utils.Config;
import com.admincmd.utils.Locales;
import com.admincmd.database.DatabaseFactory;
import com.admincmd.events.ChatListener;
import com.admincmd.events.PingListener;
import com.admincmd.events.PlayerCommandListener;
import com.admincmd.events.PlayerDamageListener;
import com.admincmd.events.PlayerDeathListener;
import com.admincmd.events.PlayerJoinListener;
import com.admincmd.events.SignListener;
import com.admincmd.events.WorldListener;
import com.admincmd.home.HomeManager;
import com.admincmd.metrics.Metrics;
import com.admincmd.player.PlayerManager;
import com.admincmd.spawn.SpawnManager;
import com.admincmd.utils.ACLogger;
import com.admincmd.utils.EventManager;
import com.admincmd.utils.Vault;
import com.admincmd.world.WorldManager;
import de.thejeterlp.bukkit.updater.Updater;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    private static Main INSTANCE;
    private final CommandManager manager = new CommandManager(this);
    
    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        
        INSTANCE = this;
        
        getDataFolder().mkdirs();
        File f = new File(getDataFolder(), "admincmd.db");
        if (f.exists()) {
            //Old AdminCMD version found
            ACLogger.warn("Old AdminCMD version was found! Renaming the AdminCMd folder to AdminCMD-Old!");
            getDataFolder().renameTo(new File(getDataFolder().getParentFile(), "AdminCMD-Old"));
        }
        
        Config.load();
        Locales.load();
        
        DatabaseFactory.init();
        
        PlayerManager.init();
        SpawnManager.init();
        WorldManager.init();
        HomeManager.init();
        
        registerCommands();
        registerEvents();
        
        if (checkForProtocolLib()) {
            ACLogger.info("Hooked into ProtocolLib.");
            new PingListener().addPingResponsePacketListener();
        }
        
        if (checkForVault()) {
            if (!Vault.setupChat()) {
                ACLogger.severe("Vault could not be set up.");
            }
            ACLogger.info("Hooked into Vault.");
        }
        
        AddonManager.loadAddons();
        
        Updater u = new Updater(this, 31318, "admincmd", "admincmd", "admincmd");
        u.search();
        
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException ex) {
            ACLogger.severe("Could not enable Metrics!", ex);
        }
        
        long timeTook = System.currentTimeMillis() - start;
        ACLogger.info("Plugin start took " + timeTook + " milliseconds");
    }
    
    @Override
    public void onDisable() {
        AddonManager.disableAddons();
        
        PlayerManager.save();
        WorldManager.save();
        HomeManager.save();
        
        try {
            DatabaseFactory.getDatabase().closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        System.gc();
    }

    /**
     * Returns an instance of this class.
     *
     * @return {@link com.admincmd.Main}
     */
    public static Main getInstance() {
        return INSTANCE;
    }
    
    public boolean checkForProtocolLib() {
        Plugin pl = getServer().getPluginManager().getPlugin("ProtocolLib");
        return pl != null && pl.isEnabled();
    }
    
    public boolean checkForVault() {
        Plugin pl = getServer().getPluginManager().getPlugin("Vault");
        return pl != null && pl.isEnabled();
    }
    
    private void registerCommands() {
        manager.registerClass(ServerCommands.class);
        manager.registerClass(PlayerCommands.class);
        manager.registerClass(DelhomeCommand.class);
        manager.registerClass(EdithomeCommand.class);
        manager.registerClass(HomeCommand.class);
        manager.registerClass(SethomeCommand.class);
        manager.registerClass(WorldCommands.class);
        manager.registerClass(MobCommands.class);
        manager.registerClass(SpawnCommands.class);
        manager.registerClass(MaintenanceCommands.class);
        manager.registerClass(TeleportCommands.class);
    }
    
    private void registerEvents() {
        EventManager.registerEvent(PlayerJoinListener.class);
        EventManager.registerEvent(PlayerCommandListener.class);
        EventManager.registerEvent(WorldListener.class);
        EventManager.registerEvent(PlayerDamageListener.class);
        EventManager.registerEvent(PlayerDeathListener.class);
        EventManager.registerEvent(SignListener.class);
        EventManager.registerEvent(ChatListener.class);
    }
    
}
