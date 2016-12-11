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
import com.admincmd.world.WorldManager;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandHandler
public class TimeCommand {

    @BaseCommand(command = "time", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.world.time", helpArguments = {"day <-w world>", "night <-w world>", "<time> <-w world>", "pause <-w world>", "unpause <-w world>"})
    public CommandResult executeTime(Player sender, CommandArgs args) {
        if (args.getLength() < 1) {
            return CommandResult.ERROR;
        }

        World target = sender.getWorld();
        if (args.hasFlag("w")) {
            if (!args.getFlag("w").isWorld()) {
                return CommandResult.NOT_A_WORLD;
            }
            if (!sender.hasPermission("admincmd.world.time.other")) {
                return CommandResult.NO_PERMISSION_OTHER;
            }
            target = args.getFlag("w").getWorld().getWorld();
        }

        if (args.getString(0).equalsIgnoreCase("day")) {
            long time = 0;
            target.setTime(time);
            String msg = Locales.WORLD_DAY_SET.getString().replaceAll("%world%", target.getName());
            return Messager.sendMessage(sender, msg, Messager.MessageType.INFO);
        } else if (args.getString(0).equalsIgnoreCase("night")) {
            long time = 13100;
            target.setTime(time);
            String msg = Locales.WORLD_NIGHT_SET.getString().replaceAll("%world%", target.getName());
            return Messager.sendMessage(sender, msg, Messager.MessageType.INFO);
        } else if (args.isInteger(0)) {
            long time = args.getInt(0);
            target.setTime(time);
            String msg = Locales.WORLD_TIME_SET.getString().replaceAll("%world%", target.getName()).replaceAll("%time%", time + "");
            return Messager.sendMessage(sender, msg, Messager.MessageType.INFO);
        } else if (args.getString(0).equalsIgnoreCase("pause")) {
            ACWorld w = WorldManager.getWorld(target);
            w.pauseTime();
            String msg = Locales.WORLD_TIME_PAUSED.getString().replaceAll("%world%", target.getName());
            return Messager.sendMessage(sender, msg, Messager.MessageType.INFO);
        } else if (args.getString(0).equalsIgnoreCase("unpause")) {
            ACWorld w = WorldManager.getWorld(target);
            w.unPauseTime();
            String msg = Locales.WORLD_TIME_UNPAUSED.getString().replaceAll("%world%", target.getName());
            return Messager.sendMessage(sender, msg, Messager.MessageType.INFO);
        }

        return CommandResult.ERROR;
    }
}
