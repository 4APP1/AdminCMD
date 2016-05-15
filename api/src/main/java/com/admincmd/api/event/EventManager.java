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
package com.admincmd.api.event;

import com.admincmd.api.Core;
import com.admincmd.api.Identifiable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private Core core;

    private final Map<Identifiable, List<Listener>> listenerMap = new HashMap<>();

    public EventManager(Core core) {
        this.core = core;
    }

    public void registerClass(Class<?> clazz, Identifiable identifiable) {
        if (identifiable == null || clazz == null) {
            return;
        }

        if (!listenerMap.containsKey(identifiable)) {
            listenerMap.put(identifiable, new ArrayList<>());
        }

        List<Listener> listeners = listenerMap.get(identifiable);
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(EventHandler.class)) {
                Class<?> parameter = m.getParameterTypes()[0];
                if (Event.class.isAssignableFrom(parameter)) {
                    Class<? extends Event> event = parameter.asSubclass(Event.class);

                    Listener listener = new Listener(event, m);
                    if (!listeners.contains(listener)) {
                        core.getRegistry().registerEvent(event);
                        listeners.add(listener);
                    }
                }
            }
        }

        listenerMap.put(identifiable, listeners);
    }

    public void registerListener(Listener listener, Identifiable identifiable) {
        if (identifiable == null || listener == null) {
            return;
        }

        if (!listenerMap.containsKey(identifiable)) {
            listenerMap.put(identifiable, new ArrayList<>());
        }

        List<Listener> listeners = listenerMap.get(identifiable);
        if (!listeners.contains(listener)) {
            core.getRegistry().registerEvent(listener.getEvent());
            listeners.add(listener);
        }
    }

    public void unregisterAll(Identifiable identifiable) {
        if (identifiable == null) {
            return;
        }

        if (listenerMap.containsKey(identifiable)) {
            listenerMap.remove(identifiable);
        }
    }

    public void callEvent(Event event) {
        for (Identifiable i : listenerMap.keySet()) {
            List<Listener> listeners = listenerMap.get(i);
            for (Listener l : listeners) {
                if (event.getClass().isAssignableFrom(l.getEvent())) {
                    l.execute(event);
                }
            }
        }
    }

}
