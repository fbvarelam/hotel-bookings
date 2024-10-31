package org.plexus.hotelbookings.infrastructure.rest.mappers;

import org.openapitools.model.BookingSearchCountResponse;
import org.openapitools.model.BookingSearchCountResponseSearch;
import org.openapitools.model.BookingSearchResponse;
import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.infrastructure.rest.model.BookingSearchRequest;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class BookingSearchRestMapper {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    public BookingSearch toRequest(final BookingSearchRequest request) {

        if (this.toDate(request.checkIn()).after(this.toDate(request.checkOut()))) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }

        return new BookingSearch(UUID.randomUUID().toString(), request.hotelId(), this.toDate(request.checkIn()), this.toDate(request.checkOut()), request.ages());
    }

    public BookingSearchResponse toBookingSearchResponse(final BookingSearch bookingSearch) {

        final var response = new BookingSearchResponse();
        response.searchId(bookingSearch.searchId());

        return response;
    }

    public BookingSearchCountResponse toBookingSearchCountResponse(final BookingSearch bookingSearch, final int count) {

        final var response = new BookingSearchCountResponse();
        response.searchId(bookingSearch.searchId());
        response.count(count);

        final var bookingSearchCountResponseSearch = new BookingSearchCountResponseSearch();
        bookingSearchCountResponseSearch.setHotelId(bookingSearch.hotelId());
        bookingSearchCountResponseSearch.setCheckIn(new SimpleDateFormat(DATE_FORMAT).format(bookingSearch.checkIn()));
        bookingSearchCountResponseSearch.setCheckOut(new SimpleDateFormat(DATE_FORMAT).format(bookingSearch.checkOut()));
        bookingSearchCountResponseSearch.setAges(bookingSearch.ages());
        response.setSearch(bookingSearchCountResponseSearch);

        return response;
    }

    private Date toDate(final String dateString) {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

            return formatter.parse(dateString);
        } catch (ParseException e) {
            return Date.from(Instant.now());
        }
    }
}
