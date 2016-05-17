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
import com.admincmd.core.ACPlugin;

import java.io.File;
import java.io.IOException;

public class ACAddonManager extends AddonManager {

    private ACPlugin plugin;

    public ACAddonManager(ACPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadAddons() {
        File file = new File(AdminCMD.getDataFolder(), "addons");
        file.mkdirs();

        if (file.exists() && file.isDirectory() && file.listFiles() != null) {
            for (File f : file.listFiles()) {
                if (!f.isDirectory() && f.getName().endsWith(".jar")) {
                    Addon addon = loadAddon(f);
                    if (addon != null) {
                        enableAddon(addon);
                    }
                }
            }
        }
    }

    protected Addon loadAddon(File file) {
        AddonClassLoader loader = null;
        try {
            loader = new AddonClassLoader(file, plugin.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            // TODO ACLogger message
        } catch (IOException e) {
            // TODO ACLogger message
        }

        Class<? extends Addon> addonClazz = null;
        if (loader != null) {
            addonClazz = loader.getAddonClass();
        }

        Addon addon = null;
        if (addonClazz != null) {
            try {
                addon = addonClazz.newInstance();
            } catch (InstantiationException e) {
                // TODO ACLogger message
            } catch (IllegalAccessException e) {
                // TODO ACLogger message
            }
        }
        return addon;
    }

}
