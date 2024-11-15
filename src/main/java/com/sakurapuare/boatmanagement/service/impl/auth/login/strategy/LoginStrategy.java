package com.sakurapuare.boatmanagement.service.impl.auth.login.strategy;

import com.sakurapuare.boatmanagement.pojo.dto.LoginRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;

public interface LoginStrategy {

    User login(LoginRequestDTO loginRequestDTO);
}