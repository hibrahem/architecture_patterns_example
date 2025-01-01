package com.charity_hub.accounts.internal.core.outputports;

public interface ILogger {
    void log(String message);
    void errorLog(String message);
}