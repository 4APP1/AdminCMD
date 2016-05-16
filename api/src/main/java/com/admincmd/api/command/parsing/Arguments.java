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
package com.admincmd.api.command.parsing;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.entity.player.Player;
import com.admincmd.api.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arguments {

    private List<String> arguments = new ArrayList<>();
    private Map<String, Flag> flags = new HashMap<>();

    public Arguments(String[] args) {
        this(args, 0);
    }

    public Arguments(String[] args, int start) {
        for (int i = start; i < args.length; i++) {
            String arg = args[i];
            if (arg.length() > 1 && arg.matches("-[a-zA-Z]")) {
                String character = arg.replaceFirst("-", "").toLowerCase();
                String value = "";
                if ((i + 1) < args.length) {
                    arg = args[++i];
                }
                flags.put(character, new Flag(character, value));
            } else {
                arguments.add(arg);
            }
        }
    }

    public int argsSize() {
        return arguments.size();
    }

    public int flagsSize() {
        return flags.size();
    }

    public String get(int index) {
        return arguments.get(index);
    }

    public int getInt(int index) throws NumberFormatException {
        return Integer.parseInt(arguments.get(index));
    }

    public double getDouble(int index) throws NumberFormatException {
        return Double.parseDouble(arguments.get(index));
    }

    public Player getPlayer(int index) {
        return AdminCMD.getServer().getPlayer(arguments.get(index));
    }

    public World getWorld(int index) {
        return AdminCMD.getServer().getWorld(arguments.get(index));
    }

    public boolean hasFlag(String flag) {
        return flags.containsKey(flag.toLowerCase());
    }

    public Flag getFlag(String flag) {
        return flags.get(flag.toLowerCase());
    }

}
