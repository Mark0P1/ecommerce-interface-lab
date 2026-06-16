package com.ws101.maningcay.ecommerceapi.exception;

import com.ws101.maningcay.ecommerceapi.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle404(Exception ex) {
        return ResponseEntity.status(404).body(
                new ErrorResponse(LocalDateTime.now(), 404, "Not Found", ex.getMessage())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handle400(Exception ex) {
        return ResponseEntity.status(400).body(
                new ErrorResponse(LocalDateTime.now(), 400, "Bad Request", ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle500(Exception ex) {
        return ResponseEntity.status(500).body(
                new ErrorResponse(LocalDateTime.now(), 500, "Server Error", ex.getMessage())
        );
    }
}