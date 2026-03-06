package tw.rating.ratingproject.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Integer bookingId;
    private Integer rating;
    private String content;
}
