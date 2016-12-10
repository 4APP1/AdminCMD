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
package com.admincmd.commands.teleport;

import com.admincmd.commandapi.BaseCommand;
import com.admincmd.commandapi.CommandArgs;
import com.admincmd.commandapi.CommandHandler;
import com.admincmd.commandapi.CommandResult;
import com.admincmd.commandapi.HelpPage;
import com.admincmd.player.BukkitPlayer;
import com.admincmd.player.PlayerManager;
import com.admincmd.teleport.RequestManagerHere;
import com.admincmd.utils.Locales;
import org.bukkit.entity.Player;

@CommandHandler
public class TpaHereCommand {

    private final HelpPage tpahere = new HelpPage("tpahere", "[yes|no]", "<player>");

    @BaseCommand(command = "tpahere", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.teleport.requests.tpahere")
    public CommandResult executeTpaHere(Player sender, CommandArgs args) {
        if (tpahere.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        if (args.getLength() != 1) {
            return CommandResult.ERROR;
        }

        String arg = args.getString(0);

        BukkitPlayer s = PlayerManager.getPlayer(sender);

        if (arg.equalsIgnoreCase("yes")) {
            if (!sender.hasPermission("admincmd.teleport.requests.tpahere.accept")) {
                String msg = Locales.COMMAND_MESSAGES_NO_PERMISSION.getString().replaceAll("%perm%", "admincmd.teleport.requests.tpahere.accept");
                sender.sendMessage(msg);
                return CommandResult.SUCCESS;
            }
            RequestManagerHere.acceptRequest(s);
        } else if (arg.equalsIgnoreCase("no")) {
            if (!sender.hasPermission("admincmd.teleport.requests.tpahere.deny")) {
                String msg = Locales.COMMAND_MESSAGES_NO_PERMISSION.getString().replaceAll("%perm%", "admincmd.teleport.requests.tpahere.deny");
                sender.sendMessage(msg);
                return CommandResult.SUCCESS;
            }
            RequestManagerHere.denyRequest(s);
        } else {
            if (!args.isPlayer(0)) {
                return CommandResult.NOT_ONLINE;
            }

            if (!sender.hasPermission("admincmd.teleport.requests.tpahere.send")) {
                String msg = Locales.COMMAND_MESSAGES_NO_PERMISSION.getString().replaceAll("%perm%", "admincmd.teleport.requests.tpahere.send");
                sender.sendMessage(msg);
                return CommandResult.SUCCESS;
            }

            Player target = args.getPlayer(0);
            BukkitPlayer t = PlayerManager.getPlayer(target);
            RequestManagerHere.sendRequest(s, t);
        }
        return CommandResult.SUCCESS;
    }

}
