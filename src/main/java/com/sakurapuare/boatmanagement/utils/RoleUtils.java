package com.sakurapuare.boatmanagement.utils;

public class RoleUtils {

    public static boolean hasRole(int role, int requiredRole) {
        return (role & requiredRole) != 0;
    }
}
