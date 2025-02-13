DROP DATABASE IF EXISTS boatmanagement;
CREATE DATABASE IF NOT EXISTS boatmanagement;
USE boatmanagement;
-- 基础账号表
CREATE TABLE accounts (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) COMMENT '用户名',
  `password` VARCHAR(255) COMMENT '密码',
  `phone` VARCHAR(20) COMMENT '手机号',
  `email` VARCHAR(255) COMMENT '邮箱',
  `role` INT NOT NULL DEFAULT 1 COMMENT '角色MASK',
  `is_active` TINYINT(1) NOT NULL DEFAULT 1,
  `is_blocked` TINYINT(1) NOT NULL DEFAULT 0,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_phone` (`phone`),
  INDEX `idx_email` (`email`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '基础账号表';
INSERT INTO `accounts` (
    `username`,
    `password`,
    `phone`,
    `email`,
    `role`,
    `is_active`,
    `is_blocked`,
    `is_deleted`,
    `created_at`,
    `updated_at`
  )
VALUES (
    'admin',
    'admin',
    '12345678901',
    'admin@example.com',
    0xFFFFFF,
    1,
    0,
    0,
    NOW(),
    NOW()
  ),
  (
    'merchant',
    'merchant',
    '12345678901',
    'merchant@example.com',
    1 << 1,
    1,
    0,
    0,
    NOW(),
    NOW()
  ),
  (
    'vendor',
    'vendor',
    '12345678901',
    'vendor@example.com',
    1 << 2,
    1,
    0,
    0,
    NOW(),
    NOW()
  ),
  (
    'merchant2',
    'merchant2',
    '12345678902',
    'merchant2@example.com',
    1 << 1,
    1,
    0,
    0,
    NOW(),
    NOW()
  ),
  (
    'vendor2',
    'vendor2',
    '12345678902',
    'vendor2@example.com',
    1 << 2,
    1,
    0,
    0,
    NOW(),
    NOW()
  );
CREATE TABLE `user_certify` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '关联用户',
  `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
  `id_card` VARCHAR(18) NOT NULL COMMENT '身份证号',
  `status` ENUM ('PENDING', 'APPROVED', 'REJECTED') NOT NULL DEFAULT 'PENDING' COMMENT '审核状态',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES accounts (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '用户实名认证表_ndto_nvo';
-- 第三方登录表
CREATE TABLE `social_auth` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` BIGINT UNSIGNED NOT NULL,
  `platform` ENUM ('WECHAT', 'ALIPAY', 'APPLE') NOT NULL COMMENT '第三方平台',
  `open_id` VARCHAR(255) NOT NULL COMMENT '第三方唯一ID',
  `union_id` VARCHAR(255) COMMENT '跨平台统一ID',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_platform_openid` (`platform`, `open_id`),
  FOREIGN KEY (`account_id`) REFERENCES accounts (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '第三方登录表_ndto_nvo';
-- RBAC权限系统
CREATE TABLE `role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
  `description` VARCHAR(255) COMMENT '角色描述',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '角色表_ndto_nvo';
CREATE TABLE `permission` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL UNIQUE COMMENT '权限名称',
  `code` VARCHAR(50) NOT NULL UNIQUE COMMENT '权限代码',
  `description` VARCHAR(255) COMMENT '权限描述',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '权限表_ndto_nvo';
CREATE TABLE `role_permission` (
  `role_id` INT UNSIGNED NOT NULL,
  `permission_id` INT UNSIGNED NOT NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`, `permission_id`),
  FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '角色权限关联表_ndto_nvo';
-- 角色继承表（实现多继承）
CREATE TABLE `role_inheritance` (
  `parent_role_id` INT UNSIGNED NOT NULL,
  `child_role_id` INT UNSIGNED NOT NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`parent_role_id`, `child_role_id`),
  FOREIGN KEY (`parent_role_id`) REFERENCES `role` (`id`),
  FOREIGN KEY (`child_role_id`) REFERENCES `role` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '角色继承关系表_ndto_nvo';
-- 用户角色关联表（带单位）
CREATE TABLE `user_role` (
  `user_id` BIGINT UNSIGNED NOT NULL,
  `role_id` INT UNSIGNED NOT NULL,
  `unit_id` BIGINT UNSIGNED COMMENT '所属单位',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `role_id`, `unit_id`),
  FOREIGN KEY (`user_id`) REFERENCES accounts (`id`),
  FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '用户角色关联表_ndto_nvo';
-- 单位表
CREATE TABLE units (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL COMMENT '对外名称',
  `unit_name` VARCHAR(255) NOT NULL COMMENT '单位名称',
  `social_credit_code` VARCHAR(18) UNIQUE COMMENT '统一社会信用代码',
  `legal_person` VARCHAR(50) COMMENT '法人代表',
  `address` VARCHAR(255) COMMENT '单位地址',
  `contact_phone` VARCHAR(20) COMMENT '联系电话',
  `status` ENUM ('PENDING', 'APPROVED', 'REJECTED') NOT NULL DEFAULT 'PENDING' COMMENT '审核状态',
  `admin_user_id` BIGINT UNSIGNED NOT NULL COMMENT '单位管理员',
  `types` ENUM ('MERCHANT', 'VENDOR') NOT NULL COMMENT '单位类型',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`admin_user_id`) REFERENCES accounts (`id`),
  INDEX `idx_social_credit_code` (`social_credit_code`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '单位表';
INSERT INTO `units` (
    `name`,
    `unit_name`,
    `social_credit_code`,
    `legal_person`,
    `address`,
    `contact_phone`,
    `status`,
    `admin_user_id`,
    `types`,
    `is_deleted`,
    `created_at`,
    `updated_at`
  )
VALUES (
    '单位1',
    '单位1',
    '12345678901',
    '单位1',
    '单位1',
    '12345678901',
    'APPROVED',
    1,
    'MERCHANT',
    0,
    NOW(),
    NOW()
  ),
  (
    '单位2',
    '单位2',
    '12345678902',
    '单位2',
    '单位2',
    '12345678902',
    'APPROVED',
    2,
    'VENDOR',
    0,
    NOW(),
    NOW()
  );
-- 商家表
CREATE TABLE merchants (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '关联用户',
  `unit_id` BIGINT UNSIGNED COMMENT '所属单位',
  `status` ENUM ('PENDING', 'APPROVED', 'REJECTED') NOT NULL DEFAULT 'PENDING' COMMENT '审核状态',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES accounts (`id`),
  FOREIGN KEY (`unit_id`) REFERENCES units (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '商家表';
INSERT INTO `merchants` (
    `user_id`,
    `unit_id`,
    `status`,
    `is_deleted`,
    `created_at`,
    `updated_at`
  )
VALUES (
    2,
    1,
    'APPROVED',
    0,
    NOW(),
    NOW()
  ),
  (
    3,
    1,
    'APPROVED',
    0,
    NOW(),
    NOW()
  );
CREATE TABLE vendors (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '关联用户',
  `unit_id` BIGINT UNSIGNED COMMENT '所属单位',
  `status` ENUM ('PENDING', 'APPROVED', 'REJECTED') NOT NULL DEFAULT 'PENDING' COMMENT '审核状态',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES accounts (`id`),
  FOREIGN KEY (`unit_id`) REFERENCES units (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '船主表';
INSERT INTO `vendors` (
    `user_id`,
    `unit_id`,
    `status`,
    `is_deleted`,
    `created_at`,
    `updated_at`
  )
VALUES (
    4,
    1,
    'APPROVED',
    0,
    NOW(),
    NOW()
  );
CREATE TABLE docks (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL COMMENT '码头名称',
  `location` POINT NOT NULL COMMENT '地理位置',
  `address` VARCHAR(255) COMMENT '详细地址',
  `contact_phone` VARCHAR(20) COMMENT '联系电话',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  SPATIAL INDEX `idx_location` (`location`),
  INDEX `idx_name` (`name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '码头表';
-- 船只类型表
CREATE TABLE boat_types (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type_name` VARCHAR(50) NOT NULL UNIQUE COMMENT '类型名称',
  `description` VARCHAR(255) COMMENT '类型描述',
  `length` DECIMAL(10, 2) COMMENT '船身长度（米）',
  `width` DECIMAL(10, 2) COMMENT '船身宽度（米）',
  `gross_number` INT UNSIGNED COMMENT '核载人数（人）',
  `max_load` DECIMAL(10, 2) COMMENT '最大载重（吨）',
  `max_speed` DECIMAL(10, 2) COMMENT '最大航速（公里/小时）',
  `max_endurance` DECIMAL(10, 2) COMMENT '最大续航（公里）',
  `created_vendor_id` BIGINT UNSIGNED COMMENT '创建者_serverside',
  `created_unit_id` BIGINT UNSIGNED COMMENT '创建单位_serverside',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`created_vendor_id`) REFERENCES vendors (`id`),
  FOREIGN KEY (`created_unit_id`) REFERENCES units (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '船只类型表';
-- 船只表
CREATE TABLE boats (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL COMMENT '船只名称',
  `type_id` INT UNSIGNED NOT NULL COMMENT '船只类型',
  `boat_type_id` INT UNSIGNED NOT NULL COMMENT '船只类型',
  `dock_id` BIGINT UNSIGNED NOT NULL COMMENT '所属码头',
  `vendor_id` BIGINT UNSIGNED NOT NULL COMMENT '船主ID_serverside',
  `unit_id` BIGINT UNSIGNED NOT NULL COMMENT '所属单位_serverside',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`type_id`) REFERENCES boat_types (`id`),
  FOREIGN KEY (`vendor_id`) REFERENCES vendors (`id`),
  FOREIGN KEY (`unit_id`) REFERENCES units (`id`),
  FOREIGN KEY (`dock_id`) REFERENCES docks (`id`),
  INDEX `idx_unit` (`unit_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '船只表';
-- 订单表
CREATE TABLE orders (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '订单编号',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '下单用户',
  `boat_id` BIGINT UNSIGNED COMMENT '指定船只',
  `start_dock_id` BIGINT UNSIGNED NOT NULL COMMENT '起始码头',
  `end_dock_id` BIGINT UNSIGNED NOT NULL COMMENT '目的码头',
  `start_time` DATETIME NOT NULL COMMENT '租用开始时间',
  `end_time` DATETIME NOT NULL COMMENT '租用结束时间',
  `total_amount` DECIMAL(12, 2) NOT NULL COMMENT '订单总金额',
  `status` ENUM ('PENDING', 'PAID', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
  `type` ENUM ('REAL_TIME', 'RESERVATION') NOT NULL COMMENT '订单类型',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES accounts (`id`),
  FOREIGN KEY (`boat_id`) REFERENCES boats (`id`),
  FOREIGN KEY (`start_dock_id`) REFERENCES docks (`id`),
  FOREIGN KEY (`end_dock_id`) REFERENCES docks (`id`),
  INDEX `idx_user_status` (`user_id`, `status`),
  INDEX `idx_time_range` (`start_time`, `end_time`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '订单表';
-- 日志表
CREATE TABLE logs (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` ENUM ('AUDIT', 'OPERATION', 'SYSTEM', 'SECURITY') NOT NULL COMMENT '日志类型',
  `level` ENUM ('INFO', 'WARNING', 'ERROR') NOT NULL COMMENT '日志等级',
  `content` TEXT NOT NULL COMMENT '日志内容',
  `operator_id` BIGINT UNSIGNED COMMENT '操作人',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_log_time` (`created_at`),
  INDEX `idx_type_level` (`type`, `level`),
  FOREIGN KEY (`operator_id`) REFERENCES accounts (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '系统日志表_ndto_nvo';
-- 验证码表
CREATE TABLE `captcha` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `target` VARCHAR(255) NOT NULL COMMENT '接收对象',
  `code` VARCHAR(10) NOT NULL COMMENT '验证码',
  `status` ENUM ('UNUSED', 'USED', 'INVALID') NOT NULL DEFAULT 'UNUSED' COMMENT '使用状态',
  `expire_at` DATETIME NOT NULL COMMENT '过期时间',
  `client_ip` VARCHAR(45) COMMENT '请求IP',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_target` (`target`),
  INDEX `idx_expire_time` (`expire_at`),
  INDEX `idx_status` (`status`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '验证码表_ndto_nvo';
-- 防刷记录表
CREATE TABLE `captcha_limit` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `target` VARCHAR(255) NOT NULL,
  `ip` VARCHAR(45) NOT NULL,
  `count` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '请求次数',
  `last_request` DATETIME NOT NULL COMMENT '最后请求时间',
  `is_blocked` TINYINT(1) NOT NULL DEFAULT 0,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_last_request` (`last_request`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '验证码防刷记录_ndto_nvo';
CREATE TABLE `goods` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL COMMENT '商品名称',
  `description` TEXT COMMENT '商品描述',
  `price` DECIMAL(12, 2) NOT NULL COMMENT '商品价格',
  `stock` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '库存_serverside',
  `sales` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '销量_serverside',
  `created_merchant_id` BIGINT UNSIGNED NOT NULL COMMENT '创建商家_serverside',
  `created_unit_id` BIGINT UNSIGNED NOT NULL COMMENT '创建单位_serverside',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`created_merchant_id`) REFERENCES merchants (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '商品表';