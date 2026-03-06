package tw.rating.ratingproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;


@Data
@Entity
@Table(name = "chat_messages", schema = "deomDB")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(nullable = false)
    private Byte role;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;
}
