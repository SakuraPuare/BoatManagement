package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.service.IService;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.pojo.vo.UserVO;
import com.sakurapuare.boatmanagement.pojo.vo.UserSelfVO;

/**
 * 服务层。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
public interface UserService extends IService<User> {
    User getUserByToken(String token);
    User getCurrentUser();
}
