package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import cn.hutool.core.util.RandomUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.CaptchaService;
import org.springframework.stereotype.Component;

@Component
public class CaptchaStrategy implements AuthStrategy {

    private final CaptchaService captchaService;

    private final AccountsMapper accountsMapper;


    public CaptchaStrategy(CaptchaService captchaService, AccountsMapper accountsMapper) {
        this.captchaService = captchaService;
        this.accountsMapper = accountsMapper;
    }

    @Override
    public Accounts auth(AuthStatus status, AuthRequestDTO authRequestDTO) {
        String username = authRequestDTO.getUsername();
        String password = authRequestDTO.getPassword();

        if (!captchaService.verifyCode(username, password)) {
            throw new IllegalArgumentException("验证码错误或已过期");
        }

        String field;

        field = AuthStrategy.getFieldName(status);

        Accounts account = accountsMapper.selectOneByQuery(
                QueryWrapper.create()
                        .eq(field, authRequestDTO.getUsername()));

        if (status.getName().equals(AuthName.PHONE)) {
            if (account == null) {
                account = Accounts.builder()
                        .username(username)
                        .phone(username)
                        .password(RandomUtil.randomString(10))
                        .isActive(true)
                        .isBlocked(false)
                        .build();

                accountsMapper.insertSelective(account);
            }
            return account;
        }

        if (account == null) {
            throw new IllegalArgumentException("账号不存在");
        }

        return account;

    }


}
