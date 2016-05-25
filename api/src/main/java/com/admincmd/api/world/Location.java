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

import com.admincmd.api.AdminCMD;

public class Location {

    private World world;
    private double x;
    private double y;
    private double z;
    private float yaw = 0;
    private float pitch = 0;
    private float roll = 0;

    public Location(World world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(World world, double x, double y, double z, float yaw, float pitch, float roll) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public int getBlockX() {
        return (int) x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public int getBlockY() {
        return (int) y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public int getBlockZ() {
        return (int) z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public static String serialize(Location location) {
        return location.world.getName() + ";" + location.x + ";" +
                location.y + ";" + location.z + ";" +
                location.yaw + ";" + location.pitch + ";" +
                location.roll + ";";
    }

    public static Location deserialize(String string) {
        String[] components = string.split(";");
        World world = AdminCMD.getServer().getWorld(components[0]);
        double x = Double.parseDouble(components[1]);
        double y = Double.parseDouble(components[2]);
        double z = Double.parseDouble(components[3]);
        float yaw = Float.parseFloat(components[4]);
        float pitch = Float.parseFloat(components[5]);
        float roll = Float.parseFloat(components[6]);
        return new Location(world, x, y, z, yaw, pitch, roll);
    }

}
