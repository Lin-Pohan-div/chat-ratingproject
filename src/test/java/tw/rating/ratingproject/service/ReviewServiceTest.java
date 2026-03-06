package tw.rating.ratingproject.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tw.rating.ratingproject.entity.Review;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    void testSaveReview_withValidData_shouldPersist() {
        Review review = new Review();
        review.setUserId(1L);
        review.setCourseId(101L);
        review.setRating((byte) 5);
        review.setComment("Excellent tutor");

        Review saved = reviewService.save(review);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getRating()).isEqualTo((byte) 5);
    }

    @Test
    void testFindByCourseId_withReview_shouldReturnReview() {
        Review review = new Review();
        review.setUserId(1L);
        review.setCourseId(101L);
        review.setRating((byte) 4);
        review.setComment("Good session");
        reviewService.save(review);

        List<Review> found = reviewService.findByCourseId(101L);

        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getRating()).isEqualTo((byte) 4);
    }

    @Test
    void testFindByCourseId_withNoReview_shouldReturnEmpty() {
        List<Review> found = reviewService.findByCourseId(999L);

        assertThat(found).isEmpty();
    }

    @Test
    void testFindByUserId_withReview_shouldReturnReview() {
        Review review = new Review();
        review.setUserId(10L);
        review.setCourseId(200L);
        review.setRating((byte) 3);
        review.setComment("Average");
        reviewService.save(review);

        List<Review> found = reviewService.findByUserId(10L);

        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getComment()).isEqualTo("Average");
    }

    @Test
    void testSaveReview_withNullRating_shouldFail() {
        Review review = new Review();
        review.setUserId(1L);
        review.setCourseId(101L);
        review.setRating(null);

        assertThatThrownBy(() -> reviewService.save(review)).isNotNull();
    }

    @Test
    void testSaveReview_withoutUserId_shouldFail() {
        Review review = new Review();
        review.setCourseId(101L);
        review.setRating((byte) 5);
        review.setComment("Test");

        assertThatThrownBy(() -> reviewService.save(review)).isNotNull();
    }
}
