package com.demo.inventory.exception;

/**
 * Thrown when a business rule is violated
 * (e.g. insufficient stock, invalid status transition).
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
