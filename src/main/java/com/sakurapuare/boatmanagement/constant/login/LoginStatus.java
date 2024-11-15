package com.sakurapuare.boatmanagement.constant.login;

import lombok.Data;

@Data
public class LoginStatus {
    private AuthMethod method; // 主登录方式
    private Username username; // 次级登录方式
    private String identifier; // 用户名、手机号、邮箱 或第三方标识

}