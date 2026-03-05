-- MySQL 完整假資料生成腳本
-- 按照 ERD 結構為所有表插入測試數據

USE learning;

-- ============================================
-- 1. 插入用戶數據 (users)
-- ============================================
INSERT INTO users (name, email, password, birthday, role, balance, created_at, updated_at) VALUES
('Alice Chen', 'alice@example.com', 'pass123', '2000-05-15', 0, 5000, DATE_SUB(NOW(), INTERVAL 60 DAY), DATE_SUB(NOW(), INTERVAL 55 DAY)),
('Bob Wang', 'bob@example.com', 'pass123', '1998-08-20', 0, 3000, DATE_SUB(NOW(), INTERVAL 50 DAY), DATE_SUB(NOW(), INTERVAL 45 DAY)),
('Carol Liu', 'carol@example.com', 'pass123', '2002-03-10', 0, 8000, DATE_SUB(NOW(), INTERVAL 40 DAY), DATE_SUB(NOW(), INTERVAL 35 DAY)),
('David Lin', 'david@example.com', 'pass123', '1999-11-25', 0, 2000, DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY)),
('Emma Yang', 'emma@example.com', 'pass123', '2001-07-08', 0, 6000, DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY)),
('Frank Chen', 'frank@example.com', 'pass123', '1997-12-03', 1, 50000, DATE_SUB(NOW(), INTERVAL 90 DAY), DATE_SUB(NOW(), INTERVAL 85 DAY)),
('Grace Wu', 'grace@example.com', 'pass123', '2000-02-14', 1, 45000, DATE_SUB(NOW(), INTERVAL 80 DAY), DATE_SUB(NOW(), INTERVAL 75 DAY)),
('Henry Lee', 'henry@example.com', 'pass123', '1996-09-30', 1, 55000, DATE_SUB(NOW(), INTERVAL 70 DAY), DATE_SUB(NOW(), INTERVAL 65 DAY)),
('Iris Tang', 'iris@example.com', 'pass123', '2001-04-22', 0, 4500, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY)),
('Jack Zhang', 'jack@example.com', 'pass123', '1999-06-18', 1, 48000, DATE_SUB(NOW(), INTERVAL 75 DAY), DATE_SUB(NOW(), INTERVAL 70 DAY))
ON DUPLICATE KEY UPDATE name=VALUES(name), password=VALUES(password), birthday=VALUES(birthday), role=VALUES(role), balance=VALUES(balance), updated_at=NOW();

-- ============================================
-- 2. 插入導師數據 (tutor)
-- ============================================
INSERT INTO tutor (user_id, intro, avatar_url, created_at, updated_at) VALUES
(6, '英文教學專家，10年教學經驗', 'https://example.com/tutor1.jpg', DATE_SUB(NOW(), INTERVAL 100 DAY), DATE_SUB(NOW(), INTERVAL 95 DAY)),
(7, '數學教育工作者，擅長初中數學', 'https://example.com/tutor2.jpg', DATE_SUB(NOW(), INTERVAL 95 DAY), DATE_SUB(NOW(), INTERVAL 90 DAY)),
(8, '物理教學專家，提供互動式學習', 'https://example.com/tutor3.jpg', DATE_SUB(NOW(), INTERVAL 90 DAY), DATE_SUB(NOW(), INTERVAL 85 DAY)),
(10, '化學科授課，具豐富實驗經驗', 'https://example.com/tutor4.jpg', DATE_SUB(NOW(), INTERVAL 85 DAY), DATE_SUB(NOW(), INTERVAL 80 DAY)),
(6, 'Java 程式設計講師，業界經驗豐富', 'https://example.com/tutor5.jpg', DATE_SUB(NOW(), INTERVAL 80 DAY), DATE_SUB(NOW(), INTERVAL 75 DAY));

