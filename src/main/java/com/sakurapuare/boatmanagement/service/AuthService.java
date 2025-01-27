package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.service.IService;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.pojo.dto.WxLoginDTO;


public interface AuthService extends IService<Users> {

    Users loginWithPassword(AuthRequestDTO authRequestDTO);

    Users loginWithCode(AuthRequestDTO authRequestDTO);

    Users registerWithPassword(AuthRequestDTO authRequestDTO);

    Users registerWithCode(AuthRequestDTO authRequestDTO);

    boolean sendCode(NameRequestDTO nameRequestDTO);

    boolean checkAvailability(NameRequestDTO nameRequestDTO);

    /**
     * 微信登录
     *
     * @param wxLoginDTO 微信登录信息
     * @return 用户信息
     */
    Users loginWithWechat(WxLoginDTO wxLoginDTO);
}
