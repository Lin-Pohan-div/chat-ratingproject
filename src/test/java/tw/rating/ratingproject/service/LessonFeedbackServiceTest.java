package tw.rating.ratingproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tw.rating.ratingproject.entity.LessonFeedback;
import tw.rating.ratingproject.repository.LessonFeedbackRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
class LessonFeedbackServiceTest {

    @Autowired
    private LessonFeedbackService lessonFeedbackService;

    @Autowired
    private LessonFeedbackRepository lessonFeedbackRepository;

    private LessonFeedback savedFeedback;

    @BeforeEach
    void setUp() {
        lessonFeedbackRepository.deleteAll();

        LessonFeedback fb = new LessonFeedback();
        fb.setLessonId(1L);
        fb.setRating((byte) 4);
        fb.setComment("Good lesson");
        savedFeedback = lessonFeedbackRepository.save(fb);
    }

    // ===================== findAll =====================

    @Test
    void findAll_shouldReturnSavedFeedbacks() {
        List<LessonFeedback> result = lessonFeedbackService.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(f -> f.getId().equals(savedFeedback.getId()));
    }

    // ===================== findById =====================

    @Test
    void findById_existingId_shouldReturnFeedback() {
        Optional<LessonFeedback> result = lessonFeedbackService.findById(savedFeedback.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getLessonId()).isEqualTo(1L);
        assertThat(result.get().getRating()).isEqualTo((byte) 4);
        assertThat(result.get().getComment()).isEqualTo("Good lesson");
    }

    @Test
    void findById_nonExistingId_shouldReturnEmpty() {
        Optional<LessonFeedback> result = lessonFeedbackService.findById(999999L);

        assertThat(result).isEmpty();
    }

    // ===================== findByLessonId =====================

