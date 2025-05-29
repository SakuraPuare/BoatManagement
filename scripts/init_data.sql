USE boatmanagement;

-- ================================
-- 1. 角色权限系统初始化
-- ================================

-- 插入角色数据
INSERT INTO `role` (`name`, `description`) VALUES 
('ADMIN', '管理员'),
('MERCHANT', '商户'),
('VENDOR', '供应商'),
('SUPER_ADMIN', '超级管理员'),
('USER', '普通用户');

-- 插入权限数据
INSERT INTO `permission` (`name`, `code`, `description`) VALUES 
('用户管理', 'USER_MANAGE', '管理用户账户'),
('商户管理', 'MERCHANT_MANAGE', '管理商户信息'),
('供应商管理', 'VENDOR_MANAGE', '管理供应商信息'),
('船只管理', 'BOAT_MANAGE', '管理船只信息'),
('码头管理', 'DOCK_MANAGE', '管理码头信息'),
('商品管理', 'GOODS_MANAGE', '管理商品信息'),
('订单管理', 'ORDER_MANAGE', '管理订单信息'),
('系统配置', 'SYSTEM_CONFIG', '系统配置管理'),
('数据统计', 'DATA_STATISTICS', '查看数据统计'),
('文件管理', 'FILE_MANAGE', '文件上传管理');

-- 角色权限关联（管理员拥有所有权限）
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES 
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10),
(4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6), (4, 7), (4, 8), (4, 9), (4, 10);

-- 商户权限
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES 
(2, 6), (2, 7), (2, 10);

-- 供应商权限
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES 
(3, 4), (3, 7), (3, 10);

-- ================================
-- 2. 基础账户数据
-- ================================

-- 创建管理员账户
INSERT INTO `accounts` (`username`, `password`, `phone`, `email`) VALUES 
('admin', '123456', '13800138000', 'admin@boatmanagement.com'),
('superadmin', '123456', '13800138001', 'superadmin@boatmanagement.com');

-- 创建商户账户
INSERT INTO `accounts` (`username`, `password`, `phone`, `email`) VALUES 
('merchant1', '123456', '13800138002', 'merchant1@example.com'),
('merchant2', '123456', '13800138003', 'merchant2@example.com');

-- 创建供应商账户
INSERT INTO `accounts` (`username`, `password`, `phone`, `email`) VALUES 
('vendor1', '123456', '13800138004', 'vendor1@example.com'),
('vendor2', '123456', '13800138005', 'vendor2@example.com');

-- 创建普通用户账户
INSERT INTO `accounts` (`username`, `password`, `phone`, `email`) VALUES 
('user1', '123456', '13800138006', 'user1@example.com'),
('user2', '123456', '13800138007', 'user2@example.com'),
('user3', '123456', '13800138008', 'user3@example.com');

-- ================================
-- 3. 单位数据
-- ================================

-- 创建商户单位
INSERT INTO `units` (`name`, `unit_name`, `social_credit_code`, `legal_person`, `address`, `contact_phone`, `status`, `admin_user_id`, `types`) VALUES 
('江南水乡特产店', '武汉江南水乡商贸有限公司', '91420100MA4K1234X1', '张三', '湖北省武汉市洪山区珞喻路123号', '027-87654321', 'APPROVED', 3, 'MERCHANT'),
('东湖渔家乐', '武汉东湖渔家乐餐饮有限公司', '91420100MA4K1234X2', '李四', '湖北省武汉市武昌区东湖路456号', '027-87654322', 'APPROVED', 4, 'MERCHANT');

-- 创建供应商单位
INSERT INTO `units` (`name`, `unit_name`, `social_credit_code`, `legal_person`, `address`, `contact_phone`, `status`, `admin_user_id`, `types`) VALUES 
('长江游船公司', '武汉长江游船服务有限公司', '91420100MA4K1234X3', '王五', '湖北省武汉市汉口区沿江大道789号', '027-87654323', 'APPROVED', 5, 'VENDOR'),
('东湖游艇俱乐部', '武汉东湖游艇俱乐部有限公司', '91420100MA4K1234X4', '赵六', '湖北省武汉市洪山区东湖新技术开发区', '027-87654324', 'APPROVED', 6, 'VENDOR');

-- ================================
-- 4. 用户角色关联
-- ================================

