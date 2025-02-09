package com.sakurapuare.boatmanagement.service;

import cn.hutool.core.util.RandomUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.common.RequestContext;
import com.sakurapuare.boatmanagement.constant.CaptchaStatus;
import com.sakurapuare.boatmanagement.mapper.CaptchaLimitMapper;
import com.sakurapuare.boatmanagement.mapper.CaptchaMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Captcha;
import com.sakurapuare.boatmanagement.pojo.entity.CaptchaLimit;
import com.sakurapuare.boatmanagement.pojo.entity.table.CaptchaLimitTableDef;
import com.sakurapuare.boatmanagement.pojo.entity.table.CaptchaTableDef;
import com.sakurapuare.boatmanagement.service.base.impl.BaseCaptchaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class CapthaService extends BaseCaptchaServiceImpl {

    @Autowired
    private final CaptchaLimitMapper captchaLimitMapper;

    @Autowired
    private final CaptchaMapper captchaMapper;

    private final CaptchaLimitTableDef captchaLimitTableDef = new CaptchaLimitTableDef();

    private final CaptchaTableDef captchaTableDef = new CaptchaTableDef();

    public boolean isReachCaptchaLimit(String target) {
        CaptchaLimit captchaLimit = captchaLimitMapper.selectOneByQuery(
                QueryWrapper.create().eq(captchaLimitTableDef.TARGET.getName(), target));

        if (captchaLimit == null) {
            captchaLimit = new CaptchaLimit();
            captchaLimit.setTarget(target);
            captchaLimit.setIp(RequestContext.getContext().getIp());
            captchaLimit.setCount(1L);
            captchaLimit.setLastRequest(new Timestamp(System.currentTimeMillis()));
            captchaLimit.setIsBlocked(false);

            captchaLimitMapper.insertSelective(captchaLimit);
            return false;
        }

        // lastRequest 是否在 1 分钟内
        if (captchaLimit.getLastRequest().getTime() + 60000 > System.currentTimeMillis()) {
            return true;
        }

        // 如果发送次数超过 20 次，则锁定
        if (captchaLimit.getCount() >= 20) {
            captchaLimit.setIsBlocked(true);
            captchaLimitMapper.update(captchaLimit);
            return true;
        }

        captchaLimit.setCount(captchaLimit.getCount() + 1);
        captchaLimit.setLastRequest(new Timestamp(System.currentTimeMillis()));
        captchaLimitMapper.update(captchaLimit);

        return false;
    }

    public Captcha generateCaptcha(String target) {
        if (isReachCaptchaLimit(target)) {
            throw new RuntimeException("验证码发送次数过多，请稍后再试");
        }

        Captcha captcha = new Captcha();

        captcha.setCode(RandomUtil.randomNumbers(6));
        captcha.setStatus(CaptchaStatus.UNUSED);
        captcha.setExpireAt(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 10));
        captcha.setClientIp(RequestContext.getContext().getIp());
        captcha.setTarget(target);

        captchaMapper.insertSelective(captcha);

        return captcha;
    }

    public boolean verifyCode(String target, String code) {
        Captcha captcha = captchaMapper.selectOneByQuery(
                QueryWrapper.create().eq(captchaTableDef.TARGET.getName(), target).eq(captchaTableDef.CODE.getName(), code));

        if (captcha == null) {
            return false;
        }

        if (captcha.getExpireAt().getTime() < System.currentTimeMillis()) {
            return false;
        }

        if (Objects.equals(captcha.getStatus(), CaptchaStatus.USED)) {
            return false;
        }

        captcha.setStatus(CaptchaStatus.USED);
        captchaMapper.insertOrUpdateSelective(captcha);

        // clear captcha limit
        // captchaLimitMapper.deleteByQuery(
        //         QueryWrapper.create().eq(captchaLimitTableDef.TARGET.getName(), target));

        // clear captcha
        captchaMapper.deleteByQuery(
                QueryWrapper.create().eq(captchaTableDef.TARGET.getName(), target));

        return true;
    }
}
