package org.store.cart.exceptions;

public class ZeroQuantityException extends RuntimeException {
    public ZeroQuantityException(String message) {
        super(message);
    }
}
