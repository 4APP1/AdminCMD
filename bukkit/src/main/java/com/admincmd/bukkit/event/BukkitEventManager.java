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
package com.admincmd.bukkit.event;

import com.admincmd.api.event.Event;
import com.admincmd.api.event.EventManager;
import com.admincmd.api.util.logger.DebugLogger;
import com.admincmd.bukkit.BukkitModule;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BukkitEventManager extends EventManager {

    private BukkitModule module;

    private List<Class<? extends Event>> eventList = new ArrayList<>();

    public BukkitEventManager(BukkitModule module) {
        this.module = module;
    }

    public void registerBukkitListener(Class<? extends BukkitListener> clazz) {
        BukkitListener listener = null;
        try {
            listener = clazz.getDeclaredConstructor(BukkitModule.class, BukkitEventManager.class).newInstance(module, this);
            listener.register();
        } catch (IllegalAccessException e) {
            DebugLogger.severe("Listener failed to be accessed: " + clazz.getName(), e);
        } catch (InstantiationException e) {
            DebugLogger.severe("Listener failed to be instantiated: " + clazz.getName(), e);
        } catch (InvocationTargetException e) {
            DebugLogger.severe("Listener failed to be invoked: " + clazz.getName(), e);
        } catch (NoSuchMethodException e) {
            DebugLogger.severe("Listener does not contain method: " + clazz.getName(), e);
        }
    }

    public void registerPluginEvent(Class<? extends Event> event) {
        if (!eventList.contains(event)) {
            eventList.add(event);
        }
    }

    public boolean isEventRegistered(Class<? extends Event> event) {
        return eventList.contains(event);
    }

}
