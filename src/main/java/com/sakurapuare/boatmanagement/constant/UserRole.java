package com.sakurapuare.boatmanagement.constant;

public class UserRole {
    public static final int BASE_NONE = 0;
    public static final int BASE_USER = 1;
    public static final int BASE_MERCHANT = 1 << 1;
    public static final int BASE_VENDOR = 1 << 2;
    public static final int BASE_SUPER_ADMIN = 0xFFFFFF;

    public static final int USER = BASE_USER;
    public static final int MERCHANT = BASE_MERCHANT | BASE_USER;
    public static final int VENDOR = BASE_VENDOR | BASE_USER;
    public static final int ADMIN = BASE_USER | BASE_MERCHANT | BASE_VENDOR;
    public static final int SUPER_ADMIN = BASE_SUPER_ADMIN;
}
