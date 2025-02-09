package com.sakurapuare.boatmanagement.common;


import com.sakurapuare.boatmanagement.pojo.entity.Accounts;

public class UserContext {
    private static final ThreadLocal<Accounts> accountHolder = new ThreadLocal<>();

    public static Accounts getAccount() {
        return accountHolder.get();
    }

    public static void setAccount(Accounts account) {
        accountHolder.set(account);
    }

    public static void clear() {
        accountHolder.remove();
    }
} 