package com.vironit.kazimirov.exception;

public class RepeatitionException extends Exception {
    public RepeatitionException() {
    }

    public RepeatitionException(String message) {
        super(message);
    }

    public RepeatitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatitionException(Throwable cause) {
        super(cause);
    }

    public RepeatitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
