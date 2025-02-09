package com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.TableName;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.CapthaService;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import org.springframework.stereotype.Component;

@Component
public class CaptchaSenderContext {

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
                field = TableName.USER_EMAIL;
            }
            case AuthName.PHONE -> {
                this.capthaSender = new PhoneCaptchaSender(capthaService);
                field = TableName.USER_PHONE;
            }
            default -> this.capthaSender = null;
        }
    }

    public boolean sendCaptcha(NameRequestDTO nameRequestDTO) {
        if (capthaSender == null) {
            return false;
        }

        Accounts account = accountsMapper.selectOneByQuery(
                QueryWrapper.create().eq(field, nameRequestDTO.getUsername()));

        if (account == null) {
            return false;
        }

        return capthaSender.sendCaptcha(account);
    }

}
