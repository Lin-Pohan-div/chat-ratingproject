package tw.rating.ratingproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tw.rating.ratingproject.dto.ChatMessageRequest;
import tw.rating.ratingproject.entity.ChatMessage;
import tw.rating.ratingproject.service.ChatMessageService;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/chat-messages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<ChatMessage>> getByBookingId(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(chatMessageService.findByBookingId(bookingId));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ChatMessageRequest request) {
        try {
            
            if (request.getBookingId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("驗證失敗: Booking ID 不能為空"));
            }
            
            if (request.getSenderId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("驗證失敗: Sender ID 不能為空"));
            }
            
            if (request.getContent() == null || request.getContent().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("驗證失敗: 消息內容不能為空"));
            }
            
            ChatMessage chatMessage = chatMessageService.saveMessage(
                request.getBookingId(),
                request.getSenderId(),
                request.getContent()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(chatMessage);
            
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
