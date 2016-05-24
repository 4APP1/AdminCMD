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
package com.admincmd.core.commands;

import com.admincmd.api.command.CommandHandler;
import com.admincmd.api.command.CommandResult;
import com.admincmd.api.command.CommandSource;
import com.admincmd.api.command.parsing.Arguments;
import com.admincmd.api.command.source.ConsoleSource;
import com.admincmd.api.entity.player.Player;
import com.admincmd.api.util.message.Color;
import com.admincmd.api.world.Weather;
import com.admincmd.api.world.World;

public class WorldCommands {

    @CommandHandler(aliases = "ac_weather weather", permission = "admincmd.world.weather", description = "Changes the weather")
    public CommandResult executeWeather(CommandSource source, Arguments arguments) {
        if (arguments.argsSize() < 1) {
            return CommandResult.builder().addColor(Color.RED).addText("Additional arguments required!").build();
        }

        World w = null;
        if (source instanceof ConsoleSource) {
            if (arguments.hasFlag("w")) {
                w = arguments.getFlag("w").getWorld();
            } else {
                return CommandResult.builder().addColor(Color.RED).addText("Must specifiy a world when running from console!").build();
            }
        } else {
            w = ((Player) source).getWorld();
        }

        if (w != null) {
            String arg = arguments.get(0).toLowerCase();
            if (Weather.get(arg) != null) {
                w.setWeather(Weather.get(arg));
            } else if (arg.equalsIgnoreCase("pause")) {
                w.setWeatherPaused(true);
            } else if (arg.equalsIgnoreCase("unpause")) {
                w.setWeatherPaused(false);
            } else {
                return CommandResult.builder().addColor(Color.RED).addText("Specified weather type does not exist!").build();
            }
            return CommandResult.builder().addText("The weather has been changed to " + arg + "!").build();
        } else {
            return CommandResult.builder().addColor(Color.RED).addText("Specified world does not exist!").build();
        }
    }

}
