package tw.rating.ratingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.rating.ratingproject.entity.ChatMessage;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    @Query("SELECT c FROM ChatMessage c WHERE c.booking.id = :bookingId ORDER BY c.createdAt ASC")
    List<ChatMessage> findByBookingIdOrderByCreatedAtAsc(@Param("bookingId") Integer bookingId);
}
