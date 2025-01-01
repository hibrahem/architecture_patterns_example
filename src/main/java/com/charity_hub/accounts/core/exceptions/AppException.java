package com.charity_hub.accounts.core.exceptions;


/**
 * Custom Exception class hierarchy extending RuntimeException.
 * Since Java doesn't have sealed classes, we use an abstract class.
 */
public abstract class AppException extends RuntimeException {

    protected AppException(String message) {
        super(message);
    }

    protected AppException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception for requirement violations.
     */
    public static class RequirementException extends AppException {
        public RequirementException(String description) {
            super(description);
        }
    }

    /**
     * Exception for bad request errors.
     */
    public static class BadRequestException extends AppException {
        public BadRequestException(String description) {
            super("Bad Request: " + (description != null ? description : ""));
        }
    }

    /**
     * Exception for unauthorized access.
     */
    public static class UnAuthorized extends AppException {
        public UnAuthorized(String description) {
            super("Unauthorized: " + (description != null ? description : ""));
        }

        public UnAuthorized() {
            super("Unauthorized");
        }

    }

    /**
     * Exception for not found errors.
     */
    public static class NotFoundException extends AppException {
        public NotFoundException(String description) {
            super("Not Found: " + (description != null ? description : ""));
        }
    }
}