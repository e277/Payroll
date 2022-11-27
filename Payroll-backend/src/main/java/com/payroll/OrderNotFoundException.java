package com.payroll;

public class OrderNotFoundException extends RuntimeException {
    OrderNotFoundException(Long id) {
        super("There is no order with id " + id);
    }
}
