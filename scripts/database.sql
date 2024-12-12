DROP DATABASE IF EXISTS boatmanagement;

CREATE DATABASE IF NOT EXISTS boatmanagement;

USE boatmanagement;

-- 用户表
CREATE TABLE IF NOT EXISTS users
(
    user_id    BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    uuid       VARCHAR(50)  NOT NULL UNIQUE DEFAULT UUID() COMMENT '用户UUID',
    username   VARCHAR(50) DEFAULT '' COMMENT '用户名',
    password   VARCHAR(255) NOT NULL DEFAULT '' COMMENT '密码',
    email      VARCHAR(100) DEFAULT '' COMMENT '邮箱',
    phone      VARCHAR(20)  DEFAULT '' COMMENT '手机号',
    role       INT UNSIGNED                   DEFAULT 1 COMMENT '角色权限',
    is_active  BOOLEAN                      DEFAULT TRUE COMMENT '是否激活',
    is_blocked BOOLEAN                      DEFAULT FALSE COMMENT '是否封禁',
    is_deleted BOOLEAN                      DEFAULT FALSE COMMENT '是否删除',
    created_at TIMESTAMP                    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

INSERT INTO users (username, password, email, phone, role)
VALUES ('admin', 'admin', 'admin@admin.com', '1234567890', 0xFFFFFF);

-- 船只类型表
CREATE TABLE IF NOT EXISTS boat_types
(
    boat_type_id          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '船只类型ID',
    type_name             VARCHAR(50) NOT NULL COMMENT '类型名称',
    type_code             VARCHAR(20) NOT NULL COMMENT '类型代码',
    max_capacity          DECIMAL(10,2) NOT NULL COMMENT '最大载量(吨)',
    max_speed             DECIMAL(10,2) NOT NULL COMMENT '最高速度(节)',
    fuel_type             VARCHAR(20) NOT NULL COMMENT '燃料类型',
    status                TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0-禁用 1-启用)',
    is_deleted            BOOLEAN   DEFAULT FALSE COMMENT '是否删除',
    created_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE INDEX uk_type_code (type_code),
    INDEX idx_type_name (type_name)
);
-- 设备表
CREATE TABLE IF NOT EXISTS boats
(
    boat_id      BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '船只ID',
    boat_name    VARCHAR(50) NOT NULL COMMENT '船只名称',
    boat_type_id BIGINT COMMENT '船只类型ID',
    FOREIGN KEY (boat_type_id) REFERENCES boat_types (boat_type_id),
    status       INT       DEFAULT 0 COMMENT '船只状态',
    is_deleted   BOOLEAN   DEFAULT FALSE COMMENT '是否删除',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);
