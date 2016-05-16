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
package com.admincmd.api.configuration;

/**
 * Represents the options for a Configuration object
 * Contains options for a Configuration object
 *
 * Design adapted/inspired from:
 *   Bukkit's ConfigurationOptions, MemoryConfigurationOptions
 */
public class ConfigurationOptions {

    // Copy the defaults when saving
    private boolean copyDefaults = false;

    public ConfigurationOptions() {

    }

    /**
     * Sets the copyDefaults option
     *
     * @param copy
     * @return The current options
     */
    public ConfigurationOptions setCopyDefaults(boolean copy) {
        this.copyDefaults = copy;
        return this;
    }

    /**
     * Gets the copyDefaults option
     *
     * @return The copyDefaults option
     */
    public boolean getCopyDefaults() {
        return copyDefaults;
    }

}
