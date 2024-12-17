package com.sakurapuare.boatmanagement.service;

import com.mybatisflex.core.service.IService;
import com.sakurapuare.boatmanagement.pojo.entity.Users;

/**
 * 用户表 服务层。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
public interface UsersService extends IService<Users> {

    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return 用户信息
     */
    Users getUserByToken(String token);

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    Users getCurrentUser();
}
