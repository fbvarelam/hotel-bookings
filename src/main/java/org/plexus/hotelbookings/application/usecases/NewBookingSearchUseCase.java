package org.plexus.hotelbookings.application.usecases;


import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.domain.ports.in.NewBookingSearchPort;
import org.plexus.hotelbookings.domain.ports.out.NewBookingSearchRepositoryPort;

public class NewBookingSearchUseCase implements NewBookingSearchPort {

    private final NewBookingSearchRepositoryPort newBookingSearchRepositoryPort;

    public NewBookingSearchUseCase(final NewBookingSearchRepositoryPort newBookingSearchRepositoryPort) {
        this.newBookingSearchRepositoryPort = newBookingSearchRepositoryPort;
    }

    @Override
    public BookingSearch execute(final BookingSearch bookingSearch) {
        return this.newBookingSearchRepositoryPort.execute(bookingSearch);
    }
}
