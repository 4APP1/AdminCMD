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
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import com.admincmd.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandHandler
public class GamemodeCommand {

    @BaseCommand(command = "gamemode", sender = BaseCommand.Sender.CONSOLE, aliases = "gm", helpArguments = {"", "<-p player>", "<0|1|2|3>", "<-p player> <0|1|2|3>"})
    public CommandResult executeGamemodeConsole(CommandSender sender, CommandArgs args) {
        if (args.isEmpty()) {
            return CommandResult.NOT_ONLINE;
        }

        if (args.hasFlag("p")) {
            CommandArgs.Flag flag = args.getFlag("p");
            if (!flag.isPlayer()) {
                return CommandResult.NOT_ONLINE;
            }

            Player target = flag.getPlayer();
            switch (args.getLength()) {
                case 2: {
                    GameMode gm = target.getGameMode() == GameMode.SURVIVAL ? GameMode.CREATIVE : GameMode.SURVIVAL;
                    target.setGameMode(gm);
                    String msg = Locales.PLAYER_GAMEMODE_CHANGED.getString().replaceAll("%status%", gm.toString());
                    Messager.sendMessage(target, msg, Messager.MessageType.INFO);

                    String msg2 = Locales.PLAYER_GAMEMODE_CHANGED_OTHER.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target)).replaceAll("%status%", gm.toString());
                    Messager.sendMessage(sender, msg2, Messager.MessageType.INFO);
                    return CommandResult.SUCCESS;
                }
                case 3: {
                    if (!args.isInteger(2)) {
                        return CommandResult.NOT_A_NUMBER;
                    }
                    int num = args.getInt(2);
                    GameMode gm = GameMode.getByValue(num);
                    target.setGameMode(gm);
                    String msg = Locales.PLAYER_GAMEMODE_CHANGED.getString().replaceAll("%status%", gm.toString());
                    Messager.sendMessage(target, msg, Messager.MessageType.INFO);

                    String msg2 = Locales.PLAYER_GAMEMODE_CHANGED_OTHER.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target)).replaceAll("%status%", gm.toString());
                    Messager.sendMessage(sender, msg2, Messager.MessageType.INFO);
                    return CommandResult.SUCCESS;
                }
                default:
                    return CommandResult.ERROR;
            }
        }
        return CommandResult.ERROR;
    }

    @BaseCommand(command = "gamemode", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.player.gamemode", aliases = "gm", helpArguments = {"", "<-p player>", "<0|1|2|3>", "<-p player> <0|1|2|3>"})
    public CommandResult executeGamemode(Player sender, CommandArgs args) {
        if (args.isEmpty()) {
            GameMode gm = sender.getGameMode() == GameMode.SURVIVAL ? GameMode.CREATIVE : GameMode.SURVIVAL;
            sender.setGameMode(gm);
            String msg = Locales.PLAYER_GAMEMODE_CHANGED.getString().replaceAll("%status%", gm.toString());
            return Messager.sendMessage(sender, msg, Messager.MessageType.INFO);
        }

        if (args.hasFlag("p")) {
            if (!sender.hasPermission("admincmd.player.gamemode.other")) {
                return CommandResult.NO_PERMISSION_OTHER;
            }
            CommandArgs.Flag flag = args.getFlag("p");
            if (!flag.isPlayer()) {
                return CommandResult.NOT_ONLINE;
            }

            Player target = flag.getPlayer();
            switch (args.getLength()) {
                case 2: {
                    GameMode gm = target.getGameMode() == GameMode.SURVIVAL ? GameMode.CREATIVE : GameMode.SURVIVAL;
                    target.setGameMode(gm);
                    String msg = Locales.PLAYER_GAMEMODE_CHANGED.getString().replaceAll("%status%", gm.toString());
                    Messager.sendMessage(target, msg, Messager.MessageType.INFO);

                    String msg2 = Locales.PLAYER_GAMEMODE_CHANGED_OTHER.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target)).replaceAll("%status%", gm.toString());
                    Messager.sendMessage(sender, msg2, Messager.MessageType.INFO);
                    return CommandResult.SUCCESS;
                }
                case 3: {
                    if (!args.isInteger(2)) {
                        return CommandResult.NOT_A_NUMBER;
                    }
                    int num = args.getInt(2);
                    GameMode gm = GameMode.getByValue(num);
                    target.setGameMode(gm);
                    String msg = Locales.PLAYER_GAMEMODE_CHANGED.getString().replaceAll("%status%", gm.toString());
                    Messager.sendMessage(target, msg, Messager.MessageType.INFO);

                    String msg2 = Locales.PLAYER_GAMEMODE_CHANGED_OTHER.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target)).replaceAll("%status%", gm.toString());
                    Messager.sendMessage(sender, msg2, Messager.MessageType.INFO);
                    return CommandResult.SUCCESS;
                }
                default:
                    return CommandResult.ERROR;
            }
        } else {
            if (args.getLength() == 1) {
                if (!args.isInteger(0)) {
                    return CommandResult.NOT_A_NUMBER;
                }
                int num = args.getInt(0);
                GameMode gm = GameMode.getByValue(num);
                sender.setGameMode(gm);
                String msg = Locales.PLAYER_GAMEMODE_CHANGED.getString().replaceAll("%status%", gm.toString());
                return Messager.sendMessage(sender, msg, Messager.MessageType.INFO);
            }
        }

        return CommandResult.ERROR;
    }

}
