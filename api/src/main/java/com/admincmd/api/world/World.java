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

import java.util.UUID;

public interface World {

    public UUID getUUID();

    public String getName();

    public boolean isRaining();

    public void setRaining(boolean raining);

    public boolean isThundering();

    public void setThundering(boolean thundering);

    public int getRainTime();

    public void setRainTime(int seconds);

    public int getThunderTime();

    public void setThunderTime(int seconds);

    public long getTime();

    public void setTime(long time);

    public Location getSpawnLocation();

    public void setSpawnLocation(Location location);

    public Weather getWeather();

    public void setWeather(Weather weather);

    public boolean isWeatherPaused();

    public Weather getWeatherPaused();

    public void setWeatherPaused(boolean paused);

    public boolean isTimePaused();

    public long getTimePaused();

    public void setTimePaused(boolean paused);

}
