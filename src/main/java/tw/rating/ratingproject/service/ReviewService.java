package tw.rating.ratingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tw.rating.ratingproject.entity.Review;
import tw.rating.ratingproject.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

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
}
