package com.sample.restaurant.search.exception;

/**
 * Custom exception class for handling scenarios where a restaurant is not found.
 * This exception is thrown when a requested restaurant does not exist in the system.
 */
public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}