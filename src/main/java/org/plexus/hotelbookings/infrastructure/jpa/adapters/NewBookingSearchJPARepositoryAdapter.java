package org.plexus.hotelbookings.infrastructure.jpa.adapters;


import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.domain.ports.out.NewBookingSearchRepositoryPort;
import org.plexus.hotelbookings.infrastructure.jpa.mappers.BookingSearchMapper;
import org.plexus.hotelbookings.infrastructure.jpa.repositories.BookingSearchJPARepository;
import org.springframework.stereotype.Service;

@Service
public class NewBookingSearchJPARepositoryAdapter implements NewBookingSearchRepositoryPort {

    public final BookingSearchJPARepository bookingSearchJPARepository;
    public final BookingSearchMapper mapper;

    public NewBookingSearchJPARepositoryAdapter(BookingSearchJPARepository bookingSearchJPARepository, BookingSearchMapper mapper) {
        this.bookingSearchJPARepository = bookingSearchJPARepository;
        this.mapper = mapper;
    }

    @Override
    public BookingSearch execute(final BookingSearch bookingSearch) {
        var entity = this.mapper.toEntity(bookingSearch);

        var entitySaved = this.bookingSearchJPARepository.save(entity);

        return mapper.toDomain(entitySaved);
    }
}
