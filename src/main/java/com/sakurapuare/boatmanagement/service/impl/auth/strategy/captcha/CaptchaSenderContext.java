package com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha;

import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.table.AccountsTableDef;
import com.sakurapuare.boatmanagement.service.CapthaService;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import org.springframework.stereotype.Component;

@Component
public class CaptchaSenderContext {

    private static final AccountsTableDef accountsTableDef = new AccountsTableDef();
    private final CapthaService capthaService;
    private final AccountsMapper accountsMapper;
    private CaptchaSender capthaSender;
    private String field = null;

    public CaptchaSenderContext(CapthaService capthaService, AccountsMapper accountsMapper) {
        this.capthaService = capthaService;
        this.accountsMapper = accountsMapper;
    }

    public void setStrategy(NameRequestDTO nameRequestDTO) {
        AuthName name = AuthNameUtils.getAuthName(nameRequestDTO.getUsername());

        switch (name) {
            case AuthName.EMAIL -> {
                this.capthaSender = new EmailCaptchaSender(capthaService);
                field = accountsTableDef.EMAIL.getName();
            }
            case AuthName.PHONE -> {
                this.capthaSender = new PhoneCaptchaSender(capthaService);
                field = accountsTableDef.PHONE.getName();
            }
            default -> throw new IllegalArgumentException("不支持的验证码发送方式");
        }
    }

    public boolean sendCaptcha(NameRequestDTO nameRequestDTO) {
        return capthaSender.sendCaptcha(nameRequestDTO);
    }

}
