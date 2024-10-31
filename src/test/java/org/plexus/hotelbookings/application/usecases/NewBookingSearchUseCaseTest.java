package org.plexus.hotelbookings.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.domain.ports.out.NewBookingSearchRepositoryPort;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewBookingSearchUseCaseTest {

    @Mock
    private NewBookingSearchRepositoryPort newBookingSearchRepositoryPort;

    @InjectMocks
    private NewBookingSearchUseCase newBookingSearchUseCase;


    @Test
    void testExecute() {
        // Arrange
        BookingSearch bookingSearch = new BookingSearch("searchId", "hotelId", new Date(), new Date(), List.of(1, 2));
        BookingSearch expectedBookingSearch = new BookingSearch("searchId", "hotelId", new Date(), new Date(), List.of(1, 2));
        when(newBookingSearchRepositoryPort.execute(bookingSearch)).thenReturn(expectedBookingSearch);

        BookingSearch result = newBookingSearchUseCase.execute(bookingSearch);

        assertEquals(expectedBookingSearch, result);
        verify(newBookingSearchRepositoryPort, times(1)).execute(bookingSearch);
    }
}