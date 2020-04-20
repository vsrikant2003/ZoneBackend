package com.zone.zissa.exception;

/**
 * CustomException class extends RuntimeException
 */

public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * This is the constructor
     */
    public CustomException(String message) {
        super(message);
    }

}
