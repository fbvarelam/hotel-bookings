package org.plexus.hotelbookings.infrastructure.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.plexus.hotelbookings.domain.model.BookingSearch;

public class BookingSearchSerializer implements Serializer<BookingSearch> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(final String topic, final BookingSearch data) {
        try {
            return this.objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing BookingSearch", e);
        }
    }
}
