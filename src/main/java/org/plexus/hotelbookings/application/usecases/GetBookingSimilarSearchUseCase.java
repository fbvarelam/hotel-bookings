package org.plexus.hotelbookings.application.usecases;


import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.domain.ports.in.GetBookingSearchPort;
import org.plexus.hotelbookings.domain.ports.out.GetBookingSearchRepositoryPort;

public class GetBookingSimilarSearchUseCase implements GetBookingSearchPort {

    private final GetBookingSearchRepositoryPort getBookingSearchRepositoryPort;

    public GetBookingSimilarSearchUseCase(final GetBookingSearchRepositoryPort getBookingSearchRepositoryPort) {
        this.getBookingSearchRepositoryPort = getBookingSearchRepositoryPort;
    }

    @Override
    public BookingSearch execute(final BookingSearch bookingSearch) {
        return this.getBookingSearchRepositoryPort.execute(bookingSearch)
                .orElse(bookingSearch);
    }
}
