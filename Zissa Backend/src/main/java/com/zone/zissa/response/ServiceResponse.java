package com.zone.zissa.response;

/**
 * The ServiceResponse class
 * 
 * @param <T>
 */
public class ServiceResponse<T> {

    private int status;

    private String message;

    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
