package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.common.UserContext;
import com.sakurapuare.boatmanagement.exception.UnauthorizedException;
import com.sakurapuare.boatmanagement.mapper.UsersMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.service.UsersService;
import com.sakurapuare.boatmanagement.utils.JWTUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
    @Override
    public Users getUserByToken(String token) {
        Long userId = JWTUtils.getUserId(token);
        return getById(userId);
    }

    @Override
    public Users getCurrentUser() {
        Users user = UserContext.getUser();
        if (user == null) {
            throw new UnauthorizedException("用户未登录");
        }
        return user;
    }

}
