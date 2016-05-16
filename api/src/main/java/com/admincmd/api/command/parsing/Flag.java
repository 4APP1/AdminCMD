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

public class Flag {

    private String flag;
    private String value;

    public Flag(String flag, String value) {
        this.flag = flag;
        this.value = value;
    }

    public String getFlag() {
        return flag;
    }

    public boolean hasValue() {
        return value != null && !value.isEmpty();
    }

    public String get() {
        return value;
    }

    public int getInt() throws NumberFormatException {
        return Integer.parseInt(value);
    }

    public double getDouble() throws NumberFormatException {
        return Double.parseDouble(value);
    }

    public Player getPlayer() {
        return AdminCMD.getServer().getPlayer(value);
    }

    public World getWorld() {
        return AdminCMD.getServer().getWorld(value);
    }

}