-- ============================================
-- 3. 插入訂單數據 (orders)
-- ============================================
INSERT INTO orders (student_id, created_at, updated_at) VALUES
(1, DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 29 DAY)),
(2, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 27 DAY)),
(3, DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 24 DAY)),
(1, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 19 DAY)),
(4, DATE_SUB(NOW(), INTERVAL 18 DAY), DATE_SUB(NOW(), INTERVAL 17 DAY)),
(5, DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY)),
(2, DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY)),
(9, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(3, DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY)),
(1, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY));

-- ============================================
-- 4. 插入預約數據 (bookings)
-- ============================================
INSERT INTO bookings (order_id, tutor_id, level, snapshot_price, total_courses, status, created_at, updated_at) VALUES
(1, 1, 2, 500, 10, 1, DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY)),
(2, 2, 1, 400, 5, 1, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 23 DAY)),
(3, 3, 3, 600, 8, 0, DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY)),
(4, 1, 2, 500, 10, 1, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY)),
(5, 4, 1, 400, 6, 1, DATE_SUB(NOW(), INTERVAL 18 DAY), DATE_SUB(NOW(), INTERVAL 13 DAY)),
(6, 5, 3, 700, 12, 0, DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(7, 2, 2, 500, 8, 1, DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY)),
(8, 1, 1, 450, 5, 1, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),
(9, 3, 2, 550, 10, 0, DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),
(10, 4, 3, 650, 12, 1, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY));

-- ============================================
-- 5. 插入導師課程數據 (tutor_courses)
-- ============================================
INSERT INTO tutor_courses (tutor_id, level, course_name, price, is_active, created_at, updated_at) VALUES
(1, 1, '英文初級班', 400, 1, DATE_SUB(NOW(), INTERVAL 100 DAY), DATE_SUB(NOW(), INTERVAL 95 DAY)),
(1, 2, '英文中級班', 500, 1, DATE_SUB(NOW(), INTERVAL 100 DAY), DATE_SUB(NOW(), INTERVAL 95 DAY)),
(1, 3, '英文高級班', 600, 1, DATE_SUB(NOW(), INTERVAL 100 DAY), DATE_SUB(NOW(), INTERVAL 95 DAY)),
(2, 1, '數學初級班', 350, 1, DATE_SUB(NOW(), INTERVAL 95 DAY), DATE_SUB(NOW(), INTERVAL 90 DAY)),
(2, 2, '數學中級班', 450, 1, DATE_SUB(NOW(), INTERVAL 95 DAY), DATE_SUB(NOW(), INTERVAL 90 DAY)),
(3, 1, '物理初級班', 400, 1, DATE_SUB(NOW(), INTERVAL 90 DAY), DATE_SUB(NOW(), INTERVAL 85 DAY)),
(3, 2, '物理中級班', 500, 1, DATE_SUB(NOW(), INTERVAL 90 DAY), DATE_SUB(NOW(), INTERVAL 85 DAY)),
(3, 3, '物理高級班', 650, 1, DATE_SUB(NOW(), INTERVAL 90 DAY), DATE_SUB(NOW(), INTERVAL 85 DAY)),
(4, 1, '化學初級班', 400, 1, DATE_SUB(NOW(), INTERVAL 85 DAY), DATE_SUB(NOW(), INTERVAL 80 DAY)),
(5, 3, 'Java 專業班', 700, 1, DATE_SUB(NOW(), INTERVAL 80 DAY), DATE_SUB(NOW(), INTERVAL 75 DAY));

