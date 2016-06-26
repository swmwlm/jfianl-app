//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jfinal.log;

import com.jfinal.log.Logger;
import java.util.logging.Level;

public class JdkLogger extends Logger {
    private java.util.logging.Logger log;
    private String clazzName;

    JdkLogger(Class<?> clazz) {
        this.log = java.util.logging.Logger.getLogger(clazz.getName());
        this.clazzName = clazz.getName();
    }

    JdkLogger(String name) {
        this.log = java.util.logging.Logger.getLogger(name);
        this.clazzName = name;
    }

    public void debug(String message) {
        this.log.logp(Level.FINE, this.clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
    }

    public void debug(String message, Throwable t) {
        this.log.logp(Level.FINE, this.clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message, t);
    }

    public void info(String message) {
        this.log.logp(Level.INFO, this.clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
    }

    public void info(String message, Throwable t) {
        this.log.logp(Level.INFO, this.clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message, t);
    }

    public void warn(String message) {
        this.log.logp(Level.WARNING, this.clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
    }

    public void warn(String message, Throwable t) {
        this.log.logp(Level.WARNING, this.clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message, t);
    }

    public void error(String message) {
        this.log.logp(Level.SEVERE, this.clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
    }

    public void error(String message, Throwable t) {
        this.log.logp(Level.SEVERE, this.clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message, t);
    }

    public void fatal(String message) {
        this.log.logp(Level.SEVERE, this.clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
    }

    public void fatal(String message, Throwable t) {
        this.log.logp(Level.SEVERE, this.clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message, t);
    }

    public boolean isDebugEnabled() {
        return this.log.isLoggable(Level.FINE);
    }

    public boolean isInfoEnabled() {
        return this.log.isLoggable(Level.INFO);
    }

    public boolean isWarnEnabled() {
        return this.log.isLoggable(Level.WARNING);
    }

    public boolean isErrorEnabled() {
        return this.log.isLoggable(Level.SEVERE);
    }

    public boolean isFatalEnabled() {
        return this.log.isLoggable(Level.SEVERE);
    }
}
