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
import com.admincmd.player.BukkitPlayer;
import com.admincmd.player.PlayerManager;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import com.admincmd.utils.Utils;
import org.bukkit.entity.Player;

@CommandHandler
public class MuteCommand {

    private final HelpPage mute = new HelpPage("mute", "", "<player>");

    @BaseCommand(command = "mute", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.player.mute", aliases = "unmute")
    public CommandResult executeMute(Player sender, CommandArgs args) {
        if (mute.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        if (args.getLength() == 1) {
            if (!args.isPlayer(0)) {
                return CommandResult.NOT_ONLINE;
            }

            BukkitPlayer p = PlayerManager.getPlayer(args.getPlayer(0));
            p.setMuted(!p.isMuted());

            String s = p.isMuted() ? Locales.COMMAND_MESSAGES_ENABLED.getString() : Locales.COMMAND_MESSAGES_DISABLED.getString();
            String msgTarget = Locales.PLAYER_MUTE_TOGGLED_SELF.getString().replaceAll("%status%", s);
            String msgSender = Locales.PLAYER_MUTE_TOGGLED_OTHER.getString().replaceAll("%status%", s).replaceAll("%player%", Utils.replacePlayerPlaceholders(args.getPlayer(0)));
            Messager.sendMessage(p.getPlayer(), msgTarget, Messager.MessageType.INFO);
            return Messager.sendMessage(sender, msgSender, Messager.MessageType.INFO);
        }

        return CommandResult.ERROR;
    }

}
