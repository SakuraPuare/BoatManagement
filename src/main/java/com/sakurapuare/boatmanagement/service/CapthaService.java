package com.sakurapuare.boatmanagement.service;

import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.entity.Captcha;
import com.sakurapuare.boatmanagement.service.base.impl.BaseCaptchaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CapthaService extends BaseCaptchaServiceImpl {


    public Captcha generateCode(Accounts account) {
        return null;
    }

    public boolean verifyCode(Accounts account, String code) {
        return false;
    }
}
