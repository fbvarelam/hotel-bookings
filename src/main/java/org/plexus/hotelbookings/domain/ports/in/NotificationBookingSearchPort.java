package org.plexus.hotelbookings.domain.ports.in;


import org.plexus.hotelbookings.domain.model.BookingSearch;

public interface NotificationBookingSearchPort {
    BookingSearch execute(final BookingSearch bookingSearch);
}
