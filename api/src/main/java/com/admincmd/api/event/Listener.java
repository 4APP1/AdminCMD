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

import com.admincmd.api.util.logger.DebugLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Listener {

    private Class<? extends Event> clazz;

    private Method method;

    public Listener(Class<? extends Event> clazz) {
        this(clazz, null);
    }

    public Listener(Class<? extends Event> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public void execute(Event event) {
        if (method != null) {
            try {
                Object o = method.getDeclaringClass().newInstance();
                method.invoke(o, event);
            } catch (IllegalAccessException e) {
                DebugLogger.severe("Listener failed to be accessed: " + clazz, e);
            } catch (InstantiationException e) {
                DebugLogger.severe("Listener failed to be instantiated: " + clazz, e);
            } catch (InvocationTargetException e) {
                DebugLogger.severe("Listener failed to be invoked: " + clazz, e);
            }
        }
    }

    public Class<? extends Event> getEvent() {
        return clazz;
    }

}
