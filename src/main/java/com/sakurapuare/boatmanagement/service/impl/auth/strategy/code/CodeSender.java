package com.sakurapuare.boatmanagement.service.impl.auth.strategy.code;

import com.sakurapuare.boatmanagement.pojo.entity.Users;

public interface CodeSender {

    boolean sendCode(Users user);
}
