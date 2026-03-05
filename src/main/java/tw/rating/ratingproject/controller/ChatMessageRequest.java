package tw.rating.ratingproject.controller;

import lombok.Data;

@Data
public class ChatMessageRequest {
    private Long bookingId;
    private Integer senderId;
    private String content;
}
