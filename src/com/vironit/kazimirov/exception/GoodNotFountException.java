package com.vironit.kazimirov.exception;

public class GoodNotFountException extends Exception {
    public GoodNotFountException() {
    }

    public GoodNotFountException(String message) {
        super(message);
    }

    public GoodNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoodNotFountException(Throwable cause) {
        super(cause);
    }

    public GoodNotFountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
