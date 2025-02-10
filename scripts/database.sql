DROP DATABASE IF EXISTS boatmanagement;

CREATE DATABASE IF NOT EXISTS boatmanagement;

USE boatmanagement;

-- 基础账号表
CREATE TABLE accounts (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` VARCHAR(64) COMMENT '用户名',
  `password` VARCHAR(255) COMMENT '密码',
  `phone` VARCHAR(20) COMMENT '手机号',
  `email` VARCHAR(255) COMMENT '邮箱',
  `role` INT NOT NULL DEFAULT 1 COMMENT '角色MASK',
  `is_active` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否激活',
  `is_blocked` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否锁定',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_phone` (`phone`),
  INDEX `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础账号表';

INSERT INTO `accounts` (`username`, `password`, `phone`, `email`, `role`, `is_active`, `is_blocked`, `is_deleted`, `created_at`, `updated_at`)
VALUES ('admin', 'admin', '12345678901', 'admin@example.com', 0xFFFFFF, 1, 0, 0, NOW(), NOW()),
('merchant', 'merchant', '12345678901', 'merchant@example.com', 0xFFFFFF, 1, 0, 0, NOW(), NOW()),
('vendor', 'vendor', '12345678901', 'vendor@example.com', 0xFFFFFF, 1, 0, 0, NOW(), NOW()),
('merchant2', 'merchant2', '12345678902', 'merchant2@example.com', 0xFFFFFF, 1, 0, 0, NOW(), NOW()),
('vendor2', 'vendor2', '12345678902', 'vendor2@example.com', 0xFFFFFF, 1, 0, 0, NOW(), NOW());

CREATE TABLE `user_certify` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '关联用户',
  `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
  `id_card` VARCHAR(18) NOT NULL COMMENT '身份证号',
  `status` ENUM('PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'PENDING' COMMENT '审核状态',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES accounts (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户实名认证表';

-- 第三方登录表
CREATE TABLE `social_auth` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` BIGINT UNSIGNED NOT NULL,
  `platform` ENUM('WECHAT','ALIPAY','APPLE') NOT NULL COMMENT '第三方平台',
  `open_id` VARCHAR(255) NOT NULL COMMENT '第三方唯一ID',
  `union_id` VARCHAR(255) COMMENT '跨平台统一ID',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_platform_openid` (`platform`, `open_id`),
  FOREIGN KEY (`account_id`) REFERENCES accounts (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方登录表';

-- RBAC权限系统
CREATE TABLE `role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
  `description` VARCHAR(255) COMMENT '角色描述',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE `permission` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL UNIQUE COMMENT '权限名称',
  `code` VARCHAR(50) NOT NULL UNIQUE COMMENT '权限代码',
  `description` VARCHAR(255) COMMENT '权限描述',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

CREATE TABLE `role_permission` (
  `role_id` INT UNSIGNED NOT NULL,
  `permission_id` INT UNSIGNED NOT NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`, `permission_id`),
  FOREIGN KEY (`role_id`) REFERENCES `role`(`id`),
  FOREIGN KEY (`permission_id`) REFERENCES `permission`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 角色继承表（实现多继承）
CREATE TABLE `role_inheritance` (
  `parent_role_id` INT UNSIGNED NOT NULL,
  `child_role_id` INT UNSIGNED NOT NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`parent_role_id`, `child_role_id`),
  FOREIGN KEY (`parent_role_id`) REFERENCES `role`(`id`),
  FOREIGN KEY (`child_role_id`) REFERENCES `role`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色继承关系表';

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
  FOREIGN KEY (`role_id`) REFERENCES `role`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 单位表
CREATE TABLE units (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL COMMENT '对外名称',
  `unit_name` VARCHAR(255) NOT NULL COMMENT '单位名称',
  `social_credit_code` VARCHAR(18) UNIQUE COMMENT '统一社会信用代码',
  `legal_person` VARCHAR(50) COMMENT '法人代表',
  `address` VARCHAR(255) COMMENT '单位地址',
  `contact_phone` VARCHAR(20) COMMENT '联系电话',
  `status` ENUM('PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'PENDING' COMMENT '审核状态',
  `admin_user_id` BIGINT UNSIGNED NOT NULL COMMENT '单位管理员',
  `types` ENUM('MERCHANT','VENDOR') NOT NULL COMMENT '单位类型',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`admin_user_id`) REFERENCES accounts (`id`),
  INDEX `idx_social_credit_code` (`social_credit_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单位表';


-- 商家表
CREATE TABLE merchants (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '关联用户',
  `unit_id` BIGINT UNSIGNED COMMENT '所属单位',
  `status` ENUM('PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'PENDING' COMMENT '审核状态',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES accounts(`id`),
  FOREIGN KEY (`unit_id`) REFERENCES units(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家表';

-- 船主表
CREATE TABLE vendors (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '关联用户',
  `unit_id` BIGINT UNSIGNED COMMENT '所属单位',
  `status` ENUM('PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'PENDING' COMMENT '审核状态',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES accounts(`id`),
  FOREIGN KEY (`unit_id`) REFERENCES units(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='船主表';

-- 码头表
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='码头表';

-- 船只类型表
CREATE TABLE ship_types (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type_name` VARCHAR(50) NOT NULL UNIQUE COMMENT '类型名称',
  `description` VARCHAR(255) COMMENT '类型描述',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='船只类型表';

-- 船只表
CREATE TABLE ships (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL COMMENT '船只名称',
  `type_id` INT UNSIGNED NOT NULL COMMENT '船只类型',
  `vendor_id` BIGINT UNSIGNED NOT NULL COMMENT '船主ID',
  `unit_id` BIGINT UNSIGNED NOT NULL COMMENT '所属单位',
  `length` DECIMAL(10,2) COMMENT '船身长度（米）',
  `width` DECIMAL(10,2) COMMENT '船身宽度（米）',
  `max_load` DECIMAL(10,2) COMMENT '最大载重（吨）',
  `max_endurance` DECIMAL(10,2) COMMENT '最大续航（公里）',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`type_id`) REFERENCES ship_types(`id`),
  FOREIGN KEY (`vendor_id`) REFERENCES vendors(`id`),
  FOREIGN KEY (`unit_id`) REFERENCES units(`id`),
  INDEX `idx_unit` (`unit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='船只表';

-- 订单表
CREATE TABLE orders (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '订单编号',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '下单用户',
  `ship_id` BIGINT UNSIGNED COMMENT '指定船只',
  `start_dock_id` BIGINT UNSIGNED NOT NULL COMMENT '起始码头',
  `end_dock_id` BIGINT UNSIGNED NOT NULL COMMENT '目的码头',
  `start_time` DATETIME NOT NULL COMMENT '租用开始时间',
  `end_time` DATETIME NOT NULL COMMENT '租用结束时间',
  `total_amount` DECIMAL(12,2) NOT NULL COMMENT '订单总金额',
  `status` ENUM('PENDING','PAID','COMPLETED','CANCELLED') NOT NULL DEFAULT 'PENDING',
  `type` ENUM('REAL_TIME','RESERVATION') NOT NULL COMMENT '订单类型',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES accounts (`id`),
  FOREIGN KEY (`ship_id`) REFERENCES ships(`id`),
  FOREIGN KEY (`start_dock_id`) REFERENCES docks(`id`),
  FOREIGN KEY (`end_dock_id`) REFERENCES docks(`id`),
  INDEX `idx_user_status` (`user_id`, `status`),
  INDEX `idx_time_range` (`start_time`, `end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 日志表
CREATE TABLE logs (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` ENUM('AUDIT','OPERATION','SYSTEM','SECURITY') NOT NULL COMMENT '日志类型',
  `level` ENUM('INFO','WARNING','ERROR') NOT NULL COMMENT '日志等级',
  `content` TEXT NOT NULL COMMENT '日志内容',
  `source` VARCHAR(50) COMMENT '来源模块',
  `operator_id` BIGINT UNSIGNED COMMENT '操作人',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_log_time` (`created_at`),
  INDEX `idx_type_level` (`type`, `level`),
  FOREIGN KEY (`operator_id`) REFERENCES accounts (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志表';

-- 验证码表
CREATE TABLE `captcha` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `target` VARCHAR(255) NOT NULL COMMENT '接收对象',
  `code` VARCHAR(10) NOT NULL COMMENT '验证码',
  `status` ENUM('UNUSED', 'USED', 'INVALID') NOT NULL DEFAULT 'UNUSED' COMMENT '使用状态',
  `expire_at` DATETIME NOT NULL COMMENT '过期时间',
  `client_ip` VARCHAR(45) COMMENT '请求IP',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_target` (`target`),
  INDEX `idx_expire_time` (`expire_at`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码表';

-- 防刷记录表
CREATE TABLE `captcha_limit` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `target` VARCHAR(255) NOT NULL,
  `ip` VARCHAR(45) NOT NULL,
  `count` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '请求次数',
  `last_request` DATETIME NOT NULL COMMENT '最后请求时间',
  `is_blocked` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否锁定',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_last_request` (`last_request`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码防刷记录';