-- 管理员角色
INSERT INTO `user_role` (`user_id`, `role_id`, `unit_id`) VALUES 
(1, 1, NULL),  -- admin - ADMIN
(2, 4, NULL);  -- superadmin - SUPER_ADMIN

-- 商户角色
INSERT INTO `user_role` (`user_id`, `role_id`, `unit_id`) VALUES 
(3, 2, 1),     -- merchant1 - MERCHANT (江南水乡特产店)
(4, 2, 2);     -- merchant2 - MERCHANT (东湖渔家乐)

-- 供应商角色
INSERT INTO `user_role` (`user_id`, `role_id`, `unit_id`) VALUES 
(5, 3, 3),     -- vendor1 - VENDOR (长江游船公司)
(6, 3, 4);     -- vendor2 - VENDOR (东湖游艇俱乐部)

-- 普通用户角色
INSERT INTO `user_role` (`user_id`, `role_id`, `unit_id`) VALUES 
(7, 5, NULL),  -- user1 - USER
(8, 5, NULL),  -- user2 - USER
(9, 5, NULL);  -- user3 - USER

-- ================================
-- 5. 商户和供应商数据
-- ================================

-- 创建商户记录
INSERT INTO `merchants` (`user_id`, `unit_id`, `status`) VALUES 
(3, 1, 'APPROVED'),  -- merchant1
(4, 2, 'APPROVED');  -- merchant2

-- 创建供应商记录
INSERT INTO `vendors` (`user_id`, `unit_id`, `status`) VALUES 
(5, 3, 'APPROVED'),  -- vendor1
(6, 4, 'APPROVED');  -- vendor2

-- ================================
-- 6. 码头数据
-- ================================

INSERT INTO `docks` (`name`, `longitude`, `latitude`, `address`, `contact_phone`, `is_enabled`) VALUES 
('汉口江滩码头', 114.2734, 30.5951, '湖北省武汉市江岸区沿江大道', '027-82345678', 1),
('武昌江滩码头', 114.3162, 30.5434, '湖北省武汉市武昌区临江大道', '027-82345679', 1),
('东湖游船码头', 114.3896, 30.5565, '湖北省武汉市洪山区东湖路', '027-82345680', 1),
('汉阳江滩码头', 114.2734, 30.5434, '湖北省武汉市汉阳区滨江大道', '027-82345681', 1),
('黄鹤楼码头', 114.3162, 30.5434, '湖北省武汉市武昌区蛇山西坡', '027-82345682', 1);

-- ================================
-- 7. 船只类型数据
-- ================================

INSERT INTO `boat_types` (`type_name`, `description`, `length`, `width`, `gross_number`, `max_load`, `max_speed`, `max_endurance`, `price`, `vendor_id`, `unit_id`, `is_enabled`) VALUES 
('观光游船', '适合团体观光的大型游船', 25.00, 6.00, 50, 5.00, 15.00, 100.00, 200.00, 1, 3, 1),
('快艇', '高速快艇，适合短途快速出行', 8.00, 2.50, 8, 1.00, 45.00, 50.00, 150.00, 1, 3, 1),
('画舫', '古典画舫，适合休闲观光', 15.00, 4.00, 20, 2.00, 8.00, 30.00, 180.00, 1, 3, 1),
('豪华游艇', '豪华游艇，适合商务接待', 20.00, 5.00, 15, 3.00, 25.00, 80.00, 500.00, 2, 4, 1),
('电动船', '环保电动船，适合湖区游览', 10.00, 3.00, 12, 1.50, 12.00, 40.00, 120.00, 2, 4, 1);

-- ================================
-- 8. 船只数据
-- ================================

INSERT INTO `boats` (`name`, `type_id`, `dock_id`, `vendor_id`, `unit_id`, `is_enabled`) VALUES 
('长江一号', 1, 1, 1, 3, 1),
('长江二号', 1, 2, 1, 3, 1),
('飞鱼号', 2, 1, 1, 3, 1),
('如意号', 3, 3, 1, 3, 1),
('东湖明珠', 4, 3, 2, 4, 1),
('碧波仙子', 5, 3, 2, 4, 1),
('江城之星', 2, 4, 1, 3, 1),
('楚天号', 3, 5, 1, 3, 1);

-- ================================
-- 9. 商品数据
-- ================================

