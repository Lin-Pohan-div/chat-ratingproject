-- 初始化 bookings 表的測試數據
INSERT INTO learning.bookings (student_id, tutor_id, status, created_at, updated_at) VALUES
(1, 101, 'confirmed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 102, 'confirmed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 101, 'pending', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 103, 'confirmed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 102, 'cancelled', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO learning.reviews (student_id, tutor_course_id, rating, comment, created_at, updated_at) VALUES
(1, 1, 5, 'Excellent tutor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 4, 'Very good', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 3, 'Average experience', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 2, 'Could improve', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 5, 'Outstanding', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
