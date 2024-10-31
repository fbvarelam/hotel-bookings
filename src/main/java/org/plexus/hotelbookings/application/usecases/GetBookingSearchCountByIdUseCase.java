package org.plexus.hotelbookings.application.usecases;

import org.plexus.hotelbookings.domain.ports.in.GetBookingSearchCountByIdPort;
import org.plexus.hotelbookings.domain.ports.out.GetBookingSearchCountByIdRepositoryPort;

public class GetBookingSearchCountByIdUseCase implements GetBookingSearchCountByIdPort {

    private final GetBookingSearchCountByIdRepositoryPort getBookingSearchCountByIdRepositoryPort;

    public GetBookingSearchCountByIdUseCase(final GetBookingSearchCountByIdRepositoryPort getBookingSearchCountByIdRepositoryPort) {
        this.getBookingSearchCountByIdRepositoryPort = getBookingSearchCountByIdRepositoryPort;
    }

    @Override
    public int execute(final String id) {
        return this.getBookingSearchCountByIdRepositoryPort.execute(id);
    }
}
