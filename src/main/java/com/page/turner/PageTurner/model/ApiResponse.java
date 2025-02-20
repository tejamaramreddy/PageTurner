package com.page.turner.PageTurner.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class ApiResponse<T> {
    private String status;
    private String message;

    @JsonProperty("data")
    private T data;

    private String code;  // Serialize HttpStatus as String

    // Constructor for success response
    public ApiResponse(String status, String message, T data, HttpStatus code) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code != null ? code.toString() : null;  // Convert HttpStatus to String
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code != null ? code.toString() : null;
    }
}


