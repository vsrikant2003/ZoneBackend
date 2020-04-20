package com.zone.zissa.exception;

/**
 * DataTruncateException class extends CustomException.
 * 
 */
public class DataToLongException extends CustomException {

    private static final long serialVersionUID = 1L;

    /**
     * This is the constructor
     */
    public DataToLongException(String message) {
        super(message);

    }

}
