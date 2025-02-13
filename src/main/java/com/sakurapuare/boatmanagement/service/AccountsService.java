package com.sakurapuare.boatmanagement.service;

import cn.hutool.json.JSONObject;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.base.impl.BaseAccountsServiceImpl;
import com.sakurapuare.boatmanagement.utils.JWTUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountsService extends BaseAccountsServiceImpl {

    public Accounts getAccountByToken(String token) {
        JSONObject payload = JWTUtils.parseToken(token);
        Long userId = payload.getLong("userId");
        return getById(userId);
    }
}
