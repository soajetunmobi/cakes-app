package com.cakes.favorite.exception.apiValidation;

public class ApiGlobalError {
    private String code;

    public ApiGlobalError(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
