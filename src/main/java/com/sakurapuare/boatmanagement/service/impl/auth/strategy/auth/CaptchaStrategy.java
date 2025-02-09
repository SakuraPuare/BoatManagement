package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import cn.hutool.core.util.RandomUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.entity.table.AccountsTableDef;
import com.sakurapuare.boatmanagement.service.CapthaService;
import org.springframework.stereotype.Component;

@Component
public class CaptchaStrategy implements AuthStrategy {

    private final CapthaService capthaService;

    private final AccountsMapper accountsMapper;

    private final AccountsTableDef accountsTableDef = new AccountsTableDef();

    public CaptchaStrategy(CapthaService capthaService, AccountsMapper accountsMapper) {
        this.capthaService = capthaService;
        this.accountsMapper = accountsMapper;
    }

    @Override
    public Accounts auth(AuthStatus status, AuthRequestDTO authRequestDTO) {
        String username = authRequestDTO.getUsername();
        String password = authRequestDTO.getPassword();

        if (!capthaService.verifyCode(username, password)) {
            throw new IllegalArgumentException("验证码错误或已过期");
        }

        String field = null;

        switch (status.getName()) {
            case AuthName.USERNAME -> field = accountsTableDef.USERNAME.getName();
            case AuthName.PHONE -> field = accountsTableDef.PHONE.getName();
            case AuthName.EMAIL -> field = accountsTableDef.EMAIL.getName();
            default -> throw new IllegalArgumentException("不支持的验证码发送方式");
        }

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
