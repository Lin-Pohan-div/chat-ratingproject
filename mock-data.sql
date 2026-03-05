-- MySQL 假資料生成腳本
-- 為 bookings、chat_messages 和 reviews 表插入測試數據

USE learning;

-- 連續插入更多的預約記錄
INSERT INTO bookings (student_id, tutor_id, status, created_at, updated_at) VALUES
(1, 101, 'confirmed', DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 29 DAY)),
(2, 102, 'confirmed', DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 27 DAY)),
(3, 101, 'pending', DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY)),
(1, 103, 'confirmed', DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 19 DAY)),
(4, 102, 'cancelled', DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY)),
(5, 104, 'confirmed', DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY)),
(6, 105, 'confirmed', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(2, 103, 'pending', DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
(7, 101, 'completed', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY)),
(8, 106, 'confirmed', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY)),
(3, 104, 'confirmed', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(1, 102, 'pending', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
(9, 107, 'confirmed', DATE_SUB(NOW(), INTERVAL 1 HOUR), NOW()),
(4, 105, 'confirmed', NOW(), NOW()),
(10, 108, 'pending', NOW(), NOW())
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- 插入聊天消息
INSERT INTO chat_messages (booking_id, sender_id, content, created_at) VALUES
(1, 1, '嗨，我想開始準備數學考試', DATE_SUB(NOW(), INTERVAL 29 DAY)),
(1, 101, '好的，我建議我們從代數開始。', DATE_SUB(NOW(), INTERVAL 29 DAY)),
(1, 1, '好，什麼時候可以開始第一堂課？', DATE_SUB(NOW(), INTERVAL 28 DAY)),
(1, 101, '明天下午3點可以嗎？', DATE_SUB(NOW(), INTERVAL 28 DAY)),
(2, 2, '請問英文寫作有特殊的技巧嗎？', DATE_SUB(NOW(), INTERVAL 27 DAY)),
(2, 102, '當然有。我會教您如何組織段落和改進句子結構。', DATE_SUB(NOW(), INTERVAL 27 DAY)),
(3, 3, '我需要幫助複習物理', DATE_SUB(NOW(), INTERVAL 25 DAY)),
(3, 101, '沒問題，我們可以從電磁學開始', DATE_SUB(NOW(), INTERVAL 25 DAY)),
(4, 1, '化學的有機部分太難了', DATE_SUB(NOW(), INTERVAL 19 DAY)),
(4, 103, '別擔心，我會一步步教您', DATE_SUB(NOW(), INTERVAL 19 DAY)),
(5, 4, '我想提高我的數學成績', DATE_SUB(NOW(), INTERVAL 14 DAY)),
(5, 102, '這很可行。我們需要了解您的弱點。', DATE_SUB(NOW(), INTERVAL 14 DAY)),
(6, 5, '我想學習Java編程', DATE_SUB(NOW(), INTERVAL 11 DAY)),
(6, 104, '很好，Java是一個很好的開始語言', DATE_SUB(NOW(), INTERVAL 11 DAY)),
(7, 6, '請問什麼時候能開始上課？', DATE_SUB(NOW(), INTERVAL 9 DAY)),
(7, 105, '下週一可以開始', DATE_SUB(NOW(), INTERVAL 9 DAY)),
(8, 2, '我想加強英文聽力', DATE_SUB(NOW(), INTERVAL 8 DAY)),
(8, 103, '我建議每天聽英文播客', DATE_SUB(NOW(), INTERVAL 8 DAY)),
(9, 7, '謝謝您的幫助，我在考試中得了A', DATE_SUB(NOW(), INTERVAL 6 DAY)),
(9, 101, '太棒了！祝賀你！', DATE_SUB(NOW(), INTERVAL 6 DAY)),
(10, 8, '這堂課的內容是什麼？', DATE_SUB(NOW(), INTERVAL 4 DAY)),
(10, 106, '我們會學習基礎數據結構', DATE_SUB(NOW(), INTERVAL 4 DAY)),
(11, 3, '我可以預約下周三的課嗎？', DATE_SUB(NOW(), INTERVAL 2 DAY)),
(11, 104, '可以，下午2點或4點？', DATE_SUB(NOW(), INTERVAL 2 DAY)),
(12, 1, '你好，我想重新開始', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(12, 102, '當然，很高興幫您', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(13, 9, '嗨！期待與您合作', NOW()),
(13, 107, '我也是！我們開始吧', NOW()),
(14, 4, '您好，我想詢問課程費用', NOW()),
(14, 105, '我們可以根據課時討論', NOW()),
(15, 10, '請問初學者適合嗎？', NOW()),
(15, 108, '完全適合，我們從基礎開始', NOW());

-- 插入評論和評分
INSERT INTO reviews (student_id, tutor_course_id, rating, comment, created_at, updated_at) VALUES
(1, 101, 5, '非常好的教學方式，老師很耐心，解釋得很清楚。強烈推薦！', DATE_SUB(NOW(), INTERVAL 29 DAY), DATE_SUB(NOW(), INTERVAL 29 DAY)),
(2, 102, 4, '老師很專業，課程內容豐富，只是有時進度有點快。', DATE_SUB(NOW(), INTERVAL 27 DAY), DATE_SUB(NOW(), INTERVAL 27 DAY)),
(3, 101, 5, '傑出的教學品質！我在短時間內進步很多。', DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY)),
(4, 102, 3, '還不錯，但我希望有更多的練習題。', DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY)),
(5, 104, 4, '很好的課程安排，老師很友善，很容易溝通。', DATE_SUB(NOW(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY)),
(6, 105, 5, '最好的教育投資！我的成績有了明顯的改進。', DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(2, 103, 4, '不錯的課程，老師很有知識，互動很好。', DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
(7, 101, 5, '在這位老師的幫助下，我在考試中取得了優異成績！', DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY)),
(8, 106, 4, '很專業的教學，課程材料很有用。', DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY)),
(3, 104, 5, '非常滿意，老師的耐心和專業知識令人印象深刻。', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(1, 102, 4, '很好的複習課程，幫助我填補知識漏洞。', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
(9, 107, 5, '優秀！這正是我所需要的。我強烈推薦這位老師。', DATE_SUB(NOW(), INTERVAL 12 HOUR), DATE_SUB(NOW(), INTERVAL 12 HOUR));

-- 顯示插入結果
SELECT COUNT(*) as 總預約數 FROM bookings;
SELECT COUNT(*) as 總聊天消息數 FROM chat_messages;
SELECT COUNT(*) as 總評論數 FROM reviews;

-- 顯示預約情況
SELECT '=== 預約情況 ===' as '';
SELECT id, student_id, tutor_id, status, created_at FROM bookings ORDER BY created_at DESC LIMIT 5;

-- 顯示最近的聊天消息
SELECT '=== 最近的聊天消息 ===' as '';
SELECT id, booking_id, sender_id, content, created_at FROM chat_messages ORDER BY created_at DESC LIMIT 5;

-- 顯示評論統計
SELECT '=== 評分統計 ===' as '';
SELECT rating, COUNT(*) as 數量, AVG(rating) as 平均評分 FROM reviews GROUP BY rating ORDER BY rating DESC;
