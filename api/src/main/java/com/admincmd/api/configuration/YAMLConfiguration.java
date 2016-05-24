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

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.*;

/**
 * Represents a Yaml implementation of Configuration
 * Provides methods to load, modify, and save configuration values and the header
 *
 * Design adapted/inspired from:
 *   LWC's YamlConfiguration (Copyright (c) 2011-2014 Tyler Blair)
 *   Bukkit's FileConfiguration, YamlConfiguration
 */
public class YAMLConfiguration extends Configuration {

    // The configuration values
    private Map<String, Object> root;

    // If null, do not load/save configuration to file
    private File file = null;

    // The YAML configuration options
    private YAMLConfigurationOptions options = new YAMLConfigurationOptions();

    /**
     * Creates an empty YAMLConfiguration
     */
    public YAMLConfiguration() {
        this.root = new LinkedHashMap<>();
    }

    /**
     * Creates an empty YAMLConfiguration linked to a file
     *
     * @param file The file to bind to
     */
    public YAMLConfiguration(File file) {
        this.root = new LinkedHashMap<>();
        this.file = file;
    }

    /**
     * Creates a YAMLConfiguration linked to a file with given defaults
     *
     * @param file The file to bind to
     * @param defaults The defaults to load
     * @param loadDefaults If true, copies defaults to root
     */
    public YAMLConfiguration(File file, Map<String, Object> defaults, boolean loadDefaults) {
        this.root = new LinkedHashMap<>();
        this.file = file;
        setDefaults(defaults);
        if (loadDefaults) {
            try {
                super.save();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public boolean contains(String path) {
        String key = getNodeKey(path);
        Map<String, Object> temp = getNode(path, false);

        if (temp != null && temp.containsKey(key)) {
            return true;
        } else {
            return super.contains(path);
        }
    }

    @Override
    public Object get(String path) {
        String key = getNodeKey(path);
        Map<String, Object> temp = getNode(path, false);

        if (temp != null && temp.containsKey(key)) {
            return temp.get(key);
        } else {
            return super.get(path);
        }
    }

    @Override
    public void set(String path, Object value) {
        String key = getNodeKey(path);
        Map<String, Object> temp = getNode(path, true);

        if (temp == null) {
            throw new IllegalStateException("The provided path is not initialized!");
        }

        temp.put(key, value);
    }

    @Override
    public void remove(String path) {
        String key = getNodeKey(path);
        Map<String, Object> temp = getNode(path, false);

        if (temp != null && temp.containsKey(key)) {
            temp.remove(key);
        }
    }

    /**
     * Gets a list of all contained paths
     *
     * @return A list of path keys
     */
    public Collection<String> getPaths() {
        Map<String, Object> map = getNodeMap(root, "");
        return map.keySet();
    }

    /**
     * Gets a list of all contained values
     *
     * @return A list of values
     */
    public Collection<Object> getValues() {
        Map<String, Object> map = getNodeMap(root, "");
        return map.values();
    }

    /**
     * Gets a map with all contained paths and values
     *
     * @return A map of paths and values
     */
    public Map<String, Object> getMap() {
        return getNodeMap(root, "");
    }

    @Override
    public void load() throws IOException {
        load(file);
    }

    /**
     * Loads configuration values and header from the given file
     *
     * @throws IOException
     */
    public void load(File file) throws IOException {
        if (file != null) {
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                String data = "";
                String line;
                while ((line = reader.readLine()) != null) {
                    data += (line + "\n");
                }
                reader.close();
                loadFromString(data);
            } else {
                file.createNewFile();
            }
        }
    }

    /**
     * Loads configuration values and header from String
     *
     * @param data
     */
    public void loadFromString(String data) {
        Yaml yaml = new Yaml(new SafeConstructor(), new Representer(), options.getDumperOptions());
        Map<String, Object> input = (Map) yaml.load(data);
        if (input != null) {
            root = input;
        }

        String header = parseHeader(data);
        if (header.length() > 0) {
            options.setHeader(header);
        }
    }

    @Override
    public void save() throws IOException {
        save(file);
    }

    /**
     * Saves configuration values and header to the given file
     *
     * @param file
     * @throws IOException
     */
    public void save(File file) throws IOException {
        if (options.getCopyDefaults()) {
            super.save();
        }

        if (file != null) {
            Writer writer = new FileWriter(file);
            writer.write(saveToString());
            writer.close();
        }
    }

    /**
     * Saves configuration values and header to String
     *
     * @return Values and header as String
     */
    public String saveToString() {
        Yaml yaml = new Yaml(new SafeConstructor(), new Representer(), options.getDumperOptions());
        String header = buildHeader();
        String dump = yaml.dump(root);
        if (dump.equals("{}\n")) {
            dump = "";
        }
        return header + dump;
    }

    /**
     * Gets the header from the input contents
     *
     * @param input
     * @return Unformatted header
     */
    public String parseHeader(String input) {
        String[] lines = input.split("\r?\n", -1);
        StringBuilder result = new StringBuilder();
        boolean readingHeader = true;
        boolean foundHeader = false;

        for (int i = 0; i < lines.length && readingHeader; ++i) {
            String line = lines[i];
            if (line.startsWith("# ")) {
                if (i > 0) {
                    result.append("\n");
                }

                if (line.length() > "# ".length()) {
                    result.append(line.substring("# ".length()));
                }

                foundHeader = true;
            } else if (foundHeader && line.length() == 0) {
                result.append("\n");
            } else if (foundHeader) {
                readingHeader = false;
            }
        }

        return result.toString();
    }

    /**
     * Gets the header from the configuration
     *
     * @return Formatted header
     */
    public String buildHeader() {
        String header = options.getHeader();
        if (header == null) {
            return "";
        } else {
            StringBuilder newHeader = new StringBuilder();
            String[] lines = header.split("\r?\n", -1);
            boolean endOfLine = false;

            for (int i = lines.length - 1; i >= 0; --i) {
                newHeader.insert(0, "\n");
                if (endOfLine || lines[i].length() != 0) {
                    newHeader.insert(0, lines[i]);
                    newHeader.insert(0, "# ");
                    endOfLine = true;
                }
            }

            return newHeader.toString();
        }
    }

    public YAMLConfigurationOptions getOptions() {
        return options;
    }

    public boolean isEmpty() {
        return (root == null || root.isEmpty()) && (options.getHeader() == null || options.getHeader().isEmpty());
    }

    /**
     * Gets the node for the given path. If nodes along the path do not exist,
     * and create is true, the nodes will be created.
     *
     * Emulates a tree through the use of Maps
     *
     * @param path
     * @param create
     * @return If path exists, return the key-value node
     */
    private Map<String, Object> getNode(String path, boolean create) {
        String[] paths = getNodePath(path);

        Map<String, Object> node = root;
        for (String key : paths) {
            Object o = node.get(key);
            if (!(o instanceof Map)) {
                if (create) {
                    o = new LinkedHashMap<>();
                    node.put(key, o);
                } else {
                    return null;
                }
            }
            node = (Map<String, Object>) o;
        }

        return node;
    }

    /**
     * Recursively builds a simple map representation of the root map by
     * iterating through all nested maps and values
     *
     * @param node
     * @param path
     * @return A simple map of paths to values
     */
    private Map<String, Object> getNodeMap(Map<String, Object> node, String path) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (!path.isEmpty()) {
            path += ".";
        }

        for (String key : node.keySet()) {
            Object o = node.get(key);
            if (o instanceof Map) {
                map.putAll(getNodeMap((Map<String, Object>) o, path + key));
            } else {
                map.put(path + key, o);
            }
        }
        return map;
    }

}
