package org.plexus.hotelbookings.domain.ports.in;


public interface GetBookingSearchCountByIdPort {
    int execute(final String id);
}
