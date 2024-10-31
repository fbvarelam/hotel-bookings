package org.plexus.hotelbookings.application.usecases;


import org.plexus.hotelbookings.domain.exceptions.BookingSearchNotFoundException;
import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.domain.ports.in.GetBookingSearchByIdPort;
import org.plexus.hotelbookings.domain.ports.out.GetBookingSearchByIdRepositoryPort;

public class GetBookingSearchByIdUseCase implements GetBookingSearchByIdPort {

    private final GetBookingSearchByIdRepositoryPort getBookingSearchByIdRepositoryPort;

    public GetBookingSearchByIdUseCase(final GetBookingSearchByIdRepositoryPort getBookingSearchByIdRepositoryPort) {
        this.getBookingSearchByIdRepositoryPort = getBookingSearchByIdRepositoryPort;
    }

    @Override
    public BookingSearch execute(final String id) throws BookingSearchNotFoundException {
        return this.getBookingSearchByIdRepositoryPort.execute(id)
                .orElseThrow(() -> new BookingSearchNotFoundException("Booking search not found"));
    }
}