-- ============================================
-- 6. 插入聊天消息數據 (chat_messages)
-- ============================================
INSERT INTO chat_messages (booking_id, sender_id, content, created_at) VALUES
(1, 1, '你好！我想開始英文課程。', DATE_SUB(NOW(), INTERVAL 29 DAY)),
(1, 6, '太好了！我們可以從基礎開始。', DATE_SUB(NOW(), INTERVAL 29 DAY)),
(1, 1, '第一堂課什麼時候開始？', DATE_SUB(NOW(), INTERVAL 28 DAY)),
(1, 6, '下週一下午3點可以嗎？', DATE_SUB(NOW(), INTERVAL 28 DAY)),
(2, 2, '我想加強數學基礎', DATE_SUB(NOW(), INTERVAL 27 DAY)),
(2, 7, '沒問題，我會為你設計課程計劃', DATE_SUB(NOW(), INTERVAL 27 DAY)),
(3, 3, '物理很難，希望能得到幫助', DATE_SUB(NOW(), INTERVAL 24 DAY)),
(3, 8, '別擔心，物理就是要理解概念', DATE_SUB(NOW(), INTERVAL 24 DAY)),
(4, 1, '上一次的課程很有幫助', DATE_SUB(NOW(), INTERVAL 19 DAY)),
(4, 6, '很開心聽到你的進展！', DATE_SUB(NOW(), INTERVAL 19 DAY)),
(5, 4, '我想學習化學應試技巧', DATE_SUB(NOW(), INTERVAL 17 DAY)),
(5, 9, '我會教你解題的方法', DATE_SUB(NOW(), INTERVAL 17 DAY)),
(6, 5, 'Java 對初學者難嗎？', DATE_SUB(NOW(), INTERVAL 14 DAY)),
(6, 10, '不會的，我會從零開始教你', DATE_SUB(NOW(), INTERVAL 14 DAY)),
(7, 2, '測試考得不錯，謝謝你的幫助', DATE_SUB(NOW(), INTERVAL 11 DAY)),
(7, 7, '太好了！繼續保持努力', DATE_SUB(NOW(), INTERVAL 11 DAY)),
(8, 9, '能否提供練習題？', DATE_SUB(NOW(), INTERVAL 9 DAY)),
(8, 6, '當然可以，我準備了很多練習題', DATE_SUB(NOW(), INTERVAL 9 DAY)),
(9, 3, '還有時間調整課程嗎？', DATE_SUB(NOW(), INTERVAL 7 DAY)),
(9, 8, '可以的，我們一起安排', DATE_SUB(NOW(), INTERVAL 7 DAY));

-- ============================================
-- 7. 插入導師日程數據 (tutor_schedules)
-- ============================================
INSERT INTO tutor_schedules (tutor_id, weekday, hour, created_at) VALUES
-- 導師1 (Frank Chen) 的日程
(1, 1, 10, DATE_SUB(NOW(), INTERVAL 100 DAY)),
(1, 1, 14, DATE_SUB(NOW(), INTERVAL 100 DAY)),
(1, 2, 15, DATE_SUB(NOW(), INTERVAL 100 DAY)),
(1, 3, 10, DATE_SUB(NOW(), INTERVAL 100 DAY)),
(1, 4, 14, DATE_SUB(NOW(), INTERVAL 100 DAY)),
(1, 5, 18, DATE_SUB(NOW(), INTERVAL 100 DAY)),
-- 導師2 (Grace Wu) 的日程
(2, 1, 11, DATE_SUB(NOW(), INTERVAL 95 DAY)),
(2, 2, 14, DATE_SUB(NOW(), INTERVAL 95 DAY)),
(2, 3, 16, DATE_SUB(NOW(), INTERVAL 95 DAY)),
(2, 4, 10, DATE_SUB(NOW(), INTERVAL 95 DAY)),
(2, 5, 15, DATE_SUB(NOW(), INTERVAL 95 DAY)),
-- 導師3 (Henry Lee) 的日程
(3, 1, 9, DATE_SUB(NOW(), INTERVAL 90 DAY)),
(3, 2, 13, DATE_SUB(NOW(), INTERVAL 90 DAY)),
(3, 3, 15, DATE_SUB(NOW(), INTERVAL 90 DAY)),
(3, 4, 18, DATE_SUB(NOW(), INTERVAL 90 DAY)),
-- 導師4 的日程
(4, 1, 14, DATE_SUB(NOW(), INTERVAL 85 DAY)),
(4, 2, 16, DATE_SUB(NOW(), INTERVAL 85 DAY)),
(4, 3, 10, DATE_SUB(NOW(), INTERVAL 85 DAY)),
(4, 5, 19, DATE_SUB(NOW(), INTERVAL 85 DAY)),
-- 導師5 的日程
(5, 2, 18, DATE_SUB(NOW(), INTERVAL 80 DAY)),
(5, 3, 19, DATE_SUB(NOW(), INTERVAL 80 DAY)),
(5, 4, 17, DATE_SUB(NOW(), INTERVAL 80 DAY)),
(5, 5, 20, DATE_SUB(NOW(), INTERVAL 80 DAY));

