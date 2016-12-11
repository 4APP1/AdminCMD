/*
 * This file is part of AdminCMD
 * Copyright (C) 2017 AdminCMD Team
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
package com.admincmd.commands.world;

import com.admincmd.commandapi.BaseCommand;
import com.admincmd.commandapi.CommandArgs;
import com.admincmd.commandapi.CommandHandler;
import com.admincmd.commandapi.CommandResult;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import com.admincmd.world.ACWorld;
import org.bukkit.entity.Player;

@CommandHandler
public class SunCommand {

    @BaseCommand(command = "sun", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.world.sun", helpArguments = {"", "<-w world>"})
    public CommandResult executeSun(Player sender, CommandArgs args) {
        if (args.isEmpty()) {
            sender.getWorld().setStorm(false);
            sender.getWorld().setThundering(false);
            String msg = Locales.WORLD_WEATHER_CLEAR.getString().replaceAll("%world%", sender.getWorld().getName());
            return Messager.sendMessage(sender, msg, Messager.MessageType.INFO);
        }

        if (args.hasFlag("w")) {
            CommandArgs.Flag flag = args.getFlag("w");
            if (!flag.isWorld()) {
                return CommandResult.NOT_A_WORLD;
            }

            if (!sender.hasPermission("admincmd.world.sun.other")) {
                return CommandResult.NO_PERMISSION_OTHER;
            }

            ACWorld world = flag.getWorld();
            world.getWorld().setStorm(false);
            world.getWorld().setThundering(false);
            String msg = Locales.WORLD_WEATHER_CLEAR.getString().replaceAll("%world%", world.getWorld().getName());
            return Messager.sendMessage(sender, msg, Messager.MessageType.INFO);
        }

        return CommandResult.ERROR;
    }

}
