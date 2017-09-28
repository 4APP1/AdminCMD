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
package com.admincmd.commands.player;

import com.admincmd.commandapi.BaseCommand;
import com.admincmd.commandapi.CommandArgs;
import com.admincmd.commandapi.CommandHandler;
import com.admincmd.commandapi.CommandResult;
import org.bukkit.entity.Player;

@CommandHandler
public class OpeninvCommand {

    @BaseCommand(command = "openinv", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.player.openinv", aliases = "invsee,oi", helpArguments = {"", "<-p player>"})
    public CommandResult executeOpenInv(Player sender, CommandArgs args) {
        if (args.isEmpty()) {
            sender.openInventory(sender.getInventory());
            return CommandResult.SUCCESS;
        }

        if (args.hasFlag("p")) {
            if (!sender.hasPermission("admincmd.player.openinv.other")) {
                return CommandResult.NO_PERMISSION_OTHER;
            }

            CommandArgs.Flag flag = args.getFlag("p");
            if (!flag.isPlayer()) {
                return CommandResult.NOT_ONLINE;
            }

            Player target = flag.getPlayer();
            sender.openInventory(target.getInventory());
            return CommandResult.SUCCESS;
        }

        return CommandResult.ERROR;

    }

}
