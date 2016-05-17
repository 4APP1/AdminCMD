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

import com.admincmd.api.addon.Addon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class AddonClassLoader extends URLClassLoader {

    private final Map<String, Class<?>> classMap = new LinkedHashMap<>();

    public AddonClassLoader(File file, ClassLoader parent) throws IOException, ClassNotFoundException {
        super(new URL[] {file.toURI().toURL()}, parent);

        JarInputStream jarStream = new JarInputStream(new FileInputStream(file));
        JarEntry entry;
        while ((entry = jarStream.getNextJarEntry()) != null) {
            String classPath = entry.getName();
            if (classPath.endsWith(".class")) {
                String className = classPath.substring(0, classPath.lastIndexOf(".")).replaceAll("/", ".");
                Class<?> clazz = Class.forName(className, true, this);
                classMap.put(className, clazz);
            }
        }
    }

    public Class<? extends Addon> getAddonClass() {
        Class<? extends Addon> addonClazz = null;
        for (String n : classMap.keySet()) {
            Class<?> clazz = classMap.get(n);
            if (Addon.class.isAssignableFrom(clazz)) {
                addonClazz = clazz.asSubclass(Addon.class);
            }
        }
        return addonClazz;
    }

}
