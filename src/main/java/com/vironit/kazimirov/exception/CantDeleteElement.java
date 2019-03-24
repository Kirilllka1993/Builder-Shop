package com.vironit.kazimirov.exception;

public class CantDeleteElement extends Exception {
    public CantDeleteElement() {
    }

    public CantDeleteElement(String message) {
        super(message);
    }

    public CantDeleteElement(String message, Throwable cause) {
        super(message, cause);
    }

    public CantDeleteElement(Throwable cause) {
        super(cause);
    }

    public CantDeleteElement(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
