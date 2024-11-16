package com.sakurapuare.boatmanagement.constant.auth;

import lombok.Data;

@Data
public class AuthStatus {
    // 登录类型
    AuthType type;

    // 登录方式
    AuthMethod method;

    // 登录名
    AuthName name;

    public AuthStatus(AuthMethod method, AuthType type) {
        this.method = method;
        this.type = type;
        this.name = AuthName.NONE;
    }

}
