package tw.rating.ratingproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.rating.ratingproject.entity.Review;
import tw.rating.ratingproject.service.ReviewService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<Review> getAll() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getById(@PathVariable Integer id) {
        return reviewService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<Review> getByStudentId(@PathVariable Integer studentId) {
        return reviewService.findByStudentId(studentId);
    }

    @GetMapping("/tutor-course/{tutorCourseId}")
    public List<Review> getByTutorCourseId(@PathVariable Integer tutorCourseId) {
        return reviewService.findByTutorCourseId(tutorCourseId);
    }

    @GetMapping("/tutor-course/{tutorCourseId}/average-rating")
    public ResponseEntity<Map<String, Object>> getAverageRating(@PathVariable Integer tutorCourseId) {
        Double avg = reviewService.getAverageRating(tutorCourseId);
        return ResponseEntity.ok(Map.of(
                "tutorCourseId", tutorCourseId,
                "averageRating", avg != null ? avg : 0.0
        ));
    }

    @PostMapping
    public ResponseEntity<Review> create(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.save(review));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> update(@PathVariable Integer id, @RequestBody Review review) {
        return reviewService.update(id, review)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return reviewService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
