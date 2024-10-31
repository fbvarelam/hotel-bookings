package org.plexus.hotelbookings.domain.ports.out;

import org.plexus.hotelbookings.domain.model.BookingSearch;

import java.util.Optional;

public interface GetBookingSearchByIdRepositoryPort {
    Optional<BookingSearch> execute(final String id);
}
