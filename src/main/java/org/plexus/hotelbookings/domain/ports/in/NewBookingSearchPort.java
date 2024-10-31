package org.plexus.hotelbookings.domain.ports.in;


import org.plexus.hotelbookings.domain.model.BookingSearch;

public interface NewBookingSearchPort {
    BookingSearch execute(final BookingSearch bookingSearch);
}
