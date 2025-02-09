package com.sakurapuare.boatmanagement.service;

import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.sakurapuare.boatmanagement.utils.JWTUtils;
import cn.hutool.json.JSONObject;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.service.base.impl.BaseAccountsServiceImpl;
import org.springframework.context.annotation.Primary;

@Service
public class AccountsService extends BaseAccountsServiceImpl {

    public Accounts getAccountByToken(String token) {
        JSONObject payload = JWTUtils.parseToken(token);
        Long userId = payload.getLong("userId");
        return super.getById(userId);
    }
}