-- 码头表
CREATE TABLE IF NOT EXISTS docks
(
    dock_id       BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '码头ID',
    dock_name     VARCHAR(50)  NOT NULL COMMENT '码头名称',
    dock_location VARCHAR(100) NOT NULL COMMENT '码头位置',
    is_deleted    BOOLEAN   DEFAULT FALSE COMMENT '是否删除',
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);
-- 船票表
CREATE TABLE IF NOT EXISTS tickets
(
    ticket_id           BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '船票ID',
    user_id             BIGINT COMMENT '用户ID',
    boat_id             BIGINT COMMENT '船只ID',
    start_time          DATETIME COMMENT '开始时间',
    end_time            DATETIME COMMENT '结束时间',
    departure_dock_id   BIGINT COMMENT '出发码头ID',
    destination_dock_id BIGINT COMMENT '目的码头ID',
    price               DECIMAL(10, 2) COMMENT '票价',
    remaining_tickets   BIGINT    DEFAULT 0 COMMENT '剩余票数',
    is_deleted          BOOLEAN   DEFAULT FALSE COMMENT '是否删除',
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (boat_id) REFERENCES boats (boat_id),
    FOREIGN KEY (departure_dock_id) REFERENCES docks (dock_id),
    FOREIGN KEY (destination_dock_id) REFERENCES docks (dock_id)
);
-- 订单表
CREATE TABLE IF NOT EXISTS orders
(
    order_id       BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
    user_id        BIGINT COMMENT '用户ID',
    ticket_id      BIGINT COMMENT '船票ID',
    total_amount   DECIMAL(10, 2) COMMENT '总金额',
    payment_status INT       DEFAULT 0 COMMENT '支付状态',
    is_deleted     BOOLEAN   DEFAULT FALSE COMMENT '是否删除',
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (ticket_id) REFERENCES tickets (ticket_id)
);
-- 支付记录表
CREATE TABLE IF NOT EXISTS payment
(
    payment_id     BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '支付ID',
    order_id       BIGINT COMMENT '订单ID',
    payment_time   DATETIME COMMENT '支付时间',
    amount         DECIMAL(10, 2) COMMENT '支付金额',
    payment_method INT       DEFAULT 0 COMMENT '支付方式',
    is_deleted     BOOLEAN   DEFAULT FALSE COMMENT '是否删除',
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);
-- 告警表
CREATE TABLE IF NOT EXISTS alerts
(
    alert_id    BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '告警ID',
    boat_id     BIGINT COMMENT '船只ID',
    alert_type  VARCHAR(50) COMMENT '告警类型',
    alert_time  DATETIME COMMENT '告警时间',
    description TEXT COMMENT '告警描述',
    status      INT       DEFAULT 0 COMMENT '告警状态',
    is_deleted  BOOLEAN   DEFAULT FALSE COMMENT '是否删除',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (boat_id) REFERENCES boats (boat_id)
);

-- 验证码
CREATE TABLE IF NOT EXISTS verification_codes
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '验证码ID',
    user_id         BIGINT      NOT NULL COMMENT '用户ID',
    code            VARCHAR(10) NOT NULL COMMENT '验证码',
    request_time    DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '请求时间',
    expiration_time DATETIME COMMENT '过期时间',
    is_deleted  BOOLEAN   DEFAULT FALSE COMMENT '是否删除',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);
-- -- 运营管理表
-- CREATE TABLE IF NOT EXISTS operations (
--     operation_id INT PRIMARY KEY AUTO_INCREMENT,
--     activity_name VARCHAR(100),
--     start_date DATE,
--     end_date DATE,
--     description TEXT
-- );
-- -- 景点介绍表
-- CREATE TABLE IF NOT EXISTS attractions (
--     attraction_id INT PRIMARY KEY AUTO_INCREMENT,
--     name VARCHAR(100),
--     description TEXT,
--     location VARCHAR(100)
-- );
-- -- 分层授权表
-- CREATE TABLE IF NOT EXISTS permissions (
--     permission_id INT PRIMARY KEY AUTO_INCREMENT,
--     user_id INT,
--     permission_level ENUM('景区', '运营方', '合伙人', '技术方'),
--     FOREIGN KEY (user_id) REFERENCES user(user_id)
-- );
-- -- 分账管理表
-- CREATE TABLE IF NOT EXISTS settlements (
--     settlement_id INT PRIMARY KEY AUTO_INCREMENT,
--     order_id INT,
--     recipient_id INT,
--     amount DECIMAL(10, 2),
--     settlement_time DATETIME,
--     FOREIGN KEY (order_id) REFERENCES order(order_id),
--     FOREIGN KEY (recipient_id) REFERENCES user(user_id)
-- );

-- 添加表注释
ALTER TABLE users COMMENT '用户表';
ALTER TABLE boat_types COMMENT '船只类型表';
ALTER TABLE boats COMMENT '船只表';
ALTER TABLE docks COMMENT '码头表';
ALTER TABLE tickets COMMENT '船票表';
ALTER TABLE orders COMMENT '订单表';
ALTER TABLE payment COMMENT '支付记录表';
ALTER TABLE alerts COMMENT '告警表';
ALTER TABLE verification_codes COMMENT '验证码表';