package tw.rating.ratingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tw.rating.ratingproject.entity.Booking;
import tw.rating.ratingproject.entity.Review;
import tw.rating.ratingproject.repository.BookingRepository;
import tw.rating.ratingproject.repository.ReviewRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;
    private static final int MAX_COMMENT_LENGTH = 500;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Optional<Review> findById(Integer id) {
        return reviewRepository.findById(id);
    }

    public List<Review> findByUserId(Integer userId) {
        return reviewRepository.findByUserId(userId);
    }

    public List<Review> findByCourseId(Integer courseId) {
        return reviewRepository.findByCourseId(courseId);
    }

    public Double getAverageRating(Integer courseId) {
        Double average = reviewRepository.findAverageRatingByCourseId(courseId);
        return average != null ? average : 0.0;
    }

    public Review save(Review review) {
        validateReview(review);

        if (review.getBookingId() == null) {
            throw new IllegalArgumentException("Booking 不能為空");
        }

        // 檢查是否已存在評論
        Review existingReview = reviewRepository.findByBookingId(review.getBookingId());
        if (existingReview != null) {
            throw new IllegalArgumentException("此預約已有評論,無法重複評論");
        }

        Booking booking = bookingRepository.findById(review.getBookingId())
            .orElseThrow(() -> new NoSuchElementException(
                    "Booking ID: " + review.getBookingId() + " 不存在"
            ));

        review.setUserId(booking.getUserId());
        review.setCourseId(booking.getCourseId());
        return reviewRepository.save(review);
    }

    public Optional<Review> update(Integer id, Review updatedReview) {
        return reviewRepository.findById(id).map(existing -> {
            validateReview(updatedReview);
            existing.setRating(updatedReview.getRating());
            existing.setComment(updatedReview.getComment());
            return reviewRepository.save(existing);
        });
    }

    public boolean deleteById(Integer id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Review findByBookingId(Long bookingId) {
        return reviewRepository.findByBookingId(bookingId);
    }
    
    private void validateReview(Review review) {
        if (review.getRating() == null) {
            throw new IllegalArgumentException("評分不能為空");
        }
        if (review.getRating() < MIN_RATING || review.getRating() > MAX_RATING) {
            throw new IllegalArgumentException("評分必須在 " + MIN_RATING + " 到 " + MAX_RATING + " 之間");
        }
        if (review.getComment() != null && review.getComment().length() > MAX_COMMENT_LENGTH) {
            throw new IllegalArgumentException("評論不能超過 " + MAX_COMMENT_LENGTH + " 個字元");
        }
    }
}
