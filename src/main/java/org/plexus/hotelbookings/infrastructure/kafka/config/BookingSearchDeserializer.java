package org.plexus.hotelbookings.infrastructure.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.plexus.hotelbookings.domain.model.BookingSearch;


public class BookingSearchDeserializer implements Deserializer<BookingSearch> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public BookingSearch deserialize(final String topic, byte[] data) {
        try {
            return this.objectMapper.readValue(data, BookingSearch.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing BookingSearch", e);
        }
    }
}
