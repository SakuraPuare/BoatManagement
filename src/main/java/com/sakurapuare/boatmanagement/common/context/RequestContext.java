package com.sakurapuare.boatmanagement.common.context;

import com.sakurapuare.boatmanagement.common.RequestInfo;

public class RequestContext {
    private static final ThreadLocal<RequestInfo> contextHolder = new ThreadLocal<>();

    public static RequestInfo getContext() {
        return contextHolder.get();
    }

    public static void setContext(RequestInfo requestInfo) {
        contextHolder.set(requestInfo);
    }

    public static void clear() {
        contextHolder.remove();
    }
}
