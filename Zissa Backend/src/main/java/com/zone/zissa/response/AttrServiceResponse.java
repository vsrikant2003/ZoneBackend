package com.zone.zissa.response;

/**
 * The ServiceResponse class
 * 
 * @param <T>
 */
public class AttrServiceResponse<T> {

    private int status;

    private String message;

    private boolean inuse;

    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isInuse() {
        return inuse;
    }

    public void setInuse(boolean inuse) {
        this.inuse = inuse;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
