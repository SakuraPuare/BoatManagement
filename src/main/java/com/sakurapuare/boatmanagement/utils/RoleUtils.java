package com.sakurapuare.boatmanagement.utils;

public class RoleUtils {

    public static boolean hasRole(int role, int requiredRole) {
        return (role & requiredRole) != 0;
    }

    public static int addRole(int role, int requiredRole) {
        return role | requiredRole;
    }

    public static int removeRole(int role, int requiredRole) {
        return role & ~requiredRole;
    }
}
