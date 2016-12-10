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
import org.bukkit.entity.Player;

@CommandHandler
public class EdithomeCommand {

    private final HelpPage edithome = new HelpPage("edithome", "<name>");

    @BaseCommand(command = "edithome", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.home.tp")
    public CommandResult executeEdithome(Player sender, CommandArgs args) {
        if (edithome.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        if (args.getLength() != 1) {
            return CommandResult.ERROR;
        }

        Home h = HomeManager.getHome(sender, args.getString(0));

        if (h == null) {
            return Messager.sendMessage(sender, Locales.HOME_NOHOME, Messager.MessageType.ERROR);
        }

        h.updateLocation(sender.getLocation());
        return Messager.sendMessage(sender, Locales.HOME_UPDATED, Messager.MessageType.INFO);
    }

}
