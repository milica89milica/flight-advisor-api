package com.htec.fa_api.exception;

import org.springframework.http.HttpStatus;

public class HttpException extends Exception {
    private HttpStatus status;
    private Object data;

    public HttpException(Object data, HttpStatus status) {
        this.status = status;
        this.data = data;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return "HttpException{status=" + this.status + ", data=" + this.data + "}";
    }
}
