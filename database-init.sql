-- MySQL 數據庫初始化腳本
-- 創建 learning 架構
CREATE SCHEMA IF NOT EXISTS learning;
USE learning;

-- 創建 users 表
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    birthday DATE,
    role INT NOT NULL DEFAULT 0,
    balance INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 創建 tutor 表
CREATE TABLE IF NOT EXISTS tutor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    intro TEXT,
    avatar_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_tutor_user 
        FOREIGN KEY (user_id) REFERENCES users (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 創建 orders 表
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_student 
        FOREIGN KEY (student_id) REFERENCES users (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 創建 bookings 表
CREATE TABLE IF NOT EXISTS bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    tutor_id INT NOT NULL,
    level INT NOT NULL,
    snapshot_price INT NOT NULL,
    total_courses INT NOT NULL,
    status INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_bookings_order 
        FOREIGN KEY (order_id) REFERENCES orders (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_bookings_tutor 
        FOREIGN KEY (tutor_id) REFERENCES tutor (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_order_id (order_id),
    INDEX idx_tutor_id (tutor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 創建 chat_messages 表
CREATE TABLE IF NOT EXISTS chat_messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    sender_id INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_chat_messages_booking 
        FOREIGN KEY (booking_id) REFERENCES bookings (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_chat_messages_sender 
        FOREIGN KEY (sender_id) REFERENCES users (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_booking_id (booking_id),
    INDEX idx_sender_id (sender_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 創建 tutor_schedules 表
CREATE TABLE IF NOT EXISTS tutor_schedules (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tutor_id INT NOT NULL,
    weekday INT NOT NULL,
    hour INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tutor_schedules_tutor 
        FOREIGN KEY (tutor_id) REFERENCES tutor (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_tutor_id (tutor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 創建 booking_schedules 表
CREATE TABLE IF NOT EXISTS booking_schedules (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    tutor_schedule_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_booking_schedules_booking 
        FOREIGN KEY (booking_id) REFERENCES bookings (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_booking_schedules_schedule 
        FOREIGN KEY (tutor_schedule_id) REFERENCES tutor_schedules (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_booking_id (booking_id),
    INDEX idx_tutor_schedule_id (tutor_schedule_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 創建 courses 表
CREATE TABLE IF NOT EXISTS courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    course_date DATE NOT NULL,
    hour INT NOT NULL,
    status INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_courses_booking 
        FOREIGN KEY (booking_id) REFERENCES bookings (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_booking_id (booking_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 創建 tutor_courses 表
CREATE TABLE IF NOT EXISTS tutor_courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tutor_id INT NOT NULL,
    level INT NOT NULL,
    course_name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    is_active TINYINT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_tutor_courses_tutor 
        FOREIGN KEY (tutor_id) REFERENCES tutor (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_tutor_id (tutor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 創建 reviews 表
CREATE TABLE IF NOT EXISTS reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tutor_course_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_reviews_tutor_course 
        FOREIGN KEY (tutor_course_id) REFERENCES tutor_courses (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_tutor_course_id (tutor_course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 創建 transactions 表
CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    amount INT NOT NULL,
    type INT NOT NULL,
    order_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_transactions_user 
        FOREIGN KEY (user_id) REFERENCES users (id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_transactions_order 
        FOREIGN KEY (order_id) REFERENCES orders (id) 
        ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 創建 tutor_feedbacks 表
CREATE TABLE IF NOT EXISTS tutor_feedbacks (
    course_id INT PRIMARY KEY,
    rating INT NOT NULL,
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_tutor_feedbacks_course 
        FOREIGN KEY (course_id) REFERENCES courses (id) 
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
