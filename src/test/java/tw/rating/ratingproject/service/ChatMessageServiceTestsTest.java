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
        testBooking.setStudentId(1);
        testBooking.setTutorId(101);
        testBooking.setStatus("confirmed");
        testBooking = bookingRepository.save(testBooking);
    }

    @Test
    void testSaveMessage_withValidData_shouldPersist() {
        ChatMessage msg = new ChatMessage();
        msg.setBooking(testBooking);
        msg.setSenderId(101);
        msg.setContent("Hello tutor");

        ChatMessage saved = chatMessageService.save(msg);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getContent()).isEqualTo("Hello tutor");
    }

    @Test
    void testFindByBookingId_withMultipleMessages_shouldReturnAll() {
        ChatMessage msg1 = new ChatMessage();
        msg1.setBooking(testBooking);
        msg1.setSenderId(1);
        msg1.setContent("Message 1");
        chatMessageService.save(msg1);

        ChatMessage msg2 = new ChatMessage();
        msg2.setBooking(testBooking);
        msg2.setSenderId(101);
        msg2.setContent("Message 2");
        chatMessageService.save(msg2);

        List<ChatMessage> messages = chatMessageService.findByBookingId(testBooking.getId());

        assertThat(messages).hasSize(2);
        assertThat(messages).extracting("content").contains("Message 1", "Message 2");
    }

    @Test
    void testFindByBookingId_withNoMessages_shouldReturnEmptyList() {
        List<ChatMessage> messages = chatMessageService.findByBookingId(testBooking.getId());

        assertThat(messages).isEmpty();
    }

    @Test
    void testFindByBookingId_withInvalidBookingId_shouldReturnEmptyList() {
        List<ChatMessage> messages = chatMessageService.findByBookingId(9999);

        assertThat(messages).isEmpty();
    }

    @Test
    void testSaveMessage_withNullContent_shouldFail() {
        ChatMessage msg = new ChatMessage();
        msg.setBooking(testBooking);
        msg.setSenderId(101);
        msg.setContent(null);

        assertThatThrownBy(() -> chatMessageService.save(msg))
                .isNotNull();
    }

    @Test
    void testSaveMessage_withoutBooking_shouldFail() {
        ChatMessage msg = new ChatMessage();
        msg.setSenderId(101);
        msg.setContent("Test");

        assertThatThrownBy(() -> chatMessageService.save(msg))
                .isNotNull();
    }

    @Test
    void testSaveMessage_withEmptyContent_shouldPersist() {
        ChatMessage msg = new ChatMessage();
        msg.setBooking(testBooking);
        msg.setSenderId(1);
        msg.setContent("");

        ChatMessage saved = chatMessageService.save(msg);

        assertThat(saved).isNotNull();
        assertThat(saved.getContent()).isEqualTo("");
    }

    @Test
    void testSaveMessage_withLongContent_shouldPersist() {
        String longContent = "A".repeat(1000);
        ChatMessage msg = new ChatMessage();
        msg.setBooking(testBooking);
        msg.setSenderId(1);
        msg.setContent(longContent);

        ChatMessage saved = chatMessageService.save(msg);

        assertThat(saved).isNotNull();
        assertThat(saved.getContent()).hasSize(1000);
    }

    @Test
    void testFindByBookingId_withMessagesInOrder_shouldReturnChronologically() {
        ChatMessage msg1 = new ChatMessage();
        msg1.setBooking(testBooking);
        msg1.setSenderId(1);
        msg1.setContent("First");
        chatMessageService.save(msg1);

        ChatMessage msg2 = new ChatMessage();
        msg2.setBooking(testBooking);
        msg2.setSenderId(101);
        msg2.setContent("Second");
        chatMessageService.save(msg2);

        List<ChatMessage> messages = chatMessageService.findByBookingId(testBooking.getId());

        assertThat(messages).hasSize(2);
        assertThat(messages.get(0).getContent()).isEqualTo("First");
        assertThat(messages.get(1).getContent()).isEqualTo("Second");
    }

    @Test
    void testSaveMessage_withDifferentSenders_shouldPersist() {
        ChatMessage msgFromStudent = new ChatMessage();
        msgFromStudent.setBooking(testBooking);
        msgFromStudent.setSenderId(1);
        msgFromStudent.setContent("Student message");

        ChatMessage msgFromTutor = new ChatMessage();
        msgFromTutor.setBooking(testBooking);
        msgFromTutor.setSenderId(101);
        msgFromTutor.setContent("Tutor message");

        chatMessageService.save(msgFromStudent);
        chatMessageService.save(msgFromTutor);

        List<ChatMessage> messages = chatMessageService.findByBookingId(testBooking.getId());

        assertThat(messages).hasSize(2);
        assertThat(messages).extracting("senderId").contains(1, 101);
    }

    @Test
    void testFindByBookingId_withMultipleBookings_shouldReturnOnlyRelevantMessages() {
        Booking anotherBooking = new Booking();
        anotherBooking.setStudentId(2);
        anotherBooking.setTutorId(102);
        anotherBooking.setStatus("confirmed");
        anotherBooking = bookingRepository.save(anotherBooking);

        ChatMessage msg1 = new ChatMessage();
        msg1.setBooking(testBooking);
        msg1.setSenderId(1);
        msg1.setContent("Booking 1 message");
        chatMessageService.save(msg1);

        ChatMessage msg2 = new ChatMessage();
        msg2.setBooking(anotherBooking);
        msg2.setSenderId(2);
        msg2.setContent("Booking 2 message");
        chatMessageService.save(msg2);

        List<ChatMessage> messages = chatMessageService.findByBookingId(testBooking.getId());

        assertThat(messages).hasSize(1);
        assertThat(messages.get(0).getContent()).isEqualTo("Booking 1 message");
    }

    @Test
    void testSaveMessage_bookingIdShouldNotBeNull() {
        ChatMessage msg = new ChatMessage();
        msg.setBooking(testBooking);
        msg.setSenderId(1);
        msg.setContent("Booking ID test");

        ChatMessage saved = chatMessageService.save(msg);

        // flush + clear 讓 JPA 一級快取失效，強制從 DB 重新載入
        entityManager.flush();
        entityManager.clear();
        ChatMessage reloaded = chatMessageRepository.findById(saved.getId()).orElseThrow();

        assertThat(reloaded.getBookingId()).isNotNull();
        assertThat(reloaded.getBookingId()).isEqualTo(testBooking.getId());
    }
}
