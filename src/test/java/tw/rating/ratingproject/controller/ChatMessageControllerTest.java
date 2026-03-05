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
        
        // 清理可能的舊資料
        bookingRepository.deleteAll();
        
        testBooking = new Booking();
        testBooking.setStudentId(1);
        testBooking.setTutorId(101);
        testBooking.setStatus("confirmed");
        testBooking = bookingRepository.save(testBooking);
    }

    /**
     * 確認 POST /api/chat-messages 回傳的 JSON 結構：
     * - booking.id 存在且正確（巢狀結構）
     * - 頂層不含獨立的 bookingId 欄位
     * - senderId 和 content 正常存在於頂層
     */
    @Test
    void testPostChatMessage_responseShouldHaveNestedBookingId() throws Exception {
        Map<String, Object> requestBody = Map.of(
                "bookingId", testBooking.getId(),
                "senderId", 1,
                "content", "Hello tutor"
        );

        mockMvc.perform(post("/api/chat-messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.senderId").value(1))
                .andExpect(jsonPath("$.content").value("Hello tutor"))
                .andExpect(jsonPath("$.booking.id").value(testBooking.getId()))
                .andExpect(jsonPath("$.bookingId").doesNotExist());
    }

    @Test
    void testPostChatMessage_verifyJsonStructureWithDifferentSender() throws Exception {
        Map<String, Object> requestBody = Map.of(
                "bookingId", testBooking.getId(),
                "senderId", 101,
                "content", "Confirming bookingId field is nested"
        );

        mockMvc.perform(post("/api/chat-messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.senderId").value(101))
                .andExpect(jsonPath("$.content").value("Confirming bookingId field is nested"))
                .andExpect(jsonPath("$.booking").exists())
                .andExpect(jsonPath("$.booking.id").isNumber())
                .andExpect(jsonPath("$.bookingId").doesNotExist());
    }
}
