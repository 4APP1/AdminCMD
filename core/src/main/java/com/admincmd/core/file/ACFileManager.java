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
package com.admincmd.core.file;

import com.admincmd.api.configuration.CSVConfiguration;
import com.admincmd.api.configuration.YAMLConfiguration;
import com.admincmd.api.file.FileManager;
import com.admincmd.api.util.logger.DebugLogger;
import com.admincmd.core.ACModule;
import com.admincmd.core.configuration.Config;
import com.admincmd.core.configuration.Locale;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ACFileManager implements FileManager {

    private final ACModule module;
    private final File dataFolder;

    private YAMLConfiguration commands;
    private CSVConfiguration items;
    private CSVConfiguration enchants;

    public ACFileManager(ACModule module) {
        this.module = module;
        this.dataFolder = module.getDataFolder();

        load();
    }

    public void load() {
        // Load the plugin configuration
        Config.load(dataFolder);
        // Load the plugin locales
        Locale.load(dataFolder);

        // Load the remaining files
        commands = loadYAML(new File(dataFolder, "commands.yml"));
        items = loadCSV(new File(dataFolder, "items.csv"));
        enchants = loadCSV(new File(dataFolder, "enchants.csv"));
    }

    public void reload() {
        // Reload the plugin configuration
        Config.reload(true);
        // Reload the plugin locales
        Locale.reload(true);

        // Reload the remaining files

    }

    public YAMLConfiguration loadYAML(File file) {
        YAMLConfiguration config = new YAMLConfiguration(file);
        try {
            config.load();
        } catch (IOException e) {
            DebugLogger.severe("YAMLConfiguration could not be loaded: " + file.getName(), e);
        }
        return config;
    }

    public CSVConfiguration loadCSV(File file) {
        CSVConfiguration config = new CSVConfiguration(file);
        try {
            config.load();
        } catch (IOException e) {
            DebugLogger.severe("CSVConfiguration could not be loaded: " + file.getName(), e);
        }
        return config;
    }

    public void reloadYAML(YAMLConfiguration config) {
        try {
            config.load();
        } catch (IOException e) {
            DebugLogger.severe("YAMLConfiguration could not be reloaded from disk", e);
        }
    }

    public void reloadCSV(CSVConfiguration config) {
        try {
            config.load();
        } catch (IOException e) {
            DebugLogger.severe("CSVConfiguration could not be reloaded from disk", e);
        }
    }

    @Override
    public Map<String, String> getItemAliases() {
        Map<String, String> aliases = new LinkedHashMap<>();
        List<List<String>> rows = items.getRows();
        for (List<String> row : rows) {
            if (row.size() > 1) {
                aliases.put(row.get(0), row.get(1));
            }
        }
        return aliases;
    }

    @Override
    public Map<String, String> getEnchantAliases() {
        Map<String, String> aliases = new LinkedHashMap<>();
        List<List<String>> rows = enchants.getRows();
        for (List<String> row : rows) {
            if (row.size() > 1) {
                aliases.put(row.get(0), row.get(1));
            }
        }
        return aliases;
    }

}
