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

import com.admincmd.Main;
import com.admincmd.commandapi.BaseCommand;
import com.admincmd.commandapi.CommandArgs;
import com.admincmd.commandapi.CommandHandler;
import com.admincmd.commandapi.CommandResult;
import com.admincmd.commandapi.HelpPage;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

@CommandHandler
public class ReloadCommand {

    private final HelpPage reload = new HelpPage("acreload", "", "<plugin>");

    @BaseCommand(command = "acreload", sender = BaseCommand.Sender.CONSOLE, aliases = "reload")
    public CommandResult executeConsole(CommandSender sender, CommandArgs args) {
        if (reload.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }
        if (args.isEmpty()) {
            Main.getInstance().getServer().reload();
            return Messager.sendMessage(sender, Locales.SERVER_RELOAD_FULL, Messager.MessageType.INFO);
        }

        if (args.getLength() != 1) {
            return CommandResult.ERROR;
        }

        Plugin pl = Main.getInstance().getServer().getPluginManager().getPlugin(args.getString(0));
        if (pl == null) {
            return Messager.sendMessage(sender, Locales.SERVER_RELOAD_NOT_FOUND, Messager.MessageType.ERROR);
        }
        Main.getInstance().getServer().getPluginManager().disablePlugin(pl);
        Main.getInstance().getServer().getPluginManager().enablePlugin(pl);
        return Messager.sendMessage(sender, Locales.SERVER_RELOAD_SINGLE, Messager.MessageType.INFO);
    }

    @BaseCommand(command = "acreload", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.server.reload", aliases = "reload")
    public CommandResult executePlayer(CommandSender sender, CommandArgs args) {
        return executeConsole(sender, args);
    }

}
