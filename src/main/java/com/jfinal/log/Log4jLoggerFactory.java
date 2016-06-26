//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jfinal.log;

public class Log4jLoggerFactory implements ILoggerFactory {
    public Log4jLoggerFactory() {
    }

    public Logger getLogger(Class<?> clazz) {
        return new Log4jLogger(clazz);
    }

    public Logger getLogger(String name) {
        return new Log4jLogger(name);
    }
}