INSERT INTO `goods` (`name`, `description`, `price`, `unit`, `stock`, `merchant_id`, `unit_id`, `is_enabled`) VALUES 
('武昌鱼', '正宗武昌鱼，肉质鲜美', 38.00, '条', 100, 1, 1, 1),
('莲藕', '洪湖莲藕，清脆甘甜', 12.00, '斤', 200, 1, 1, 1),
('热干面', '武汉特色热干面', 8.00, '份', 500, 1, 1, 1),
('豆皮', '武汉三鲜豆皮', 15.00, '份', 300, 1, 1, 1),
('鸭脖子', '精武鸭脖，香辣可口', 25.00, '份', 150, 1, 1, 1),
('清蒸武昌鱼', '东湖渔家乐招牌菜', 68.00, '份', 50, 2, 2, 1),
('莲藕排骨汤', '营养丰富的汤品', 28.00, '份', 80, 2, 2, 1),
('油焖大虾', '新鲜河虾制作', 48.00, '份', 60, 2, 2, 1),
('农家小炒肉', '地道农家菜', 32.00, '份', 100, 2, 2, 1),
('东湖银鱼', '东湖特产银鱼', 45.00, '份', 40, 2, 2, 1);

-- ================================
-- 10. 用户实名认证数据
-- ================================

INSERT INTO `user_certify` (`user_id`, `real_name`, `id_card`, `status`) VALUES 
(7, '张小明', '420106199001011234', 'APPROVED'),
(8, '李小红', '420106199002022345', 'APPROVED'),
(9, '王小强', '420106199003033456', 'PENDING');

-- ================================
-- 11. 船只请求数据
-- ================================

INSERT INTO `boat_requests` (`user_id`, `start_dock_id`, `end_dock_id`, `start_time`, `end_time`, `type`, `status`) VALUES 
(7, 1, 2, '2024-03-15 09:00:00', '2024-03-15 11:00:00', 'RESERVATION', 'COMPLETED'),
(7, 3, 3, '2024-03-20 14:00:00', '2024-03-20 16:00:00', 'RESERVATION', 'PAID'),
(8, 1, 3, '2024-03-25 10:00:00', '2024-03-25 12:00:00', 'RESERVATION', 'PENDING'),
(8, 2, 4, '2024-03-28 15:00:00', '2024-03-28 17:00:00', 'REAL_TIME', 'PENDING'),
(9, 3, 5, '2024-04-01 09:30:00', '2024-04-01 11:30:00', 'RESERVATION', 'PENDING');

-- ================================
-- 12. 船只订单数据
-- ================================

INSERT INTO `boat_orders` (`user_id`, `request_id`, `boat_id`, `discount`, `price`, `status`) VALUES 
(7, 1, 1, 0.00, 400.00, 'COMPLETED'),
(7, 2, 5, 50.00, 950.00, 'PAID'),
(8, 3, 2, 0.00, 300.00, 'PENDING'),
(8, 4, 3, 0.00, 300.00, 'PENDING'),
(9, 5, 4, 100.00, 900.00, 'PENDING');

-- ================================
-- 13. 商品订单数据
-- ================================

INSERT INTO `goods_orders` (`user_id`, `merchant_id`, `order_info`, `discount`, `price`, `status`) VALUES 
(7, 1, '1:2,3:3,5:1', 10.00, 89.00, 'COMPLETED'),
(8, 2, '6:1,7:2,9:1', 20.00, 124.00, 'PAID'),
(9, 1, '2:5,4:2', 5.00, 85.00, 'PENDING'),
(7, 2, '8:1,10:1', 0.00, 93.00, 'PENDING');

-- ================================
-- 14. 支付记录数据
-- ================================

INSERT INTO `payments` (`order_id`, `order_type`, `user_id`, `amount`, `payment_method`, `transaction_id`, `status`, `paid_at`) VALUES 
(1, 'BOAT_ORDER', 7, 400.00, 'WECHAT', 'wx_20240315_001', 'SUCCESS', '2024-03-15 08:45:00'),
(2, 'BOAT_ORDER', 7, 950.00, 'ALIPAY', 'ali_20240320_001', 'SUCCESS', '2024-03-20 13:30:00'),
(1, 'GOODS_ORDER', 7, 89.00, 'WECHAT', 'wx_20240316_001', 'SUCCESS', '2024-03-16 10:20:00'),
(2, 'GOODS_ORDER', 8, 124.00, 'ALIPAY', 'ali_20240322_001', 'SUCCESS', '2024-03-22 16:15:00');

