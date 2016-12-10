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
import com.admincmd.player.PlayerManager;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandHandler
public class ReplyCommand {

    private final HelpPage reply = new HelpPage("reply", "", "<message>");

    @BaseCommand(command = "reply", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.player.reply", aliases = "")
    public CommandResult executeReply(Player sender, CommandArgs args) {
        if (reply.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        String lastMsg = PlayerManager.getPlayer(sender).getLastMsg();
        if (lastMsg != null && args.getLength() >= 1) {
            Player target = Bukkit.getPlayer(lastMsg);
            if (target == null) {
                return CommandResult.NOT_ONLINE;
            }

            String message = "";
            for (String temp : args.getArgs()) {
                message += temp + " ";
            }

            PlayerManager.getPlayer(target).setLastMsg(sender.getName());
            String msgSpy = Locales.PLAYER_MSG_FORMAT.getString().replaceAll("%sender%", sender.getDisplayName());
            msgSpy = msgSpy.replaceAll("%target%", target.getDisplayName());
            msgSpy = msgSpy.replaceAll("%message%", message);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (PlayerManager.getPlayer(p).isSpy()) {
                    Messager.sendMessage(p, msgSpy, Messager.MessageType.NONE);
                }
            }

            String msgSender = Locales.PLAYER_MSG_FORMAT.getString().replaceAll("%sender%", "You");
            msgSender = msgSender.replaceAll("%target%", target.getDisplayName());
            msgSender = msgSender.replaceAll("%message%", message);
            String msgTarget = Locales.PLAYER_MSG_FORMAT.getString().replaceAll("%target%", "You");
            msgTarget = msgTarget.replaceAll("%sender%", sender.getDisplayName());
            msgTarget = msgTarget.replaceAll("%message%", message);
            Messager.sendMessage(target, msgTarget, Messager.MessageType.NONE);
            return Messager.sendMessage(sender, msgSender, Messager.MessageType.NONE);
        }

        return CommandResult.ERROR;

    }

}
