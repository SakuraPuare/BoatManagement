package com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha;

import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Captcha;
import com.sakurapuare.boatmanagement.service.CapthaService;
import org.springframework.stereotype.Component;

@Component
public class EmailCaptchaSender implements CaptchaSender {

    private final CapthaService capthaService;

    public EmailCaptchaSender(CapthaService capthaService) {
        this.capthaService = capthaService;
    }

    @Override
    public boolean sendCaptcha(NameRequestDTO nameRequestDTO) {
        // TODO: Send email

        Captcha captha = capthaService.generateCaptcha(nameRequestDTO.getUsername());
        return false;
    }

}
