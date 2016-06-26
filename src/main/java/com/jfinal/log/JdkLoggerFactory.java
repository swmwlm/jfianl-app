//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jfinal.log;

import com.jfinal.log.ILoggerFactory;
import com.jfinal.log.JdkLogger;
import com.jfinal.log.Logger;

public class JdkLoggerFactory implements ILoggerFactory {
    public JdkLoggerFactory() {
    }

    public Logger getLogger(Class<?> clazz) {
        return new JdkLogger(clazz);
    }

    public Logger getLogger(String name) {
        return new JdkLogger(name);
    }
}
