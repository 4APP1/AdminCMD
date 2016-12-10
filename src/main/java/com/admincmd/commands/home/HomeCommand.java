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
package com.admincmd.commands.home;

import com.admincmd.commandapi.BaseCommand;
import com.admincmd.commandapi.CommandArgs;
import com.admincmd.commandapi.CommandHandler;
import com.admincmd.commandapi.CommandResult;
import com.admincmd.commandapi.HelpPage;
import com.admincmd.home.Home;
import com.admincmd.home.HomeManager;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import com.google.common.base.Joiner;
import org.bukkit.entity.Player;

@CommandHandler
public class HomeCommand {

    private final HelpPage home = new HelpPage("home", "", "<name>");

    @BaseCommand(command = "home", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.home.tp")
    public CommandResult executeHome(Player p, CommandArgs args) {
        if (home.sendHelp(p, args)) {
            return CommandResult.SUCCESS;
        }

        if (args.getLength() > 1) {
            return CommandResult.ERROR;
        }

        if (args.isEmpty()) {
            String homes = Locales.HOME_HOME.getString() + " (" + HomeManager.getHomes(p).size() + "): §6" + Joiner.on(", ").join(HomeManager.getHomes(p).keySet());
            return Messager.sendMessage(p, homes, Messager.MessageType.INFO);
        } else {
            Home h = HomeManager.getHome(p, args.getString(0));
            if (h != null) {
                h.teleport();
                return CommandResult.SUCCESS;
            } else {
                return Messager.sendMessage(p, Locales.HOME_NOHOME, Messager.MessageType.ERROR);
            }
        }
    }

}
