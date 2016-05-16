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
package com.admincmd.core.world;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.world.World;
import com.admincmd.core.util.tasks.TimeReset;
import com.admincmd.core.util.tasks.WeatherReset;

import java.util.UUID;

public abstract class ACWorld extends SQLWorld implements World {

    public ACWorld(UUID uuid, String name) {
        super(uuid, name);

        AdminCMD.getServer().scheduleSyncRepeatingTask(new WeatherReset(this), 20 * 3, 20 * 3);
        AdminCMD.getServer().scheduleSyncRepeatingTask(new TimeReset(this), 20 * 3, 20 * 3);
    }

    public String getWeather() {
        if (isRaining() && isThundering()) {
            return "STORM";
        } else if (isRaining() && !isThundering()) {
            return "RAIN";
        } else {
            return "CLEAR";
        }
    }

    public void setWeather(String weather) {
        if (weather.equalsIgnoreCase("STORM")) {
            setRaining(true);
            setThundering(true);
        } else if (weather.equalsIgnoreCase("RAIN")) {
            setRaining(true);
            setThundering(false);
        } else if (weather.equalsIgnoreCase("CLEAR")) {
            setRaining(false);
            setThundering(false);
        }
    }

    public void setWeatherPaused(boolean paused) {
        this.weatherPaused = paused;
        weatherPausedMoment = getWeather();
    }

    public void setTimePaused(boolean paused) {
        this.timePaused = paused;
        timePausedMoment = getTime();
    }

}
