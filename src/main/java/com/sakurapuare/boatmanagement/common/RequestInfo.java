package com.sakurapuare.boatmanagement.common;

import lombok.Data;

@Data
public class RequestInfo {
    private String ip;
    private String userAgent;
    private String referer;
    private String url;
    private String method;
}
