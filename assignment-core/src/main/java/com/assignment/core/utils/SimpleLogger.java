package com.assignment.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@Slf4j
public class SimpleLogger {
    private static final int CALLER_CLASS_INDEX_IN_STACK_TRACE = 3;

    private SimpleLogger() {
    }

    private static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static void info(String message) {
        Class<?> clazz = getCallerClass();
        getLogger(clazz).info(message, Collections.emptyList());
    }

    public static void info(String message, Object... params) {
        Class<?> clazz = getCallerClass();
        getLogger(clazz).info(message, params);
    }

    public static void debug(String message) {
        Class<?> clazz = getCallerClass();
        getLogger(clazz).debug(message, Collections.emptyList());
    }

    public static void debug(String message, Object... params) {
        Class<?> clazz = getCallerClass();
        getLogger(clazz).debug(message, params);
    }

    public static void error(Throwable throwable) {
        Class<?> clazz = getCallerClass();
        getLogger(clazz).error(throwable.getMessage(), throwable);
    }

    public static void error(String message) {
        Class<?> clazz = getCallerClass();
        getLogger(clazz).error(message, Collections.emptyList());
    }

    public static void error(String message, Object... params) {
        Class<?> clazz = getCallerClass();
        getLogger(clazz).error(message, params);
    }

    public static void error(String message, Throwable throwable) {
        Class<?> clazz = getCallerClass();
        getLogger(clazz).error(message, throwable);
    }

    private static Class<?> getCallerClass() {
        try {
            String className = Thread.currentThread().getStackTrace()[CALLER_CLASS_INDEX_IN_STACK_TRACE].getClassName();
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            log.error("", ex);
        }
        return Logger.class;
    }

    public static void warn(String message) {
        Class<?> clazz = getCallerClass();
        getLogger(clazz).warn(message);
    }
}
