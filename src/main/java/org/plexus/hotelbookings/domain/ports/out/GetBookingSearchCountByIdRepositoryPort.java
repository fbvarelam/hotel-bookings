package org.plexus.hotelbookings.domain.ports.out;

public interface GetBookingSearchCountByIdRepositoryPort {
    int execute(final String id);
}
