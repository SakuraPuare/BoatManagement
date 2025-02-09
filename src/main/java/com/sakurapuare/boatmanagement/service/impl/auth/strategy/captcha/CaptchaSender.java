package com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha;

import com.sakurapuare.boatmanagement.pojo.entity.Accounts;

public interface CaptchaSender {

    boolean sendCaptcha(Accounts account);
}
