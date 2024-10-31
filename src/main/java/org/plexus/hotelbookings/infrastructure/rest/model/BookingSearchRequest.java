package org.plexus.hotelbookings.infrastructure.rest.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record BookingSearchRequest(
        @NotBlank(message = "cannot be blank") String hotelId,
        @Pattern(regexp = DATE_FORMAT_REGEXP,
                message = DATE_FORMAT_VALIDATION_MESSAGE)
        @NotBlank(message = "is blank or has incorrect format") String checkIn,

        @Pattern(regexp = DATE_FORMAT_REGEXP,
                message = DATE_FORMAT_VALIDATION_MESSAGE)
        @NotBlank(message = "is blank or has incorrect format") String checkOut,

        @NotEmpty(message = "cannot be empty") List<Integer> ages
) {
    private static final String DATE_FORMAT_REGEXP = "^\\d{2}/\\d{2}/\\d{4}$";
    private static final String DATE_FORMAT_VALIDATION_MESSAGE = "Date format: 'dd/MM/yyyy'";
}