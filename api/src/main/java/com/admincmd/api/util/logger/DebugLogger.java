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
package com.admincmd.api.util.logger;

import com.admincmd.api.configuration.YAMLConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DebugLogger {

    private static final File f = new File("debug.yml");
    private static final YAMLConfiguration config = new YAMLConfiguration(f);

    static {
        try {
            config.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (config.isEmpty()) {
            config.set("debugger.enabled", false);
            config.set("debugger.level", 1);
        }

        try {
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger logger = Logger.getLogger("Minecraft");
    private static final String PREFIX = "[AdminCMD] ";

    public static void info(String message) {
        logger.log(Level.INFO, PREFIX + message);
        if (config.getBoolean("debugger.enabled") && config.getInteger("debugger.level") > 2) {
            printDebug(message);
        }
    }

    public static void warn(String message) {
        logger.log(Level.WARNING, PREFIX + message);
        if (config.getBoolean("debugger.enabled") && config.getInteger("debugger.level") > 1) {
            printDebug(message);
        }
    }

    public static void severe(String message) {
        logger.log(Level.SEVERE, PREFIX + message);
        if (config.getBoolean("debugger.enabled") && config.getInteger("debugger.level") > 0) {
            printDebug(message);
        }
    }

    public static void severe(String message, Throwable thrown) {
        logger.log(Level.SEVERE, PREFIX + message, thrown);
        if (config.getBoolean("debugger.enabled")) {
            printDebug(message, thrown, "err_" + prefix() + ".log");
        }
    }

    public static void debug(String message) {
        if (config.getBoolean("debugger.enabled")) {
            logger.log(Level.INFO, PREFIX + message);
            printDebug(message);
        }
    }

    public static void debug(String message, Throwable thrown) {
        if (config.getBoolean("debugger.enabled")) {
            logger.log(Level.INFO, PREFIX + message, thrown);
            printDebug(message, thrown);
        }
    }

    private static String prefix() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(format);
    }

    private static void printDebug(String message) {
        printDebug(message, null);
    }

    private static void printDebug(String message, Throwable thrown) {
        printDebug(message, thrown, "debug.log");
    }

    private static void printDebug(String message, Throwable thrown, String file) {
        File logDir = new File("logs");
        logDir.mkdirs();

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(logDir, file), true));
            if (thrown == null) {
                bw.write("[" + prefix() + "]:" + message);
                bw.newLine();
            } else {
                bw.newLine();
                bw.newLine();
                bw.write("///////////////////////////////////////////////////////////////////////////////");
                bw.newLine();
                bw.newLine();
                bw.write("[" + prefix() + "]: An Exception happened!");
                bw.newLine();
                bw.write("[" + prefix() + "]: " + message);
                bw.newLine();
                bw.write(getStackTrace(thrown));
                bw.newLine();
                bw.newLine();
                bw.write("///////////////////////////////////////////////////////////////////////////////");
                bw.newLine();
                bw.newLine();
            }
        } catch (IOException e) {
            severe("Unable to write to log file!");
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (Exception e) {
                severe("Unable to close log writer!");
            }
        }
    }

    private static String getStackTrace(Throwable thrown) {
        String result = "[" + prefix() + "]: " + thrown + ": " + thrown.getMessage();
        for (StackTraceElement element : thrown.getStackTrace()) {
            result += "\n" + "[" + prefix() + "]" + ": " + element;
        }
        return result;
    }

}
