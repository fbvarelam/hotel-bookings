package org.plexus.hotelbookings.domain.ports.out;

import org.plexus.hotelbookings.domain.model.BookingSearch;

public interface NewBookingSearchRepositoryPort {
    BookingSearch execute(final BookingSearch bookingSearch);
}
