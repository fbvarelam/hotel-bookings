package org.plexus.hotelbookings.config;

import org.plexus.hotelbookings.application.usecases.GetBookingSearchByIdUseCase;
import org.plexus.hotelbookings.application.usecases.GetBookingSearchCountByIdUseCase;
import org.plexus.hotelbookings.application.usecases.GetBookingSimilarSearchUseCase;
import org.plexus.hotelbookings.application.usecases.NewBookingSearchUseCase;
import org.plexus.hotelbookings.application.usecases.NotificationBookingSearchUseCase;
import org.plexus.hotelbookings.domain.ports.in.GetBookingSearchByIdPort;
import org.plexus.hotelbookings.domain.ports.in.GetBookingSearchCountByIdPort;
import org.plexus.hotelbookings.domain.ports.in.GetBookingSearchPort;
import org.plexus.hotelbookings.domain.ports.in.NewBookingSearchPort;
import org.plexus.hotelbookings.domain.ports.in.NotificationBookingSearchPort;
import org.plexus.hotelbookings.domain.ports.out.GetBookingSearchByIdRepositoryPort;
import org.plexus.hotelbookings.domain.ports.out.GetBookingSearchCountByIdRepositoryPort;
import org.plexus.hotelbookings.domain.ports.out.GetBookingSearchRepositoryPort;
import org.plexus.hotelbookings.domain.ports.out.NewBookingSearchRepositoryPort;
import org.plexus.hotelbookings.domain.ports.out.NotificationBookingSearchRepositoryPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public GetBookingSearchByIdPort getBookingSearchByIdUseCase(final GetBookingSearchByIdRepositoryPort getBookingSearchByIdRepositoryPort) {
        return new GetBookingSearchByIdUseCase(getBookingSearchByIdRepositoryPort);
    }

    @Bean
    public GetBookingSearchCountByIdPort getBookingSearchCountByIdPort(final GetBookingSearchCountByIdRepositoryPort getBookingSearchCountByIdRepositoryPort) {
        return new GetBookingSearchCountByIdUseCase(getBookingSearchCountByIdRepositoryPort);
    }

    @Bean
    public NewBookingSearchPort newBookingSearchPort(final NewBookingSearchRepositoryPort newBookingSearchRepositoryPort) {
        return new NewBookingSearchUseCase(newBookingSearchRepositoryPort);
    }

    @Bean
    public GetBookingSearchPort getBookingSearchesPort(final GetBookingSearchRepositoryPort getBookingSearchRepositoryPort) {
        return new GetBookingSimilarSearchUseCase(getBookingSearchRepositoryPort);
    }

    @Bean
    public NotificationBookingSearchPort notificationBookingSearchPort(final @Qualifier("bookingSearchProducerAdapter") NotificationBookingSearchRepositoryPort notificationBookingSearchRepositoryPort) {
        return new NotificationBookingSearchUseCase(notificationBookingSearchRepositoryPort);
    }
}
