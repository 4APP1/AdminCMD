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
package com.admincmd.commands.warps;

import com.admincmd.commandapi.BaseCommand;
import com.admincmd.commandapi.CommandArgs;
import com.admincmd.commandapi.CommandHandler;
import com.admincmd.commandapi.CommandResult;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import com.admincmd.warp.Warp;
import com.admincmd.warp.WarpManager;
import com.google.common.base.Joiner;
import org.bukkit.entity.Player;

@CommandHandler
public class WarpCommand {

    @BaseCommand(command = "warp", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.warp.tp", helpArguments = {"", "<name>"})
    public CommandResult executeWarp(Player p, CommandArgs args) {
        if (args.getLength() > 1) {
            return CommandResult.ERROR;
        }

        if (args.isEmpty()) {
            String homes = Locales.WARP_WARP.getString() + " (" + WarpManager.getWarps().size() + "): §6" + Joiner.on(", ").join(WarpManager.getWarps().keySet());
            return Messager.sendMessage(p, homes, Messager.MessageType.INFO);
        } else {
            Warp w = WarpManager.getWarp(args.getString(0));
            if (w != null) {

                String permission = "admincmd.warp.tp." + w.getName();
                if (!p.hasPermission(permission)) {
                    return Messager.sendMessage(p, Locales.COMMAND_MESSAGES_NO_PERMISSION.getString().replaceAll("%perm%", permission), Messager.MessageType.ERROR);
                }

                w.teleport(p);
                return CommandResult.SUCCESS;
            } else {
                return Messager.sendMessage(p, Locales.WARP_NO_SUCH_WARP, Messager.MessageType.ERROR);
            }
        }
    }

}
