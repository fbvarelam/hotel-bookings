package org.plexus.hotelbookings.infrastructure.rest.adapters;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.openapitools.model.BookingSearchCountResponse;
import org.openapitools.model.BookingSearchResponse;
import org.plexus.hotelbookings.domain.exceptions.BookingSearchNotFoundException;
import org.plexus.hotelbookings.domain.ports.in.GetBookingSearchByIdPort;
import org.plexus.hotelbookings.domain.ports.in.GetBookingSearchCountByIdPort;
import org.plexus.hotelbookings.domain.ports.in.GetBookingSearchPort;
import org.plexus.hotelbookings.domain.ports.in.NotificationBookingSearchPort;
import org.plexus.hotelbookings.infrastructure.rest.exceptions.BookingSearchNotFoundRestException;
import org.plexus.hotelbookings.infrastructure.rest.mappers.BookingSearchRestMapper;
import org.plexus.hotelbookings.infrastructure.rest.model.BookingSearchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class BookingSearchController {

    private final GetBookingSearchByIdPort getBookingSearchByIdPort;
    private final GetBookingSearchCountByIdPort getBookingSearchCountByIdPort;
    private final NotificationBookingSearchPort notificationBookingSearchPort;
    private final GetBookingSearchPort getBookingSearchPort;

    private final BookingSearchRestMapper bookingSearchRestMapper;

    public BookingSearchController(final GetBookingSearchByIdPort getBookingSearchByIdPort, final GetBookingSearchCountByIdPort getBookingSearchCountByIdPort, final NotificationBookingSearchPort notificationBookingSearchPort, final GetBookingSearchPort getBookingSearchPort, BookingSearchRestMapper bookingSearchRestMapper) {
        this.getBookingSearchByIdPort = getBookingSearchByIdPort;
        this.getBookingSearchCountByIdPort = getBookingSearchCountByIdPort;
        this.notificationBookingSearchPort = notificationBookingSearchPort;
        this.getBookingSearchPort = getBookingSearchPort;
        this.bookingSearchRestMapper = bookingSearchRestMapper;
    }


    @PostMapping("/search")
    public ResponseEntity<BookingSearchResponse> search(@Valid @RequestBody final BookingSearchRequest request) {

        final var bookingSearch = this.bookingSearchRestMapper.toRequest(request);

        final var search = this.getBookingSearchPort.execute(bookingSearch);

        final var searchSaved = this.notificationBookingSearchPort.execute(search);

        final var response = this.bookingSearchRestMapper.toBookingSearchResponse(searchSaved);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/count")
    public ResponseEntity<BookingSearchCountResponse> getBookingSearchWithCount(@Valid @NotBlank @RequestParam final String searchId) {

        try {
            final var bookingSearch = this.getBookingSearchByIdPort.execute(searchId);
            final var countSearches = this.getBookingSearchCountByIdPort.execute(searchId);
            final var response = this.bookingSearchRestMapper.toBookingSearchCountResponse(bookingSearch, countSearches);
            return ResponseEntity.ok(response);

        } catch (final BookingSearchNotFoundException e) {
            throw new BookingSearchNotFoundRestException(e.getMessage());
        } catch (final HttpServerErrorException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}