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
import tw.rating.ratingproject.entity.Review;
import tw.rating.ratingproject.repository.ReviewRepository;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
class ReviewControlTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired(required = false)
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    private Review savedReview;

    @BeforeEach
    void setUp() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        reviewRepository.deleteAll();

        Review review = new Review();
        review.setUserId(1L);
        review.setCourseId(101L);
        review.setRating((byte) 4);
        review.setComment("Initial comment");
        savedReview = reviewRepository.save(review);
    }

    // ===================== GET =====================

    @Test
    void getAll_shouldReturnListWithSavedReview() throws Exception {
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].rating").isNumber());
    }

    @Test
    void getById_existingId_shouldReturn200WithReview() throws Exception {
        mockMvc.perform(get("/api/reviews/{id}", savedReview.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedReview.getId()))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.courseId").value(101))
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comment").value("Initial comment"));
    }

    @Test
    void getById_nonExistingId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/reviews/{id}", 999999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByUserId_shouldReturnMatchingReviews() throws Exception {
        mockMvc.perform(get("/api/reviews/user/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    void getByCourseId_shouldReturnMatchingReviews() throws Exception {
        mockMvc.perform(get("/api/reviews/course/{courseId}", 101L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].courseId").value(101));
    }

    @Test
    void getAverageRating_shouldReturnCourseIdAndAverageRating() throws Exception {
        mockMvc.perform(get("/api/reviews/course/{courseId}/average-rating", 101L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId").value(101))
                .andExpect(jsonPath("$.averageRating").isNumber());
    }

    // ===================== POST =====================

    @Test
    void post_validRequest_shouldReturn201WithCreatedReview() throws Exception {
        Map<String, Object> body = Map.of(
                "userId", 2,
                "courseId", 102,
                "rating", 5,
                "comment", "Excellent session"
        );

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").value(2))
                .andExpect(jsonPath("$.courseId").value(102))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Excellent session"));
    }

    @Test
    void post_missingUserId_shouldReturn400() throws Exception {
        Map<String, Object> body = Map.of(
                "courseId", 102,
                "rating", 5
        );

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("userId")));
    }

    @Test
    void post_missingCourseId_shouldReturn400() throws Exception {
        Map<String, Object> body = Map.of(
                "userId", 1,
                "rating", 3
        );

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("courseId")));
    }

    @Test
    void post_missingRating_shouldReturn400() throws Exception {
        Map<String, Object> body = Map.of(
                "userId", 1,
                "courseId", 101,
                "comment", "No rating provided"
        );

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("rating")));
    }

    // ===================== PUT =====================

    @Test
    void put_existingId_shouldReturn200WithUpdatedReview() throws Exception {
        Review updateBody = new Review();
        updateBody.setUserId(savedReview.getUserId());
        updateBody.setCourseId(savedReview.getCourseId());
        updateBody.setRating((byte) 2);
        updateBody.setComment("Updated comment");

        mockMvc.perform(put("/api/reviews/{id}", savedReview.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedReview.getId()))
                .andExpect(jsonPath("$.rating").value(2))
                .andExpect(jsonPath("$.comment").value("Updated comment"));
    }

    @Test
    void put_nonExistingId_shouldReturn404() throws Exception {
        Review updateBody = new Review();
        updateBody.setUserId(1L);
        updateBody.setCourseId(101L);
        updateBody.setRating((byte) 3);

        mockMvc.perform(put("/api/reviews/{id}", 999999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBody)))
                .andExpect(status().isNotFound());
    }

    // ===================== DELETE =====================

    @Test
    void delete_existingId_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/reviews/{id}", savedReview.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_nonExistingId_shouldReturn404() throws Exception {
        mockMvc.perform(delete("/api/reviews/{id}", 999999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_thenGetById_shouldReturn404() throws Exception {
        mockMvc.perform(delete("/api/reviews/{id}", savedReview.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/reviews/{id}", savedReview.getId()))
                .andExpect(status().isNotFound());
    }
}
