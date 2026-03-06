package tw.rating.ratingproject.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tw.rating.ratingproject.entity.Booking;
import tw.rating.ratingproject.entity.ChatMessage;
import tw.rating.ratingproject.repository.BookingRepository;
import tw.rating.ratingproject.repository.ChatMessageRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
class ChatMessageServiceTestsTest {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private EntityManager entityManager;

    private Booking testBooking;

    @BeforeEach
    void setUp() {
        testBooking = new Booking();
        testBooking.setOrderId(1L);
        testBooking.setCourseId(101L);
        testBooking.setUnitPrice(100);
        testBooking.setLessonCount(1);
        testBooking = bookingRepository.save(testBooking);
    }

    @Test
    void testSaveMessage_withValidData_shouldPersist() {
        ChatMessage saved = chatMessageService.save(testBooking.getId(), (byte) 1, "Hello tutor");

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getMessage()).isEqualTo("Hello tutor");
    }

    @Test
    void testFindByBookingId_withMultipleMessages_shouldReturnAll() {
        chatMessageService.save(testBooking.getId(), (byte) 1, "Message 1");
        chatMessageService.save(testBooking.getId(), (byte) 2, "Message 2");

        List<ChatMessage> messages = chatMessageService.findByBookingId(testBooking.getId());

        assertThat(messages).hasSize(2);
        assertThat(messages).extracting("message").contains("Message 1", "Message 2");
    }

    @Test
    void testFindByBookingId_withNoMessages_shouldReturnEmptyList() {
        List<ChatMessage> messages = chatMessageService.findByBookingId(testBooking.getId());

        assertThat(messages).isEmpty();
    }

    @Test
    void testFindByBookingId_withInvalidBookingId_shouldReturnEmptyList() {
        List<ChatMessage> messages = chatMessageService.findByBookingId(9999L);

        assertThat(messages).isEmpty();
    }

    @Test
    void testSaveMessage_withNullMessage_shouldFail() {
        assertThatThrownBy(() -> chatMessageService.save(testBooking.getId(), (byte) 1, null))
                .isNotNull();
    }

    @Test
    void testSaveMessage_withNullBookingId_shouldFail() {
        assertThatThrownBy(() -> chatMessageService.save(null, (byte) 1, "Test"))
                .isNotNull();
    }

    @Test
    void testSaveMessage_withLongMessage_shouldPersist() {
        String longMessage = "A".repeat(1000);
        ChatMessage saved = chatMessageService.save(testBooking.getId(), (byte) 1, longMessage);

        assertThat(saved).isNotNull();
        assertThat(saved.getMessage()).hasSize(1000);
    }

    @Test
    void testFindByBookingId_withMessagesInOrder_shouldReturnChronologically() {
        chatMessageService.save(testBooking.getId(), (byte) 1, "First");
        chatMessageService.save(testBooking.getId(), (byte) 2, "Second");

        List<ChatMessage> messages = chatMessageService.findByBookingId(testBooking.getId());

        assertThat(messages).hasSize(2);
        assertThat(messages.get(0).getMessage()).isEqualTo("First");
        assertThat(messages.get(1).getMessage()).isEqualTo("Second");
    }

    @Test
    void testSaveMessage_withDifferentRoles_shouldPersist() {
        chatMessageService.save(testBooking.getId(), (byte) 1, "Student message");
        chatMessageService.save(testBooking.getId(), (byte) 2, "Tutor message");

        List<ChatMessage> messages = chatMessageService.findByBookingId(testBooking.getId());

        assertThat(messages).hasSize(2);
        assertThat(messages).extracting("role").contains((byte) 1, (byte) 2);
    }

    @Test
    void testFindByBookingId_withMultipleBookings_shouldReturnOnlyRelevantMessages() {
        Booking anotherBooking = new Booking();
        anotherBooking.setOrderId(2L);
        anotherBooking.setCourseId(102L);
        anotherBooking.setUnitPrice(100);
        anotherBooking.setLessonCount(1);
        anotherBooking = bookingRepository.save(anotherBooking);

        chatMessageService.save(testBooking.getId(), (byte) 1, "Booking 1 message");
        chatMessageService.save(anotherBooking.getId(), (byte) 2, "Booking 2 message");

        List<ChatMessage> messages = chatMessageService.findByBookingId(testBooking.getId());

        assertThat(messages).hasSize(1);
        assertThat(messages.get(0).getMessage()).isEqualTo("Booking 1 message");
    }

    @Test
    void testSaveMessage_bookingIdShouldNotBeNull() {
        ChatMessage saved = chatMessageService.save(testBooking.getId(), (byte) 1, "Booking ID test");

        // flush + clear 讓 JPA 一級快取失效，強制從 DB 重新載入
        entityManager.flush();
        entityManager.clear();
        ChatMessage reloaded = chatMessageRepository.findById(saved.getId()).orElseThrow();

        assertThat(reloaded.getBookingId()).isNotNull();
        assertThat(reloaded.getBookingId()).isEqualTo(testBooking.getId());
    }
}
