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
package com.admincmd.api.world;

public class Weather {

    public static final Weather CLEAR = new Weather("clear");

    public static final Weather RAIN = new Weather("rain");

    public static final Weather STORM = new Weather("storm");

    private final String name;

    private Weather(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Weather get(String weather) {
        if (weather.equalsIgnoreCase("clear"))
            return Weather.CLEAR;
        else if (weather.equalsIgnoreCase("rain"))
            return Weather.RAIN;
        else if (weather.equalsIgnoreCase("storm"))
            return Weather.STORM;
        else
            return null;
    }

}
