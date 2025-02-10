package com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha;

import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.service.CaptchaService;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import org.springframework.stereotype.Component;

@Component
public class CaptchaSenderContext {

    private final CaptchaService captchaService;
    private CaptchaSender capthaSender;

    public CaptchaSenderContext(CaptchaService captchaService, AccountsMapper accountsMapper) {
        this.captchaService = captchaService;
    }

    public void setStrategy(NameRequestDTO nameRequestDTO) {
        AuthName name = AuthNameUtils.getAuthName(nameRequestDTO.getUsername());
        switch (name) {
            case AuthName.EMAIL -> {
                this.capthaSender = new EmailCaptchaSender(captchaService);
            }
            case AuthName.PHONE -> {
                this.capthaSender = new PhoneCaptchaSender(captchaService);
            }
            default -> throw new IllegalArgumentException("不支持的验证码发送方式");
        }
    }

    public boolean sendCaptcha(NameRequestDTO nameRequestDTO) {
        return capthaSender.sendCaptcha(nameRequestDTO);
    }

}
