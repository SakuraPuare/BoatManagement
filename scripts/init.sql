USE boatmanagement;
-- 创建管理员和他的其他角色
INSERT INTO `accounts` (
        `username`,
        `password`,
        `phone`,
        `email`,
        `role`
    )
VALUES (
        'admin',
        'admin',
        '12345678901',
        'admin@example.com',
        0xff
    );
-- 创建单位示例
INSERT INTO `units` (
        `name`,
        `unit_name`,
        `social_credit_code`,
        `legal_person`,
        `address`,
        `contact_phone`,
        `status`,
        `admin_user_id`,
        `types`
    )
VALUES (
        '商户',
        '商户',
        '12345678901',
        '张三',
        '武汉市洪山区珞喻路123号',
        '12345678901',
        'APPROVED',
        1,
        'MERCHANT'
    ),
    (
        '供应商',
        '供应商',
        '12345678902',
        '李四',
        '武汉市洪山区珞喻路123号',
        '12345678902',
        'APPROVED',
        1,
        'VENDOR'
    );
-- 创建商户
INSERT INTO `merchants` (
        `user_id`,
        `unit_id`,
        `status`
    )
VALUES (
        '1',
        '1',
        'APPROVED'
    );
-- 创建供应商
INSERT INTO `vendors` (
        `user_id`,
        `unit_id`,
        `status`
    )
VALUES (
        '1',
        '1',
        'APPROVED'
    );
-- 创建码头示例
INSERT INTO `docks` (
        `name`,
        `longitude`,
        `latitude`,
        `address`,
        `contact_phone`
    )
VALUES (
        '码头1',
        114.34,
        30.57,
        '武汉市洪山区珞喻路123号',
        '12345678901'
    ),
    (
        '码头2',
        116.46,
        39.91,
        '北京市海淀区中关村大街1号',
        '12345678902'
    );
-- 创建商家示例
INSERT INTO `accounts` (
        `username`,
        `password`,
        `phone`,
        `email`,
        `role`
    )
VALUES (
        'merchant',
        'merchant',
        '12345678903',
        'merchant@example.com',
        0b11
    );
INSERT INTO `merchants` (
        `user_id`,
        `unit_id`,
        `status`
    )
VALUES (
        '2',
        '1',
        'APPROVED'
    );
INSERT INTO `goods` (
        `name`,
        `description`,
        `price`,
        `unit`,
        `merchant_id`,
        `unit_id`
    )
VALUES (
        '商品1',
        '商品1',
        100,
        '个',
        '2',
        '1'
    );
-- 创建船主
INSERT INTO `accounts` (
        `username`,
        `password`,
        `phone`,
        `email`,
        `role`
    )
VALUES (
        'vendor',
        'vendor',
        '12345678904',
        'vendor@example.com',
        0b101
    );
INSERT INTO `vendors` (
        `user_id`,
        `unit_id`,
        `status`
    )
VALUES (
        '3',
        '1',
        'APPROVED'
    );
-- 创建船只类型示例
INSERT INTO `boat_types` (
        `type_name`,
        `description`,
        `length`,
        `width`,
        `gross_number`,
        `max_load`,
        `max_speed`,
        `max_endurance`,
        `price`,
        `vendor_id`,
        `unit_id`
    )
VALUES (
        '游艇',
        '游艇',
        10,
        10,
        100,
        1000,
        100,
        1000,
        100,
        '1',
        '1'
    );
-- 创建船只
INSERT INTO `boats` (
        `name`,
        `type_id`,
        `dock_id`,
        `vendor_id`,
        `unit_id`
    )
VALUES (
        '游艇114514',
        '1',
        '1',
        '1',
        '1'
    );
-- 创建普通用户
INSERT INTO `accounts` (
        `username`,
        `password`,
        `phone`,
        `email`,
        `role`
    )
VALUES (
        'user',
        'user',
        '12345678905',
        'user@example.com',
        1
    );
-- 实名认证一下
INSERT INTO `user_certify` (
        `user_id`,
        `real_name`,
        `id_card`,
        `status`
    )
VALUES (
        '4',
        '张三',
        '12345678901',
        'PENDING'
    );
-- 买张票
INSERT INTO `boat_requests` (
        `user_id`,
        `start_dock_id`,
        `end_dock_id`,
        `start_time`,
        `end_time`,
        `type`,
        `status`
    )
VALUES (
        '4',
        '1',
        '2',
        '2024-01-01 00:00:00',
        '2024-01-02 00:00:00',
        'REAL_TIME',
        'PENDING'
    ),
    (
        '4',
        '2',
        '1',
        '2024-02-01 00:00:00',
        '2024-02-02 00:00:00',
        'RESERVATION',
        'PENDING'
    ),
    (
        '4',
        '1',
        '1',
        '2024-03-01 00:00:00',
        '2024-03-02 00:00:00',
        'RESERVATION',
        'PENDING'
    );