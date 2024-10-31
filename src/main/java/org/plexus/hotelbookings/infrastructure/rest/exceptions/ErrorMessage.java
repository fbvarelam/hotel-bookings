package org.plexus.hotelbookings.infrastructure.rest.exceptions;

public record ErrorMessage(
        String message,
        String description
) {

}
