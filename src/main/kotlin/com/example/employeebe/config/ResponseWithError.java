package com.example.employeebe.config;

import org.springframework.http.HttpStatus;

public class ResponseWithError<T> {
    private T response;
    private boolean error;
    private String errorMsg;
    private HttpStatus httpStatus;


    public ResponseWithError() {
    }

    private ResponseWithError(String errorMsg) {
        this.error = true;
        this.errorMsg = errorMsg;
    }
    private ResponseWithError(String errorMsg, HttpStatus httpStatus) {
        this.error = true;
        this.errorMsg = errorMsg;
        this.httpStatus = httpStatus;
    }

    private ResponseWithError(T response) {
        this.response = response;
        this.error=false;
    }
    public static <K> ResponseWithError<K> ofError(String errorMsg){
        return new ResponseWithError<K>(errorMsg);
    }

    public static <K> ResponseWithError<K> ofError(String errorMsg, HttpStatus httpStatus){
        return new ResponseWithError<K>(errorMsg,httpStatus);
    }

    public static <K> ResponseWithError<K> of(K response){
        return new ResponseWithError(response);
    }

    public T getResponse() {
        return response;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
