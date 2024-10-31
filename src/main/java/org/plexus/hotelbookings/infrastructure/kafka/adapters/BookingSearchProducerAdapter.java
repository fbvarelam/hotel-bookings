package org.plexus.hotelbookings.infrastructure.kafka.adapters;

import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.domain.ports.out.NotificationBookingSearchRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingSearchProducerAdapter implements NotificationBookingSearchRepositoryPort {

    private final KafkaTemplate<String, BookingSearch> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public BookingSearchProducerAdapter(final KafkaTemplate<String, BookingSearch> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public BookingSearch execute(final BookingSearch bookingSearch) {
        kafkaTemplate.send(topic, bookingSearch);
        return bookingSearch;
    }
}

