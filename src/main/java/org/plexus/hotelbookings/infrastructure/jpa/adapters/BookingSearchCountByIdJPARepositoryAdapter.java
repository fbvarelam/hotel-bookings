package org.plexus.hotelbookings.infrastructure.jpa.adapters;


import org.plexus.hotelbookings.domain.ports.out.GetBookingSearchCountByIdRepositoryPort;
import org.plexus.hotelbookings.infrastructure.jpa.repositories.BookingSearchJPARepository;
import org.springframework.stereotype.Service;

@Service
public class BookingSearchCountByIdJPARepositoryAdapter implements GetBookingSearchCountByIdRepositoryPort {

    public final BookingSearchJPARepository bookingSearchJPARepository;

    public BookingSearchCountByIdJPARepositoryAdapter(final BookingSearchJPARepository bookingSearchJPARepository) {
        this.bookingSearchJPARepository = bookingSearchJPARepository;
    }

    @Override
    public int execute(final String id) {
        return this.bookingSearchJPARepository.countBySearchId(id);
    }
}
