package com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha;

import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AllCaptchaSender implements CaptchaSender {

    private final EmailCaptchaSender emailCaptchaSender;
    private final PhoneCaptchaSender phoneCaptchaSender;


    @Override
    public boolean sendCaptcha(NameRequestDTO nameRequestDTO) {
        boolean emailResult = emailCaptchaSender.sendCaptcha(nameRequestDTO);
        boolean phoneResult = phoneCaptchaSender.sendCaptcha(nameRequestDTO);

        return emailResult || phoneResult;
    }
}
