package com.sakurapuare.boatmanagement.constant;

import java.util.Arrays;

public class UnitsTypes {
    public static final String MERCHANT = "MERCHANT";
    public static final String VENDOR = "VENDOR";

    public static final String[] TYPES = {MERCHANT, VENDOR};

    public static boolean isValidType(String type) {
        return Arrays.asList(TYPES).contains(type);
    }
}
