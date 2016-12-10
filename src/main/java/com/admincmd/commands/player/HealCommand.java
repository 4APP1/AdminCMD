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
import com.admincmd.commandapi.HelpPage;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import com.admincmd.utils.Utils;
import org.bukkit.entity.Player;

@CommandHandler
public class HealCommand {

    private final HelpPage heal = new HelpPage("heal", "", "<-p player>");

    @BaseCommand(command = "heal", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.player.heal", aliases = "pheal")
    public CommandResult executeHeal(Player sender, CommandArgs args) {
        if (heal.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        if (args.isEmpty()) {
            sender.setHealth(sender.getMaxHealth());
            return Messager.sendMessage(sender, Locales.PLAYER_HEAL_SELF, Messager.MessageType.INFO);
        }

        if (args.hasFlag("p")) {
            if (!sender.hasPermission("admincmd.player.heal.other")) {
                return CommandResult.NO_PERMISSION_OTHER;
            }

            CommandArgs.Flag flag = args.getFlag("p");
            if (!flag.isPlayer()) {
                return CommandResult.NOT_ONLINE;
            }

            Player target = flag.getPlayer();
            target.setHealth(target.getMaxHealth());
            String msgSender = Locales.PLAYER_HEAL_OTHER.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target));
            Messager.sendMessage(sender, msgSender, Messager.MessageType.INFO);
            return Messager.sendMessage(target, Locales.PLAYER_HEAL_SELF, Messager.MessageType.INFO);
        }

        return CommandResult.ERROR;
    }

}
