package tw.rating.ratingproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.rating.ratingproject.entity.Review;
import tw.rating.ratingproject.service.ReviewService;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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

    @GetMapping("/user/{userId}")
    public List<Review> getByUserId(@PathVariable Integer userId) {
        return reviewService.findByUserId(userId);
    }

    @GetMapping("/course/{courseId}")
    public List<Review> getByCourseId(@PathVariable Integer courseId) {
        return reviewService.findByCourseId(courseId);
    }

    @GetMapping("/course/{courseId}/average-rating")
    public ResponseEntity<Map<String, Object>> getAverageRating(@PathVariable Integer courseId) {
        Double avg = reviewService.getAverageRating(courseId);
        return ResponseEntity.ok(Map.of(
                "courseId", courseId,
                "averageRating", avg != null ? avg : 0.0
        ));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReviewRequest request) {
        try {
            if (request.getBookingId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("驗證失敗: bookingId 不能為空"));
            }
            if (request.getRating() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("驗證失敗: rating 不能為空"));
            }

            Review review = new Review();
            review.setBookingId(request.getBookingId());
            review.setRating(request.getRating());
            review.setComment(request.getContent());

            Review saved = reviewService.save(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("錯誤: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("驗證失敗: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("伺服器錯誤: " + e.getMessage()));
        }
    }

    public static class ReviewRequest {
        private Long bookingId;
        private Integer rating;
        private String content;

        public Long getBookingId() { return bookingId; }
        public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
        public Integer getRating() { return rating; }
        public void setRating(Integer rating) { this.rating = rating; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("伺服器錯誤: " + e.getMessage()));
    }

    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
