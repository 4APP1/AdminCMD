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
import java.util.UUID;
import org.bukkit.command.CommandSender;

@CommandHandler
public class UuidCommand {

    private final HelpPage uuid = new HelpPage("uuid", "<-p player>");

    @BaseCommand(command = "uuid", sender = BaseCommand.Sender.CONSOLE, permission = "admincmd.server.uuid")
    public CommandResult executeUUID(CommandSender sender, CommandArgs args) {
        if (uuid.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        if (!args.hasFlag("p")) {
            return CommandResult.ERROR;
        }

        CommandArgs.Flag f = args.getFlag("p");
        if (!f.isPlayer()) {
            return CommandResult.NOT_ONLINE;
        }

        UUID uuid = f.getPlayer().getUniqueId();
        String msg = Locales.SERVER_UUID.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(f.getPlayer())).replaceAll("%uuid%", uuid.toString());
        return Messager.sendMessage(sender, msg, Messager.MessageType.INFO);
    }

    @BaseCommand(command = "uuid", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.server.uuid")
    public CommandResult executePlayerUUID(CommandSender sender, CommandArgs args) {
        return executeUUID(sender, args);
    }

}
