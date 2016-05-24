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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class ACAddonClassLoader extends URLClassLoader {

    private final Map<File, List<Class>> files;

    public ACAddonClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
        files = new HashMap<>();
    }

    public void addFile(File file) throws IOException, ClassNotFoundException {
        addURL(file.toURI().toURL());

        JarEntry entry;
        List<Class> classes = new ArrayList<>();
        JarInputStream jar = new JarInputStream(new FileInputStream(file));
        while ((entry = jar.getNextJarEntry()) != null) {
            String className = entry.getName().replaceAll("\\.", "/");
            className = className.substring(0, className.lastIndexOf("."));
            classes.add(Class.forName(className, true, this));
        }

        files.put(file, classes);
    }

    public Class<?> findClass(String name) throws ClassNotFoundException {
        return loadClass(name);
    }

    public Class<? extends Addon> getAddonClass(File file) {
        if (files.containsKey(file)) {
            List<Class> classes = files.get(file);
            for (Class<?> c : classes) {
                if (Addon.class.isAssignableFrom(c)) {
                    return c.asSubclass(Addon.class);
                }
            }
        }
        return null;
    }

}
