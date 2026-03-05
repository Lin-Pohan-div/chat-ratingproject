package tw.rating.ratingproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import tw.rating.ratingproject.entity.Booking;

@Data
@Entity
@Table(name = "chat_messages", schema = "learning")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false, 
                foreignKey = @ForeignKey(name = "fk_chat_messages_bookings",
                                        value = ConstraintMode.CONSTRAINT))
    private Booking booking;

    @Column(name = "booking_id", insertable = false, updatable = false)
    private Integer bookingId;

    @Column(name = "sender_id", nullable = false)
    private Integer senderId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
