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
package com.admincmd.api.database;

import java.sql.*;

public abstract class Database {

    public enum Type {

        MYSQL("com.mysql.jdbc.Driver"),
        SQLITE("org.sqlite.JDBC");

        private final String driver;

        private Type(String driver) {
            this.driver = driver;
        }

        public String getDriver() {
            return driver;
        }

    }

    private Connection conn = null;
    private final Type type;

    public Database(Type type) {
        this.type = type;
        try {
            Class driver = Class.forName(type.getDriver());
            Object o = driver.newInstance();
            if (o instanceof Driver) {
                DriverManager.registerDriver((Driver) o);
            } else {
                // TODO ACLogger message
            }
        } catch (ClassNotFoundException e) {
            // TODO ACLogger message
        } catch (InstantiationException e) {
            // TODO ACLogger message
        } catch (IllegalAccessException e) {
            // TODO ACLogger message
        } catch (SQLException e) {
            // TODO ACLogger message
        }
    }

    public Type getType() {
        return type;
    }

    public abstract void connect() throws SQLException;

    public void disconnect() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public boolean testConnection() {
        try {
            getConnection();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            connect();
        }
        return conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public Statement getStatement() throws SQLException {
        return getConnection().createStatement();
    }

    public void executeStatement(String query) throws SQLException {
        Statement statement = getStatement();
        statement.executeQuery(query);
        statement.close();
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        return getConnection().prepareStatement(query);
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // TODO ACLogger message
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO ACLogger message
            }
        }
    }

}
