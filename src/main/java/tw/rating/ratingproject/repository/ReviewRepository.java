package tw.rating.ratingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import tw.rating.ratingproject.entity.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByStudentId(Integer studentId);
    List<Review> findByTutorCourseId(Integer tutorCourseId);
    Review findByBookingId(Integer bookingId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.tutorCourseId = :tutorCourseId")
    Double findAverageRatingByTutorCourseId(@Param("tutorCourseId") Integer tutorCourseId);
}
