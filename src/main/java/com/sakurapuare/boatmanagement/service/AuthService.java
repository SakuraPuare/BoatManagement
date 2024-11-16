package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.service.IService;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;


public interface AuthService extends IService<User> {

    User loginWithPassword(AuthRequestDTO authRequestDTO);

    User loginWithCode(AuthRequestDTO authRequestDTO);

    User registerWithPassword(AuthRequestDTO authRequestDTO);

    User registerWithCode(AuthRequestDTO authRequestDTO);
}
