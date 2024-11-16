package com.sakurapuare.boatmanagement.service.impl.auth.strategy.code;

import com.sakurapuare.boatmanagement.pojo.entity.User;

public interface CodeSender {

    boolean sendCode(User user);
}
