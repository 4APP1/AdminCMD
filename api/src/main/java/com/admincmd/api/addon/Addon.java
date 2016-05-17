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

import com.admincmd.api.Identifiable;

public class Addon implements Identifiable {

    private String id;
    private String name;
    private String version;

    private boolean enabled = false;

    protected Addon(String id, String name, String version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    protected boolean isEnabled() {
        return enabled;
    }

    protected void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;

            if (this.enabled) {
                onEnable();
            } else {
                onDisable();
            }
        }
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    @Override
    public String getModuleId() {
        return id;
    }

    @Override
    public String getModuleName() {
        return name;
    }

    public String getModuleVersion() {
        return version;
    }

}
