package tw.rating.ratingproject.controller;

import lombok.Data;

@Data
public class ChatMessageRequest {
    private Long bookingId;
    private Byte role;
    private String message;
}
