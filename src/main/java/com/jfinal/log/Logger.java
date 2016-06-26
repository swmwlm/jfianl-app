//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jfinal.log;

public abstract class Logger {
    private static ILoggerFactory factory;

    public Logger() {
    }

    public static void setLoggerFactory(ILoggerFactory loggerFactory) {
        if(loggerFactory != null) {
            factory = loggerFactory;
        }

    }

    public static Logger getLogger(Class<?> clazz) {
        return factory.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        return factory.getLogger(name);
    }

    public static void init() {
        if(factory == null) {
            try {
                Class.forName("org.apache.log4j.Logger");
                Class e = Class.forName("com.jfinal.log.Log4jLoggerFactory");
                factory = (ILoggerFactory)e.newInstance();
            } catch (Exception var1) {
                factory = new JdkLoggerFactory();
            }

        }
    }

    public abstract void debug(String var1);

    public abstract void debug(String var1, Throwable var2);

    public abstract void info(String var1);

    public abstract void info(String var1, Throwable var2);

    public abstract void warn(String var1);

    public abstract void warn(String var1, Throwable var2);

    public abstract void error(String var1);

    public abstract void error(String var1, Throwable var2);

    public abstract void fatal(String var1);

    public abstract void fatal(String var1, Throwable var2);

    public abstract boolean isDebugEnabled();

    public abstract boolean isInfoEnabled();

    public abstract boolean isWarnEnabled();

    public abstract boolean isErrorEnabled();

    public abstract boolean isFatalEnabled();

    static {
        init();
    }
}
