package com.admincmd.api.util.logger;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.configuration.YAMLConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jkmalan (aka John Malandrakis)
 */
public class DebugLogger {

    private static final File f = new File(AdminCMD.getDataFolder(), "");
    private static final YAMLConfiguration config = new YAMLConfiguration(f);

    private static final Logger logger = Logger.getLogger("Minecraft");
    private static final String PREFIX = "[AdminCMD] ";

    public static void info(String message) {
        logger.log(Level.INFO, PREFIX + message);
    }

    public static void warn(String message) {
        logger.log(Level.WARNING, PREFIX + message);
    }

    public static void severe(String message) {
        logger.log(Level.SEVERE, PREFIX + message);
    }

    public static void severe(String message, Throwable thrown) {
        logger.log(Level.SEVERE, PREFIX + message, thrown);
        debug(message, thrown, "err_" + prefix() + ".log");
    }

    public static String prefix() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(format);
    }

    public static void debug(String message) {
        debug(message, null);
    }

    public static void debug(String message, Throwable thrown) {
        debug(message, thrown, "debug.log");
    }

    private static void debug(String message, Throwable thrown, String file) {
        File logDir = new File(AdminCMD.getDataFolder(), "logs");
        logDir.mkdirs();

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(logDir, file), true));
            if (thrown == null) {
                bw.write(message);
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
