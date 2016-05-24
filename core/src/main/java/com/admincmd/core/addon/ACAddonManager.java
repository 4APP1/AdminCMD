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
package com.admincmd.core.addon;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.addon.Addon;
import com.admincmd.api.addon.AddonManager;
import com.admincmd.api.util.logger.DebugLogger;
import com.admincmd.core.ACModule;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

public class ACAddonManager implements AddonManager {

    private final ACModule module;

    private final ACAddonClassLoader classLoader;

    private final Map<String, Addon> loaded = new HashMap<>();
    private final List<Addon> enabled = new ArrayList<>();

    public ACAddonManager(ACModule module) {
        this.module = module;
        this.classLoader = new ACAddonClassLoader(module.getClassLoader());
    }

    @Override
    public void loadAddons() {
        File file = new File(module.getDataFolder(), "addons");
        file.mkdirs();

        File[] files = file.listFiles();
        if (file.exists() && file.isDirectory() && files != null) {
            for (File f : files) {
                if (!f.isDirectory() && f.getName().endsWith(".jar")) {
                    Addon addon = loadAddon(f);
                    if (addon != null) {
                        String name = addon.getModName().toLowerCase();
                        loaded.put(name, addon);
                        enableAddon(addon);
                    }
                }
            }
        }
    }

    private Addon loadAddon(File file) {
        try {
            classLoader.addFile(file);
        } catch (IOException e) {
            DebugLogger.severe("Addon file could not be added to the class loader: " + file.getName(), e);
        } catch (ClassNotFoundException e) {
            DebugLogger.severe("Addon classes could not be loaded: " + file.getName(), e);
        }

        Class<? extends Addon> clazz = classLoader.getAddonClass(file);

        Addon addon = null;
        if (clazz != null) {
            try {
                addon = clazz.newInstance();
            } catch (InstantiationException e) {
                DebugLogger.severe("Addon class could not be instantiated: " + clazz, e);
            } catch (IllegalAccessException e) {
                DebugLogger.severe("Addon class could not be accessed: " + clazz, e);
            }
        }
        return addon;
    }

    @Override
    public void unloadAddons() {
        if (loaded.size() > 0) {
            for (Addon a : getAddons()) {
                disableAddon(a);

                AdminCMD.getCommandManager().unregisterAll(a);
                AdminCMD.getEventManager().unregisterAll(a);
            }
        }
    }

    @Override
    public Collection<Addon> getAddons() {
        return loaded.values();
    }

    @Override
    public Addon getAddon(String name) {
        return loaded.get(name.toLowerCase());
    }

    @Override
    public boolean isAddonEnabled(String name) {
        return enabled.contains(getAddon(name));
    }

    private void enableAddon(Addon addon) {
        String name = addon.getModName().toLowerCase();
        if (loaded.containsKey(name) && !enabled.contains(addon)) {
            addon.onEnable();
            enabled.add(addon);
        }
    }

    private void disableAddon(Addon addon) {
        String name = addon.getModName().toLowerCase();
        if (loaded.containsKey(name) && enabled.contains(addon)) {
            addon.onDisable();
            enabled.remove(addon);
        }
    }

}
