package org.plexus.hotelbookings.domain.ports.out;


import org.plexus.hotelbookings.domain.model.BookingSearch;

import java.util.Optional;

public interface GetBookingSearchRepositoryPort {
    Optional<BookingSearch> execute(final BookingSearch bookingSearch);
}
