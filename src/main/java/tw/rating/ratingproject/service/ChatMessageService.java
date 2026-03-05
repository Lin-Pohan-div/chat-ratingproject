package tw.rating.ratingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tw.rating.ratingproject.entity.Booking;
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

    public List<ChatMessage> findByBookingId(Integer bookingId) {
        return chatMessageRepository.findByBookingIdOrderByCreatedAtAsc(bookingId);
    }

    public ChatMessage save(ChatMessage chatMessage) {
        // 驗證 booking 存在
        if (chatMessage.getBooking() == null || chatMessage.getBooking().getId() == null) {
            throw new IllegalArgumentException("Booking 不能為空");
        }
        
        Booking booking = bookingRepository.findById(chatMessage.getBooking().getId())
            .orElseThrow(() -> new NoSuchElementException(
                "Booking ID: " + chatMessage.getBooking().getId() + " 不存在"
            ));
        
        chatMessage.setBooking(booking);
        return chatMessageRepository.save(chatMessage);
    }

    public ChatMessage saveMessage(Integer bookingId, Integer senderId, String content) {
        // 驗證 booking 存在
        if (bookingId == null || bookingId <= 0) {
            throw new IllegalArgumentException("Booking ID 不能為空");
        }
        
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new NoSuchElementException(
                "Booking ID: " + bookingId + " 不存在"
            ));
        
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setBooking(booking);
        chatMessage.setSenderId(senderId);
        chatMessage.setContent(content);
        
        return chatMessageRepository.save(chatMessage);
    }
}

