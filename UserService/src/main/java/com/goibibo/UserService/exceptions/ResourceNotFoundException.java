package com.goibibo.UserService.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Resource not found for request.");
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
