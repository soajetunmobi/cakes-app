package com.cakes.favorite.exception.apiValidation;

import java.util.List;

/**
 * This class is a container for all the API validation errors.
 * It is used to form the base on the JSON response on a validation error.
 */
public class ApiErrorsView {
    private List<ApiFieldError> fieldErrors;
    private List<ApiGlobalError> globalErrors;

    public ApiErrorsView(List<ApiFieldError> fieldErrors, List<ApiGlobalError> globalErrors) {
        this.fieldErrors = fieldErrors;
        this.globalErrors = globalErrors;
    }

    public List<ApiFieldError> getFieldErrors() {
        return fieldErrors;
    }


    public List<ApiGlobalError> getGlobalErrors() {
        return globalErrors;
    }

}
