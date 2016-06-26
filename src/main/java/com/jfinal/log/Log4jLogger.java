//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jfinal.log;

import org.apache.log4j.Level;

public class Log4jLogger extends Logger {
    private org.apache.log4j.Logger log;
    private static final String callerFQCN = Log4jLogger.class.getName();

    Log4jLogger(Class<?> clazz) {
        this.log = org.apache.log4j.Logger.getLogger(clazz);
    }

    Log4jLogger(String name) {
        this.log = org.apache.log4j.Logger.getLogger(name);
    }

    public void info(String message) {
        this.log.log(callerFQCN, Level.INFO, message, (Throwable)null);
    }

    public void info(String message, Throwable t) {
        this.log.log(callerFQCN, Level.INFO, message, t);
    }

    public void debug(String message) {
        this.log.log(callerFQCN, Level.DEBUG, message, (Throwable)null);
    }

    public void debug(String message, Throwable t) {
        this.log.log(callerFQCN, Level.DEBUG, message, t);
    }

    public void warn(String message) {
        this.log.log(callerFQCN, Level.WARN, message, (Throwable)null);
    }

    public void warn(String message, Throwable t) {
        this.log.log(callerFQCN, Level.WARN, message, t);
    }

    public void error(String message) {
        this.log.log(callerFQCN, Level.ERROR, message, (Throwable)null);
    }

    public void error(String message, Throwable t) {
        this.log.log(callerFQCN, Level.ERROR, message, t);
    }

    public void fatal(String message) {
        this.log.log(callerFQCN, Level.FATAL, message, (Throwable)null);
    }

    public void fatal(String message, Throwable t) {
        this.log.log(callerFQCN, Level.FATAL, message, t);
    }

    public boolean isDebugEnabled() {
        return this.log.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return this.log.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return this.log.isEnabledFor(Level.WARN);
    }

    public boolean isErrorEnabled() {
        return this.log.isEnabledFor(Level.ERROR);
    }

    public boolean isFatalEnabled() {
        return this.log.isEnabledFor(Level.FATAL);
    }
}
