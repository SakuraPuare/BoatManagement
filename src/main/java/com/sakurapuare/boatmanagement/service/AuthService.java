package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.service.IService;
import com.sakurapuare.boatmanagement.pojo.dto.LoginRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;


public interface AuthService extends IService<User> {

    User loginWithPassword(LoginRequestDTO loginRequestDTO);

    User loginWithCode(LoginRequestDTO loginRequestDTO);
}
