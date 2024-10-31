package org.plexus.hotelbookings.infrastructure.jpa.mappers;

import org.mapstruct.Mapper;
import org.plexus.hotelbookings.domain.model.BookingSearch;
import org.plexus.hotelbookings.infrastructure.jpa.model.BookingSearchEntity;

@Mapper(componentModel = "spring")
public abstract class BookingSearchMapper {

    public abstract BookingSearchEntity toEntity(final BookingSearch bookingSearch);

    public abstract BookingSearch toDomain(final BookingSearchEntity entity);
}