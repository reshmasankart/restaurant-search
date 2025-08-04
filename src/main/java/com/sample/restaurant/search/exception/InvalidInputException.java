package com.sample.restaurant.search.exception;

/**
 * Custom exception class for handling invalid input scenarios in the Restaurant API.
 * This exception is thrown when the input provided by the user does not meet the required validation criteria.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
