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
package com.admincmd.commands;

import com.admincmd.Main;
import com.admincmd.commandapi.BaseCommand;
import com.admincmd.commandapi.CommandArgs;
import com.admincmd.commandapi.CommandArgs.Flag;
import com.admincmd.commandapi.CommandHandler;
import com.admincmd.commandapi.CommandResult;
import com.admincmd.commandapi.HelpPage;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import com.admincmd.utils.Utils;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@CommandHandler
public class ServerCommands {

    private final HelpPage reload = new HelpPage("acreload", "", "<plugin>");
    private final HelpPage uuid = new HelpPage("uuid", "<-p player>");
    private final HelpPage ip = new HelpPage("ip", "<-p player>");

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

    @BaseCommand(command = "uuid", sender = BaseCommand.Sender.CONSOLE, permission = "admincmd.server.uuid")
    public CommandResult executeUUID(CommandSender sender, CommandArgs args) {
        if (uuid.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        if (!args.hasFlag("p")) {
            return CommandResult.ERROR;
        }

        Flag f = args.getFlag("p");
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

    @BaseCommand(command = "ip", sender = BaseCommand.Sender.CONSOLE, permission = "admincmd.player.ip")
    public CommandResult executeIp(CommandSender sender, CommandArgs args) {
        if (ip.sendHelp(sender, args)) {
            return CommandResult.NO_PERMISSION_OTHER;
        }

        if (args.hasFlag("p")) {
            Flag flag = args.getFlag("p");
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

}
