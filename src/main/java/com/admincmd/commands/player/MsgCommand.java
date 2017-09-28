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
import com.admincmd.player.BukkitPlayer;
import com.admincmd.player.PlayerManager;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import com.admincmd.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandHandler
public class MsgCommand {
    
    @BaseCommand(command = "msg", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.player.msg", aliases = "pm,message", helpArguments = {"<player> <message>"})
    public CommandResult executeMsg(Player sender, CommandArgs args) {
        if (args.getLength() >= 2) {
            if (!args.isPlayer(0)) {
                return CommandResult.NOT_ONLINE;
            }
            
            String message = "";
            for (int i = 1; i < args.getLength(); i++) {
                message += args.getString(i) + " ";
            }
            
            Player target = args.getPlayer(0);
            BukkitPlayer se = PlayerManager.getPlayer(sender);
            PlayerManager.getPlayer(target).setLastMsg(se.getId());
            String msgSpy = Locales.PLAYER_MSG_FORMAT.getString().replaceAll("%sender%", Utils.replacePlayerPlaceholders(sender));
            msgSpy = msgSpy.replaceAll("%target%", Utils.replacePlayerPlaceholders(target));
            msgSpy = msgSpy.replaceAll("%message%", message);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (PlayerManager.getPlayer(p).isSpy()) {
                    Messager.sendMessage(p, msgSpy, Messager.MessageType.NONE);
                }
            }
            
            String msgSender = Locales.PLAYER_MSG_FORMAT.getString().replaceAll("%sender%", Utils.replacePlayerPlaceholders(sender));
            msgSender = msgSender.replaceAll("%target%", Utils.replacePlayerPlaceholders(target));
            msgSender = msgSender.replaceAll("%message%", message);
            String msgTarget = Locales.PLAYER_MSG_FORMAT.getString().replaceAll("%target%", Utils.replacePlayerPlaceholders(target));
            msgTarget = msgTarget.replaceAll("%sender%", Utils.replacePlayerPlaceholders(sender));
            msgTarget = msgTarget.replaceAll("%message%", message);
            Messager.sendMessage(target, msgTarget, Messager.MessageType.NONE);
            return Messager.sendMessage(sender, msgSender, Messager.MessageType.NONE);
        }
        
        return CommandResult.ERROR;
        
    }
    
}
