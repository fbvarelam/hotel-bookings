package org.plexus.hotelbookings.rest.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.openapitools.model.BookingSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/sql/data-dev.sql")
class SearchControllerAdapterE2eTest {

    private static final String UUID_PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void postBookingSearchOK() throws Exception {
        var request = new BookingSearchRequest();
        request.setHotelId("hotel123");
        request.setCheckIn("09/10/2024");
        request.setCheckOut("10/10/2024");
        request.setAges(List.of(25, 30));

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(jsonPath("searchId").value(matchesPattern(UUID_PATTERN)));
    }

    @Test
    void postBookingSearchCheckInAfterCheckOut() throws Exception {
        var request = new BookingSearchRequest();
        request.setHotelId("hotel123");
        request.setCheckIn("11/10/2024");
        request.setCheckOut("10/10/2024");
        request.setAges(List.of(25, 30));

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request"))
                .andExpect(jsonPath("$.description").value("Check-in date must be before check-out date"));
    }

    @Test
    void postBookingSearchInvalidCheckInFormat() throws Exception {
        var request = new BookingSearchRequest();
        request.setHotelId("hotel123");
        request.setCheckIn("2024/10/09");
        request.setCheckOut("10/10/2024");
        request.setAges(List.of(25, 30));

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request"))
                .andExpect(jsonPath("$.description").value("checkIn: Date format: 'dd/MM/yyyy'"));
    }

    @Test
    void postBookingSearchEmptyHotelId() throws Exception {
        var request = new BookingSearchRequest();
        request.setHotelId("");
        request.setCheckIn("09/10/2024");
        request.setCheckOut("10/10/2024");
        request.setAges(List.of(25, 30));

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request"))
                .andExpect(jsonPath("$.description").value("hotelId: cannot be blank"));
    }

    @Test
    void postBookingSearchNullHotelId() throws Exception {
        var request = new BookingSearchRequest();
        request.setCheckIn("09/10/2024");
        request.setCheckOut("10/10/2024");
        request.setAges(List.of(25, 30));

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request"))
                .andExpect(jsonPath("$.description").value("hotelId: cannot be blank"));
    }

    @Test
    void getBookingSearchWithCountOK() throws Exception {
        String searchId = "59a683b7-fc53-41ee-9928-2001bef90176";

        mockMvc.perform(get("/count")
                        .param("searchId", searchId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value(searchId))
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.count").value(1));
    }

    @Test
    void getBookingSearchWithNewSearchIdCountThrowException() throws Exception {
        String searchId = "59a683b7-fc53-41ee-9928-xxxxxxxxxxxx";

        mockMvc.perform(get("/count")
                        .param("searchId", searchId))
                .andExpect(status().isNoContent());
    }
}