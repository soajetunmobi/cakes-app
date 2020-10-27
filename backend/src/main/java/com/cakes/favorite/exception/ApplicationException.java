package com.cakes.favorite.exception;

import java.util.List;

import static java.util.stream.Collectors.toList;
import com.cakes.favorite.exception.apiValidation.ApiErrorsView;
import com.cakes.favorite.exception.apiValidation.ApiFieldError;
import com.cakes.favorite.exception.apiValidation.ApiGlobalError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApplicationException extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationException.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity badRequest(ResourceNotFoundException e) {
        logger.error("{}, {}", e.getMessage(), e.getStackTrace());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    public ResponseEntity badRequest(Exception e) {
        logger.error("{}, {}", e.getMessage(), e.getStackTrace());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(MissingIdException.class)
    public ResponseEntity forbidden(MissingIdException e) {
        logger.error("{}, {}", e.getMessage(), e.getStackTrace());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity catchAllException(Exception e) {
        logger.error("{}, {}", e.getMessage(), e.getStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return handleBindingResultErrors(ex.getBindingResult());
    }

    @ExceptionHandler(EntityValidationFailedException.class)
    public ResponseEntity<Object> handleEntityValidationFailedException(EntityValidationFailedException ex){
        return handleBindingResultErrors(ex.getBindingResult());
    }


    private ResponseEntity<Object> handleBindingResultErrors(BindingResult bindingResult){
        List<ApiFieldError> apiFieldErrors = bindingResult
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ApiFieldError(
                        fieldError.getField(),
                        fieldError.getCode(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage())
                )
                .collect(toList());

        List<ApiGlobalError> apiGlobalErrors = bindingResult
                .getGlobalErrors()
                .stream()
                .map(globalError -> new ApiGlobalError(
                        globalError.getCode())
                )
                .collect(toList());

        ApiErrorsView apiErrorsView = new ApiErrorsView(apiFieldErrors, apiGlobalErrors);

        return new ResponseEntity<>(apiErrorsView, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
