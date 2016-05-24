package com.admincmd.core.util.tasks;

import com.admincmd.api.world.World;

public class WeatherReset implements Runnable {

    private final World world;

    public WeatherReset(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        if (world.isWeatherPaused()) {
            world.setWeather(world.getWeatherPaused());
        }
    }

}
