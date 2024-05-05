package de.chocolatesec.hotchocolate.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PrefixedLogger {

    private final Logger logger;
    private final String prefix;

    public PrefixedLogger(Logger logger, String prefix) {
        this.logger = logger;
        this.prefix = prefix;
    }

    private String prefixString(String string) {
        return "[" + prefix + "] " + string;
    }

    public void severe(String message) {
        logger.severe(prefixString(message));
    }

    public void warning(String message) {
        logger.warning(prefixString(message));
    }

    public void info(String message) {
        logger.info(prefixString(message));
    }

    public void config(String message) {
        logger.config(prefixString(message));
    }

    public void fine(String message) {
        logger.fine(prefixString(message));
    }

    public void finer(String message) {
        logger.finer(prefixString(message));
    }

    public void finest(String message) {
        logger.finest(prefixString(message));
    }

    public void log(Level level, String message) {
        logger.log(level, prefixString(message));
    }

    public void log(Level level, String message, Throwable thrown) {
        logger.log(level, prefixString(message), thrown);
    }

}
