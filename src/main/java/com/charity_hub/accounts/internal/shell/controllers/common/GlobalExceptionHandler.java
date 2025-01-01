package com.charity_hub.accounts.internal.shell.controllers.common;

import com.charity_hub.accounts.internal.core.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> handleAppException(AppException ex, WebRequest request) {
        HttpStatus status;
        
        if (ex instanceof AppException.RequirementException) {
            status = HttpStatus.CONFLICT;
        } else if (ex instanceof AppException.BadRequestException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof AppException.UnAuthorized) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof AppException.NotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        Map<String, String> body = new HashMap<>();
        body.put("description", ex.getMessage());
        
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("description", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyMap());
    }
}