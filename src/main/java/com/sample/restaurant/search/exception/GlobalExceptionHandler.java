package com.sample.restaurant.search.exception;


import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the Restaurant API.
 * This class handles various exceptions that may occur during the execution of the API,
 * providing a consistent error response format.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles RestaurantNotFoundException.
     * Returns a 404 Not Found response with a custom error message.
     *
     * @param ex the exception thrown when a restaurant is not found
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(RestaurantNotFoundException ex) {
        return buildErrorResponse("Restaurant does not exists: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InvalidInputException.
     * Returns a 400 Bad Request response with a custom error message.
     *
     * @param ex the exception thrown when input validation fails
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleBadRequest(InvalidInputException ex) {
        return buildErrorResponse("Invalid input " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles TypeMismatchException.
     * Returns a 400 Bad Request response with a custom error message indicating the invalid value.
     *
     * @param ex the exception thrown when a type mismatch occurs
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(TypeMismatchException ex) {
        String message = ex.getValue() != null ? "Invalid value '" + ex.getValue() +"' of :'"+ex.getRequiredType() + "' provided for " + ex.getPropertyName()
                : "Invalid value provided for " + ex.getRequiredType();
        return buildErrorResponse(message, HttpStatus.BAD_REQUEST);
    }
    /**
     * Handles HttpMessageNotReadableException.
     * Returns a 400 Bad Request response with a custom error message indicating malformed JSON.
     *
     * @param ex the exception thrown when the request body is not readable
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleInvalidFormat(HttpMessageNotReadableException ex) {
        return buildErrorResponse("Malformed JSON request: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general exceptions.
     * Returns a 500 Internal Server Error response with a custom error message.
     *
     * @param ex the exception thrown during the execution of the API
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return buildErrorResponse("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Builds a standardized error response.
     *
     * @param message the error message to include in the response
     * @param status  the HTTP status code for the response
     * @return ResponseEntity with error details
     */
    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", status.value());
        errorBody.put("error", status.getReasonPhrase());
        errorBody.put("message", message);
        return new ResponseEntity<>(errorBody, status);
    }
}








