package com.mellulitech.exception;

public class AlreadyActivityExistsException extends RuntimeException {
    public AlreadyActivityExistsException(String message) {
        super(message);
    }
    public AlreadyActivityExistsException(String resourceName, Object resourceId) {
        super(String.format("%s Activity already exist: %s", resourceName, resourceId));
    }
}
