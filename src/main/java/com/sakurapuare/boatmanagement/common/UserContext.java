package com.sakurapuare.boatmanagement.common;


import com.sakurapuare.boatmanagement.pojo.entity.Users;

public class UserContext {
    private static final ThreadLocal<Users> userHolder = new ThreadLocal<>();

    public static Users getUser() {
        return userHolder.get();
    }

    public static void setUser(Users users) {
        userHolder.set(users);
    }

    public static void clear() {
        userHolder.remove();
    }
} 