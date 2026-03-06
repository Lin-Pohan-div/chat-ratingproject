package tw.rating.ratingproject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lesson_feedback")
public class LessonFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @Column(nullable = false)
    private Byte rating;

    @Column(nullable = true, length = 1000)
    private String comment;
}
