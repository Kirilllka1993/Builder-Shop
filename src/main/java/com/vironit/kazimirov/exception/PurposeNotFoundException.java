package com.vironit.kazimirov.exception;

public class PurposeNotFoundException extends Throwable {
    public PurposeNotFoundException() {
        super();
    }

    public PurposeNotFoundException(String message) {
        super(message);
    }

    public PurposeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PurposeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PurposeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
