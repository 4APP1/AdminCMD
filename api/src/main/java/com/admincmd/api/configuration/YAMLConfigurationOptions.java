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

import org.yaml.snakeyaml.DumperOptions;

/**
 * Represents the options for a YAMLConfiguration object
 * Contains options for a YAMLConfiguration object
 *
 * Design adapted/inspired from:
 *   Bukkit's FileConfigurationOptions, YamlConfigurationOptions
 */
public class YAMLConfigurationOptions extends ConfigurationOptions {

    // Copy the header when saving
    private boolean copyHeader = true;

    // The header to be used
    private String header = null;

    // The indent for the YAML options
    private int indent = 2;

    public YAMLConfigurationOptions() {

    }

    public YAMLConfigurationOptions setCopyDefaults(boolean copy) {
        super.setCopyDefaults(copy);
        return this;
    }

    /**
     * Sets the copyHeader option
     *
     * @param copy
     * @return The current options
     */
    public YAMLConfigurationOptions setCopyHeader(boolean copy) {
        this.copyHeader = copy;
        return this;
    }

    /**
     * Gets the copyHeader option
     *
     * @return The copyHeader option
     */
    public boolean getCopyHeader() {
        return copyHeader;
    }


    /**
     * Sets the header
     *
     * @param header
     * @return The current options
     */
    public YAMLConfigurationOptions setHeader(String header) {
        this.header = header;
        return this;
    }

    /**
     * Gets the header
     *
     * @return The header
     */
    public String getHeader() {
        return header;
    }


    /**
     * Sets the indent size
     *
     * @param indent
     * @return The current options
     */
    public YAMLConfigurationOptions setIndent(int indent) {
        this.indent = indent;
        return this;
    }

    /**
     * Gets the indent size
     *
     * @return The indent size
     */
    public int getIndent() {
        return indent;
    }

    /**
     * The DumperOptions to build a Yaml object
     *
     * @return A new DumperOptions with options set
     */
    public DumperOptions getDumperOptions() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setIndent(indent);
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return dumperOptions;
    }

}
