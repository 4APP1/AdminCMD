package com.admincmd.core.util.tasks;

import com.admincmd.api.world.World;

public class TimeReset implements Runnable {

    private final World world;

    public TimeReset(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        if (world.isTimePaused()) {
            world.setTime(world.getTimePaused());
        }
    }

}
