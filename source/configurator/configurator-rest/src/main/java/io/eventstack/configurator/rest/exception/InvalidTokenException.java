package io.eventstack.configurator.rest.exception;

/**
 * Created by gavin on 8/20/14.
 */
public class InvalidTokenException extends Exception {
    public InvalidTokenException() {
    }

    public InvalidTokenException(String s) {
        super(s);
    }

    public InvalidTokenException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidTokenException(Throwable throwable) {
        super(throwable);
    }
}
