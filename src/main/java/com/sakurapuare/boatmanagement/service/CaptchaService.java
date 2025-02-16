package com.sakurapuare.boatmanagement.service;

import cn.hutool.core.util.RandomUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.context.RequestContext;
import com.sakurapuare.boatmanagement.constant.CaptchaStatus;
import com.sakurapuare.boatmanagement.pojo.entity.Captcha;
import com.sakurapuare.boatmanagement.pojo.entity.CaptchaLimit;
import com.sakurapuare.boatmanagement.service.base.impl.BaseCaptchaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.CAPTCHA;
import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.CAPTCHA_LIMIT;


@Service
@RequiredArgsConstructor
public class CaptchaService extends BaseCaptchaServiceImpl {

    private final CaptchaLimitService captchaLimitService;

    public boolean isReachCaptchaLimit(String target) {
        CaptchaLimit captchaLimit = captchaLimitService.getOne(QueryWrapper.create().eq(CAPTCHA_LIMIT.TARGET.getName(), target));

        if (captchaLimit == null) {
            captchaLimit = new CaptchaLimit();
            captchaLimit.setTarget(target);
            captchaLimit.setIp(RequestContext.getContext().getIp());
            captchaLimit.setCount(1L);
            captchaLimit.setLastRequest(LocalDateTime.now());
            captchaLimit.setIsBlocked(false);

            captchaLimitService.save(captchaLimit);
            return false;
        }

        // lastRequest 是否在 1 分钟内
        if (captchaLimit.getLastRequest().isBefore(LocalDateTime.now().minusMinutes(1))) {
            return true;
        }

        // 如果发送次数超过 20 次，则锁定
        if (captchaLimit.getCount() >= 20) {
            captchaLimit.setIsBlocked(true);
            captchaLimitService.updateById(captchaLimit);
            return true;
        }

        captchaLimit.setCount(captchaLimit.getCount() + 1);
        captchaLimit.setLastRequest(LocalDateTime.now());
        captchaLimitService.updateById(captchaLimit);

        return false;
    }

    public Captcha generateCaptcha(String target) {
        if (isReachCaptchaLimit(target)) {
            throw new RuntimeException("验证码发送次数过多，请稍后再试");
        }

        Captcha captcha = new Captcha();

        captcha.setCode(RandomUtil.randomNumbers(6));
        captcha.setStatus(CaptchaStatus.UNUSED);
        captcha.setExpireAt(LocalDateTime.now().plusMinutes(10));
        captcha.setClientIp(RequestContext.getContext().getIp());
        captcha.setTarget(target);

        super.save(captcha);

        return captcha;
    }

    public boolean verifyCode(String target, String code) {
        Captcha captcha = super.getOne(QueryWrapper.create().eq(CAPTCHA.TARGET.getName(), target).eq(CAPTCHA.CODE.getName(), code));

        if (captcha == null) {
            return false;
        }

        if (captcha.getExpireAt().isBefore(LocalDateTime.now())) {
            return false;
        }

        if (Objects.equals(captcha.getStatus(), CaptchaStatus.USED)) {
            return false;
        }

        captcha.setStatus(CaptchaStatus.USED);
        super.updateById(captcha);

        // clear captcha limit
        // captchaLimitMapper.deleteByQuery(
        //         QueryWrapper.create().eq(captchaLimit.TARGET.getName(), target));

        // clear captcha
        super.remove(QueryWrapper.create().eq(CAPTCHA.TARGET.getName(), target));

        return true;
    }
}
