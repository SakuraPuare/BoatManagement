package com.sakurapuare.boatmanagement.service.captcha;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.AccountsService;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.sakurapuare.boatmanagement.pojo.entity.table.AccountsTableDef.ACCOUNTS;

@Component
@RequiredArgsConstructor
public class CaptchaSenderContext {

    private final AccountsService accountsService;
    private final EmailCaptchaSender emailCaptchaSender;
    private final PhoneCaptchaSender phoneCaptchaSender;
    private final AllCaptchaSender allCaptchaSender;

    private CaptchaSender capthaSender;

    public void setStrategy(NameRequestDTO nameRequestDTO) {
        AuthName name = AuthNameUtils.getAuthName(nameRequestDTO.getUsername());
        switch (name) {
            case AuthName.EMAIL -> this.capthaSender = emailCaptchaSender;
            case AuthName.PHONE -> this.capthaSender = phoneCaptchaSender;
            case AuthName.USERNAME -> {
                Accounts account = accountsService.getOne(
                        QueryWrapper.create()
                                .where(ACCOUNTS.USERNAME.eq(nameRequestDTO.getUsername())));
                if (account == null) {
                    throw new IllegalArgumentException("不支持的验证码发送方式");
                }

                if (account.getEmail() != null) {
                    this.capthaSender = emailCaptchaSender;
                } else if (account.getPhone() != null) {
                    this.capthaSender = phoneCaptchaSender;
                } else {
                    this.capthaSender = allCaptchaSender;
                }
            }
            default -> throw new IllegalArgumentException("不支持的验证码发送方式");
        }
    }

    public boolean sendCaptcha(NameRequestDTO nameRequestDTO) {
        return capthaSender.sendCaptcha(nameRequestDTO);
    }

}
