package com.admincmd.core.util.tasks;

import com.admincmd.core.world.ACWorld;

public class WeatherReset implements Runnable {

    private final ACWorld world;

    public WeatherReset(ACWorld world) {
        this.world = world;
    }

    @Override
    public void run() {
        if (world.isWeatherPaused()) {
            world.setWeather(world.getWeatherPaused());
        }
    }

}
