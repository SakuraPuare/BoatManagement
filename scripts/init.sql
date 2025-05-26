USE boatmanagement;
-- RBAC
INSERT INTO `role` (`name`, `description`) VALUES ('ADMIN', '管理员');
INSERT INTO `role` (`name`, `description`) VALUES ('MERCHANT', '商户');
INSERT INTO `role` (`name`, `description`) VALUES ('VENDOR', '供应商');
INSERT INTO `role` (`name`, `description`) VALUES ('BOAT_OWNER', '船主');
INSERT INTO `role` (`name`, `description`) VALUES ('USER', '普通用户');
-- 创建管理员和他的其他角色
INSERT INTO `accounts` ( `username`, `password`, `phone`, `email` )
VALUES ( 'admin', 'admin', '12345678901', 'admin@example.com' );
INSERT INTO `user_role` (`user_id`, `role_id`, `unit_id`)
VALUES ('1', '1', NULL);
-- 创建单位示例
INSERT INTO `units` ( `name`, `unit_name`, `social_credit_code`, `legal_person`, `address`, `contact_phone`, `status`, `admin_user_id`, `types` )
VALUES ( '商户', '商户', '12345678901', '张三', '武汉市洪山区珞喻路 123 号', '12345678901', 'APPROVED', 1, 'MERCHANT' ), ( '供应商', '供应商', '12345678902', '李四', '武汉市洪山区珞喻路 123 号', '12345678902', 'APPROVED', 1, 'VENDOR' );
INSERT INTO `user_role` (`user_id`, `role_id`, `unit_id`)
VALUES ('1', '2', 1);
-- 创建商户
INSERT INTO `merchants` ( `user_id`, `unit_id`, `status` )
VALUES ( '1', '1', 'APPROVED' );
-- 创建供应商
INSERT INTO `vendors` ( `user_id`, `unit_id`, `status` )
VALUES ( '1', '1', 'APPROVED' );
INSERT INTO `user_role` (`user_id`, `role_id`, `unit_id`)
VALUES ('1', '3', 2);
-- 创建码头示例
INSERT INTO `docks` ( `name`, `longitude`, `latitude`, `address`, `contact_phone` )
VALUES ( '码头 1', 114.34, 30.57, '武汉市洪山区珞喻路 123 号', '12345678901' ), ( '码头 2', 116.46, 39.91, '北京市海淀区中关村大街 1 号', '12345678902' );
-- 创建商家示例
INSERT INTO `accounts` ( `username`, `password`, `phone`, `email` )
VALUES ( 'merchant', 'merchant', '12345678903', 'merchant@example.com' );
INSERT INTO `merchants` ( `user_id`, `unit_id`, `status` )
VALUES ( '2', '1', 'APPROVED' );
INSERT INTO `user_role` ( `user_id`, `role_id`, `unit_id` )
VALUES ( '2', '2', 1 );
INSERT INTO `goods` ( `name`, `description`, `price`, `unit`, `merchant_id`, `unit_id` ) VALUES ( '商品 1', '商品 1', 100, '个', '2', '1' ), ( '商品 2', '商品 2', 200, '个', '2', '1' ), ( '商品 3', '商品 3', 300, '个', '2', '1' );
-- 创建船主
INSERT INTO `accounts` ( `username`, `password`, `phone`, `email` )
VALUES ( 'vendor', 'vendor', '12345678904', 'vendor@example.com' );
INSERT INTO `vendors` ( `user_id`, `unit_id`, `status` )
VALUES ( '3', '1', 'APPROVED' );
INSERT INTO `user_role` ( `user_id`, `role_id`, `unit_id` )
VALUES ( '3', '3', 2 );
-- 创建船只类型示例
INSERT INTO `boat_types` ( `type_name`, `description`, `length`, `width`, `gross_number`, `max_load`, `max_speed`, `max_endurance`, `price`, `vendor_id`, `unit_id` )
VALUES ( '游艇', '游艇', 10, 10, 100, 1000, 100, 1000, 100, '1', '1' );
-- 创建船只
INSERT INTO `boats` ( `name`, `type_id`, `dock_id`, `vendor_id`, `unit_id` )
VALUES ( '游艇 114514', '1', '1', '1', '1' );
-- 创建普通用户
INSERT INTO `accounts` ( `username`, `password`, `phone`, `email` )
VALUES ( 'user', 'user', '12345678905', 'user@example.com' );
INSERT INTO `user_role` ( `user_id`, `role_id`, `unit_id` )
VALUES ( '4', '5', NULL );
-- 实名认证一下
INSERT INTO `user_certify` ( `user_id`, `real_name`, `id_card`, `status` )
VALUES ( '4', '张三', '12345678901', 'PENDING' );
-- 买张票
INSERT INTO `boat_requests` ( `user_id`, `start_dock_id`, `end_dock_id`, `start_time`, `end_time`, `type`, `status` )
VALUES ( '4', '1', '2', '2024-01-01 00:00:00', '2024-01-02 00:00:00', 'REAL_TIME', 'PENDING' ), ( '4', '2', '1', '2024-02-01 00:00:00', '2024-02-02 00:00:00', 'RESERVATION', 'PENDING' ), ( '4', '1', '1', '2024-03-01 00:00:00', '2024-03-02 00:00:00', 'RESERVATION', 'PENDING' );