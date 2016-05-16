package com.admincmd.core.util.tasks;

import com.admincmd.core.world.ACWorld;

public class TimeReset implements Runnable {

    private final ACWorld world;

    public TimeReset(ACWorld world) {
        this.world = world;
    }

    @Override
    public void run() {
        if (world.isTimePaused()) {
            world.setTime(world.getTimePaused());
        }
    }

}
