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
package com.admincmd.api.database.type;

import com.admincmd.api.database.Database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends Database {

    private final String host;
    private final int port;
    private final String name;
    private final String user;
    private final String pass;

    public MySQL(String host, int port, String name, String user, String pass) {
        super(Database.Type.MYSQL);

        this.host = host;
        this.port = port;
        this.name = name;
        this.user = user;
        this.pass = pass;
    }

    @Override
    public void connect() throws SQLException {
        setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name, user, pass));
    }

}
