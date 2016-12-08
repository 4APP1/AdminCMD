/*
 * This file is part of AdminCMD
 * Copyright (C) 2016 AdminCMD Team
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
package com.admincmd.teleport;

import com.admincmd.player.BukkitPlayer;
import com.admincmd.player.PlayerManager;
import com.admincmd.utils.Locales;
import com.admincmd.utils.Messager;
import com.admincmd.utils.Utils;
import java.util.HashMap;
import java.util.Timer;
import org.bukkit.ChatColor;

public class RequestManager {

    private static final HashMap<Integer, Integer> requests = new HashMap<>();
    private static final int reqTimeOut = 120;

    public static void sendRequest(BukkitPlayer requestor, BukkitPlayer target) {
        int rID = requestor.getId();
        int tID = target.getId();
        Timer timer = new Timer();

        if (!requests.containsKey(tID)) {
            requests.put(tID, rID);
            String r = Locales.TELEPORT_TPA_SENT_REQUESTER.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target.getPlayer()));
            String t = Locales.TELEPORT_TPA_SENT_TARGET.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(requestor.getPlayer()));
            Messager.sendMessage(requestor.getPlayer(), r, Messager.MessageType.INFO);
            Messager.sendMessage(target.getPlayer(), t, Messager.MessageType.INFO);
            timer.schedule(new RequestTimeOut(requestor, target), (reqTimeOut * 1000));
        } else {
            Messager.sendMessage(requestor.getPlayer(), Locales.TELEPORT_TPA_ALREADY_HAS_REQUEST.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target.getPlayer())), Messager.MessageType.ERROR);
        }
    }

    public static void acceptRequest(BukkitPlayer target) {
        int tID = target.getId();

        if (requests.containsKey(tID)) {
            BukkitPlayer requestor = PlayerManager.getPlayer(requests.get(tID));
            int rID = requestor.getId();
            if (requests.get(tID) != rID) {
                Messager.sendMessage(requestor.getPlayer(), Locales.TELEPORT_TPA_ALREADY_HAS_REQUEST.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target.getPlayer())), Messager.MessageType.ERROR);
                return;
            }

            if (requestor.isOnline()) {
                String r = Locales.TELEPORT_TPA_ACCEPT_REQUEST.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target.getPlayer()));
                String t = Locales.TELEPORT_TPA_ACCEPT_TARGET.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(requestor.getPlayer()));
                Messager.sendMessage(requestor.getPlayer(), r, Messager.MessageType.INFO);
                Messager.sendMessage(target.getPlayer(), t, Messager.MessageType.INFO);
                requestor.getPlayer().teleport(target.getPlayer());
            } else {
                Messager.sendMessage(target.getPlayer(), Locales.COMMAND_MESSAGES_NOT_ONLINE, Messager.MessageType.ERROR);
            }

            requests.remove(tID);
        } else {
            Messager.sendMessage(target.getPlayer(), Locales.TELEPORT_TPA_NO_REQUEST, Messager.MessageType.ERROR);
        }
    }

    public static void denyRequest(BukkitPlayer target) {
        int tID = target.getId();

        if (requests.containsKey(tID)) {
            BukkitPlayer requestor = PlayerManager.getPlayer(requests.get(tID));
            String r = Locales.TELEPORT_TPA_DENY_REQUEST.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target.getPlayer()));
            String t = Locales.TELEPORT_TPA_DENY_TARGET.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(requestor.getPlayer()));

            Messager.sendMessage(target.getPlayer(), t, Messager.MessageType.INFO);
            if (requestor.isOnline()) {
                Messager.sendMessage(requestor.getPlayer(), r, Messager.MessageType.INFO);
            }
            requests.remove(tID);
        } else {
            Messager.sendMessage(target.getPlayer(), Locales.TELEPORT_TPA_NO_REQUEST, Messager.MessageType.ERROR);
        }
    }

    public static void timeOutRequest(BukkitPlayer requestor, BukkitPlayer target) {
        int tID = target.getId();
        int rID = requestor.getId();

        if (requests.containsKey(tID)) {
            if (requests.get(tID) != rID) {
                Messager.sendMessage(requestor.getPlayer(), Locales.TELEPORT_TPA_ALREADY_HAS_REQUEST.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target.getPlayer())), Messager.MessageType.ERROR);
                return;
            }

            String r = Locales.TELEPORT_TPA_TIMEOUT_REQUEST.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(target.getPlayer()));
            String t = Locales.TELEPORT_TPA_TIMEOUT_TARGET.getString().replaceAll("%player%", Utils.replacePlayerPlaceholders(requestor.getPlayer()));

            Messager.sendMessage(target.getPlayer(), t, Messager.MessageType.INFO);
            if (requestor.isOnline()) {
                Messager.sendMessage(requestor.getPlayer(), r, Messager.MessageType.INFO);
            }
            requests.remove(tID);
        }
    }

}
