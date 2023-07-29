package com.example.hogwarts.exceptions;

public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException() {
        super();
    }

    public FacultyNotFoundException(String message) {
        super(message);
    }

    public FacultyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacultyNotFoundException(Throwable cause) {
        super(cause);
    }

    protected FacultyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
