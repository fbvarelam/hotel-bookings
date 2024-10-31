package org.plexus.hotelbookings.infrastructure.rest.exceptions;

public class BookingSearchNotFoundRestException extends RuntimeException {

    public BookingSearchNotFoundRestException(final String message) {
        super(message);
    }
}
