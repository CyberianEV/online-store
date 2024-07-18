package org.store.core.exceptions;

public class ZeroQuantityException extends RuntimeException {
    public ZeroQuantityException(String message) {
        super(message);
    }
}
