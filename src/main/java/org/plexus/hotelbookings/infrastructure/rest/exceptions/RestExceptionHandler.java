package org.plexus.hotelbookings.infrastructure.rest.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        final var errorResponse = new ErrorMessage("Invalid request", Objects.requireNonNull(ex.getDetailMessageArguments())[1].toString());

        logger.error("MethodArgumentNotValidException: {}", errorResponse.description());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(final IllegalArgumentException ex) {
        final var errorResponse = new ErrorMessage("Invalid request", ex.getMessage());

        logger.error("IllegalArgumentException: {}", errorResponse.description());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BookingSearchNotFoundRestException.class)
    public ResponseEntity<ErrorMessage> handleBookingSearchNotFoundException(final BookingSearchNotFoundRestException ex) {
        final var errorResponse = new ErrorMessage(
                "Resource not found",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);

    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleInternalServerErrorException(final ResponseStatusException ex) {
        final var errorResponse = new ErrorMessage("Contact the admin", ex.getMessage());

        logger.error("InternalServerError: {}", errorResponse.description());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
