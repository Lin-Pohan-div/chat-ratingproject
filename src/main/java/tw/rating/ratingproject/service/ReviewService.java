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

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Optional<Review> findById(Integer id) {
        return reviewRepository.findById(id);
    }

    public List<Review> findByStudentId(Integer studentId) {
        return reviewRepository.findByStudentId(studentId);
    }

    public List<Review> findByTutorCourseId(Integer tutorCourseId) {
        return reviewRepository.findByTutorCourseId(tutorCourseId);
    }

    public Double getAverageRating(Integer tutorCourseId) {
        return reviewRepository.findAverageRatingByTutorCourseId(tutorCourseId);
    }

    public Review save(Review review) {
        // 驗證 rating 不能為空
        if (review.getRating() == null) {
            throw new IllegalArgumentException("評分不能為空");
        }
        // 確保訂單存在並填入 studentId / tutorCourseId
        if (review.getBooking() == null || review.getBooking().getId() == null) {
            throw new IllegalArgumentException("Booking 不能為空");
        }
        Booking booking = bookingRepository.findById(review.getBooking().getId())
            .orElseThrow(() -> new NoSuchElementException(
                    "Booking ID: " + review.getBooking().getId() + " 不存在"
            ));
        review.setBooking(booking);
        review.setStudentId(booking.getStudentId());
        // 目前 booking 沒有 tutorCourseId 欄位，暫時用 tutorId 作為代替
        review.setTutorCourseId(booking.getTutorId());
        return reviewRepository.save(review);
    }

    public Optional<Review> update(Integer id, Review updatedReview) {
        return reviewRepository.findById(id).map(existing -> {
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

    public Review findByBookingId(Integer bookingId) {
        return reviewRepository.findByBookingId(bookingId);
    }
}
