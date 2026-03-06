package tw.rating.ratingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.rating.ratingproject.entity.ChatMessage;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT c FROM ChatMessage c WHERE c.bookingId = :bookingId ORDER BY c.createdAt ASC")
    List<ChatMessage> findByBookingIdOrderByCreatedAtAsc(@Param("bookingId") Long bookingId);
}
