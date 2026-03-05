package tw.rating.ratingproject.controller;

import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import tw.rating.ratingproject.entity.Booking;
import tw.rating.ratingproject.repository.BookingRepository;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
class ReviewControlTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    private Booking testBooking;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        bookingRepository.deleteAll();
        
        testBooking = new Booking();
        testBooking.setOrderId(1L);
        testBooking.setUserId(1L);
        testBooking.setCourseId(101L);
        testBooking.setLessonCount(1);
        testBooking.setUnitPrice(100);
        testBooking.setStatus(2);
        testBooking = bookingRepository.save(testBooking);
    }

    @Test
    void testPostReview_responseShouldHaveCorrectStructure() throws Exception {
        Map<String, Object> requestBody = Map.of(
                "bookingId", testBooking.getId(),
                "rating", 5,
                "content", "Great tutoring session"
        );

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.content").value("Great tutoring session"))
            .andExpect(jsonPath("$.bookingId").value(testBooking.getId()));
    }

    @Test
    void testPostReview_verifyJsonStructure() throws Exception {
        Map<String, Object> requestBody = Map.of(
                "bookingId", testBooking.getId(),
                "rating", 4,
                "content", "Good experience"
        );

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.content").value("Good experience"))
            .andExpect(jsonPath("$.bookingId").exists())
            .andExpect(jsonPath("$.bookingId").isNumber());
    }
}
