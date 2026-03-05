package tw.rating.ratingproject.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tw.rating.ratingproject.entity.Booking;
import tw.rating.ratingproject.entity.Review;
import tw.rating.ratingproject.repository.BookingRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookingRepository bookingRepository;

    private Booking testBooking;

    @BeforeEach
    void setUp() {
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
    void testSaveReview_withValidData_shouldPersist() {
        Review review = new Review();
        review.setBookingId(testBooking.getId());
        review.setRating(5);
        review.setComment("Excellent tutor");

        Review saved = reviewService.save(review);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getRating()).isEqualTo(5);
    }

    @Test
    void testFindByBookingId_withReview_shouldReturnReview() {
        Review review = new Review();
        review.setBookingId(testBooking.getId());
        review.setRating(4);
        review.setComment("Good session");
        reviewService.save(review);

        Review found = reviewService.findByBookingId(testBooking.getId());

        assertThat(found).isNotNull();
        assertThat(found.getRating()).isEqualTo(4);
    }

    @Test
    void testFindByBookingId_withNoReview_shouldReturnNull() {
        Review found = reviewService.findByBookingId(testBooking.getId());

        assertThat(found).isNull();
    }

    @Test
    void testSaveReview_withNullRating_shouldFail() {
        Review review = new Review();
        review.setBookingId(testBooking.getId());
        review.setRating(null);

        assertThatThrownBy(() -> reviewService.save(review)).isNotNull();
    }

    @Test
    void testSaveReview_withoutBooking_shouldFail() {
        Review review = new Review();
        review.setRating(5);
        review.setComment("Test");

        assertThatThrownBy(() -> reviewService.save(review)).isNotNull();
    }
}