-- ============================================
-- 8. 插入預約時程數據 (booking_schedules)
-- ============================================
INSERT INTO booking_schedules (booking_id, tutor_schedule_id, created_at) VALUES
(1, 1, DATE_SUB(NOW(), INTERVAL 30 DAY)),
(1, 2, DATE_SUB(NOW(), INTERVAL 30 DAY)),
(2, 7, DATE_SUB(NOW(), INTERVAL 28 DAY)),
(2, 8, DATE_SUB(NOW(), INTERVAL 28 DAY)),
(3, 13, DATE_SUB(NOW(), INTERVAL 25 DAY)),
(4, 3, DATE_SUB(NOW(), INTERVAL 20 DAY)),
(5, 19, DATE_SUB(NOW(), INTERVAL 18 DAY)),
(6, 21, DATE_SUB(NOW(), INTERVAL 15 DAY)),
(7, 9, DATE_SUB(NOW(), INTERVAL 12 DAY)),
(8, 2, DATE_SUB(NOW(), INTERVAL 10 DAY));

-- ============================================
-- 9. 插入課程數據 (courses)
-- ============================================
INSERT INTO courses (booking_id, course_date, hour, status, created_at, updated_at) VALUES
(1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 10, 0, DATE_SUB(NOW(), INTERVAL 29 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY)),
(1, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 14, 0, DATE_SUB(NOW(), INTERVAL 29 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY)),
(1, DATE_ADD(CURDATE(), INTERVAL 5 DAY), 10, 0, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 27 DAY)),
(2, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 11, 1, DATE_SUB(NOW(), INTERVAL 27 DAY), DATE_SUB(NOW(), INTERVAL 26 DAY)),
(2, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 14, 1, DATE_SUB(NOW(), INTERVAL 27 DAY), DATE_SUB(NOW(), INTERVAL 26 DAY)),
(3, DATE_SUB(CURDATE(), INTERVAL 3 DAY), 9, 1, DATE_SUB(NOW(), INTERVAL 22 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY)),
(3, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 13, 1, DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY)),
(4, DATE_ADD(CURDATE(), INTERVAL 6 DAY), 14, 0, DATE_SUB(NOW(), INTERVAL 19 DAY), DATE_SUB(NOW(), INTERVAL 18 DAY)),
(5, DATE_SUB(CURDATE(), INTERVAL 2 DAY), 16, 1, DATE_SUB(NOW(), INTERVAL 16 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY)),
(6, DATE_ADD(CURDATE(), INTERVAL 7 DAY), 18, 0, DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 13 DAY));

