-- 简单的修复脚本：为 goods 表添加 is_enabled 字段
-- 如果字段已存在，会报错但不影响数据库

ALTER TABLE `goods` 
ADD COLUMN `is_enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用' 
AFTER `unit_id`;

-- 验证表结构
DESCRIBE `goods`; 