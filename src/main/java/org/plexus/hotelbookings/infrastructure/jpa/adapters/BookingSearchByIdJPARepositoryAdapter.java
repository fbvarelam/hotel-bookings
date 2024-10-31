package org.plexus.hotelbookings.infrastructure.jpa.adapters;

import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.domain.ports.out.GetBookingSearchByIdRepositoryPort;
import org.plexus.hotelbookings.infrastructure.jpa.mappers.BookingSearchMapper;
import org.plexus.hotelbookings.infrastructure.jpa.repositories.BookingSearchJPARepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingSearchByIdJPARepositoryAdapter implements GetBookingSearchByIdRepositoryPort {

    public final BookingSearchJPARepository bookingSearchJPARepository;
    public final BookingSearchMapper bookingSearchMapper;

    public BookingSearchByIdJPARepositoryAdapter(final BookingSearchJPARepository bookingSearchJPARepository, final BookingSearchMapper bookingSearchMapper) {
        this.bookingSearchJPARepository = bookingSearchJPARepository;
        this.bookingSearchMapper = bookingSearchMapper;
    }

    @Override
    public Optional<BookingSearch> execute(final String id) {
        return this.bookingSearchJPARepository.findFirstBySearchId(id)
                .map(this.bookingSearchMapper::toDomain);
    }
}
