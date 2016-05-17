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
package com.admincmd.api.addon;

import com.admincmd.api.AdminCMD;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AddonManager {

    private final Map<String, Addon> addons = new HashMap<>();

    public AddonManager() {

    }

    public abstract void loadAddons();

    protected abstract Addon loadAddon(File file);

    public void unloadAddons() {
        if (addons.size() > 0) {
            for (Addon a : getAddons()) {
                unloadAddon(a);
            }
        }
    }

    protected void unloadAddon(Addon addon) {
        if (addon != null) {
            disableAddon(addon);

            AdminCMD.getCommandManager().unregisterAll(addon);
            AdminCMD.getEventManager().unregisterAll(addon);
        }
    }

    public Collection<Addon> getAddons() {
        return addons.values();
    }

    public Addon getAddon(String name) {
        return addons.get(name.toLowerCase());
    }

    public boolean isAddonEnabled(String name) {
        Addon addon = getAddon(name);
        if (addon != null) {
            return addon.isEnabled();
        }
        return false;
    }

    protected void enableAddon(Addon addon) {
        if (!addon.isEnabled()) {
            String name = addon.getModuleName().toLowerCase();

            try {
                addon.setEnabled(true);
                addons.put(name, addon);
            } catch (Exception e) {
                // TODO ACLogger message
            }
        }
    }

    protected void disableAddon(Addon addon) {
        if (addon.isEnabled()) {
            try {
                addon.setEnabled(false);
            } catch (Exception ex) {
                // TODO ACLogger message
            }
        }
    }

}
