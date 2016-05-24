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

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a default Configuration object
 * Contains default values for a configuration instance
 *
 * Design adapted/inspired from:
 *   LWC's AbstractDefaultConfiguration (Copyright (c) 2011-2014 Tyler Blair)
 *   Bukkit's Configuration, MemoryConfiguration
 */
public class Configuration {

    // The default configuration values
    private Map<String, Object> defaults;

    // The basic configuration options
    private ConfigurationOptions options = new ConfigurationOptions();

    public Configuration() {
        this.defaults = new LinkedHashMap<>();
    }

    public Configuration(Map<String, Object> defaults) {
        this.defaults = defaults;
    }

    /**
     * Gets the default values
     *
     * @return Returns the default values
     */
    public Map<String, Object> getDefaults() {
        return defaults;
    }

    /**
     * Sets the default values
     *
     * @param defaults
     */
    public void setDefaults(Map<String, Object> defaults) {
        this.defaults = defaults;
    }

    /**
     * Checks for the given path
     *
     * @param path
     * @return If path exists, return true
     */
    public boolean contains(String path) {
        return defaults.containsKey(path);
    }

    /**
     * Gets a value for the given path
     *
     * @param path
     * @return  If path exists, return value for path
     */
    public Object get(String path) {
        return defaults.get(path);
    }

    /**
     * Sets a value for the given path
     *
     * @param path
     * @param value
     */
    public void set(String path, Object value) {
        defaults.put(path, value);
    }

    /**
     * Removes a given path
     *
     * @param path
     */
    public void remove(String path) {
        defaults.remove(path);
    }

    /**
     * Gets a String value from the given path
     *
     * @param path
     * @return If path exists, return String value for path
     */
    public String getString(String path) {
        Object o = get(path);
        if (o instanceof String) {
            return (String) o;
        }
        return null;
    }

    /**
     * Gets a List value from the given path
     *
     * @param path
     * @return If path exists, return List value for path
     */
    public List<String> getStringList(String path) {
        Object o = get(path);
        if (o instanceof List<?>) {
            return (List<String>) o;
        }
        return null;
    }

    /**
     * Gets an int value from the given path
     *
     * @param path
     * @return If path exists, return int value for path
     */
    public int getInteger(String path) {
        Object o = get(path);
        if (o instanceof Integer) {
            return (int) (Integer) o;
        }
        return 0;
    }

    /**
     * Gets a double value from the given path
     *
     * @param path
     * @return If path exists, return double value for path
     */
    public double getDouble(String path) {
        Object o = get(path);
        if (o instanceof Double) {
            return (double) (Double) o;
        }
        return 0;
    }

    /**
     * Gets a float value from the given path
     *
     * @param path
     * @return If path exists, return float value for path
     */
    public float getFloat(String path) {
        Object o = get(path);
        if (o instanceof Float) {
            return (float) (Float) o;
        }
        return 0;
    }

    /**
     * Gets a long value from the given path
     *
     * @param path
     * @return If path exists, return long value for path
     */
    public long getLong(String path) {
        Object o = get(path);
        if (o instanceof Long) {
            return (long) (Long) o;
        }
        return 0;
    }

    /**
     * Gets a boolean value from the given path
     *
     * @param path
     * @return If path exists, return boolean value for path
     */
    public boolean getBoolean(String path) {
        Object o = get(path);
        if (o instanceof Boolean) {
            return (boolean) (Boolean) o;
        }
        return false;
    }

    /**
     * Loads the configuration values
     *
     * @throws IOException
     */
    public void load() throws IOException {

    }

    /**
     * Saves the configuration values
     *
     * @throws IOException
     */
    public void save() throws IOException {
        for (String path : defaults.keySet()) {
            Object value = defaults.get(path);

            if (get(path).equals(value)) {
                set(path, value);
            }
        }
    }

    /**
     * Gets the ConfigurationOptions object
     *
     * @return ConfigurationOptions object
     */
    public ConfigurationOptions getOptions() {
        return options;
    }

    /**
     * Gets if this configuration is empty
     *
     * @return If maps are empty, return true
     */
    public boolean isEmpty() {
        return (defaults == null || defaults.isEmpty());
    }

    /*
     * Gets an array representation of a path
     *
     * @param path
     * @return Path as an array
     */
    protected String[] getNodePath(String path) {
        String[] paths = path.split("\\.");
        if (paths.length > 1) {
            return Arrays.copyOf(paths, paths.length - 1);
        }
        return new String[0];
    }

    /*
     * Gets the key of a path
     *
     * @param path
     * @return Key of a path
     */
    protected String getNodeKey(String path) {
        String[] paths = path.split("\\.");

        if (paths.length == 1) {
            return paths[0];
        }
        return paths[paths.length - 1];
    }

}
