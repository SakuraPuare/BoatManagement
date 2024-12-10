package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.common.UserContext;
import com.sakurapuare.boatmanagement.exception.UnauthorizedException;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.service.UserService;
import com.sakurapuare.boatmanagement.utils.JWTUtils;

import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-11-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUserByToken(String token) {
        Long userId = JWTUtils.getUserId(token);
        return getById(userId);
    }

    @Override
    public User getCurrentUser() {
        User user = UserContext.getUser();
        if (user == null) {
            throw new UnauthorizedException("用户未登录");
        }
        return user;
    }

}
