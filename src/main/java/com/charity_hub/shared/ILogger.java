package com.charity_hub.shared;

public interface ILogger {
    void info(String message);

    void info(String message, Object... arguments);

    void debug(String message);

    void debug(String message, Object... arguments);

    void warn(String message);

    void warn(String message, Object... arguments);

    void error(String message);

    void error(String message, Object... arguments);
}