    @Test
    void findByLessonId_withMatchingFeedback_shouldReturnList() {
        List<LessonFeedback> result = lessonFeedbackService.findByLessonId(1L);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getLessonId()).isEqualTo(1L);
    }

    @Test
    void findByLessonId_noMatchingFeedback_shouldReturnEmptyList() {
        List<LessonFeedback> result = lessonFeedbackService.findByLessonId(999L);

        assertThat(result).isEmpty();
    }

    @Test
    void findByLessonId_multipleFeedbacks_shouldReturnAll() {
        LessonFeedback fb2 = new LessonFeedback();
        fb2.setLessonId(1L);
        fb2.setRating((byte) 5);
        fb2.setComment("Excellent");
        lessonFeedbackRepository.save(fb2);

        List<LessonFeedback> result = lessonFeedbackService.findByLessonId(1L);

        assertThat(result).hasSize(2);
    }

    // ===================== getAverageRating =====================

    @Test
    void getAverageRating_withFeedback_shouldReturnCorrectAverage() {
        LessonFeedback fb2 = new LessonFeedback();
        fb2.setLessonId(1L);
        fb2.setRating((byte) 2);
        lessonFeedbackRepository.save(fb2);

        // lessonId=1 has rating 4 and 2 → average = 3.0
        Double avg = lessonFeedbackService.getAverageRating(1L);

        assertThat(avg).isEqualTo(3.0);
    }

    @Test
    void getAverageRating_noFeedback_shouldReturnZero() {
        Double avg = lessonFeedbackService.getAverageRating(999L);

        assertThat(avg).isEqualTo(0.0);
    }

    // ===================== save =====================

    @Test
    void save_validFeedback_shouldPersistAndReturnWithId() {
        LessonFeedback fb = new LessonFeedback();
        fb.setLessonId(2L);
        fb.setRating((byte) 5);
        fb.setComment("Outstanding");

        LessonFeedback saved = lessonFeedbackService.save(fb);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getRating()).isEqualTo((byte) 5);
        assertThat(saved.getComment()).isEqualTo("Outstanding");
    }

    @Test
    void save_withNullComment_shouldSucceed() {
        LessonFeedback fb = new LessonFeedback();
        fb.setLessonId(2L);
        fb.setRating((byte) 3);
        fb.setComment(null);

        LessonFeedback saved = lessonFeedbackService.save(fb);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getComment()).isNull();
    }

    @Test
    void save_withNullRating_shouldThrowIllegalArgumentException() {
        LessonFeedback fb = new LessonFeedback();
        fb.setLessonId(1L);
        fb.setRating(null);

        assertThatThrownBy(() -> lessonFeedbackService.save(fb))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("評分不能為空");
    }

    @Test
    void save_withRatingBelowMin_shouldThrowIllegalArgumentException() {
        LessonFeedback fb = new LessonFeedback();
        fb.setLessonId(1L);
        fb.setRating((byte) 0);

        assertThatThrownBy(() -> lessonFeedbackService.save(fb))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("評分必須在");
    }

    @Test
    void save_withRatingAboveMax_shouldThrowIllegalArgumentException() {
        LessonFeedback fb = new LessonFeedback();
        fb.setLessonId(1L);
        fb.setRating((byte) 6);

        assertThatThrownBy(() -> lessonFeedbackService.save(fb))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("評分必須在");
    }

    @Test
    void save_withCommentExceedingMaxLength_shouldThrowIllegalArgumentException() {
        LessonFeedback fb = new LessonFeedback();
        fb.setLessonId(1L);
        fb.setRating((byte) 3);
        fb.setComment("A".repeat(1001));

        assertThatThrownBy(() -> lessonFeedbackService.save(fb))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("評論不能超過");
    }

    // ===================== update =====================

    @Test
    void update_existingId_shouldUpdateRatingAndComment() {
        LessonFeedback updated = new LessonFeedback();
        updated.setRating((byte) 2);
        updated.setComment("Needs improvement");

        Optional<LessonFeedback> result = lessonFeedbackService.update(savedFeedback.getId(), updated);

        assertThat(result).isPresent();
        assertThat(result.get().getRating()).isEqualTo((byte) 2);
        assertThat(result.get().getComment()).isEqualTo("Needs improvement");
    }

    @Test
    void update_existingId_shouldNotChangeLessonId() {
        LessonFeedback updated = new LessonFeedback();
        updated.setRating((byte) 5);
        updated.setComment("Updated");

        Optional<LessonFeedback> result = lessonFeedbackService.update(savedFeedback.getId(), updated);

        assertThat(result).isPresent();
        assertThat(result.get().getLessonId()).isEqualTo(savedFeedback.getLessonId());
    }

    @Test
    void update_nonExistingId_shouldReturnEmpty() {
        LessonFeedback updated = new LessonFeedback();
        updated.setRating((byte) 3);

        Optional<LessonFeedback> result = lessonFeedbackService.update(999999L, updated);

        assertThat(result).isEmpty();
    }

    @Test
    void update_withInvalidRating_shouldThrowIllegalArgumentException() {
        LessonFeedback updated = new LessonFeedback();
        updated.setRating((byte) 0);

        assertThatThrownBy(() -> lessonFeedbackService.update(savedFeedback.getId(), updated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("評分必須在");
    }

    // ===================== deleteById =====================

    @Test
    void deleteById_existingId_shouldReturnTrueAndRemove() {
        boolean result = lessonFeedbackService.deleteById(savedFeedback.getId());

        assertThat(result).isTrue();
        assertThat(lessonFeedbackRepository.findById(savedFeedback.getId())).isEmpty();
    }

    @Test
    void deleteById_nonExistingId_shouldReturnFalse() {
        boolean result = lessonFeedbackService.deleteById(999999L);

        assertThat(result).isFalse();
    }

    @Test
    void deleteById_afterDelete_findByLessonIdShouldReturnEmpty() {
        lessonFeedbackService.deleteById(savedFeedback.getId());

        List<LessonFeedback> remaining = lessonFeedbackService.findByLessonId(1L);

        assertThat(remaining).isEmpty();
    }
}
