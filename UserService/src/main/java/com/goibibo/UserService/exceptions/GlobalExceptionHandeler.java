package com.goibibo.UserService.exceptions;

import com.goibibo.UserService.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandeler {
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse response = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
