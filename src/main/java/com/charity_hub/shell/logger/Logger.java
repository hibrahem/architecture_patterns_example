package com.charity_hub.shell.logger;

import com.charity_hub.core.outputports.ILogger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Logger implements ILogger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("[Charity-hub - App]");

    @Override
    public void log(String message) {
        logger.debug(message);

    }

    @Override
    public void errorLog(String message) {
        logger.error(message);
    }
}
