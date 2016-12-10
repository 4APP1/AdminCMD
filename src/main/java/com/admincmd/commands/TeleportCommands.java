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
package com.admincmd.commands;

import com.admincmd.commandapi.BaseCommand;
import com.admincmd.commandapi.CommandArgs;
import com.admincmd.commandapi.CommandArgs.Flag;
import com.admincmd.commandapi.CommandHandler;
import com.admincmd.commandapi.CommandResult;
import com.admincmd.commandapi.HelpPage;
import com.admincmd.player.BukkitPlayer;
import com.admincmd.player.PlayerManager;
import com.admincmd.teleport.RequestManagerHere;
import com.admincmd.teleport.RequestManagerTo;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import com.admincmd.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

@CommandHandler
public class TeleportCommands {

    private final HelpPage down = new HelpPage("down", "<-p player>");
    private final HelpPage top = new HelpPage("top", "<-p player>");
    private final HelpPage tpa = new HelpPage("tpa", "[yes|no]", "<player>");
    private final HelpPage tpahere = new HelpPage("tpahere", "[yes|no]", "<player>");

    @BaseCommand(command = "tpa", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.teleport.requests.tpa")
    public CommandResult executeTpa(Player sender, CommandArgs args) {
        if (tpa.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        if (args.getLength() != 1) {
            return CommandResult.ERROR;
        }

        String arg = args.getString(0);

        BukkitPlayer s = PlayerManager.getPlayer(sender);

        if (arg.equalsIgnoreCase("yes")) {
            if (!sender.hasPermission("admincmd.teleport.requests.tpa.accept")) {
                String msg = Locales.COMMAND_MESSAGES_NO_PERMISSION.getString().replaceAll("%perm%", "admincmd.teleport.requests.tpa.accept");
                sender.sendMessage(msg);
                return CommandResult.SUCCESS;
            }
            RequestManagerTo.acceptRequest(s);
        } else if (arg.equalsIgnoreCase("no")) {
            if (!sender.hasPermission("admincmd.teleport.requests.tpa.deny")) {
                String msg = Locales.COMMAND_MESSAGES_NO_PERMISSION.getString().replaceAll("%perm%", "admincmd.teleport.requests.tpa.deny");
                sender.sendMessage(msg);
                return CommandResult.SUCCESS;
            }
            RequestManagerTo.denyRequest(s);
        } else {
            if (!args.isPlayer(0)) {
                return CommandResult.NOT_ONLINE;
            }

            if (!sender.hasPermission("admincmd.teleport.requests.tpa.send")) {
                String msg = Locales.COMMAND_MESSAGES_NO_PERMISSION.getString().replaceAll("%perm%", "admincmd.teleport.requests.tpa.send");
                sender.sendMessage(msg);
                return CommandResult.SUCCESS;
            }
            Player target = args.getPlayer(0);
            BukkitPlayer t = PlayerManager.getPlayer(target);
            RequestManagerTo.sendRequest(s, t);
        }

        return CommandResult.SUCCESS;
    }

    @BaseCommand(command = "tpahere", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.teleport.requests.tpahere")
    public CommandResult executeTpaHere(Player sender, CommandArgs args) {
        if (tpahere.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        if (args.getLength() != 1) {
            return CommandResult.ERROR;
        }

        String arg = args.getString(0);

        BukkitPlayer s = PlayerManager.getPlayer(sender);

        if (arg.equalsIgnoreCase("yes")) {
            if (!sender.hasPermission("admincmd.teleport.requests.tpahere.accept")) {
                String msg = Locales.COMMAND_MESSAGES_NO_PERMISSION.getString().replaceAll("%perm%", "admincmd.teleport.requests.tpahere.accept");
                sender.sendMessage(msg);
                return CommandResult.SUCCESS;
            }
            RequestManagerHere.acceptRequest(s);
        } else if (arg.equalsIgnoreCase("no")) {
            if (!sender.hasPermission("admincmd.teleport.requests.tpahere.deny")) {
                String msg = Locales.COMMAND_MESSAGES_NO_PERMISSION.getString().replaceAll("%perm%", "admincmd.teleport.requests.tpahere.deny");
                sender.sendMessage(msg);
                return CommandResult.SUCCESS;
            }
            RequestManagerHere.denyRequest(s);
        } else {
            if (!args.isPlayer(0)) {
                return CommandResult.NOT_ONLINE;
            }

            if (!sender.hasPermission("admincmd.teleport.requests.tpahere.send")) {
                String msg = Locales.COMMAND_MESSAGES_NO_PERMISSION.getString().replaceAll("%perm%", "admincmd.teleport.requests.tpahere.send");
                sender.sendMessage(msg);
                return CommandResult.SUCCESS;
            }

            Player target = args.getPlayer(0);
            BukkitPlayer t = PlayerManager.getPlayer(target);
            RequestManagerHere.sendRequest(s, t);
        }
        return CommandResult.SUCCESS;
    }

    @BaseCommand(command = "down", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.tp.down", aliases = "tpdown")
    public CommandResult executeDown(Player sender, CommandArgs args) {
        if (down.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        if (args.isEmpty()) {
            Location loc = sender.getLocation();
            Location target = loc.clone();
            Location target2 = loc.clone();

            boolean found = false;

            Location underFeet = loc.getBlock().getRelative(BlockFace.DOWN, 2).getLocation();

            boolean air = (underFeet.getBlock().getType() == Material.AIR);

            if (air) {
                for (int y = underFeet.getBlockY() - 1; y >= 0; y--) {
                    target.setY(y);
                    target2.setY(y + 1);
                    Location target3 = target2.clone();
                    target3.setY(y + 2);

                    if (target.getBlock().getType() != Material.AIR && target2.getBlock().getType() == Material.AIR && target3.getBlock().getType() == Material.AIR) {
                        found = true;
                        target.setY(y + 1);
                        break;
                    }
                }
            } else {

                for (int y = loc.getBlockY() - 2; y >= 0; y--) {
                    target.setY(y);
                    target2.setY(y + 1);

                    if (target.getBlock().getType() == Material.AIR && target2.getBlock().getType() == Material.AIR) {
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
                sender.teleport(target);
                return Messager.sendMessage(sender, Locales.TELEPORT_DOWN, Messager.MessageType.INFO);
            } else {
                return CommandResult.NO_SPACE;
            }
        } else {
            if (args.hasFlag("p") && args.getLength() == 2) {
                if (!sender.hasPermission("admincmd.tp.down.other")) {
                    return CommandResult.NO_PERMISSION_OTHER;
                }

                Flag f = args.getFlag("p");
                if (!f.isPlayer()) {
                    return CommandResult.NOT_ONLINE;
                }
                Player p = f.getPlayer();
                Location loc = p.getLocation();
                Location target = loc.clone();
                Location target2 = loc.clone();

                boolean found = false;

                Location underFeet = loc.getBlock().getRelative(BlockFace.DOWN, 2).getLocation();

                boolean air = (underFeet.getBlock().getType() == Material.AIR);

                if (air) {
                    for (int y = underFeet.getBlockY() - 1; y >= 0; y--) {
                        target.setY(y);
                        target2.setY(y + 1);
                        Location target3 = target2.clone();
                        target3.setY(y + 2);

                        if (target.getBlock().getType() != Material.AIR && target2.getBlock().getType() == Material.AIR && target3.getBlock().getType() == Material.AIR) {
                            found = true;
                            target.setY(y + 1);
                            break;
                        }
                    }
                } else {

                    for (int y = loc.getBlockY() - 2; y >= 0; y--) {
                        target.setY(y);
                        target2.setY(y + 1);

                        if (target.getBlock().getType() == Material.AIR && target2.getBlock().getType() == Material.AIR) {
                            found = true;
                            break;
                        }
                    }
                }

                if (found) {
                    p.teleport(target);
                    Messager.sendMessage(p, Locales.TELEPORT_DOWN, Messager.MessageType.INFO);
                    return Messager.sendMessage(sender, Locales.TELEPORT_DOWN_OTHER.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(p)), Messager.MessageType.INFO);
                } else {
                    return CommandResult.NO_SPACE;
                }

            }
            return CommandResult.ERROR;
        }
    }

    @BaseCommand(command = "up", sender = BaseCommand.Sender.PLAYER, permission = "admincmd.tp.up", aliases = "top, tpup, tptop")
    public CommandResult executeUp(Player sender, CommandArgs args) {
        if (top.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        if (args.isEmpty()) {
            Location loc = sender.getLocation();
            Location target = loc.clone();
            Location target2 = loc.clone();

            boolean found = false;

            Location overHead = loc.getBlock().getRelative(BlockFace.UP, 2).getLocation();
            boolean air = (overHead.getBlock().getType() == Material.AIR);

            if (air) {
                for (int y = overHead.getBlockY(); y <= loc.getWorld().getMaxHeight(); y++) {
                    target.setY(y + 1);
                    target2.setY(y + 2);
                    Location target3 = target2.clone();
                    target3.setY(y + 3);
                    if (target.getBlock().getType() != Material.AIR && target2.getBlock().getType() == Material.AIR && target3.getBlock().getType() == Material.AIR) {
                        found = true;
                        target.setY(y + 2);
                        break;
                    }
                }
            } else {
                for (int y = overHead.getBlockY(); y <= loc.getWorld().getMaxHeight(); y++) {
                    target.setY(y);
                    target2.setY(y + 1);

                    if (target.getBlock().getType() == Material.AIR && target2.getBlock().getType() == Material.AIR) {
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
                sender.teleport(target);
                return Messager.sendMessage(sender, Locales.TELEPORT_UP, Messager.MessageType.INFO);
            } else {
                return CommandResult.NO_SPACE;
            }
        } else {
            if (args.hasFlag("p") && args.getLength() == 2) {
                if (!sender.hasPermission("admincmd.tp.down.other")) {
                    return CommandResult.NO_PERMISSION_OTHER;
                }

                Flag f = args.getFlag("p");
                if (!f.isPlayer()) {
                    return CommandResult.NOT_ONLINE;
                }
                Player p = f.getPlayer();
                Location loc = p.getLocation();
                Location target = loc.clone();
                Location target2 = loc.clone();

                boolean found = false;

                Location overHead = loc.getBlock().getRelative(BlockFace.UP, 2).getLocation();
                boolean air = (overHead.getBlock().getType() == Material.AIR);

                if (air) {
                    for (int y = overHead.getBlockY(); y <= loc.getWorld().getMaxHeight(); y++) {
                        target.setY(y + 1);
                        target2.setY(y + 2);
                        Location target3 = target2.clone();
                        target3.setY(y + 3);
                        if (target.getBlock().getType() != Material.AIR && target2.getBlock().getType() == Material.AIR && target3.getBlock().getType() == Material.AIR) {
                            found = true;
                            target.setY(y + 2);
                            break;
                        }
                    }
                } else {
                    for (int y = overHead.getBlockY(); y <= loc.getWorld().getMaxHeight(); y++) {
                        target.setY(y);
                        target2.setY(y + 1);

                        if (target.getBlock().getType() == Material.AIR && target2.getBlock().getType() == Material.AIR) {
                            found = true;
                            break;
                        }
                    }
                }

                if (found) {
                    p.teleport(target);
                    Messager.sendMessage(p, Locales.TELEPORT_UP, Messager.MessageType.INFO);
                    return Messager.sendMessage(sender, Locales.TELEPORT_UP_OTHER.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(p)), Messager.MessageType.INFO);
                } else {
                    return CommandResult.NO_SPACE;
                }

            }
            return CommandResult.ERROR;
        }
    }

}
