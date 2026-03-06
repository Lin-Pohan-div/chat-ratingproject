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
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testPostReview_responseShouldHaveCorrectStructure() throws Exception {
        Map<String, Object> requestBody = Map.of(
                "userId", 1,
                "courseId", 101,
                "rating", 5,
                "comment", "Great tutoring session"
        );

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Great tutoring session"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.courseId").value(101));
    }

    @Test
    void testPostReview_verifyJsonStructure() throws Exception {
        Map<String, Object> requestBody = Map.of(
                "userId", 2,
                "courseId", 102,
                "rating", 4,
                "comment", "Good experience"
        );

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comment").value("Good experience"))
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.userId").isNumber())
                .andExpect(jsonPath("$.courseId").exists())
                .andExpect(jsonPath("$.courseId").isNumber());
    }
}
