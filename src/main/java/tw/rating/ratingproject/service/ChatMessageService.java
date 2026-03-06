package tw.rating.ratingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tw.rating.ratingproject.entity.ChatMessage;
import tw.rating.ratingproject.repository.BookingRepository;
import tw.rating.ratingproject.repository.ChatMessageRepository;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final BookingRepository bookingRepository;

    public List<ChatMessage> findByBookingId(Long bookingId) {
        return chatMessageRepository.findByBookingIdOrderByCreatedAtAsc(bookingId);
    }

    public ChatMessage save(Long bookingId, Byte role, String message) {
        if (bookingId == null || bookingId <= 0) {
            throw new IllegalArgumentException("Booking ID 不能為空");
        }

        bookingRepository.findById(bookingId)
            .orElseThrow(() -> new NoSuchElementException("Booking ID: " + bookingId + " 不存在"));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setBookingId(bookingId);
        chatMessage.setRole(role);
        chatMessage.setMessage(message);

        return chatMessageRepository.save(chatMessage);
    }
}
