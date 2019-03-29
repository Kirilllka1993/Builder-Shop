package com.vironit.kazimirov.exception;

public class SubsectionNotFoundException extends Throwable {
    public SubsectionNotFoundException() {
        super();
    }

    public SubsectionNotFoundException(String message) {
        super(message);
    }

    public SubsectionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubsectionNotFoundException(Throwable cause) {
        super(cause);
    }

    protected SubsectionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
