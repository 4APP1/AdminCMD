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

import com.admincmd.api.util.logger.DebugLogger;

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
                DebugLogger.severe("Database driver is not a valid driver: " + type.getDriver());
            }
        } catch (ClassNotFoundException e) {
            DebugLogger.severe("Database driver could not be found: " + type.getDriver(), e);
        } catch (InstantiationException e) {
            DebugLogger.severe("Database driver could not be instantiated: " + type.getDriver(), e);
        } catch (IllegalAccessException e) {
            DebugLogger.severe("Database driver could not be accessed: " + type.getDriver(), e);
        } catch (SQLException e) {
            DebugLogger.severe("Database driver could not be registered: " + type.getDriver(), e);
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
        statement.execute(query);
        closeStatement(statement);
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        return getConnection().prepareStatement(query);
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                DebugLogger.severe("Unable to close database statement", e);
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                DebugLogger.severe("Unable to close database resultset", e);
            }
        }
    }

}
