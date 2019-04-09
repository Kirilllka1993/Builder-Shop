package com.vironit.kazimirov.exception;

public class CartItemNotFoundException extends Exception {
    public CartItemNotFoundException() {
    }

    public CartItemNotFoundException(String message) {
        super(message);
    }

    public CartItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartItemNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CartItemNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
