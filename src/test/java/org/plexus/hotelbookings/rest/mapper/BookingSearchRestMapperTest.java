package org.plexus.hotelbookings.rest.mapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.infrastructure.rest.mappers.BookingSearchRestMapper;
import org.plexus.hotelbookings.infrastructure.rest.model.BookingSearchRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingSearchRestMapperTest {

    private BookingSearchRestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new BookingSearchRestMapper();
    }

    @Test
    void toBookingSearchOK() {
        var request = new BookingSearchRequest("hotel123",
                "01/01/2023", "05/01/2023", List.of(25, 30));

        var bookingSearch = mapper.toRequest(request);

        assertNotNull(bookingSearch);
        assertEquals(request.hotelId(), bookingSearch.hotelId());
        assertEquals(request.ages(), bookingSearch.ages());
        assertEquals(request.checkIn(), new SimpleDateFormat("dd/MM/yyyy").format(bookingSearch.checkIn()));
        assertEquals(request.checkOut(), new SimpleDateFormat("dd/MM/yyyy").format(bookingSearch.checkOut()));
    }

    @Test
    void toBookingSearchResponseOK() {
        var bookingSearch = BookingSearch.builder()
                .searchId(UUID.randomUUID().toString())
                .build();
        var response = mapper.toBookingSearchResponse(bookingSearch);

        assertNotNull(response);
        assertEquals(bookingSearch.searchId(), response.getSearchId());
    }

    @Test
    void toBookingSearchCountResponseOK() {
        var bookingSearch = BookingSearch.builder()
                .searchId(UUID.randomUUID().toString())
                .hotelId("hotel123")
                .checkIn(new Date())
                .checkOut(new Date())
                .ages(List.of(25, 30))
                .build();

        var count = 5;

        var response = mapper.toBookingSearchCountResponse(bookingSearch, count);

        assertNotNull(response);
        assertEquals(bookingSearch.searchId(), response.getSearchId());
        assertEquals(count, response.getCount());
        assertEquals(bookingSearch.hotelId(), response.getSearch().getHotelId());
        assertEquals(new SimpleDateFormat("dd/MM/yyyy").format(bookingSearch.checkIn()), response.getSearch().getCheckIn());
        assertEquals(new SimpleDateFormat("dd/MM/yyyy").format(bookingSearch.checkOut()), response.getSearch().getCheckOut());
        assertEquals(bookingSearch.ages(), response.getSearch().getAges());
    }

    @Test
    void toRequestBookingSearchRequestCheckInAfterCheckOutThrowsException() {
        var request = new BookingSearchRequest("hotel123",
                "05/01/2023", "01/01/2023", List.of(25, 30));

        assertThrows(IllegalArgumentException.class, () -> mapper.toRequest(request));
    }
}