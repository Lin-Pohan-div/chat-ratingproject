package tw.rating.ratingproject.dto;

import lombok.Data;

@Data
public class LessonFeedbackRequest {
    private Long lessonId;
    private Byte rating;
    private String comment;
}