-- ============================================
-- 10. 插入評論數據 (reviews)
-- ============================================
INSERT INTO reviews (tutor_course_id, rating, comment, created_at, updated_at) VALUES
(1, 5, '老師教學認真，解釋清楚，課程進度適當', DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY)),
(2, 4, '課程內容豐富，有時進度稍快', DATE_SUB(NOW(), INTERVAL 22 DAY), DATE_SUB(NOW(), INTERVAL 22 DAY)),
(4, 5, '數字教學方式很有趣，易於理解', DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY)),
(5, 4, '詳細講解，但希望有更多練習題', DATE_SUB(NOW(), INTERVAL 18 DAY), DATE_SUB(NOW(), INTERVAL 18 DAY)),
(6, 5, '非常棒的物理課程，老師很耐心', DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY)),
(1, 4, '很專業的英文教學，推薦給朋友', DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY)),
(9, 5, '化學課程生動有趣，收穫很大', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(3, 4, '英文口語進步明顯', DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
(7, 5, '物理老師知識淵博，很有幫助', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),
(10, 4, 'Java課程由淺入深，很適合初學者', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY));

-- ============================================
-- 11. 插入交易數據 (transactions)
-- ============================================
INSERT INTO transactions (user_id, amount, type, order_id, created_at) VALUES
(1, 5000, 1, 1, DATE_SUB(NOW(), INTERVAL 30 DAY)),
(2, 2000, 1, 2, DATE_SUB(NOW(), INTERVAL 28 DAY)),
(3, 4800, 1, 3, DATE_SUB(NOW(), INTERVAL 25 DAY)),
(1, 5000, 1, 4, DATE_SUB(NOW(), INTERVAL 20 DAY)),
(4, 2400, 1, 5, DATE_SUB(NOW(), INTERVAL 18 DAY)),
(5, 8400, 1, 6, DATE_SUB(NOW(), INTERVAL 15 DAY)),
(2, 4000, 1, 7, DATE_SUB(NOW(), INTERVAL 12 DAY)),
(9, 2250, 1, 8, DATE_SUB(NOW(), INTERVAL 10 DAY)),
(3, 5500, 1, 9, DATE_SUB(NOW(), INTERVAL 8 DAY)),
(1, 6500, 1, 10, DATE_SUB(NOW(), INTERVAL 5 DAY));

-- ============================================
-- 12. 插入導師反饋數據 (tutor_feedbacks)
-- ============================================
INSERT INTO tutor_feedbacks (course_id, rating, comment, created_at, updated_at) VALUES
(4, 5, '學生表現很好，進度快', DATE_SUB(NOW(), INTERVAL 26 DAY), DATE_SUB(NOW(), INTERVAL 26 DAY)),
(5, 4, '學生參與度高，需加強寫作', DATE_SUB(NOW(), INTERVAL 26 DAY), DATE_SUB(NOW(), INTERVAL 26 DAY)),
(6, 5, '非常積極的學生，願意主動提問', DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY)),
(7, 5, '學生進步明顯，繼續加油', DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY)),
(8, 4, '課堂表現不錯，課後應多複習', DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY)),
(9, 5, '非常專心的學生', DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY)),
(10, 4, '基礎紮實，學習效率高', DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY));

-- ============================================
-- 查詢統計
-- ============================================
SELECT '========== 數據庫統計 ==========' as '';
SELECT COUNT(*) as 用戶總數 FROM users;
SELECT COUNT(*) as 導師總數 FROM tutor;
SELECT COUNT(*) as 訂單總數 FROM orders;
SELECT COUNT(*) as 預約總數 FROM bookings;
SELECT COUNT(*) as 聊天消息總數 FROM chat_messages;
SELECT COUNT(*) as 課程總數 FROM courses;
SELECT COUNT(*) as 評論總數 FROM reviews;
SELECT COUNT(*) as 交易總數 FROM transactions;

SELECT '========== 用戶信息 ==========' as '';
SELECT id, name, email, role, balance FROM users LIMIT 5;

SELECT '========== 導師課程 ==========' as '';
SELECT tc.id, t.id as tutor_id, tc.course_name, tc.level, tc.price FROM tutor_courses tc JOIN tutor t ON tc.tutor_id = t.id LIMIT 5;

SELECT '========== 最近的預約 ==========' as '';
SELECT b.id, b.order_id, b.tutor_id, b.level, b.total_courses, b.status FROM bookings b ORDER BY b.created_at DESC LIMIT 5;

SELECT '========== 評論統計 ==========' as '';
SELECT rating, COUNT(*) as 數量, ROUND(AVG(rating), 2) as 平均評分 FROM reviews GROUP BY rating ORDER BY rating DESC;
