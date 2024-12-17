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

-- 设备表
CREATE TABLE IF NOT EXISTS boats
(
    boat_id            BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '船只ID',
    boat_name          VARCHAR(50) NOT NULL COMMENT '船只名称',
    boat_type_id       BIGINT COMMENT '船只类型ID',
    registration_number VARCHAR(50) COMMENT '注册编号',
    build_year         INTEGER COMMENT '建造年份',
    last_maintenance   TIMESTAMP COMMENT '上次维护时间',
    next_maintenance   TIMESTAMP COMMENT '下次维护时间',
    current_dock_id    BIGINT COMMENT '当前码头ID',
    status             INT       DEFAULT 0 COMMENT '船只状态',
    is_deleted         BOOLEAN   DEFAULT FALSE COMMENT '是否删除',
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (boat_type_id) REFERENCES boat_types (boat_type_id),
    FOREIGN KEY (current_dock_id) REFERENCES docks (dock_id)
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
CREATE TABLE IF NOT EXISTS payments
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
CREATE TABLE IF NOT EXISTS codes
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
--     FOREIGN KEY (user_id) REFERENCES users(user_id)
-- );
-- -- 分账管理表
-- CREATE TABLE IF NOT EXISTS settlements (
--     settlement_id INT PRIMARY KEY AUTO_INCREMENT,
--     order_id INT,
--     recipient_id INT,
--     amount DECIMAL(10, 2),
--     settlement_time DATETIME,
--     FOREIGN KEY (order_id) REFERENCES order(order_id),
--     FOREIGN KEY (recipient_id) REFERENCES users(user_id)
-- );

-- 创建日志表
CREATE TABLE IF NOT EXISTS logs
(
    log_id      BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    table_name  VARCHAR(50)  NOT NULL COMMENT '表名',
    operation   VARCHAR(20)  NOT NULL COMMENT '操作类型(INSERT/UPDATE/DELETE)',
    record_id   BIGINT      NOT NULL COMMENT '记录ID',
    operator_id BIGINT      COMMENT '操作者ID',
    old_data    JSON        COMMENT '原数据',
    new_data    JSON        COMMENT '新数据',
    ip_address  VARCHAR(50) COMMENT 'IP地址',
    created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '操作日志表';

-- 添加表注释
ALTER TABLE users COMMENT '用户表';
ALTER TABLE boat_types COMMENT '船只类型表';
ALTER TABLE boats COMMENT '船只表';
ALTER TABLE docks COMMENT '码头表';
ALTER TABLE tickets COMMENT '船票表';
ALTER TABLE orders COMMENT '订单表';
ALTER TABLE payments COMMENT '支付记录表';
ALTER TABLE alerts COMMENT '告警表';
ALTER TABLE codes COMMENT '验证码表';
ALTER TABLE logs COMMENT '操作日志表';

-- 插入船只类型数据
INSERT INTO boat_types (type_name, type_code, max_capacity, max_speed, fuel_type) VALUES
('豪华游轮', 'LUXURY_CRUISE', 500.00, 25.00, '柴油'),
('快速客轮', 'FAST_FERRY', 200.00, 35.00, '柴油'),
('观光游船', 'SIGHTSEEING', 100.00, 15.00, '电力'),
('水上巴士', 'WATER_BUS', 50.00, 20.00, '电力'),
('帆船', 'SAILBOAT', 20.00, 12.00, '风力');

-- 插入码头数据
INSERT INTO docks (dock_name, dock_location) VALUES
('东方明珠码头', '上海市浦东新区陆家嘴'),
('西湖码头', '杭州市西湖区'),
('三亚湾码头', '海南省三亚市三亚湾'),
('中山码头', '武汉市江岸区'),
('鼓浪屿码头', '厦门市思明区');

-- 插入船只数据
INSERT INTO boats (boat_name, boat_type_id, registration_number, build_year, last_maintenance, next_maintenance, current_dock_id, status) VALUES
('东方之星', 1, 'SH2024001', 2020, '2024-01-15', '2024-07-15', 1, 1),
('西湖明珠', 2, 'HZ2024001', 2021, '2024-02-01', '2024-08-01', 2, 1),
('碧海蓝天', 1, 'SY2024001', 2019, '2024-03-01', '2024-09-01', 3, 1),
('长江号', 3, 'WH2024001', 2022, '2024-01-20', '2024-07-20', 4, 1),
('鹭江号', 4, 'XM2024001', 2023, '2024-02-15', '2024-08-15', 5, 1);

-- 插入船票数据
INSERT INTO tickets (boat_id, start_time, end_time, departure_dock_id, destination_dock_id, price, remaining_tickets) VALUES
(1, '2024-03-20 09:00:00', '2024-03-20 11:00:00', 1, 2, 299.99, 100),
(2, '2024-03-20 10:00:00', '2024-03-20 11:30:00', 2, 3, 199.99, 80),
(3, '2024-03-20 13:00:00', '2024-03-20 15:00:00', 3, 4, 399.99, 150),
(4, '2024-03-20 14:00:00', '2024-03-20 15:30:00', 4, 5, 149.99, 40),
(5, '2024-03-20 16:00:00', '2024-03-20 17:00:00', 5, 1, 99.99, 30);

-- 插入测试用户数据
INSERT INTO users (username, password, email, phone, role) VALUES
('test_user1', 'password123', 'user1@example.com', '13800138001', 1),
('test_user2', 'password123', 'user2@example.com', '13800138002', 1),
('operator1', 'password123', 'operator1@example.com', '13800138003', 2),
('manager1', 'password123', 'manager1@example.com', '13800138004', 3);

-- 插入订单数据
INSERT INTO orders (user_id, ticket_id, total_amount, payment_status) VALUES
(2, 1, 299.99, 1),
(2, 2, 199.99, 1),
(3, 3, 399.99, 0),
(4, 4, 149.99, 1);

-- 插入支付记录
INSERT INTO payments (order_id, payment_time, amount, payment_method) VALUES
(1, '2024-03-19 15:30:00', 299.99, 1),
(2, '2024-03-19 16:00:00', 199.99, 2),
(4, '2024-03-19 16:30:00', 149.99, 1);

-- 插入告警数据
INSERT INTO alerts (boat_id, alert_type, alert_time, description, status) VALUES
(1, '维护提醒', '2024-03-18 10:00:00', '需要进行季度维护检查', 0),
(2, '天气警告', '2024-03-19 08:00:00', '未来24小时可能有强风天气', 1),
(3, '设备异常', '2024-03-19 14:00:00', '发动机温度偏高', 2);
