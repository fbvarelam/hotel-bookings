package org.plexus.hotelbookings.kafka;

import jakarta.validation.constraints.NotNull;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.infrastructure.kafka.config.BookingSearchDeserializer;
import org.plexus.hotelbookings.infrastructure.kafka.config.BookingSearchSerializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
@EmbeddedKafka(partitions = 1, topics = {BookingSearchKafkaAdapterTest.BOOKING_SEARCH_TOPIC})
class BookingSearchKafkaAdapterTest {

    static final String BOOKING_SEARCH_TOPIC = "booking-search-topic";

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("kafka.topic", () -> "booking-search-topic");
    }

    private static @NotNull Consumer<String, BookingSearch> getStringBookingSearchConsumer() {
        Map<String, Object> consumerProps = new HashMap<>(KafkaTestUtils.consumerProps(kafkaContainer.getBootstrapServers(), "testGroup", "true"));
        consumerProps.put("key.deserializer", StringDeserializer.class.getName());
        consumerProps.put("value.deserializer", BookingSearchDeserializer.class.getName());
        consumerProps.put("enable.auto.commit", "true");
        DefaultKafkaConsumerFactory<String, BookingSearch> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);

        return consumerFactory.createConsumer();
    }

    private static @NotNull KafkaProducer<String, BookingSearch> getStringBookingSearchKafkaProducer() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BookingSearchSerializer.class.getName());

        return new KafkaProducer<>(producerProps);
    }

    @Test
    void produceAndConsumeBookingSearchOK() throws Exception {
        BookingSearch bookingSearch = BookingSearch.builder()
                .hotelId("1")
                .checkIn(new SimpleDateFormat("yyyy/MM/dd").parse("2023/10/01"))
                .checkOut(new SimpleDateFormat("yyyy/MM/dd").parse("2023/10/10"))
                .ages(Arrays.asList(25, 30))
                .build();

        KafkaProducer<String, BookingSearch> producer = getStringBookingSearchKafkaProducer();

        producer.send(new ProducerRecord<>(BOOKING_SEARCH_TOPIC, bookingSearch));
        producer.close();

        Consumer<String, BookingSearch> consumer = getStringBookingSearchConsumer();
        consumer.subscribe(List.of(BOOKING_SEARCH_TOPIC));
        ConsumerRecord<String, BookingSearch> singleRecord = KafkaTestUtils.getSingleRecord(consumer, BOOKING_SEARCH_TOPIC);

        BookingSearch consumedBookingSearch = singleRecord.value();

        assertNotNull(consumedBookingSearch);
        assertEquals(bookingSearch.hotelId(), consumedBookingSearch.hotelId());
        assertEquals(bookingSearch.checkIn(), consumedBookingSearch.checkIn());
        assertEquals(bookingSearch.checkOut(), consumedBookingSearch.checkOut());
        assertEquals(bookingSearch.ages(), consumedBookingSearch.ages());
    }
}