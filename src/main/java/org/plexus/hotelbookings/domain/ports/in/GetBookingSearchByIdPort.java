package org.plexus.hotelbookings.domain.ports.in;


import org.plexus.hotelbookings.domain.exceptions.BookingSearchNotFoundException;
import org.plexus.hotelbookings.domain.model.BookingSearch;

public interface GetBookingSearchByIdPort {
    BookingSearch execute(final String id) throws BookingSearchNotFoundException;
}
