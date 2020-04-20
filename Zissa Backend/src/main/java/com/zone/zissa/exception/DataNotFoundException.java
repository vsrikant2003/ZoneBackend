package com.zone.zissa.exception;

/**
 * SuccessException class extends CustomException.
 * 
 */
public class DataNotFoundException extends CustomException {

    private static final long serialVersionUID = 1L;

    /**
     * This is the constructor
     */
    public DataNotFoundException(String message) {

        super(message);

    }

}