-- ================================
-- 15. 评价数据
-- ================================

INSERT INTO `reviews` (`user_id`, `target_type`, `target_id`, `order_id`, `rating`, `content`, `is_anonymous`) VALUES 
(7, 'BOAT', 1, 1, 5, '船只很干净，服务很好，风景优美！', 0),
(7, 'GOODS', 1, 1, 4, '武昌鱼很新鲜，味道不错', 0),
(8, 'MERCHANT', 2, 2, 5, '东湖渔家乐的菜品很棒，环境也很好', 0),
(7, 'VENDOR', 1, 1, 5, '长江游船公司服务专业，推荐！', 0);

-- ================================
-- 16. 优惠券数据
-- ================================

INSERT INTO `coupons` (`name`, `code`, `type`, `value`, `min_amount`, `max_discount`, `total_count`, `start_time`, `end_time`, `applicable_type`, `creator_id`, `is_enabled`) VALUES 
('新用户专享券', 'NEW_USER_50', 'FIXED', 50.00, 200.00, 50.00, 1000, '2024-01-01 00:00:00', '2024-12-31 23:59:59', 'ALL', 1, 1),
('船只租赁8折券', 'BOAT_80', 'PERCENTAGE', 0.20, 100.00, 100.00, 500, '2024-03-01 00:00:00', '2024-06-30 23:59:59', 'BOAT', 1, 1),
('美食9折券', 'FOOD_90', 'PERCENTAGE', 0.10, 50.00, 30.00, 800, '2024-03-01 00:00:00', '2024-05-31 23:59:59', 'GOODS', 1, 1),
('春游特惠券', 'SPRING_100', 'FIXED', 100.00, 500.00, 100.00, 200, '2024-03-01 00:00:00', '2024-04-30 23:59:59', 'ALL', 1, 1);

-- ================================
-- 17. 用户优惠券数据
-- ================================

INSERT INTO `user_coupons` (`user_id`, `coupon_id`, `order_id`, `status`, `used_at`) VALUES 
(7, 1, 1, 'USED', '2024-03-15 08:45:00'),
(7, 2, NULL, 'UNUSED', NULL),
(8, 1, NULL, 'UNUSED', NULL),
(8, 3, 2, 'USED', '2024-03-22 16:15:00'),
(9, 1, NULL, 'UNUSED', NULL),
(9, 4, NULL, 'UNUSED', NULL);

-- ================================
-- 18. 通知数据
-- ================================

INSERT INTO `notifications` (`user_id`, `title`, `content`, `type`, `business_type`, `business_id`, `is_read`) VALUES 
(7, '订单支付成功', '您的船只租赁订单已支付成功，订单号：1', 'ORDER', 'BOAT_ORDER', 1, 1),
(7, '订单完成', '您的船只租赁已完成，感谢您的使用！', 'ORDER', 'BOAT_ORDER', 1, 1),
(8, '实名认证通过', '恭喜您，实名认证已通过审核！', 'AUDIT', 'USER_CERTIFY', 2, 1),
(9, '实名认证待审核', '您的实名认证申请已提交，请耐心等待审核', 'AUDIT', 'USER_CERTIFY', 3, 0),
(7, '新优惠券到账', '您有一张新的优惠券到账，快去使用吧！', 'PROMOTION', 'COUPON', 2, 0),
(8, '春游活动开始', '春游特惠活动开始啦！多种优惠等您来享受', 'PROMOTION', NULL, NULL, 0);

-- ================================
-- 19. 系统配置数据
-- ================================

INSERT INTO `system_configs` (`config_key`, `config_value`, `config_type`, `description`, `is_public`) VALUES 
('site_name', '绿色智能船艇农文旅服务平台', 'STRING', '网站名称', 1),
('site_logo', '/images/logo.png', 'STRING', '网站Logo', 1),
('contact_phone', '400-123-4567', 'STRING', '客服电话', 1),
('contact_email', 'service@boatmanagement.com', 'STRING', '客服邮箱', 1),
('max_upload_size', '10485760', 'NUMBER', '最大上传文件大小（字节）', 0),
('session_timeout', '7200', 'NUMBER', '会话超时时间（秒）', 0),
('enable_registration', 'true', 'BOOLEAN', '是否开放用户注册', 0),
('maintenance_mode', 'false', 'BOOLEAN', '维护模式', 0),
('payment_methods', '["WECHAT", "ALIPAY", "BANK_CARD"]', 'JSON', '支持的支付方式', 0),
('business_hours', '{"start": "08:00", "end": "18:00"}', 'JSON', '营业时间', 1);

