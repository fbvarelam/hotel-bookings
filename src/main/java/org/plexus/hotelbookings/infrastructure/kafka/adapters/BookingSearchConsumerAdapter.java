package org.plexus.hotelbookings.infrastructure.kafka.adapters;

import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.domain.ports.in.NewBookingSearchPort;
import org.plexus.hotelbookings.domain.ports.out.NotificationBookingSearchRepositoryPort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookingSearchConsumerAdapter implements NotificationBookingSearchRepositoryPort {

    private final NewBookingSearchPort newBookingSearchPort;

    public BookingSearchConsumerAdapter(final NewBookingSearchPort newBookingSearchPort) {
        this.newBookingSearchPort = newBookingSearchPort;
    }

    @KafkaListener(topics = "#{'${kafka.topic}'}", groupId = "#{'${kafka.group-id}'}")
    public BookingSearch execute(final BookingSearch bookingSearch) {
        return this.newBookingSearchPort.execute(bookingSearch);
    }
}
