-- 修复 goods 表，添加缺失的 is_enabled 字段
-- 使用兼容的SQL语法

-- 检查字段是否存在的安全方法
SET @sql = (SELECT IF(
    (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS 
     WHERE TABLE_SCHEMA = DATABASE() 
     AND TABLE_NAME = 'goods' 
     AND COLUMN_NAME = 'is_enabled') = 0,
    'ALTER TABLE `goods` ADD COLUMN `is_enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT ''是否启用'' AFTER `unit_id`;',
    'SELECT ''字段 is_enabled 已存在'' AS message;'
));

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 验证表结构
DESCRIBE `goods`; 