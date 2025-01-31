DROP DATABASE IF EXISTS boatmanagement;
CREATE DATABASE boatmanagement;
\c boatmanagement;
-- 用户表
CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(50) NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    username VARCHAR(50),
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    role VARCHAR(10) DEFAULT 'NONE',
    is_active BOOLEAN DEFAULT TRUE,
    is_blocked BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 船只类型表
CREATE TABLE IF NOT EXISTS boat_types (
    boat_type_id BIGSERIAL PRIMARY KEY,
    boat_type_name VARCHAR(50) NOT NULL,
    boat_type_description TEXT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 设备表
CREATE TABLE IF NOT EXISTS boats (
    boat_id BIGSERIAL PRIMARY KEY,
    boat_name VARCHAR(50) NOT NULL,
    boat_type_id BIGINT,
    FOREIGN KEY (boat_type_id) REFERENCES boat_types (boat_type_id),
    status INT DEFAULT 0,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 码头表
CREATE TABLE IF NOT EXISTS docks (
    dock_id BIGSERIAL PRIMARY KEY,
    dock_name VARCHAR(50) NOT NULL,
    dock_location VARCHAR(100) NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 船票表
CREATE TABLE IF NOT EXISTS tickets (
    ticket_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    boat_id BIGINT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    departure_dock_id BIGINT,
    destination_dock_id BIGINT,
    price DECIMAL(10, 2),
    remaining_tickets BIGINT DEFAULT 0,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (boat_id) REFERENCES boats (boat_id),
    FOREIGN KEY (departure_dock_id) REFERENCES docks (dock_id),
    FOREIGN KEY (destination_dock_id) REFERENCES docks (dock_id)
);
-- 订单表
CREATE TABLE IF NOT EXISTS orders (
    order_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    ticket_id BIGINT,
    total_amount DECIMAL(10, 2),
    payment_status INT DEFAULT 0,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (ticket_id) REFERENCES tickets (ticket_id)
);
-- 支付记录表
CREATE TABLE IF NOT EXISTS payment (
    payment_id BIGSERIAL PRIMARY KEY,
    order_id BIGINT,
    payment_time TIMESTAMP,
    amount DECIMAL(10, 2),
    payment_method INT DEFAULT 0,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);
-- 告警表
CREATE TABLE IF NOT EXISTS alerts (
    alert_id BIGSERIAL PRIMARY KEY,
    boat_id BIGINT,
    alert_type VARCHAR(50),
    alert_time TIMESTAMP,
    description TEXT,
    status INT DEFAULT 0,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (boat_id) REFERENCES boats (boat_id)
);
-- 验证码
CREATE TABLE IF NOT EXISTS verification_codes (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    code VARCHAR(10) NOT NULL,
    request_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expiration_time TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);
CREATE INDEX idx_user_id ON verification_codes(user_id);
-- -- 运营管理表
-- CREATE TABLE IF NOT EXISTS operations (
--     operation_id SERIAL PRIMARY KEY,
--     activity_name VARCHAR(100),
--     start_date DATE,
--     end_date DATE,
--     description TEXT
-- );
-- -- 景点介绍表
-- CREATE TABLE IF NOT EXISTS attractions (
--     attraction_id SERIAL PRIMARY KEY,
--     name VARCHAR(100),
--     description TEXT,
--     location VARCHAR(100)
-- );
-- -- 分层授权表
-- CREATE TABLE IF NOT EXISTS permissions (
--     permission_id SERIAL PRIMARY KEY,
--     user_id INT,
--     permission_level VARCHAR(50) CHECK (permission_level IN ('景区', '运营方', '合伙人', '技术方')),
--     FOREIGN KEY (user_id) REFERENCES users(user_id)
-- );
-- -- 分账管理表
-- CREATE TABLE IF NOT EXISTS settlements (
--     settlement_id SERIAL PRIMARY KEY,
--     order_id INT,
--     recipient_id INT,
--     amount DECIMAL(10, 2),
--     settlement_time TIMESTAMP,
--     FOREIGN KEY (order_id) REFERENCES orders(order_id),
--     FOREIGN KEY (recipient_id) REFERENCES users(user_id)
-- );