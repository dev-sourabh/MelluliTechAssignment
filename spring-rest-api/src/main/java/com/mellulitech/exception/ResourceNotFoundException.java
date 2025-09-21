package com.mellulitech.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Object resourceId) {
        super(String.format("%s not found with ID: %s", resourceName, resourceId));
    }
}
