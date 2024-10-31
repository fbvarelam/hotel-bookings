package org.plexus.hotelbookings.infrastructure.jpa.repositories;

import org.plexus.hotelbookings.infrastructure.jpa.model.BookingSearchEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface BookingSearchJPARepository extends CrudRepository<BookingSearchEntity, Long> {
    Optional<BookingSearchEntity> findFirstBySearchId(final String searchId);

    Optional<BookingSearchEntity> findFirstByHotelIdAndCheckInBetweenAndCheckOutBetween(
            final String hotelId, final Date checkIn, final Date checkIn2, final Date checkOut, final Date checkOut2);

    int countBySearchId(final String searchId);
}
