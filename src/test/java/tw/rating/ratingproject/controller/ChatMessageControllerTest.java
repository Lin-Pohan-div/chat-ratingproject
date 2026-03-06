package tw.rating.ratingproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
class ChatMessageControllerTest {

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
        testBooking.setCourseId(101L);
        testBooking.setUnitPrice(100);
        testBooking.setLessonCount(1);
        testBooking = bookingRepository.save(testBooking);
    }

    /**
     * 確認 POST /api/chat-messages 回傳的 JSON 結構：
     * - bookingId 存在且正確（對應 ChatMessage.bookingId）
     * - role 和 message 正常存在於頂層
     */
    @Test
    void testPostChatMessage_responseShouldHaveCorrectFields() throws Exception {
        Map<String, Object> requestBody = Map.of(
                "bookingId", testBooking.getId(),
                "role", 1,
                "message", "Hello tutor"
        );

        mockMvc.perform(post("/api/chat-messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value(1))
                .andExpect(jsonPath("$.message").value("Hello tutor"))
                .andExpect(jsonPath("$.bookingId").value(testBooking.getId()));
    }

    @Test
    void testPostChatMessage_verifyJsonStructureWithDifferentRole() throws Exception {
        Map<String, Object> requestBody = Map.of(
                "bookingId", testBooking.getId(),
                "role", 2,
                "message", "Confirming role field is correct"
        );

        mockMvc.perform(post("/api/chat-messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value(2))
                .andExpect(jsonPath("$.message").value("Confirming role field is correct"))
                .andExpect(jsonPath("$.bookingId").exists())
                .andExpect(jsonPath("$.bookingId").isNumber());
    }
}
