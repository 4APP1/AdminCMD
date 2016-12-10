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
package com.admincmd.commands.server;

import com.admincmd.commandapi.BaseCommand;
import com.admincmd.commandapi.CommandArgs;
import com.admincmd.commandapi.CommandHandler;
import com.admincmd.commandapi.CommandResult;
import com.admincmd.commandapi.HelpPage;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import com.admincmd.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandHandler
public class IpCommand {

    private final HelpPage ip = new HelpPage("ip", "<-p player>");

    @BaseCommand(command = "ip", sender = BaseCommand.Sender.CONSOLE, permission = "admincmd.server.ip")
    public CommandResult executeIp(CommandSender sender, CommandArgs args) {
        if (ip.sendHelp(sender, args)) {
            return CommandResult.NO_PERMISSION_OTHER;
        }

        if (args.hasFlag("p")) {
            CommandArgs.Flag flag = args.getFlag("p");
            if (!flag.isPlayer()) {
                return CommandResult.NOT_ONLINE;
            }

            Player target = flag.getPlayer();
            String ip = target.getAddress().getAddress().toString();
            String msg = Locales.PLAYER_IP_OTHER.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target)).replaceAll("%ip%", ip);
            return Messager.sendMessage(sender, msg, Messager.MessageType.INFO);
        }
        return CommandResult.ERROR;
    }

    @BaseCommand(command = "ip", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.server.ip")
    public CommandResult executeIpPlayer(CommandSender sender, CommandArgs args) {
        return executeIp(sender, args);
    }

}
