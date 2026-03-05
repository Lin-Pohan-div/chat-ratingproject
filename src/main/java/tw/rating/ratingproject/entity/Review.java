package tw.rating.ratingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Entity
@Table(name = "reviews", schema = "learning")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 存放學生與課程的資料，透過 Booking 取得相關資訊
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_reviews_bookings",
                                        value = ConstraintMode.CONSTRAINT))
    private Booking booking;

    // 方便查詢或回傳時直接拿到 booking id
    @Column(name = "booking_id", insertable = false, updatable = false)
    private Integer bookingId;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "tutor_course_id", nullable = false)
    private Integer tutorCourseId;

    // 1~5 星評分
    @NotNull(message = "評分不能為空")
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    @JsonProperty("content")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
