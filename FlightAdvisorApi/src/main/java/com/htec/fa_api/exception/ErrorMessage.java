package com.htec.fa_api.exception;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

public class ErrorMessage {
    private String message;
    private String httpStatus;
    private String details;
    private Timestamp timestamp;

    public ErrorMessage(String message, String httpStatus, String details) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.details = details;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String statusCode) {
        this.httpStatus = statusCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
