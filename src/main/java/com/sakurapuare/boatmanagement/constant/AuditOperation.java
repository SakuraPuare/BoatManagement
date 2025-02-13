package com.sakurapuare.boatmanagement.constant;

import java.util.Arrays;

public class AuditOperation {
    public static final String APPROVE = "APPROVE";
    public static final String REJECT = "REJECT";

    public static final String AUDIT = "AUDIT";
    public static final String OPERATION = "OPERATION";
    public static final String SYSTEM = "SYSTEM";
    public static final String SECURITY = "SECURITY";

    public static final String[] AUDIT_OPERATIONS = {APPROVE, REJECT};

    public static final String[] OPERATIONS = {OPERATION, SYSTEM, SECURITY};

    public static boolean isAuditOperation(String operation) {
        return Arrays.asList(AUDIT_OPERATIONS).contains(operation);
    }

    public static boolean isOperation(String operation) {
        return Arrays.asList(OPERATIONS).contains(operation);
    }

}
