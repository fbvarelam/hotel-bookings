package org.plexus.hotelbookings.infrastructure.jpa.adapters;

import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.domain.ports.out.GetBookingSearchRepositoryPort;
import org.plexus.hotelbookings.infrastructure.jpa.mappers.BookingSearchMapper;
import org.plexus.hotelbookings.infrastructure.jpa.repositories.BookingSearchJPARepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingSearchJPARepositoryAdapter implements GetBookingSearchRepositoryPort {

    public final BookingSearchJPARepository bookingSearchJPARepository;
    public final BookingSearchMapper bookingSearchMapper;

    public BookingSearchJPARepositoryAdapter(final BookingSearchJPARepository bookingSearchJPARepository, final BookingSearchMapper bookingSearchMapper1) {
        this.bookingSearchJPARepository = bookingSearchJPARepository;
        this.bookingSearchMapper = bookingSearchMapper1;
    }

    @Override
    public Optional<BookingSearch> execute(final BookingSearch bookingSearch) {
        return this.bookingSearchJPARepository.findFirstByHotelIdAndCheckInBetweenAndCheckOutBetween(bookingSearch.hotelId(), bookingSearch.checkIn(), bookingSearch.checkIn(),
                        bookingSearch.checkOut(), bookingSearch.checkOut())
                .map(bookingSearchMapper::toDomain);
    }
}
