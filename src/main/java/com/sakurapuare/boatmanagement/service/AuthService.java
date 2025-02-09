package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.service.IService;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.dto.WxLoginDTO;


public interface AuthService extends IService<Accounts> {

    Accounts loginWithPassword(AuthRequestDTO authRequestDTO);

    Accounts loginWithCode(AuthRequestDTO authRequestDTO);

    Accounts registerWithPassword(AuthRequestDTO authRequestDTO);

    Accounts registerWithCode(AuthRequestDTO authRequestDTO);

    boolean sendCode(NameRequestDTO nameRequestDTO);

    boolean checkAvailability(NameRequestDTO nameRequestDTO);

    /**
     * 微信登录
     *
     * @param wxLoginDTO 微信登录信息
     * @return 用户信息
     */
    Accounts loginWithWechat(WxLoginDTO wxLoginDTO);

    String generateToken(Accounts account);
}
