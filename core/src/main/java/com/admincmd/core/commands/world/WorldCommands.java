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
package com.admincmd.core.commands.world;

import com.admincmd.api.command.CommandHandler;
import com.admincmd.api.command.CommandResult;
import com.admincmd.api.command.CommandSource;
import com.admincmd.api.command.parsing.Arguments;
import com.admincmd.api.entity.player.Player;
import com.admincmd.api.world.Weather;
import com.admincmd.api.world.World;

public class WorldCommands {

    @CommandHandler(aliases = "ac_weather weather", permission = "admincmd.world.weather", description = "Changes the weather")
    public CommandResult executeWeather(CommandSource source, Arguments arguments) {
        if (arguments.argsSize() != 1) {
            return CommandResult.FAILURE;
        }

        World world = null;
        if (source instanceof Player) {
            world = ((Player) source).getWorld();
        }

        if (arguments.hasFlag("w")) {
            world = arguments.getFlag("w").getWorld();
        }

        if (world != null) {
            Weather weather = Weather.get(arguments.get(0).toLowerCase());
            if (weather != null) {
                world.setWeather(weather);
                return new CommandResult("Weather has been changed to " + weather.getName());
            } else if (arguments.get(0).equalsIgnoreCase("pause")) {
                world.setWeatherPaused(true);
                return new CommandResult("Weather has been paused on " + world.getWeather().getName());
            } else if (arguments.get(0).equalsIgnoreCase("unpause")) {
                world.setWeatherPaused(false);
                return new CommandResult("Weather has been unpaused");
            } else {
                return new CommandResult("Specified weather does not exist");
            }
        } else {
            return new CommandResult("Specified world does not exist");
        }
    }

    @CommandHandler(aliases = "ac_time time", permission = "admincmd.world.time", description = "Changes the time")
    public CommandResult executeTime(CommandSource source, Arguments arguments) {
        if (arguments.argsSize() != 1) {
            return CommandResult.FAILURE;
        }

        World world = null;
        if (source instanceof Player) {
            world = ((Player) source).getWorld();
        }

        if (arguments.hasFlag("w")) {
            world = arguments.getFlag("w").getWorld();
        }

        if (world != null) {
            String timeString = arguments.get(0).toLowerCase();
            if (timeString.equalsIgnoreCase("day")) {
                world.setTime(0);
                return new CommandResult("Time has been changed to " + timeString);
            } else if (timeString.equalsIgnoreCase("night")) {
                world.setTime(13000);
                return new CommandResult("Time has been changed to " + timeString);
            } else if (timeString.equalsIgnoreCase("pause")) {
                world.setTimePaused(true);
                return new CommandResult("Time has been paused at " + world.getTime());
            } else if (timeString.equalsIgnoreCase("unpause")) {
                world.setTimePaused(false);
                return new CommandResult("Time has been unpaused");
            } else {
                try {
                    long timeLong = arguments.getInt(0);
                    world.setTime(timeLong);
                    return new CommandResult("Time has been changed to " + timeLong);
                } catch (NumberFormatException e) {
                    return new CommandResult("Specified time does not exist");
                }
            }
        } else {
            return new CommandResult("Specified world does not exist");
        }
    }

    @CommandHandler(aliases = "ac_spawn spawn spawntp", permission = "admincmd.world.spawn", description = "Tp to world spawn")
    public CommandResult executeSpawn(CommandSource source, Arguments arguments) {
        if (arguments.argsSize() > 0) {
            return CommandResult.FAILURE;
        }

        Player player = null;
        World world = null;
        if (source instanceof Player) {
            player = (Player) source;
            world = player.getWorld();
        }

        if (arguments.hasFlag("p")) {
            player = arguments.getFlag("p").getPlayer();
        }

        if (arguments.hasFlag("w")) {
            world = arguments.getFlag("w").getWorld();
        }

        if (world != null && player != null) {
            player.teleport(world.getSpawnLocation());
            return new CommandResult("Teleported " + player.getName() + " to " + world.getName() + "spawn");
        } else {
            return new CommandResult("Specified world or player does not exist");
        }
    }

    @CommandHandler(aliases = "ac_setspawn setspawn", permission = "admincmd.world.setspawn", description = "Set the world spawn")
    public CommandResult executeSetspawn(CommandSource source, Arguments arguments) {
        if (arguments.argsSize() > 0) {
            return CommandResult.FAILURE;
        }

        Player player = null;
        World world = null;
        if (source instanceof Player) {
            player = (Player) source;
            world = player.getWorld();
        } else {
            return new CommandResult("Only a player can run this command");
        }

        if (world != null) {
            world.setSpawnLocation(player.getLocation());
            return new CommandResult("Spawn set for the world " + world.getName());
        } else {
            return CommandResult.FAILURE;
        }
    }

}
