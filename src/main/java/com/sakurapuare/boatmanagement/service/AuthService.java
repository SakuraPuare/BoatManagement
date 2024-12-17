package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.service.IService;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Users;


public interface AuthService extends IService<Users> {

    Users loginWithPassword(AuthRequestDTO authRequestDTO);

    Users loginWithCode(AuthRequestDTO authRequestDTO);

    Users registerWithPassword(AuthRequestDTO authRequestDTO);

    Users registerWithCode(AuthRequestDTO authRequestDTO);

    boolean sendCode(NameRequestDTO nameRequestDTO);

    boolean checkAvailability(NameRequestDTO nameRequestDTO);
}
