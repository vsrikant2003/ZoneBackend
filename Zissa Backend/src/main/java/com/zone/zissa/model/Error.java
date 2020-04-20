package com.zone.zissa.model;

import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Error class
 * 
 */

public class Error {

    private String context;
    private String code;
    private String message;

    private static final Logger LOGGER = LoggerFactory.getLogger(Error.class);

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setContext(Throwable th) {
        try {
            th.printStackTrace(new PrintWriter(context));
        } catch (Exception e) {
            LOGGER.error("Error", e);
            context = th.getLocalizedMessage();
        }
    }

}
