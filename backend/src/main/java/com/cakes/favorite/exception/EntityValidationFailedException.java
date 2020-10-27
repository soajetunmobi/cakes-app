package com.cakes.favorite.exception;

import org.springframework.validation.BindingResult;

public class EntityValidationFailedException extends RuntimeException {

    private final BindingResult bindingResult;

    public EntityValidationFailedException(BindingResult bindingResult){
        super("");
        this.bindingResult = bindingResult;
    }

    BindingResult getBindingResult(){
        return this.bindingResult;
    }
}
