package com.sakurapuare.boatmanagement.service.captcha;

import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;

public interface CaptchaSender {

    boolean sendCaptcha(NameRequestDTO nameRequestDTO);
}
