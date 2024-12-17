package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.service.IService;
import com.sakurapuare.boatmanagement.pojo.entity.Codes;
import com.sakurapuare.boatmanagement.pojo.entity.Users;

/**
 * 验证码表 服务层。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
public interface CodesService extends IService<Codes> {


    /**
     * 验证码验证。
     *
     * @param user 用户
     * @param code 验证码
     * @return 验证结果
     */
    boolean verifyCode(Users user, String code);


    /**
     * 生成验证码。
     *
     * @param user 用户
     * @return 验证码
     */
    Codes generateCode(Users user);

}
