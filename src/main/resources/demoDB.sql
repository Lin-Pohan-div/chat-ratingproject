-- ============================================================
-- Demo版 資料庫建立腳本
-- ============================================================

CREATE DATABASE IF NOT EXISTS demo_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE demo_db;

-- ============================================================
-- 1. users：系統所有使用者的帳號基礎資料
-- ============================================================
CREATE TABLE users (
  id          BIGINT        NOT NULL AUTO_INCREMENT,
  name        VARCHAR(100)  NOT NULL COMMENT '使用者真實姓名',
  email       VARCHAR(255)  NOT NULL COMMENT '登入信箱',
  password    VARCHAR(64)   NOT NULL COMMENT '密碼',
  birthday    DATE              NULL COMMENT '使用者生日',
  role        TINYINT       NOT NULL COMMENT '身分：1 學生 / 2 老師',
  is_admin    TINYINT       NOT NULL DEFAULT 0,
  created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '帳號建立時間',
  PRIMARY KEY (id),
  UNIQUE KEY uq_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='使用者帳號基礎資料';

-- ============================================================
-- 2. tutors：老師的擴充展示資訊與匯款帳戶
-- ============================================================
CREATE TABLE tutors (
  id           BIGINT        NOT NULL COMMENT '關聯 users.id',
  intro        VARCHAR(1000)     NULL COMMENT '老師個人簡介',
  certificate  VARCHAR(500)      NULL COMMENT '專業證照說明',
  video_url_1  VARCHAR(500)      NULL COMMENT '介紹影片連結 1',
  video_url_2  VARCHAR(500)      NULL COMMENT '介紹影片連結 2',
  bank_code    VARCHAR(10)       NULL COMMENT '收款銀行代碼（如 822）',
  bank_account VARCHAR(20)       NULL COMMENT '收款銀行帳號',
  PRIMARY KEY (id),
  CONSTRAINT fk_tutors_users FOREIGN KEY (id) REFERENCES users (id)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老師擴充資訊與匯款帳戶';

-- ============================================================
-- 3. courses：課程的呈現內容與價格
-- ============================================================
CREATE TABLE courses (
  id          BIGINT        NOT NULL AUTO_INCREMENT,
  tutor_id    BIGINT        NOT NULL COMMENT '所屬老師 (users.id)',
  name        VARCHAR(200)  NOT NULL COMMENT '課程標題',
  subject     TINYINT       NOT NULL COMMENT '科目：1 英文 / 2 程式',
  level       TINYINT       NOT NULL COMMENT '等級：1 初級 / 2 中級 / 3 高級',
  description VARCHAR(1000)     NULL COMMENT '課程詳細介紹內容',
  price       INT           NOT NULL COMMENT '單堂課程定價',
  is_active   TINYINT       NOT NULL DEFAULT 1 COMMENT '1 上架 / 0 下架',
  PRIMARY KEY (id),
  CONSTRAINT fk_courses_tutor FOREIGN KEY (tutor_id) REFERENCES users (id)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='課程定義';

-- ============================================================
-- 4. tutor_schedules：老師每週可供預約的時段
-- ============================================================
CREATE TABLE tutor_schedules (
  id        BIGINT  NOT NULL AUTO_INCREMENT,
  tutor_id  BIGINT  NOT NULL COMMENT '關聯老師 (users.id)',
  weekday   TINYINT NOT NULL COMMENT '1-7（星期一至日）',
  hour      TINYINT NOT NULL COMMENT '9-21（開放時段）',
  PRIMARY KEY (id),
  CONSTRAINT fk_schedules_tutor FOREIGN KEY (tutor_id) REFERENCES users (id)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老師每週可預約時段';

-- ============================================================
-- 5. orders：綠界支付成功後的總收據
-- ============================================================
CREATE TABLE orders (
  id                 BIGINT        NOT NULL AUTO_INCREMENT,
  merchant_trade_no  VARCHAR(100)  NOT NULL COMMENT '綠界單號',
  user_id            BIGINT        NOT NULL COMMENT '購買的學生 (users.id)',
  total_amount       INT           NOT NULL COMMENT '支付總金額',
  created_at         TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '付款成功時間',
  PRIMARY KEY (id),
  UNIQUE KEY uq_orders_trade_no (merchant_trade_no),
  CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users (id)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='綠界支付收據';

-- ============================================================
-- 6. bookings：訂單中的課程項目及成交單價
-- ============================================================
CREATE TABLE bookings (
  id              BIGINT  NOT NULL AUTO_INCREMENT,
  order_id        BIGINT  NOT NULL COMMENT '關聯訂單 (orders.id)',
  course_id       BIGINT  NOT NULL COMMENT '關聯課程 (courses.id)',
  unit_price      INT     NOT NULL COMMENT '成交單價快照（原價，用於分潤計算）',
  discount_price  INT         NULL COMMENT '折扣單價',
  lesson_count    INT     NOT NULL COMMENT '購買總堂數',
  PRIMARY KEY (id),
  CONSTRAINT fk_bookings_order  FOREIGN KEY (order_id)  REFERENCES orders  (id) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT fk_bookings_course FOREIGN KEY (course_id) REFERENCES courses (id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='訂單課程項目';

-- ============================================================
-- 7. lessons：實際排課時段（status=2 為撥款證明）
-- ============================================================
CREATE TABLE lessons (
  id          BIGINT  NOT NULL AUTO_INCREMENT,
  booking_id  BIGINT  NOT NULL COMMENT '關聯預約 (bookings.id)',
  tutor_id    BIGINT  NOT NULL COMMENT '老師 ID',
  student_id  BIGINT  NOT NULL COMMENT '學生 ID',
  date        DATE    NOT NULL COMMENT '上課日期',
  hour        TINYINT NOT NULL COMMENT '上課時段',
  status      TINYINT NOT NULL DEFAULT 1 COMMENT '1 排程中 / 2 已完成（老師領錢）/ 3 已取消',
  PRIMARY KEY (id),
  CONSTRAINT fk_lessons_booking FOREIGN KEY (booking_id)  REFERENCES bookings (id) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT fk_lessons_tutor   FOREIGN KEY (tutor_id)   REFERENCES users    (id) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT fk_lessons_student FOREIGN KEY (student_id) REFERENCES users    (id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='實際排課時段';

-- ============================================================
-- 8. reviews：學生對課程整體的評價
-- ============================================================
CREATE TABLE reviews (
  id         BIGINT        NOT NULL AUTO_INCREMENT,
  user_id    BIGINT        NOT NULL COMMENT '學生 ID',
  course_id  BIGINT        NOT NULL COMMENT '課程 ID',
  rating     TINYINT       NOT NULL COMMENT '1-5 分',
  comment    VARCHAR(1000)     NULL COMMENT '評價內容',
  PRIMARY KEY (id),
  CONSTRAINT fk_reviews_user   FOREIGN KEY (user_id)   REFERENCES users   (id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_reviews_course FOREIGN KEY (course_id) REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='學生課程評價';

-- ============================================================
-- 9. lesson_feedback：老師對單堂課學生的評語
-- ============================================================
CREATE TABLE lesson_feedback (
  id         BIGINT        NOT NULL AUTO_INCREMENT,
  lesson_id  BIGINT        NOT NULL COMMENT '關聯 lessons.id',
  rating     TINYINT       NOT NULL COMMENT '1-5 分',
  comment    VARCHAR(1000)     NULL COMMENT '回饋內容',
  PRIMARY KEY (id),
  CONSTRAINT fk_feedback_lesson FOREIGN KEY (lesson_id) REFERENCES lessons (id)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老師單堂課回饋';

-- ============================================================
-- 10. chat_messages：師生通訊紀錄
-- ============================================================
CREATE TABLE chat_messages (
  id          BIGINT        NOT NULL AUTO_INCREMENT,
  booking_id  BIGINT        NOT NULL COMMENT '關聯購買項目 (bookings.id)',
  role        TINYINT       NOT NULL COMMENT '1 學生 / 2 老師',
  message     VARCHAR(1000) NOT NULL COMMENT '訊息內容',
  created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '傳送時間',
  PRIMARY KEY (id),
  CONSTRAINT fk_chat_booking FOREIGN KEY (booking_id) REFERENCES bookings (id)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='師生通訊紀錄';