-- ================================
-- 20. 文件上传记录数据
-- ================================

INSERT INTO `file_uploads` (`original_name`, `stored_name`, `file_path`, `file_size`, `file_type`, `mime_type`, `uploader_id`, `business_type`, `business_id`) VALUES 
('avatar1.jpg', 'avatar_7_20240315.jpg', '/uploads/avatars/avatar_7_20240315.jpg', 102400, 'jpg', 'image/jpeg', 7, 'AVATAR', 7),
('goods1.jpg', 'goods_1_20240310.jpg', '/uploads/goods/goods_1_20240310.jpg', 204800, 'jpg', 'image/jpeg', 3, 'GOODS_IMAGE', 1),
('goods2.jpg', 'goods_2_20240310.jpg', '/uploads/goods/goods_2_20240310.jpg', 184320, 'jpg', 'image/jpeg', 3, 'GOODS_IMAGE', 2),
('boat1.jpg', 'boat_1_20240312.jpg', '/uploads/boats/boat_1_20240312.jpg', 256000, 'jpg', 'image/jpeg', 5, 'BOAT_IMAGE', 1),
('cert1.pdf', 'cert_9_20240318.pdf', '/uploads/certs/cert_9_20240318.pdf', 512000, 'pdf', 'application/pdf', 9, 'CERT_FILE', 3);

-- ================================
-- 21. 统计数据
-- ================================

INSERT INTO `statistics` (`stat_date`, `stat_type`, `metric_name`, `metric_value`, `unit_id`) VALUES 
('2024-03-15', 'DAILY', 'boat_orders', 2, 3),
('2024-03-15', 'DAILY', 'goods_orders', 1, 1),
('2024-03-15', 'DAILY', 'revenue', 489.00, NULL),
('2024-03-16', 'DAILY', 'boat_orders', 1, 4),
('2024-03-16', 'DAILY', 'goods_orders', 1, 2),
('2024-03-16', 'DAILY', 'revenue', 1074.00, NULL),
('2024-03-11', 'WEEKLY', 'total_users', 9, NULL),
('2024-03-11', 'WEEKLY', 'active_merchants', 2, NULL),
('2024-03-11', 'WEEKLY', 'active_vendors', 2, NULL),
('2024-03-01', 'MONTHLY', 'new_registrations', 6, NULL);

-- ================================
-- 22. 系统日志数据
-- ================================

INSERT INTO `logs` (`type`, `level`, `content`, `operator_id`) VALUES 
('OPERATION', 'INFO', '用户登录成功', 7),
('OPERATION', 'INFO', '创建船只订单', 7),
('AUDIT', 'INFO', '实名认证审核通过', 1),
('SYSTEM', 'INFO', '系统配置更新', 1),
('OPERATION', 'INFO', '商品订单支付成功', 8),
('SECURITY', 'WARNING', '多次登录失败', NULL),
('OPERATION', 'INFO', '文件上传成功', 9),
('AUDIT', 'INFO', '商户审核通过', 1);

-- 提交事务
COMMIT;

-- 显示初始化完成信息
SELECT '数据库初始化完成！' AS message;
SELECT 
    '账户总数' AS type, COUNT(*) AS count FROM accounts
UNION ALL
SELECT 
    '角色总数' AS type, COUNT(*) AS count FROM role
UNION ALL
SELECT 
    '权限总数' AS type, COUNT(*) AS count FROM permission
UNION ALL
SELECT 
    '单位总数' AS type, COUNT(*) AS count FROM units
UNION ALL
SELECT 
    '码头总数' AS type, COUNT(*) AS count FROM docks
UNION ALL
SELECT 
    '船只总数' AS type, COUNT(*) AS count FROM boats
UNION ALL
SELECT 
    '商品总数' AS type, COUNT(*) AS count FROM goods
UNION ALL
SELECT 
    '订单总数' AS type, COUNT(*) AS count FROM (
        SELECT id FROM boat_orders 
        UNION ALL 
        SELECT id FROM goods_orders
    ) AS all_orders; 