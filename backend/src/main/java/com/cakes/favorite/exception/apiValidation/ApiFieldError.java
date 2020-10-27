package com.cakes.favorite.exception.apiValidation;

/**
 * This class represents an individual validation error for a field.
 * It is used to form part of the JSON response on a validation error.
 */
public class ApiFieldError {
    private String field;
    private String code;
    private Object rejectedValue;
    private String message;

    public ApiFieldError(String field, String code, Object rejectedValue, String message) {
        this.field = field;
        this.code = code;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
