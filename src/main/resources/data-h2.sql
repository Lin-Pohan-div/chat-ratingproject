-- H2 測試用種子資料
-- 目標:
-- 1) Tester 預設 bookingId=1 可直接 POST review，不會碰到「已有評論」
-- 2) Reviews 預設可支援 user/course 查詢與平均評分測試
-- 3) Chat messages 可支援 bookingId=1 的查詢測試

-- bookings columns: order_id, user_id, course_id, lesson_count, unit_price, discount_price, status, created_at
INSERT INTO learning.bookings (order_id, user_id, course_id, lesson_count, unit_price, discount_price, status, created_at) VALUES
(1, 1, 1, 3, 100, NULL, 1, CURRENT_TIMESTAMP),
(1, 1, 1, 2, 120, 10, 2, CURRENT_TIMESTAMP),
(2, 2, 1, 1, 150, NULL, 2, CURRENT_TIMESTAMP),
(3, 3, 2, 4, 90, 5, 1, CURRENT_TIMESTAMP),
(4, 1, 2, 2, 110, NULL, 3, CURRENT_TIMESTAMP),
(5, 4, 3, 2, 130, NULL, 2, CURRENT_TIMESTAMP);

-- reviews columns: booking_id, user_id, course_id, rating, comment, created_at
-- 注意: 不放 booking_id=1，保留給 POST /api/reviews 建立新評論
INSERT INTO learning.reviews (booking_id, user_id, course_id, rating, comment, created_at) VALUES
(2, 1, 1, 4, 'Updated comment', CURRENT_TIMESTAMP),
(3, 2, 1, 4, 'Very good', CURRENT_TIMESTAMP),
(4, 3, 2, 3, 'Average experience', CURRENT_TIMESTAMP),
(5, 1, 2, 2, 'Could improve', CURRENT_TIMESTAMP),
(6, 4, 3, 5, 'Outstanding', CURRENT_TIMESTAMP);

-- chat_messages columns: booking_id, sender_id, content, created_at
INSERT INTO learning.chat_messages (booking_id, sender_id, content, created_at) VALUES
(1, 1, 'Hello, this message is from seed data.', CURRENT_TIMESTAMP),
(1, 101, 'Hi! How can I help you today?', CURRENT_TIMESTAMP);
