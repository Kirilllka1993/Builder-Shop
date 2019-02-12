package com.vironit.kazimirov.exception;

public class PurchaseNotFoundException extends Exception {
    public PurchaseNotFoundException() {
    }

    public PurchaseNotFoundException(String message) {
        super(message);
    }

    public PurchaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PurchaseNotFoundException(Throwable cause) {
        super(cause);
    }

    public PurchaseNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
