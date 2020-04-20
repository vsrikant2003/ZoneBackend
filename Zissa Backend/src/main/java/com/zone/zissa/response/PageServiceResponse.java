package com.zone.zissa.response;

/**
 * The ServiceResponse class
 * 
 * @param <T>
 */
public class PageServiceResponse<T> {

    private int status;

    private String message;

    private T data;

    private double totalRecords;

    public double getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(double l) {
        this.totalRecords = l;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

}
