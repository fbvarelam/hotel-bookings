package org.plexus.hotelbookings.application.usecases;


import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.domain.ports.in.NotificationBookingSearchPort;
import org.plexus.hotelbookings.domain.ports.out.NotificationBookingSearchRepositoryPort;

public class NotificationBookingSearchUseCase implements NotificationBookingSearchPort {

    private final NotificationBookingSearchRepositoryPort notificationBookingSearchRepositoryPort;


    public NotificationBookingSearchUseCase(final NotificationBookingSearchRepositoryPort notificationBookingSearchRepositoryPort) {
        this.notificationBookingSearchRepositoryPort = notificationBookingSearchRepositoryPort;
    }

    @Override
    public BookingSearch execute(final BookingSearch bookingSearch) {
        return notificationBookingSearchRepositoryPort.execute(bookingSearch);
    }
}
