package com.sakurapuare.boatmanagement.constant;

enum ROLE {
    USER,
    MERCHANT,
    VENDOR,
    ADMIN,
    SUPER_ADMIN
}

public class UserRole {
    public static final int NONE = 0;
    public static final int USER = 1 << ROLE.USER.ordinal();
    public static final int MERCHANT = 1 << ROLE.MERCHANT.ordinal();
    public static final int VENDOR = 1 << ROLE.VENDOR.ordinal();
    public static final int ADMIN = 1 << ROLE.ADMIN.ordinal();
    public static final int SUPER_ADMIN = 0xFFFFFF;

    /**
     * 检查是否拥有指定角色权限
     *
     * @param role         当前角色值
     * @param requiredRole 需要检查的角色权限
     * @return 是否拥有该角色权限
     */
    public static boolean hasRole(int role, int requiredRole) {
        // 如果是超级管理员,拥有所有权限
        if (role == SUPER_ADMIN) {
            return true;
        }
        // 使用位与运算判断是否包含所有需要的权限位
        return (role & requiredRole) == requiredRole;
    }

    /**
     * 添加角色权限
     *
     * @param currentRole 当前角色值
     * @param roleToAdd   要添加的角色权限
     * @return 添加后的角色值
     */
    public static int addRole(int currentRole, int roleToAdd) {
        // 使用位或运算添加权限
        return currentRole | roleToAdd;
    }

    /**
     * 移除角色权限
     *
     * @param currentRole  当前角色值
     * @param roleToRemove 要移除的角色权限
     * @return 移除后的角色值
     */
    public static int removeRole(int currentRole, int roleToRemove) {
        // 如果是超级管理员,不能移除权限
        if (currentRole == SUPER_ADMIN) {
            return SUPER_ADMIN;
        }
        // 使用位与运算和位非运算移除权限
        return currentRole & (~roleToRemove);
    }
}