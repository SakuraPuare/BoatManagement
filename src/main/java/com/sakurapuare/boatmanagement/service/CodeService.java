package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.service.IService;
import com.sakurapuare.boatmanagement.pojo.entity.Code;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import org.springframework.stereotype.Service;

/**
 * 服务层。
 *
 * @author sakurapuare
 * @since 2024-11-1
 */
@Service
public interface CodeService extends IService<Code> {

    Code generateCode(User user);

    boolean verifyCode(User user, String password);

}
