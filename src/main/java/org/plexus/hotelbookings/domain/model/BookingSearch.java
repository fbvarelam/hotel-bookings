package org.plexus.hotelbookings.domain.model;

import java.util.Date;
import java.util.List;

public record BookingSearch(
        String searchId,
        String hotelId,
        Date checkIn,
        Date checkOut,
        List<Integer> ages
) {
    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String searchId;
        private String hotelId;
        private Date checkIn;
        private Date checkOut;
        private List<Integer> ages;

        public Builder() {
        }

        public Builder(BookingSearch bookingSearch) {
            this.searchId = bookingSearch.searchId;
            this.hotelId = bookingSearch.hotelId;
            this.checkIn = bookingSearch.checkIn;
            this.checkOut = bookingSearch.checkOut;
            this.ages = bookingSearch.ages;
        }

        public Builder searchId(String searchId) {
            this.searchId = searchId;
            return this;
        }

        public Builder hotelId(String hotelId) {
            this.hotelId = hotelId;
            return this;
        }

        public Builder checkIn(Date checkIn) {
            this.checkIn = checkIn;
            return this;
        }

        public Builder checkOut(Date checkOut) {
            this.checkOut = checkOut;
            return this;
        }

        public Builder ages(List<Integer> ages) {
            this.ages = ages;
            return this;
        }

        public BookingSearch build() {
            return new BookingSearch(searchId, hotelId, checkIn, checkOut, ages);
        }
    }
}
