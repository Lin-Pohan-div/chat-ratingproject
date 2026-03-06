package tw.rating.ratingproject.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long userId;
    private Long courseId;
    private Byte rating;
    private String comment;
}
